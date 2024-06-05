package com.smokey.practicafct.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.smokey.practicafct.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var imArrow: ImageButton
    private lateinit var imArrow2: ImageButton
    private lateinit var imArrow3: ImageButton
    private lateinit var logoutButton: ImageButton
    private lateinit var clPractica1: ConstraintLayout
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var remoteConfig: FirebaseRemoteConfig
    private val PREFS_NAME = "MyPrefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Init Firebase Remote Config
        initFirebaseRemoteConfig()
        fetchRemoteConfig()

        // Set theme based on Remote Config value
        applyThemeFromRemoteConfig()

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        initComponents()
        initListeners()
    }

    private fun fetchRemoteConfig() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI()
                } else {
                    // Handle fetch failure
                }
            }
    }

    private fun updateUI() {
        val isPractica1Visible = remoteConfig.getBoolean("isPractica1Visible")
        Log.d("RemoteConfig", "isPractica1Visible: $isPractica1Visible")
        clPractica1.visibility = if (isPractica1Visible) View.VISIBLE else View.GONE
    }

    private fun initFirebaseRemoteConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(0) // Adjust as necessary
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("RemoteConfig", "Config params updated: $updated")
                    applyThemeFromRemoteConfig()
                    updateUI()
                } else {
                    Log.e("RemoteConfig", "Fetch failed")
                }
            }
    }

    private fun applyThemeFromRemoteConfig() {
        val isDarkTheme = remoteConfig.getBoolean("isDarkTheme")
        Log.d("RemoteConfig", "isDarkTheme: $isDarkTheme")
        val nightMode = if (isDarkTheme) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)
    }

    private fun initComponents() {
        imArrow = findViewById(R.id.imArrow)
        imArrow2 = findViewById(R.id.imArrow2)
        imArrow3 = findViewById(R.id.imArrow3)
        logoutButton = findViewById(R.id.btnLogout)
        clPractica1 = findViewById(R.id.clPractica1)
    }

    private fun initListeners() {
        imArrow.setOnClickListener {
            navigateToPracticaUno()
        }
        imArrow2.setOnClickListener {
            navigateToPracticaDos()
        }
        imArrow3.setOnClickListener {
            navigateToWebViewsActivity()
        }
        logoutButton.setOnClickListener {
            auth.signOut()
            clearRememberedUser()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun navigateToWebViewsActivity() {
        val intent = Intent(this, WebViewsActivity::class.java)
        startActivity(intent)
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