package com.smokey.practicafct.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.smokey.practicafct.R

class MainActivity : AppCompatActivity() {

    private lateinit var imArrow: ImageButton
    private lateinit var imArrow2: ImageButton
    private lateinit var logoutButton: ImageButton

    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFS_NAME = "MyPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        initComponents()
        initListeners()
    }

    private fun initComponents() {
        imArrow = findViewById(R.id.imArrow)
        imArrow2 = findViewById(R.id.imArrow2)
        logoutButton = findViewById(R.id.btnLogout)
    }

    private fun initListeners() {
        imArrow.setOnClickListener {
            navigateToPracticaUno()
        }
        imArrow2.setOnClickListener {
            navigateToPracticaDos()
        }
        logoutButton.setOnClickListener {
            auth.signOut()
            clearRememberedUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToPracticaUno() {
        val intent = Intent(this, ListadoFacturas::class.java)
        startActivity(intent)
    }

    private fun navigateToPracticaDos() {
        val intent = Intent(this, PantallaSmartSolar::class.java)
        startActivity(intent)
    }

    private fun clearRememberedUser() {
        val sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}