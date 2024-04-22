package com.smokey.practicafct.data.retrofit

import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.response.Facturas

class FacturasService {

    private val retrofitBuilder = RetrofitHelper.getRetrofit()

     suspend fun searchFacturas() : List<Facturas>?{
            val response = retrofitBuilder.create(APIService::class.java).searchFacturas()
            val facturas = response.body()?.facturas ?: emptyList()
            return facturas
    }


}