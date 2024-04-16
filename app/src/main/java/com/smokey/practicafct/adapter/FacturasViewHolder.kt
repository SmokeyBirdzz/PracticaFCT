package com.smokey.practicafct.adapter

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.Facturas
import com.smokey.practicafct.R
class FacturasViewHolder(view: View):RecyclerView.ViewHolder(view) {
    val tvFecha = view.findViewById<TextView>(R.id.tvFecha)
    val tvPendiente = view.findViewById<TextView>(R.id.tvPendiente)
    val tvMoney = view.findViewById<TextView>(R.id.tvMoney)
    val clRecyclerFacturas = view.findViewById<ConstraintLayout>(R.id.clRecyclerFacturas)

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
        tvFecha.text = facturasModel.fecha
        tvPendiente.text = facturasModel.pendiente
        tvMoney.text = facturasModel.dinero
        clRecyclerFacturas.setOnClickListener {
            setAlertDialog()
        }

    }
}