package com.smokey.practicafct.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toolbar
import androidx.navigation.fragment.findNavController
import com.smokey.practicafct.R
import java.util.Calendar

class FragmentFiltradoFacturas : Fragment() {

private lateinit var btnDesde : Button
private lateinit var btnHasta : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.toolBarTitle)
        val cancelButton = view.findViewById<ImageButton>(R.id.imageButtonCancel)
        btnDesde = view.findViewById(R.id.btnDesde)
        btnHasta = view.findViewById(R.id.btnHasta)

        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFiltradoFacturas_to_fragmentListadoFacturas)
        }
        btnDesde.setOnClickListener { showDatePickerDesde(it) }
        btnHasta.setOnClickListener { showDatePickerHasta(it) }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filtrado_facturas, container, false)
    }

    private fun showDatePickerDesde(view: View) {
        val cal = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                btnDesde.text = selectedDate
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
    fun showDatePickerHasta(view: View) {
        val cal = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                btnHasta.text = selectedDate
            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

}