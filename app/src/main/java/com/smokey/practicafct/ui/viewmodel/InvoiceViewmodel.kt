package com.smokey.practicafct.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smokey.practicafct.MyApplication
import com.smokey.practicafct.R
import com.smokey.practicafct.constants.Constants
import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.ui.model.adapter.FilterVO
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class InvoiceViewmodel : ViewModel() {

    private lateinit var repository: InvoicesRepository
    private val _filteredInvoicesLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    var useRetrofitService = false
    private var invoices: List<InvoiceModelRoom> = emptyList()
    val filteredInvoicesLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _filteredInvoicesLiveData

    private var _maxAmount: Float = 0.0f
    var maxAmount = 0.0f
        get() = _maxAmount

    private var _filterLiveData = MutableLiveData<FilterVO>()
    val filterLiveData: LiveData<FilterVO>
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
            MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
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
        if ((minDate == getString(
                MyApplication.context,
                R.string.diaMesAnno
            ) && maxDate == getString(MyApplication.context, R.string.diaMesAnno)) ||
            (minDate != getString(
                MyApplication.context,
                R.string.diaMesAnno
            ) && maxDate != getString(MyApplication.context, R.string.diaMesAnno))
        ) {
            val filtro = FilterVO(maxDate, minDate, maxValueSlider, status)
            _filterLiveData.postValue(filtro)

            // Aplicar los filtros y actualizar el LiveData con la lista filtrada
            _filteredInvoicesLiveData.postValue(verificarFiltros())
        } else {
            // Si alguna de las dos fechas está vacía, muestra un mensaje de error o realiza alguna acción apropiada.
        }
    }

    fun verificarFiltros(): List<InvoiceModelRoom>{
        var filteredList = invoices.toList()
        filteredList = verificarDatosFiltro(filteredList)
        filteredList = verificarCheckBox(filteredList)
        filteredList = verificarBalanceBar(filteredList)

        return filteredList

    }

    private fun verificarDatosFiltro(filteredList: List<InvoiceModelRoom>): List<InvoiceModelRoom> {
        val maxDate = filterLiveData.value?.maxDate
        val minDate = filterLiveData.value?.minDate
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

            for (factura in filteredList) {
                var invoiceDate = Date()
                try {
                    invoiceDate = simpleDateFormat.parse(factura.fecha)!!
                } catch (e: ParseException) {
                    Log.d("Error", "Error al analizar la fecha de la factura: ${e.message}")
                }

                if (invoiceDate.after(minDateLocal) && invoiceDate.before(maxDateLocal)) {
                    filteredListResult.add(factura)
                }
            }
        }

        return filteredListResult
    }

    private fun verificarCheckBox(
        filteredInvoices: List<InvoiceModelRoom>?
    ): List<InvoiceModelRoom>{
        var filteredInvoicesCheckBox = ArrayList<InvoiceModelRoom>()
        val status = filterLiveData.value?.status
        //Se obtienen los estados de las CheckBoxes.
        val checkBoxPaid = status?.get(Constants.PAID_STRING) ?: false
        val checkBoxCanceled = status?.get(Constants.CANCELED_STRING) ?: false
        val checkBoxFixedPayment = status?.get(Constants.FIXED_PAYMENT_STRING) ?: false
        val checkBoxPendingPayment = status?.get(Constants.PENDING_PAYMENT_STRING) ?: false
        val checkBoxPaymentPlan = status?.get(Constants.PAYMENT_PLAN_STRING) ?: false

        if (checkBoxPaid || checkBoxCanceled || checkBoxFixedPayment || checkBoxPendingPayment || checkBoxPaymentPlan) {
            for (invoice in filteredInvoices ?: emptyList()) {
                val invoiceState = invoice.descEstado
                val isPaid = invoiceState == "Pagada"
                val isCanceled = invoiceState == "Anuladas"
                val isFixedPayment = invoiceState == "Cuota fija"
                val isPendingPayment = invoiceState == "Pendiente de pago"
                val isPaymentPlan = invoiceState == "planPago"

                if ((isPaid && checkBoxPaid) || (isCanceled && checkBoxCanceled) || (isFixedPayment && checkBoxFixedPayment) || (isPendingPayment && checkBoxPendingPayment) || (isPaymentPlan && checkBoxPaymentPlan)) {
                    filteredInvoicesCheckBox.add(invoice)
                }
            }
            return filteredInvoicesCheckBox

    } else {
        return filteredInvoices?: emptyList()

        }
    }

    private fun verificarBalanceBar(filteredList: List<InvoiceModelRoom>):List<InvoiceModelRoom>{
        var filteredInvoicesBalanceBar = ArrayList<InvoiceModelRoom>()
        val maxValueSlider = filterLiveData.value?.maxValorSlider
        for (factura in filteredList){
            if (factura.importeOrdenacion!! < maxValueSlider!!){
                filteredInvoicesBalanceBar.add(factura)
            }
        }
        return filteredInvoicesBalanceBar
    }

    fun searchInvoices() {
        viewModelScope.launch {
            _filteredInvoicesLiveData.postValue(repository.getEveryInvoiceFromRoom())
            try {
                if (isInternetReady()) {
                    if (useRetrofitService) {
                        // Si hay conexión a Internet, usar Retrofit
                        repository.searchAndInsertInvoicesFromRetromock()
                        Log.d("Retromock", "Usando Retromock")

                    } else {
                        repository.searchAndInsertInvoicesFromAPI()
                        Log.d("Retrofit", "Usando Retrofit")
                    }
                } else {
                    // Si no hay conexión a Internet, usar Retromock
                    repository.searchAndInsertInvoicesFromRetromock()
                    Log.d("Retromock", "Usando Retromock")
                }
                _filteredInvoicesLiveData.postValue(repository.getEveryInvoiceFromRoom())

            } catch (e: Exception) {
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }

}