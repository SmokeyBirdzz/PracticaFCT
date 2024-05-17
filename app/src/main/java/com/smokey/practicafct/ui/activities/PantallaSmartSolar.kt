package com.smokey.practicafct.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.smokey.practicafct.MyApplication
import com.smokey.practicafct.MyApplication.Companion.context
import com.smokey.practicafct.R
import com.smokey.practicafct.databinding.PantallaSmartSolarBinding
import com.smokey.practicafct.ui.fragments.SmartSolarDetallesFragment
import com.smokey.practicafct.ui.fragments.SmartSolarEnergiaFragment
import com.smokey.practicafct.ui.fragments.SmartSolarMiInstalacionFragment

class PantallaSmartSolar : AppCompatActivity() {

    private lateinit var binding: PantallaSmartSolarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = PantallaSmartSolarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnClickListener()

        binding.tableLayoutSmart.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null){
                    when (tab.position) {
                        0 -> sustituirFragment(SmartSolarMiInstalacionFragment())
                        1 -> sustituirFragment(SmartSolarEnergiaFragment())
                        2 -> sustituirFragment(SmartSolarDetallesFragment())
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // No es necesario implementar nada aquí
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // No es necesario implementar nada aquí
            }
        })

        // Mostrar el primer fragmento por defecto al iniciar la actividad
        sustituirFragment(SmartSolarMiInstalacionFragment())
    }

    private fun setOnClickListener() {
        binding.matToolbar.setNavigationOnClickListener {
            // Manejar el clic en el botón de navegación aquí
        }
    }

    private fun sustituirFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragContainer, fragment)
            commit()
        }
    }
}