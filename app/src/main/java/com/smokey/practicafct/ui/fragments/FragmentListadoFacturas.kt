import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.smokey.practicafct.R
import com.smokey.practicafct.constants.Constants
import com.smokey.practicafct.core.network.toDateString
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.databinding.FragmentFiltradoFacturasBinding
import com.smokey.practicafct.databinding.FragmentListadoFacturasBinding
import com.smokey.practicafct.ui.activities.ListadoFacturas
import com.smokey.practicafct.ui.activities.MainActivity
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import com.smokey.practicafct.ui.model.adapter.Filters
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel
import com.smokey.practicafct.ui.viewmodel.SharedViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class FragmentListadoFacturas : Fragment() {
    private lateinit var facturasAdapter: FacturasAdapter
    private lateinit var rvFacturas: RecyclerView
    private lateinit var binding: FragmentListadoFacturasBinding
    private val viewmodel: InvoiceViewmodel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var originalList: List<InvoiceModelRoom> = emptyList() // Inicializar como lista vacía

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListadoFacturasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbarImageButton: ImageButton = view.findViewById(R.id.toolbarImageButton)
        toolbarImageButton.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentListadoFacturas_to_fragmentFiltradoFacturas)
        }
        val btnArrowLeft: ImageButton = view.findViewById(R.id.btnArrowLeft)
        btnArrowLeft.setOnClickListener {
            requireActivity().finish()
        }

        // Configurar RecyclerView
        rvFacturas = binding.rvFacturas
        rvFacturas.layoutManager = LinearLayoutManager(requireContext())

        // Inicializar el adaptador con una lista vacía para evitar errores de inicialización
        facturasAdapter = FacturasAdapter(emptyList())
        rvFacturas.adapter = facturasAdapter

        // Observar cambios en los datos
        viewmodel.filteredInvoicesLiveData.observe(viewLifecycleOwner) { invoices ->
            originalList = invoices
            Log.d("ListadoFacturas", "Original List Size: ${invoices.size}")
            facturasAdapter.updateFacturas(invoices)
        }

        val switchRetromock = view.findViewById<SwitchCompat>(R.id.switchRetromock)
        switchRetromock.setOnCheckedChangeListener { _, isChecked ->
            viewmodel.useRetrofitService = isChecked
            viewmodel.searchInvoices()
        }

        viewmodel.filterLiveData.observe(viewLifecycleOwner) { filter ->
            filter?.let {
                viewmodel.verificarFiltros()
            }
        }

        // Observar los filtros y aplicar el filtrado
        sharedViewModel.filters.observe(viewLifecycleOwner) { filters ->
            if (filters != null) {
                Log.d("Filtros", "Recibidos: $filters")
                viewmodel.applyFilters(filters.maxDate, filters.minDate, filters.maxValueSlider, filters.status)
            }
        }
    }

}