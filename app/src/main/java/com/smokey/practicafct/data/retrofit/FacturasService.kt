package com.smokey.practicafct.data.retrofit

import android.util.Log
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.core.retromock.RetroMockHelper
import com.smokey.practicafct.data.retrofit.network.Detail
import com.smokey.practicafct.data.room.InvoiceModelRoom

class FacturasService {

    private val retrofitBuilder = RetrofitHelper.getRetrofit()
    private val retromock = RetroMockHelper.getRetromock(retrofitBuilder)

     suspend fun getInvoices() : List<InvoiceModelRoom>?{
         try {
             val response = retrofitBuilder.create(APIService::class.java).getInvoices()
             if (response.isSuccessful) {
                 val facturas = response.body()?.facturas ?: emptyList()
                 return facturas
             } else {
                 return null
             }
         } catch (e: Exception) {
             return null
         }
    }

    suspend fun getInvoicesFromMock(): List<InvoiceModelRoom>? {
        try {
            val response = retromock.create(InvoiceRetromock::class.java).getInvoices()
            if (response.isSuccessful) {
                val invoices = response.body()?.facturas
                if (invoices.isNullOrEmpty()) {
                    return emptyList()
                } else {
                    return invoices
                }
            } else {
                return null
            }
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun getDetailsSmartSolarFromRetromock(): Detail?{
        val response = retromock.create(DetailsSmartSolarRetroMock::class.java).getDetailsSmartSolarFromMock()
        if (response.isSuccessful && response.body() != null) {
            val detailsData = response.body()
            return detailsData
        } else{
            Log.d("DETAILS_TAB", "Ha fallado algo")
            return null
        }
    }

}