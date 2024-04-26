package com.smokey.practicafct.data

import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.retrofit.response.Invoices

class InvoicesRepository {
    val api = FacturasService()

suspend fun getInvoices(): List<Invoices>?{
    return api.getInvoices()
}

    suspend fun fetchInvoices(): List<Invoices>? {
        return api.getInvoices() ?: emptyList()
    }

}