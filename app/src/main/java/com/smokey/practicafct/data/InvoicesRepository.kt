package com.smokey.practicafct.data

import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.retrofit.response.Invoices
import com.smokey.practicafct.data.room.InvoiceDAO
import com.smokey.practicafct.data.room.InvoiceDatabase
import com.smokey.practicafct.data.room.InvoiceModelRoom

class InvoicesRepository {
    val api = FacturasService()
    val invoiceDao = InvoiceDatabase.getAppDBInstance().getInvoiceDao()

suspend fun getInvoices(): List<InvoiceModelRoom>?{
    return api.getInvoices()
}


    suspend fun insertInvoicesFromRoom(invoices: List<InvoiceModelRoom>){
        invoiceDao.insertInvoicesRoom(invoices)
    }
    fun getAllInvoicesFromRoom(): List<InvoiceModelRoom>{
        return  invoiceDao.getAllInvoicesFromRoom()
    }

    suspend fun fetchAndInsertInvoicesFromAPI(){
        val invoicesFromAPI = getInvoices() ?: emptyList()
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        insertInvoicesFromRoom(invoicesRoom)
    }

}