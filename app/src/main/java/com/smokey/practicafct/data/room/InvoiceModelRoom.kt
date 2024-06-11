package com.smokey.practicafct.data.room

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "invoice_table", primaryKeys = ["importeOrdenacion","fecha"])
class InvoiceModelRoom (
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String
)