package com.smokey.practicafct.data.retrofit

import com.smokey.practicafct.data.retrofit.response.InvoicesResponse
import retrofit2.Response
import retrofit2.http.GET

//Creamos la interfaz para la petición del Retrofit
interface APIService {

    @GET("facturas")
    suspend fun getInvoices(): Response<InvoicesResponse>

}