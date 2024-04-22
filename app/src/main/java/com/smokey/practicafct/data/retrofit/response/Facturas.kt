package com.smokey.practicafct.data.retrofit.response

import com.google.gson.annotations.SerializedName

//Creamos la data class que estará contenida dentro de la Lista en la que guardamos la información
//de la API, la cual luego pasaremos al RecyclerView
data class Facturas (
    val fecha: String,
    @SerializedName ("descEstado") val pendiente: String,
   @SerializedName("importeOrdenacion") val dinero: Double,
)