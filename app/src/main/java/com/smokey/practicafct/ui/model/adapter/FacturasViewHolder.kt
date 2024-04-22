package com.smokey.practicafct.ui.model.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.data.retrofit.response.Facturas
import com.smokey.practicafct.databinding.ItemFacturaBinding

//Creamos una clase ViewHolder a la que le pasaremos una vista
class FacturasViewHolder(view: View):RecyclerView.ViewHolder(view) {
    //Enlazamos la vista con nuestro Item del RecyclerView
    val binding = ItemFacturaBinding.bind(view)

    //Método para crear el pop-up al clickar en una celda del RecyclerView
    fun setAlertDialog(){

        val builder = android.app.AlertDialog.Builder(itemView.context)
        builder.setTitle("Información")
        builder.setMessage("Esta funcionalidad aún no está disponible")
        builder.setPositiveButton("Cerrar") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()

    }
    //Método render en el que pintamos el contenido de la lista que hemos recibido
    //del Json mediante Retrofit , y lo sobreescribimos sobre los textView del item de nuestro
    //RecyclerView
    fun render(facturasModel: Facturas){
        binding.tvFecha.text = facturasModel.fecha
        binding.tvPendiente.text = facturasModel.pendiente
        binding.tvMoney.text = "${facturasModel.dinero} €"
        //Llamamos al método del pop-up en el caso de que el usuario clicke en la celda
        binding.clRecyclerFacturas.setOnClickListener {
            setAlertDialog()
        }

    }
}