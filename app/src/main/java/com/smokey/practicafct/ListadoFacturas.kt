package com.smokey.practicafct

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.UI.APIService
import com.smokey.practicafct.adapter.FacturasAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListadoFacturas : AppCompatActivity() {

    private lateinit var facturasAdapter: FacturasAdapter
    companion object {
        const val BASE_URL = "https://viewnextandroid4.wiremockapi.cloud/facturas/"}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_facturas)
        val rvFacturas = findViewById<RecyclerView>(R.id.rvFacturas)
        initRecyclerView()
        facturasAdapter = FacturasAdapter(FacturasProvider.facturasList)
        rvFacturas.adapter = facturasAdapter
        searchFacturas()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rvFacturas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FacturasAdapter(FacturasProvider.facturasList)

    }

    private fun getRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchFacturas(){
        CoroutineScope(Dispatchers.IO).launch {
            val retrofit = getRetrofit()
            val service = retrofit.create(APIService::class.java)
            val response = service.getFacturas()
            if (response.isSuccessful){
                val facturasResponse = response.body()
                val facturas = facturasResponse?.facturas ?: emptyList()
                runOnUiThread {
                    facturasAdapter.updateFacturas(facturas)
                }
            }

        }
    }

}