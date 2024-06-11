package com.smokey.practicafct.data.retrofit.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

//Creamos la data class que estará contenida dentro de la Lista en la que guardamos la información
//de la API, la cual luego pasaremos al RecyclerView
@Serializable
data class Invoices (
    val fecha: String,
    @SerializedName ("descEstado") val pendiente: String,
   @SerializedName("importeOrdenacion") val dinero: Double,
)