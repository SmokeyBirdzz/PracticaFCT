package com.smokey.practicafct.core.retromock
import co.infinum.retromock.Retromock
import retrofit2.Retrofit

object RetroMockHelper {
    fun getRetromock(retrofit: Retrofit): Retromock{
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory())
            .build()
    }
}