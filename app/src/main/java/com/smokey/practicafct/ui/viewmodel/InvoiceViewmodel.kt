package com.smokey.practicafct.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smokey.practicafct.ContextApplication
import com.smokey.practicafct.ContextApplication.Companion.context
import com.smokey.practicafct.constants.Constants
import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.ui.model.adapter.Filters
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InvoiceViewmodel : ViewModel() {

    private val _filteredInvoicesLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    var useRetrofitService = false
    var useKtorService = false
    private lateinit var repository : InvoicesRepository
    private var invoices: List<InvoiceModelRoom> = emptyList()
    val filteredInvoicesLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _filteredInvoicesLiveData

    private var _maxAmount: Float = 0.0f
    var maxAmount = 0.0f
        get() = _maxAmount

    private var _filterLiveData = MutableLiveData<Filters>()
    val filterLiveData: LiveData<Filters>
        get() = _filterLiveData

    init {
        initRepository()
        searchInvoices()
    }

    fun initRepository() {
        repository = InvoicesRepository()
    }

    private fun isInternetReady(): Boolean {
        val gestorDeConectividad =
            ContextApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = gestorDeConectividad.activeNetwork
        val capabilities = gestorDeConectividad.getNetworkCapabilities(network)
        val isReady = capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
        return isReady
    }

    fun applyFilters(
        maxDate: String,
        minDate: String,
        maxValueSlider: Double,
        status: HashMap<String, Boolean>
    ) {
        val filtro = Filters(minDate, maxDate, maxValueSlider, status)
        Log.d("InvoiceViewmodel", "Aplicando filtros: $filtro")
        _filterLiveData.postValue(filtro)
        verificarFiltros()
    }

    fun verificarFiltros() {
        Log.d("InvoiceViewmodel", "Verificando filtros")
        val currentFilters = _filterLiveData.value
        if (currentFilters != null) {
            var filteredList = invoices.toList()
            filteredList = verificarDatosFiltro(filteredList, currentFilters)
            filteredList = verificarCheckBox(filteredList, currentFilters)
            filteredList = verificarBalanceBar(filteredList, currentFilters)
            Log.d("InvoiceViewmodel", "Lista filtrada: ${filteredList.size}")
            _filteredInvoicesLiveData.postValue(filteredList)
        } else {
            Log.d("InvoiceViewmodel", "No se encontraron filtros para aplicar")
        }
    }

    private fun verificarDatosFiltro(filteredList: List<InvoiceModelRoom>, filters: Filters): List<InvoiceModelRoom> {
        val maxDate = filters.maxDate
        val minDate = filters.minDate
        val filteredListResult = ArrayList<InvoiceModelRoom>()

        if (!maxDate.isNullOrEmpty() && !minDate.isNullOrEmpty()) {
            val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            var minDateLocal: Date? = null
            var maxDateLocal: Date? = null

            try {
                minDateLocal = simpleDateFormat.parse(minDate)
                maxDateLocal = simpleDateFormat.parse(maxDate)
            } catch (e: ParseException) {
                Log.d("Error", "Error al analizar las fechas: ${e.message}")
            }

            Log.d("VerificarDatosFiltro", "minDateLocal: $minDateLocal, maxDateLocal: $maxDateLocal")

            for (factura in filteredList) {
                var invoiceDate = Date()
                try {
                    invoiceDate = simpleDateFormat.parse(factura.fecha)!!
                } catch (e: ParseException) {
                    Log.d("Error", "Error al analizar la fecha de la factura: ${e.message}")
                }

                Log.d("VerificarDatosFiltro", "invoiceDate: $invoiceDate")

                if (invoiceDate.after(minDateLocal) && invoiceDate.before(maxDateLocal)) {
                    filteredListResult.add(factura)
                }
            }
        }

        return filteredListResult
    }

    private fun verificarCheckBox(filteredInvoices: List<InvoiceModelRoom>, filters: Filters): List<InvoiceModelRoom> {
        val filteredInvoicesCheckBox = ArrayList<InvoiceModelRoom>()
        val status = filters.status
        //Se obtienen los estados de las CheckBoxes.
        val checkBoxPaid = status[Constants.PAID_STRING] ?: false
        val checkBoxCanceled = status[Constants.CANCELED_STRING] ?: false
        val checkBoxFixedPayment = status[Constants.FIXED_PAYMENT_STRING] ?: false
        val checkBoxPendingPayment = status[Constants.PENDING_PAYMENT_STRING] ?: false
        val checkBoxPaymentPlan = status[Constants.PAYMENT_PLAN_STRING] ?: false

        Log.d("VerificarCheckBox", "checkBoxPaid=$checkBoxPaid, checkBoxCanceled=$checkBoxCanceled, checkBoxFixedPayment=$checkBoxFixedPayment, checkBoxPendingPayment=$checkBoxPendingPayment, checkBoxPaymentPlan=$checkBoxPaymentPlan")

        if (checkBoxPaid || checkBoxCanceled || checkBoxFixedPayment || checkBoxPendingPayment || checkBoxPaymentPlan) {
            for (invoice in filteredInvoices) {
                val invoiceState = invoice.descEstado
                val isPaid = invoiceState == "Pagada"
                val isCanceled = invoiceState == "Anuladas"
                val isFixedPayment = invoiceState == "Cuota fija"
                val isPendingPayment = invoiceState == "Pendiente de pago"
                val isPaymentPlan = invoiceState == "planPago"

                Log.d("VerificarCheckBox", "invoiceState=$invoiceState, isPaid=$isPaid, isCanceled=$isCanceled, isFixedPayment=$isFixedPayment, isPendingPayment=$isPendingPayment, isPaymentPlan=$isPaymentPlan")

                if ((isPaid && checkBoxPaid) || (isCanceled && checkBoxCanceled) || (isFixedPayment && checkBoxFixedPayment) || (isPendingPayment && checkBoxPendingPayment) || (isPaymentPlan && checkBoxPaymentPlan)) {
                    filteredInvoicesCheckBox.add(invoice)
                }
            }
            return filteredInvoicesCheckBox
        } else {
            return filteredInvoices
        }
    }

    private fun verificarBalanceBar(filteredList: List<InvoiceModelRoom>, filters: Filters): List<InvoiceModelRoom> {
        val filteredInvoicesBalanceBar = ArrayList<InvoiceModelRoom>()
        val maxValueSlider = filters.maxValueSlider

        Log.d("VerificarBalanceBar", "maxValueSlider=$maxValueSlider")

        if (maxValueSlider > 0) {  // Ignorar el filtro si maxValueSlider es 0
            for (factura in filteredList) {
                Log.d("VerificarBalanceBar", "factura.importeOrdenacion=${factura.importeOrdenacion}")
                if (factura.importeOrdenacion < maxValueSlider) {
                    filteredInvoicesBalanceBar.add(factura)
                }
            }
            return filteredInvoicesBalanceBar
        }
        return filteredList
    }

    fun searchInvoices() {
        viewModelScope.launch {
            invoices = repository.getEveryInvoiceFromRoom()
            _filteredInvoicesLiveData.postValue(invoices)
            try {
                if (isInternetReady()) {
                    when {
                        useRetrofitService -> {
                            repository.searchAndInsertInvoicesFromAPI()
                            Log.d("Retrofit", "Usando Retrofit")
                            Toast.makeText(context, "Cargado desde Retrofit", Toast.LENGTH_SHORT).show()
                        }
                        useKtorService -> {
                            repository.searchAndInsertInvoicesFromKtor()
                            Log.d("Ktor", "Usando Ktor")
                            Toast.makeText(context, "Cargado desde Ktor", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            repository.searchAndInsertInvoicesFromRetromock()
                            Log.d("Retromock", "Usando Retromock")
                            Toast.makeText(context, "Cargado desde Retromock", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Si no hay conexión a Internet, usar Retromock
                    repository.searchAndInsertInvoicesFromRetromock()
                    Log.d("Retromock", "Usando Retromock")
                }
                invoices = repository.getEveryInvoiceFromRoom()
                _filteredInvoicesLiveData.postValue(invoices)
                verificarFiltros()
            } catch (e: Exception) {
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }
}