package com.smokey.practicafct.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.MyApplication
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.databinding.ActivityListadoFacturasBinding
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel


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
        MyApplication()


        viewmodel.filteredInvoicesLiveData.observe(this, Observer { invoices ->
            // Aquí va el código para manejar los cambios en los datos
            InitRecyclerView(invoices)
        })


        //Iniciamos el método que va a hacer la llamada a la API y sustituir el contenido de la
        // lista vacía por el contenido del Json

       // searchFacturas()
    }

   private fun InitRecyclerView (invoices: List<InvoiceModelRoom>) {
       binding.rvFacturas.layoutManager = LinearLayoutManager(this)
       facturasAdapter = FacturasAdapter(invoices)
       binding.rvFacturas.adapter = facturasAdapter
   }

}