package com.smokey.practicafct.core.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid4.wiremockapi.cloud/facturas/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}