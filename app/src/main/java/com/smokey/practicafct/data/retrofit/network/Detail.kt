package com.smokey.practicafct.data.retrofit.network

import com.smokey.practicafct.data.room.DetailsSmartSolarRoom

data class Detail (
    val cau: String,
    val estadoSolicitud: String,
    val tipoAutoconsumo: String,
    val compensacionExcedentes: String,
    val potenciaInstalacion: String
){
    fun asDetailsSmartSolarModelRoom(): DetailsSmartSolarRoom {
        return DetailsSmartSolarRoom(
            cau,
            estadoSolicitud,
            tipoAutoconsumo,
            compensacionExcedentes,
            potenciaInstalacion
        )
    }
}