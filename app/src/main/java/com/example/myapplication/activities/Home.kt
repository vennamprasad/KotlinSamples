package com.example.myapplication.activities

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.frags.*
import com.example.myapplication.intro.MApplication
import com.example.myapplication.intro.NetworkCheck
import com.google.android.material.bottomnavigation.BottomNavigationView

class Home : AppCompatActivity(), NetworkCheck.ConnectivityReceiverListener {

    var no_internet_dialog: Dialog? = null
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        when (menuItem.itemId) {
            R.id.profile -> {
                val fragment = Profile()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.ongoing -> {
                val fragment = OnGoing()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.play -> {
                val fragment = Play()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.participated -> {
                val fragment = Participated()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.result -> {
                val fragment = Result()
                supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        no_internet_dialog = Dialog(this)
        no_internet_dialog!!.setContentView(R.layout.content_network_detect)
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setDefaultFragment()
    }

    private fun setDefaultFragment() {
        val fragment = Profile()
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment, fragment.javaClass.simpleName).commit()
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
            no_internet_dialog?.dismiss()
        } else {
            no_internet_dialog?.setCancelable(false)
            no_internet_dialog?.show()
        }
    }
}
