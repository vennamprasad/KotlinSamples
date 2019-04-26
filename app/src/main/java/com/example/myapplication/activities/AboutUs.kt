package com.example.myapplication.activities

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.intro.MApplication
import com.example.myapplication.intro.NetworkCheck

class AboutUs : AppCompatActivity(), NetworkCheck.ConnectivityReceiverListener {
    var no_internet_dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        no_internet_dialog = Dialog(this@AboutUs)
        no_internet_dialog!!.setContentView(R.layout.content_network_detect)
    }

    override fun onStart() {
        super.onStart()
        MApplication.getInstance().setConnectivityListener(this@AboutUs)
    }

    override fun onPause() {
        MApplication.getInstance().resetConnectivityListener(null)
        super.onPause()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
            no_internet_dialog?.dismiss()
        } else {
            no_internet_dialog?.setCancelable(false)
            no_internet_dialog?.show()
        }
    }
}