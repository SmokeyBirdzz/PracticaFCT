package com.smokey.practicafct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.room.DetailsSmartSolarRoom
import kotlinx.coroutines.launch

class DetailsSmartSolarViewmodel: ViewModel() {

    private lateinit var appRepository: InvoicesRepository
    private val _energyDataLiveData = MutableLiveData<DetailsSmartSolarRoom>()
    val energyDataLiveData: LiveData<DetailsSmartSolarRoom>
        get() = _energyDataLiveData

    init {
        initRepository()
        fetchDetailsSmartSolarData()
    }

    private fun initRepository(){
        appRepository = InvoicesRepository()
    }

    fun fetchDetailsSmartSolarData(){
        viewModelScope.launch {
            appRepository.fetchAndInsertDetailsSmartSolarFromMock()
            _energyDataLiveData.postValue(appRepository.getDetailsSmartSolarFromRoom())
        }
    }

}