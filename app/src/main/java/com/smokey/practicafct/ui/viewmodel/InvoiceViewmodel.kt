package com.smokey.practicafct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.retrofit.response.Invoices
import kotlinx.coroutines.launch

class InvoiceViewmodel: ViewModel() {

    private lateinit var repository : InvoicesRepository
    private val _filteredInvoicesLiveData = MutableLiveData<List<Invoices>>()
    val filteredInvoicesLiveData: LiveData<List<Invoices>>
        get() = _filteredInvoicesLiveData
    init {
        initRepository()
        fetchInvoices()
    }
    fun initRepository(){
        repository = InvoicesRepository()
    }

    fun fetchInvoices(){
        viewModelScope.launch {
            _filteredInvoicesLiveData.postValue(repository.fetchInvoices())
        }
    }

}