package com.smokey.practicafct.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import com.smokey.practicafct.R
import com.smokey.practicafct.databinding.FragmentSmartSolarDetallesBinding
import com.smokey.practicafct.ui.viewmodel.DetailsSmartSolarViewmodel

class SmartSolarDetallesFragment : Fragment() {

    private lateinit var binding: FragmentSmartSolarDetallesBinding
    private val viewModel: DetailsSmartSolarViewmodel by activityViewModels()
    private lateinit var tooltipImage: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSmartSolarDetallesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tooltipImage = binding.tooltipImage

        tooltipImage.setOnClickListener {
         val tooltipDialog = DialogFragmentSmartSolar()
            tooltipDialog.show(childFragmentManager, "SmartSolarDialog")
        }

        viewModel.energyDataLiveData.observe(viewLifecycleOwner){ energyData ->
            binding.etCAU.setText(energyData.cau)
            binding.etEstado.setText(energyData.estadoSolicitud)
            binding.etTipo.setText(energyData.tipoAutoconsumo)
            binding.etCompensacion.setText(energyData.compensacionExcedentes)
            binding.etPotencia.setText(energyData.potenciaInstalacion)

        }
    }
}