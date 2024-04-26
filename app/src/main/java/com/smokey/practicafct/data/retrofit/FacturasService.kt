package com.smokey.practicafct.data.retrofit

import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.response.Invoices

class FacturasService {

    private val retrofitBuilder = RetrofitHelper.getRetrofit()

     suspend fun getInvoices() : List<Invoices>?{
            val response = retrofitBuilder.create(APIService::class.java).getInvoices()
            val facturas = response.body()?.facturas ?: emptyList()
            return facturas
    }


}