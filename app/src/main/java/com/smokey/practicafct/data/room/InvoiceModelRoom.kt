package com.smokey.practicafct.data.room

import androidx.room.Entity
import javax.inject.Inject

@Entity(tableName = "invoice_table", primaryKeys = ["importeOrdenacion","fecha"])
class InvoiceModelRoom (
    val descEstado: String,
    val importeOrdenacion: Double,
    val fecha: String
)