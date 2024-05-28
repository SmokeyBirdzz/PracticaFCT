package com.smokey.practicafct.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smokey.practicafct.ui.model.adapter.Filters

class SharedViewModel : ViewModel() {
    private val _filters = MutableLiveData<Filters>()
    val filters: LiveData<Filters> get() = _filters

    private val _minDate = MutableLiveData<String>()
    val minDate: LiveData<String> get() = _minDate

    private val _maxDate = MutableLiveData<String>()
    val maxDate: LiveData<String> get() = _maxDate

    private val _maxValueSlider = MutableLiveData<Double>()
    val maxValueSlider: LiveData<Double> get() = _maxValueSlider

    private val _status = MutableLiveData<HashMap<String, Boolean>>()
    val status: LiveData<HashMap<String, Boolean>> get() = _status

    fun setFilters(filters: Filters) {
        Log.d("SharedViewModel", "Set Filters: $filters")
        _filters.value = filters
        _minDate.value = filters.minDate
        _maxDate.value = filters.maxDate
        _maxValueSlider.value = filters.maxValueSlider
        _status.value = filters.status
    }
}