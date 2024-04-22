package com.smokey.practicafct.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.data.retrofit.response.Facturas
import com.smokey.practicafct.FacturasProvider
import com.smokey.practicafct.R
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.APIService
import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListadoFacturas : AppCompatActivity() {
    //Declaración del adaptador
    private lateinit var facturasAdapter: FacturasAdapter
    private lateinit var rvFacturas: RecyclerView
    private lateinit var servicioFacturas : FacturasService
    private var retrofit = RetrofitHelper.getRetrofit()
    //Creamos un companion object que contenga la URL de la API para RetroFit
    companion object {
        const val BASE_URL = "https://viewnextandroid4.wiremockapi.cloud/facturas/"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_facturas)
        initRecyclerView()
        initAdapter(FacturasProvider.facturasList)

        //Iniciamos el método que va a hacer la llamada a la API y sustituir el contenido de la
        // lista vacía por el contenido del Json

        searchFacturas()


    }

    private fun initRecyclerView() {
        //Inicializamos el RecyclerView
        rvFacturas = findViewById<RecyclerView>(R.id.rvFacturas)
        rvFacturas.layoutManager = LinearLayoutManager(this)
    }

    private fun initAdapter(facturasList: List<Facturas>) {
        //Inicializamos el adaptador del RecyclerView
        facturasAdapter = FacturasAdapter(FacturasProvider.facturasList)
        rvFacturas.adapter = facturasAdapter
    }

    private fun updateAdapter(facturasList: List<Facturas>) {
        Log.d("VIEWNEXT ListadoFacturas", facturasList.size.toString())
        facturasAdapter.updateFacturas(facturasList)
    }

    //Creamos la conexión mediante Retrofit a la URL de la API
//    private fun getRetrofit(): Retrofit{
//        return FacturasService.retrofitBuilder
//    }

    //Creamos el método con corrutinas para que coja el contenido del Json y lo pinte en
    //el item del RecyclerView
     fun searchFacturas(){
        CoroutineScope(Dispatchers.IO).launch {
            val service = retrofit.create(APIService::class.java)
            //Creamos la respuesta, llamando al método que tenemos en el FacturasResponse
            val response = service.searchFacturas()
            if (response.isSuccessful){
                //Si la respuesta sucede, pintamos el contenido de la respuesta en el cuerpo
                val facturasResponse = response.body()
                //Cogemos este contenido del body y lo pintamos en la nueva List<Facturas>
                //lamada val facturas
                val facturas = facturasResponse?.facturas ?: emptyList()
                runOnUiThread {
                    //Llamamos al método updateFacturas del adaptador
                    updateAdapter(facturas)
                }
            }

        }
    }

}