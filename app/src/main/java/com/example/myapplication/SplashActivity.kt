package com.example.myapplication

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Utils.Root_Path
import com.github.jksiezni.permissive.Permissive
import java.io.File
import java.io.FileOutputStream

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val icon = findViewById<View>(R.id.icon) as ImageView
        icon.visibility = View.VISIBLE
        val splash = object : Thread() {
            override fun run() {
                try {
                    sleep((4 * 1000).toLong())
                    Permissive.Request(Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA).whenPermissionsGranted {
                        Root_Path = filesDir.path
                        createDirectories()
                        val splash_intent = Intent(this@SplashActivity, LoginActivity::class.java)
                        startActivity(splash_intent)
                        finish()
                    }.whenPermissionsRefused { }.execute(this@SplashActivity)
                } catch (ignored: Exception) {
                }

            }
        }
        splash.start()
    }

    private fun createDirectories() {
        //DB
        try {
            val rootPath = Root_Path + "/" + Utils.DB_FOLDER_NAME + "/"
            val root = File(rootPath)
            if (!root.exists()) {
                root.mkdirs()
            }
            val f = File(rootPath + Utils.DB_Name)
            if (!f.exists()) {
                f.createNewFile()
                val out = FileOutputStream(f)
                out.flush()
                out.close()
            }
            Utils.DB_PATH = f.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
        }

        //
        val image = File(Root_Path, Utils.IMAGE_FOLDER_NAME)
        if (!image.exists()) {
            image.mkdirs()
        }
        Utils.IMAGE_FOLDER_PATH = image.path
    }

}
