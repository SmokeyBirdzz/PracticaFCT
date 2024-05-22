package com.smokey.practicafct.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smokey.practicafct.ui.model.adapter.Filters

class SharedViewModel : ViewModel() {
    private val _filters = MutableLiveData<Filters>()
    val filters: LiveData<Filters> get() = _filters

    fun setFilters(filters: Filters) {
        Log.d("SharedViewModel", "Set Filters: $filters")
        _filters.value = filters
    }
}