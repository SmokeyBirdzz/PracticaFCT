package com.smokey.practicafct.data

import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.retrofit.InvoiceRetromock
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

    suspend fun getInvoicesFromMock() : List<InvoiceModelRoom>?
    {
        return api.getInvoicesFromMock()
    }
    suspend fun searchAndInsertInvoicesFromAPI(){
        // Borra el contenido anterior del Room antes de insertar nuevos datos
        invoiceDao.deleteEveryInvoicesFromRoom()

        // Obtiene los datos de la API
        val invoicesFromAPI = getInvoices() ?: emptyList()

        // Convierte los datos de la API en objetos Room y los inserta
        val invoicesRoom = invoicesFromAPI.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        addInvoicesFromRoom(invoicesRoom)
    }

    suspend fun searchAndInsertInvoicesFromRetromock(){
        invoiceDao.deleteEveryInvoicesFromRoom() // Eliminar todas las facturas existentes
        val invoicesFromRetromock = getInvoicesFromMock() ?: emptyList()
        val invoicesRoom = invoicesFromRetromock.map { invoice ->
            InvoiceModelRoom(
                descEstado = invoice.descEstado,
                importeOrdenacion = invoice.importeOrdenacion,
                fecha = invoice.fecha
            )
        }
        addInvoicesFromRoom(invoicesRoom)
    }

}