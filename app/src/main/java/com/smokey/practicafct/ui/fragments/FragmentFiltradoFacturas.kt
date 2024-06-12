package com.smokey.practicafct.ui.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.slider.Slider
import com.smokey.practicafct.R
import com.smokey.practicafct.constants.Constants
import com.smokey.practicafct.core.network.toDateString
import com.smokey.practicafct.databinding.FragmentFiltradoFacturasBinding
import com.smokey.practicafct.ui.model.adapter.Filters
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel
import com.smokey.practicafct.ui.viewmodel.SharedViewModel
import java.time.LocalDate
import java.util.Calendar

class FragmentFiltradoFacturas : Fragment() {

    private lateinit var btnDesde: Button
    private lateinit var btnHasta: Button
    private lateinit var binding: FragmentFiltradoFacturasBinding
    private lateinit var textViewSlider: TextView
    private lateinit var tvSliderLeft: TextView
    private lateinit var tvSliderRight: TextView
    private lateinit var slider: Slider
    private lateinit var paid: CheckBox
    private lateinit var canceled: CheckBox
    private lateinit var fixedPayment: CheckBox
    private lateinit var pendingPayment: CheckBox
    private lateinit var paymentPlan: CheckBox
    private val viewModel: InvoiceViewmodel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBar = view.findViewById<Toolbar>(R.id.toolBarTitle)
        val cancelButton = view.findViewById<ImageButton>(R.id.imageButtonCancel)

        btnDesde = binding.btnDesde
        btnHasta = binding.btnHasta
        btnDesde.setOnClickListener { showDatePickerDesde(it) }
        btnHasta.setOnClickListener { showDatePickerHasta(it) }
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentFiltradoFacturas_to_fragmentListadoFacturas)
        }
        initComponents()
        loadFilters()
    }

    private fun initComponents() {
        initSeekBar()
        initCheckBoxes()
        initApplyFiltersButton()
        initResetFilterButton()
    }

    private fun initCheckBoxes() {
        paid = binding.cBPagadas
        canceled = binding.cBAnuladas
        fixedPayment = binding.cBCuotaFija
        pendingPayment = binding.cBPendientesDePago
        paymentPlan = binding.cBPlanDePago
    }

    private fun initResetFilterButton() {
        binding.btnEliminarFiltros.setOnClickListener {
            resetFilters()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiltradoFacturasBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun loadFilters() {
        sharedViewModel.minDate.value?.let {
            binding.btnDesde.text = it
        }
        sharedViewModel.maxDate.value?.let {
            binding.btnHasta.text = it
        }
        sharedViewModel.maxValueSlider.value?.let {
            binding.slider.value = it.toFloat()
            textViewSlider.text = it.toInt().toString()
        } ?: run {
            // Valor predeterminado si no hay filtros guardados
            binding.slider.value = 300f
            textViewSlider.text = "300"
        }
        sharedViewModel.status.value?.let {
            binding.cBPagadas.isChecked = it[Constants.PAID_STRING] ?: false
            binding.cBAnuladas.isChecked = it[Constants.CANCELED_STRING] ?: false
            binding.cBCuotaFija.isChecked = it[Constants.FIXED_PAYMENT_STRING] ?: false
            binding.cBPendientesDePago.isChecked = it[Constants.PENDING_PAYMENT_STRING] ?: false
            binding.cBPlanDePago.isChecked = it[Constants.PAYMENT_PLAN_STRING] ?: false
        }
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

    private fun initSeekBar() {
        textViewSlider = binding.textViewSlider
        tvSliderLeft = binding.tvSliderLeft
        tvSliderRight = binding.tvSliderRight
        slider = binding.slider

        tvSliderRight.text = "300€"

        slider.valueFrom = 0f
        slider.valueTo = 300f
        slider.value = 300f

        slider.addOnChangeListener { slider, _, _ ->
            val value = slider.value.toInt()
            textViewSlider.text = "$value"
        }
        tvSliderLeft.text = "0"
    }

    private fun initApplyFiltersButton() {
        binding.btnAplicar.setOnClickListener {
            val maxValueSlider = if (binding.textViewSlider.text.toString().toDouble() == 0.0) 300.0 else binding.textViewSlider.text.toString().toDouble()
            val status = hashMapOf(
                Constants.PAID_STRING to paid.isChecked,
                Constants.CANCELED_STRING to canceled.isChecked,
                Constants.FIXED_PAYMENT_STRING to fixedPayment.isChecked,
                Constants.PENDING_PAYMENT_STRING to pendingPayment.isChecked,
                Constants.PAYMENT_PLAN_STRING to paymentPlan.isChecked
            )

            val minDate: String = if (binding.btnDesde.text == getString(R.string.fragment_filtrado_diaMesAnno))
                LocalDate.ofEpochDay(0).toDateString("dd/MM/yyyy")
            else binding.btnDesde.text.toString()
            val maxDate: String =
                if (binding.btnHasta.text == getString(R.string.fragment_filtrado_diaMesAnno)) LocalDate.now().toDateString("dd/MM/yyyy") else binding.btnHasta.text.toString()

            Log.d("Filtros", "minDate: $minDate, maxDate: $maxDate, maxValueSlider: $maxValueSlider")
            Log.d("Filtros", "status: $status")

            val filters = Filters(minDate, maxDate, maxValueSlider, status)
            sharedViewModel.setFilters(filters)
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    private fun resetFilters() {
        binding.btnDesde.text = getString(R.string.fragment_filtrado_diaMesAnno)
        binding.btnHasta.text = getString(R.string.fragment_filtrado_diaMesAnno)
        binding.slider.value = 300f
        textViewSlider.text = "300"
        binding.cBPagadas.isChecked = false
        binding.cBAnuladas.isChecked = false
        binding.cBCuotaFija.isChecked = false
        binding.cBPendientesDePago.isChecked = false
        binding.cBPlanDePago.isChecked = false
    }
}