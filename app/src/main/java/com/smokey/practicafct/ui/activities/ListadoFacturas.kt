package com.smokey.practicafct.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.data.retrofit.response.Invoices
import com.smokey.practicafct.FacturasProvider
import com.smokey.practicafct.R
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.APIService
import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.databinding.ActivityListadoFacturasBinding
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ListadoFacturas : AppCompatActivity() {
    //Declaración del adaptador
    private lateinit var facturasAdapter: FacturasAdapter
    private lateinit var rvFacturas: RecyclerView
    private lateinit var servicioFacturas : FacturasService
    private lateinit var binding: ActivityListadoFacturasBinding
    private var retrofit = RetrofitHelper.getRetrofit()
    private val viewmodel: InvoiceViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListadoFacturasBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)


        viewmodel.filteredInvoicesLiveData.observe(this, Observer { invoices ->
            // Aquí va el código para manejar los cambios en los datos
            InitRecyclerView(invoices)
        })


        //Iniciamos el método que va a hacer la llamada a la API y sustituir el contenido de la
        // lista vacía por el contenido del Json

       // searchFacturas()
    }

   private fun InitRecyclerView (invoices: List<Invoices>) {
       binding.rvFacturas.layoutManager = LinearLayoutManager(this)
       facturasAdapter = FacturasAdapter(invoices)
       binding.rvFacturas.adapter = facturasAdapter
   }





    //Creamos la conexión mediante Retrofit a la URL de la API
//    private fun getRetrofit(): Retrofit{
//        return FacturasService.retrofitBuilder
//    }

    //Creamos el método con corrutinas para que coja el contenido del Json y lo pinte en
    //el item del RecyclerView
//     fun searchFacturas(){
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val service = retrofit.create(APIService::class.java)
//            //Creamos la respuesta, llamando al método que tenemos en el FacturasResponse
//            val response = service.getInvoices()
//            if (response.isSuccessful){
//                //Si la respuesta sucede, pintamos el contenido de la respuesta en el cuerpo
//                val facturasResponse = response.body()
//                //Cogemos este contenido del body y lo pintamos en la nueva List<Facturas>
//                //lamada val facturas
//                val facturas = facturasResponse?.facturas ?: emptyList()
//                runOnUiThread {
//                    //Llamamos al método updateFacturas del adaptador
//                   // updateAdapter(facturas)
//                }
//            }
//
//        }
//    }

}