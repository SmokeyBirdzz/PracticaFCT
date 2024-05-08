package com.smokey.practicafct.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.MyApplication
import com.smokey.practicafct.R
import com.smokey.practicafct.core.retrofit.RetrofitHelper
import com.smokey.practicafct.data.retrofit.FacturasService
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.databinding.ActivityListadoFacturasBinding
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel


class ListadoFacturas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_facturas)

    }

}

