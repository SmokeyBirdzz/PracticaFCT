package com.smokey.practicafct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smokey.practicafct.ui.model.adapter.Filters

class SharedViewModel: ViewModel() {
    private val _filters = MutableLiveData<Filters>()
    val filters: LiveData<Filters> get() = _filters
    fun setFilters(
        minDate: String,
        maxDate: String,
        maxValueSlider: Double,
        status: Map<String, Boolean>
    ){
        _filters.value = Filters(minDate, maxDate, maxValueSlider, status)
    }
}