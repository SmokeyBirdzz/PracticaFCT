package com.smokey.practicafct

import com.google.gson.annotations.SerializedName

data class Facturas (
    val fecha: String,
    @SerializedName ("descEstado") val pendiente: String,
   @SerializedName("importeOrdenacion") val dinero: Double,
)