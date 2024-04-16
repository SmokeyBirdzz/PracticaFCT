package com.smokey.practicafct.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.Facturas
import com.smokey.practicafct.R

class FacturasAdapter(private val facturasList: List<Facturas>): RecyclerView.Adapter<FacturasViewHolder> (){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return FacturasViewHolder(layoutInflater.inflate(R.layout.item_factura,parent,false))
    }

    override fun getItemCount(): Int = facturasList.size

    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        val item = facturasList[position]
        holder.render(item)
    }
}