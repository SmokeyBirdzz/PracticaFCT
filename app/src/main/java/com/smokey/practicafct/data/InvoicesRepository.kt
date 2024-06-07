package com.smokey.practicafct.data

import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.retrofit.network.Detail
import com.smokey.practicafct.data.room.DetailsSmartSolarRoom
import com.smokey.practicafct.data.room.InvoiceDatabase
import com.smokey.practicafct.data.room.InvoiceModelRoom

class InvoicesRepository {
    val api = FacturasService()
    val invoiceDao = InvoiceDatabase.getAppDBInstance().getInvoiceDao()
    val detailsSmartSolarDAO = InvoiceDatabase.getAppDBInstance().getDetailsSmartSolarDAO()

    suspend fun getDetailsSmartSolarFromRetromMock(): Detail? {
        return api.getDetailsSmartSolarFromRetromock()
    }

    suspend fun insertDetailsSmartSolarInRoom(detailsSmartSolarRoom: DetailsSmartSolarRoom){
        detailsSmartSolarDAO.insertDetailsInRoom(detailsSmartSolarRoom)
    }

    fun getDetailsSmartSolarFromRoom(): DetailsSmartSolarRoom{
        return detailsSmartSolarDAO.getDetailsFromRoom()
    }

    suspend fun fetchAndInsertDetailsSmartSolarFromMock(){
        val energyDetail = getDetailsSmartSolarFromRetromMock()
        val energyDetailRoom = energyDetail?.asDetailsSmartSolarModelRoom()
        if (energyDetailRoom != null){
            insertDetailsSmartSolarInRoom(energyDetailRoom)
        }
    }

    suspend fun getInvoices(): List<InvoiceModelRoom>? {
        return api.getInvoices()
    }


    suspend fun addInvoicesFromRoom(invoices: List<InvoiceModelRoom>) {
        invoiceDao.insertInvoicesRoom(invoices)
    }

    fun getEveryInvoiceFromRoom(): List<InvoiceModelRoom> {
        return invoiceDao.getEveryInvoicesFromRoom()
    }

    suspend fun getInvoicesFromMock(): List<InvoiceModelRoom>? {
        return api.getInvoicesFromMock()
    }

    suspend fun searchAndInsertInvoicesFromAPI() {
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

    suspend fun searchAndInsertInvoicesFromRetromock() {
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