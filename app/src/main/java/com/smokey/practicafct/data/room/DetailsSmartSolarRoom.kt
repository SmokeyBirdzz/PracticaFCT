package com.smokey.practicafct.data.room

import androidx.room.Entity

@Entity(tableName = "detailsSmartSolar_table", primaryKeys = ["cau"])
class DetailsSmartSolarRoom (
    val cau: String,
    val estadoSolicitud: String,
    val tipoAutoconsumo: String,
    val compensacionExcedentes: String,
    val potenciaInstalacion: String

)