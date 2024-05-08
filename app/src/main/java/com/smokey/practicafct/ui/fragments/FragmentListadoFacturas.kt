import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smokey.practicafct.R
import com.smokey.practicafct.data.room.InvoiceModelRoom
import com.smokey.practicafct.databinding.FragmentListadoFacturasBinding
import com.smokey.practicafct.ui.model.adapter.FacturasAdapter
import com.smokey.practicafct.ui.viewmodel.InvoiceViewmodel

class FragmentListadoFacturas : Fragment() {
    private lateinit var facturasAdapter: FacturasAdapter
    private lateinit var rvFacturas: RecyclerView
    private lateinit var binding: FragmentListadoFacturasBinding
    private val viewmodel: InvoiceViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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

        // Configurar RecyclerView
        rvFacturas = binding.rvFacturas
        rvFacturas.layoutManager = LinearLayoutManager(requireContext())

        // Observar cambios en los datos
        viewmodel.filteredInvoicesLiveData.observe(viewLifecycleOwner) { invoices ->
            facturasAdapter = FacturasAdapter(invoices)
            rvFacturas.adapter = facturasAdapter
        }

        val switchRetromock = view.findViewById<SwitchCompat>(R.id.switchRetromock)
        switchRetromock.setOnCheckedChangeListener { _, isChecked ->
            viewmodel.useRetrofitService = isChecked
            viewmodel.searchInvoices()
        }

    }
}