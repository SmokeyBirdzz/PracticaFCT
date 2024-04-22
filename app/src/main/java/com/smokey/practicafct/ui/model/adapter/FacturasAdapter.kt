package com.smokey.practicafct.ui.model.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.data.retrofit.response.Facturas
import com.smokey.practicafct.R

//Creamos una clase Adapter a la que le proporcionaremos una variable que será una Lista del
//tipo de data class que contenga el item de nuestro RecyclerView
class FacturasAdapter(private var facturasList: List<Facturas>): RecyclerView.Adapter<FacturasViewHolder> (){
    //Asignamos los item.xml del RecyclerView al mismo
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturasViewHolder {
        val  layoutInflater = LayoutInflater.from(parent.context)
        return FacturasViewHolder(layoutInflater.inflate(R.layout.item_factura,parent,false))
    }
    //El número de elementos que contendrá el RecyclerView es igual al largo de la Lista
    //en este caso facturasList
    override fun getItemCount(): Int = facturasList.size

    //Enlazamos la posición de cada item del RecyclerView a la posición en la que están en el Json
    override fun onBindViewHolder(holder: FacturasViewHolder, position: Int) {
        val item = facturasList[position]
        //Llamamos al método render de la clase ViewHolder el cual nos pide un data class Facturas
        //en este caso item es un List del data class Facturas es decir List<Facturas>
        holder.render(item)
    }

    //Creamos el método que actualizará el contenido de
    //una nueva List<Con la data class de nuestro item>
    fun updateFacturas (newFacturasList: List<Facturas>){
        facturasList = newFacturasList
        //Notificamos el cambio en los datos
        notifyDataSetChanged()
    }
}