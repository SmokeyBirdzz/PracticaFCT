package com.smokey.practicafct.data.retrofit

import android.util.Log
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.core.retromock.RetroMockHelper
import com.smokey.practicafct.data.retrofit.response.Invoices
import com.smokey.practicafct.data.retrofit.response.InvoicesResponse
import com.smokey.practicafct.data.room.InvoiceModelRoom

class FacturasService {

    private val retrofitBuilder = RetrofitHelper.getRetrofit()
    private val retromock = RetroMockHelper.getRetromock(retrofitBuilder)

     suspend fun getInvoices() : List<InvoiceModelRoom>?{
            val response = retrofitBuilder.create(APIService::class.java).getInvoices()
            val facturas = response.body()?.facturas ?: emptyList()
            return facturas
    }

//    suspend fun getInvoicesFromMock(): List<InvoicesResponse>? {
//        val response = retromock.create(InvoiceRetromock::class.java).getInvoices()
//        if (response.isSuccessful){
//            val invoices =response.body()?.facturas
//            if (invoices.isNullOrEmpty()){
//                return emptyList()
//            }else{
//                return invoices
//            }
//        }else {
//            Log.d("Fallo", response.toString())
//            return null
//        }
//    }

}