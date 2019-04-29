package com.example.myapplication.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.intro.MApplication
import com.example.myapplication.intro.NetworkCheck

class AboutUs : AppCompatActivity(), NetworkCheck.ConnectivityReceiverListener {
    private var webView: WebView? = null
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView!!.canGoBack()) {
            webView!!.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onStart() {
        super.onStart()
        MApplication.getInstance().setConnectivityListener(this)
    }

    override fun onPause() {
        MApplication.getInstance().resetConnectivityListener(null)
        super.onPause()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            reload()
        } else {
            this.setContentView(R.layout.content_network_detect)
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun reload() {
        setContentView(R.layout.activity_about_us)
        webView = findViewById(R.id.web)
        val webSettings = webView!!.settings
        webSettings.javaScriptEnabled = true
        webView!!.webViewClient = HelloWebViewClient()
        webView!!.loadUrl("https://stackoverflow.com/legal/acceptable-use-policy")
    }

    private inner class HelloWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (Uri.parse(url).host == "https://stackoverflow.com") {
                return false
            }
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
            return true
        }
    }
}