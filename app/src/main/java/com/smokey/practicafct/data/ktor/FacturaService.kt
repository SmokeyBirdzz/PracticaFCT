package com.smokey.practicafct.data.ktor

import com.smokey.practicafct.core.network.KtorClient
import com.smokey.practicafct.data.retrofit.response.Invoices
import com.smokey.practicafct.data.room.InvoiceModelRoom
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class FacturaService {
    private val client = KtorClient.client
    private val baseUrl = "https://viewnextandroid2.wiremockapi.cloud"

    suspend fun getFacturas(): List<InvoiceModelRoom> {
        val response: HttpResponse = client.get("$baseUrl/facturas")
        return response.body()
    }
}