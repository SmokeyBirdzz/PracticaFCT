package com.smokey.practicafct

import com.smokey.practicafct.data.retrofit.response.Invoices

//Creamos la clase que propocionará el tipo de variable que necesitará el adaptador para mostrar
//en el RecyclerView
class FacturasProvider {
    companion object {
        var facturasList = listOf<Invoices>(
        )
    }
}