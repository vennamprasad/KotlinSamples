package com.example.myapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    fun lodImage(context: Context, URL: String, view: ImageView) {
        Glide.with(context).load(URL).into(view)
    }

    companion object {
        var Root_Path = ""
        var DB_PATH = ""
        var IMAGE_FOLDER_PATH = ""
        var IMAGE_FOLDER_NAME = "ProGamerImages"
        var DB_FOLDER_NAME = "ProGamerDB"
        var DB_Name = "ProGamer.db"
        var LogFolder = "ProGamerLogs.db"
        var MobilePattern = "[6-9][0-9]{9}"
        var iEnableLog = true


        internal fun setCustomTitle(activity: AppCompatActivity) {
            Objects.requireNonNull<ActionBar>(activity.supportActionBar).setDisplayShowCustomEnabled(true)
            activity.supportActionBar!!.setDisplayShowTitleEnabled(false)
            val inflator = LayoutInflater.from(activity)
            val v = inflator.inflate(R.layout.titleview, null)
            (v.findViewById<View>(R.id.title) as TextView).text = activity.title
            activity.supportActionBar!!.customView = v
        }

        fun creRowId(): String {
            return UUID.randomUUID().toString().replace("-".toRegex(), "").toUpperCase()
        }

        @SuppressLint("ResourceType")
        fun getId(view: View): String {
            return if (view.id == -0x1)
                "no-id"
            else
                view.resources.getResourceName(view.id)
        }

        @SuppressLint("HardwareIds")
        fun getMacAddress(context: Context): String? {
            var result: String? = ""
            try {
                val wifiManager = context.applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
                val wifiInfo = Objects.requireNonNull(wifiManager).connectionInfo
                result = wifiInfo.macAddress
            } catch (ex: Exception) {
                ex.message?.let { Log(it) }
            }

            if ("" != result) {
                val settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE)
                val editor = settings.edit()
                editor.putString("MAC_ADDRESS", result)
                editor.apply()
            } else {
                val settings = context.getSharedPreferences("DeviceInfo", MODE_PRIVATE)
                result = settings.getString("MAC_ADDRESS", "")
            }
            return result
        }

        internal fun showMsg(message: String, context: Context) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        internal fun getExceptionRootMessage(throwable: Throwable): String {
            val stringBuilder = StringBuilder()
            stringBuilder.append(throwable.message).append("\r\n")
            val stackTraceElementsList = throwable.stackTrace
            if (stackTraceElementsList != null) {
                for (stackTraceElement in stackTraceElementsList) {
                    if (stackTraceElement != null) {
                        stringBuilder.append(stackTraceElement.className)
                        stringBuilder.append(".")
                        stringBuilder.append(stackTraceElement.methodName)
                        stringBuilder.append("()-")
                        stringBuilder.append(stackTraceElement.lineNumber).append("\r\n")
                    }
                }
            }
            return stringBuilder.toString()
        }

        @SuppressLint("SimpleDateFormat")
        fun Log(message: String): Int {
            try {
                if (iEnableLog) {
                    val currentDate = Calendar.getInstance()
                    @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("yyyy-MMM-dd")
                    val dateNow = formatter.format(currentDate.time)
                    val dir = File("$Root_Path/$LogFolder")
                    if (!dir.exists()) {
                        dir.mkdir()
                    }
                    val file = File("$Root_Path/$LogFolder/$dateNow.txt")
                    if (!file.exists()) {
                        file.createNewFile()
                    }
                    SimpleDateFormat("hh:mm:ss.SSS")
                    val dat1 = Date()
                    val fstream = FileWriter(file, true)
                    val out = BufferedWriter(fstream)
                    out.write(dat1.toString())
                    out.write(":")
                    out.write(message + "\n")
                    out.newLine()
                    out.close()
                }
            } catch (var10: Exception) {
                println("Exception in writelog fn" + var10.message)
            }

            return 0
        }
    }


}
