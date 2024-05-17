package com.smokey.practicafct.data.retrofit

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockCircular
import co.infinum.retromock.meta.MockResponse
import co.infinum.retromock.meta.MockResponses
import com.smokey.practicafct.data.retrofit.network.Detail
import com.smokey.practicafct.data.retrofit.response.InvoicesResponse
import retrofit2.Response
import retrofit2.http.GET

//Creamos la interfaz para la petición del Retrofit

interface InvoiceRetromock{
    @Mock
    @MockResponses(
        MockResponse(body = "facturas.json"),
    )
    @GET("resources")
    suspend fun getInvoices(): Response<InvoicesResponse>

}

interface DetailsSmartSolarRetroMock {
    @Mock
    @MockResponses(
        MockResponse(body = "detallesSmartSolar.json")
    )
    @MockCircular
    @GET("resources")
    suspend fun getDetailsSmartSolarFromMock(): Response<Detail>
}

interface APIService {

    @GET("facturas")
    suspend fun getInvoices(): Response<InvoicesResponse>

}