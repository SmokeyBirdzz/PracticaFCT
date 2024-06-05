package com.smokey.practicafct

import com.smokey.practicafct.data.InvoicesRepository
import com.smokey.practicafct.data.retrofit.FacturasService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFacturasService(): FacturasService {
        return FacturasService()
    }

    @Provides
    @Singleton
    fun provideInvoicesRepository(service: FacturasService): InvoicesRepository {
        return InvoicesRepository(service)
    }
}