package com.smokey.practicafct.adapter

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.Facturas
import com.smokey.practicafct.R
import com.smokey.practicafct.databinding.ItemFacturaBinding

class FacturasViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val binding = ItemFacturaBinding.bind(view)

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
    fun render(facturasModel:Facturas){
        binding.tvFecha.text = facturasModel.fecha
        binding.tvPendiente.text = facturasModel.pendiente
        binding.tvMoney.text = "${facturasModel.dinero} €"
        binding.clRecyclerFacturas.setOnClickListener {
            setAlertDialog()
        }

    }
}