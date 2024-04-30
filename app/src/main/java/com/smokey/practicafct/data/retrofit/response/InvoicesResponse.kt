package com.smokey.practicafct.data.retrofit.response

import com.smokey.practicafct.data.room.InvoiceModelRoom

class InvoicesResponse (
//Este valor numFacturas está contenido en el Json y indica el número de objetos "facturas"
    //que contiene el Json
    val numFacturas: Int,
    //Este valor facturas esta contenido en el Json en forma de un array en el que se encuentra
    //contenida toda la información de cada factura , como sus val : descEstado,
    //importeOrdenacion y fecha
    val facturas: List<InvoiceModelRoom>

)