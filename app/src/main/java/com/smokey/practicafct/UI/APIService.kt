package com.smokey.practicafct.UI

import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("/facturas")
    suspend fun getFacturas(): Response<FacturasResponse>

}