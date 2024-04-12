package com.smokey.practicafct

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imArrow: ImageButton
    private lateinit var imArrow2: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
    }

    private fun initComponents() {
        imArrow = findViewById(R.id.imArrow)
        imArrow2 = findViewById(R.id.imArrow2)
    }

    private fun initListeners() {
        imArrow.setOnClickListener {
            navigateToPracticaUno()
        }
        imArrow2.setOnClickListener {
            navigateToPracticaDos()
        }
    }

    private fun navigateToPracticaUno() {
        val intent = Intent(this, PracticaUno::class.java)
        startActivity(intent)
    }

    private fun navigateToPracticaDos() {
        val intent = Intent(this, PracticaDos::class.java)
        startActivity(intent)
    }

}