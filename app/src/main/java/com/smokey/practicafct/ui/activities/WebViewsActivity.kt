package com.smokey.practicafct.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.smokey.practicafct.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewsActivity : AppCompatActivity() {

    private lateinit var btnAbrirNavegador: Button
    private lateinit var btnAbrirWebView: Button
    private lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_web_views)

        btnAbrirNavegador = findViewById(R.id.btnAbrirNavegador)
        btnAbrirWebView = findViewById(R.id.btnAbrirWebView)
        webView = findViewById(R.id.webView)

        btnAbrirNavegador.setOnClickListener {

            if (webView.visibility == View.VISIBLE) {
                webView.visibility = View.GONE
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iberdrola.es"))
            startActivity(intent)

        }

        btnAbrirWebView.setOnClickListener {
            webView.visibility = View.VISIBLE
            webView.loadUrl("https://www.iberdrola.es")
        }

    }
}