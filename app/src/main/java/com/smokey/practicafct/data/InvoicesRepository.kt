package com.smokey.practicafct.data

import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.retrofit.response.InvoicesResponse
import com.smokey.practicafct.data.room.InvoiceDatabase
import com.smokey.practicafct.data.room.InvoiceModelRoom

class InvoicesRepository {
    val api = FacturasService()
    val invoiceDao = InvoiceDatabase.getAppDBInstance().getInvoiceDao()

suspend fun getInvoices(): List<InvoiceModelRoom>?{
    return api.getInvoices()
}


    suspend fun addInvoicesFromRoom(invoices: List<InvoiceModelRoom>){
        invoiceDao.insertInvoicesRoom(invoices)
    }
    fun getEveryInvoiceFromRoom(): List<InvoiceModelRoom>{
        return  invoiceDao.getEveryInvoicesFromRoom()
    }

//    suspend fun getInvoicesFromMock() : List<InvoicesResponse>?
//    {
//        return api.getInvoicesFromMock()
//    }
    suspend fun searchAndInsertInvoicesFromAPI(){
        val invoicesFromAPI = getInvoices() ?: emptyList()
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        addInvoicesFromRoom(invoicesRoom)
    }

}