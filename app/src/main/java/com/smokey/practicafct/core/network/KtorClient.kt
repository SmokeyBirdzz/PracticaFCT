package com.smokey.practicafct.core.network

import com.smokey.practicafct.data.retrofit.response.Invoices
import com.smokey.practicafct.data.retrofit.response.InvoicesResponse
import com.smokey.practicafct.data.room.InvoiceModelRoom
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*

import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    suspend fun getInvoices(): List<InvoiceModelRoom> {
        val response = client.get("https://viewnextandroid2.wiremockapi.cloud/facturas")
        if (response.status == HttpStatusCode.OK){
            val invoicesResponse: InvoicesResponse = response.body()
            return invoicesResponse.facturas.map { invoice ->
                InvoiceModelRoom(
                    descEstado = invoice.descEstado,
                    importeOrdenacion = invoice.importeOrdenacion,
                    fecha = invoice.fecha
                )
            }
        }else{
            throw Exception("Fallo HTTP : ${response.status.value}")
        }
    }
}