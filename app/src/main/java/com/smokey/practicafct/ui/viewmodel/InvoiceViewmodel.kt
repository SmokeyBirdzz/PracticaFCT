package com.smokey.practicafct.ui.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smokey.practicafct.MyApplication
import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.room.InvoiceModelRoom
import kotlinx.coroutines.launch
import java.lang.Exception

class InvoiceViewmodel: ViewModel() {

    private var useApi = false
    private var invoices : List<InvoiceModelRoom> = emptyList()
    private lateinit var repository : InvoicesRepository
    private val _filteredInvoicesLiveData = MutableLiveData<List<InvoiceModelRoom>>()
    val filteredInvoicesLiveData: LiveData<List<InvoiceModelRoom>>
        get() = _filteredInvoicesLiveData


    init {
        initRepository()
        searchInvoices()
    }
    fun initRepository(){
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

    fun searchInvoices(){
        viewModelScope.launch {
            _filteredInvoicesLiveData.postValue(repository.getEveryInvoiceFromRoom())
            try {
                if (isInternetReady()) {
                    // Si hay conexión a Internet, usar Retrofit
                    repository.searchAndInsertInvoicesFromAPI()
                } else {
                    // Si no hay conexión a Internet, usar Retromock
                    repository.searchAndInsertInvoicesFromRetromock()
                }
                _filteredInvoicesLiveData.postValue(repository.getEveryInvoiceFromRoom())

            }catch (e: Exception){
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }

}