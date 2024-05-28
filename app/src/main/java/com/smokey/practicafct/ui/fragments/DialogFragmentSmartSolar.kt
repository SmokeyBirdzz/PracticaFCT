package com.smokey.practicafct.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.smokey.practicafct.R

class DialogFragmentSmartSolar: DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_tooltip, container, false)
        val titleTextView = view.findViewById<TextView>(R.id.dialogTitle)
        titleTextView.text = "Estado solicitud autoconsumo"

        val messageTextView = view.findViewById<TextView>(R.id.dialogMessage)
        messageTextView.text = "El tiempo estimado de activación de tu autoconsumo es de 1 a 2 meses, éste variará en función de tu comunidad autónoma y distribuidora"

        val button = view.findViewById<Button>(R.id.dialogButton)
        button.setOnClickListener {
            dismiss()
        }
        return view
    }
}