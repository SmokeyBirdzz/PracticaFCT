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
import com.smokey.practicafct.data.retrofit.response.Invoices
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
        fetchInvoices()
    }
    fun initRepository(){
        repository = InvoicesRepository()
    }

    private fun isInternetAvailable(): Boolean {
        val gestorDeConectividad =
            MyApplication.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = gestorDeConectividad.activeNetwork
            val capabilities = gestorDeConectividad.getNetworkCapabilities(network)

        return capabilities != null && (
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                )
    }

    fun fetchInvoices(){
        viewModelScope.launch {
            _filteredInvoicesLiveData.postValue(repository.getAllInvoicesFromRoom())
            try {
                if (isInternetAvailable()){
                    when(useApi){
                        true -> repository.fetchAndInsertInvoicesFromAPI()
                        //Por poner algo mientras
                        false -> repository.fetchAndInsertInvoicesFromAPI()
                    }
                    invoices = repository.getAllInvoicesFromRoom()
                    _filteredInvoicesLiveData.postValue(invoices)
                }
            }catch (e: Exception){
                Log.d("Error", e.printStackTrace().toString())
            }
        }
    }

}