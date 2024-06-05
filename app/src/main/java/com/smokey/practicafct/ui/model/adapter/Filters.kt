package com.smokey.practicafct.ui.model.adapter

import javax.inject.Inject

data class Filters (
    val minDate: String,
    val maxDate: String,
    val maxValueSlider: Double,
    val status: HashMap<String, Boolean>
) {
    override fun toString(): String {
        return "Filters(minDate='$minDate', maxDate='$maxDate', maxValueSlider=$maxValueSlider, status=$status)"
    }
}

