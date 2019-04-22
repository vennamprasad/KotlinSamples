package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var userName: EditText
    private lateinit var password: EditText
    private val editorListener = TextView.OnEditorActionListener { v, actionId, event ->
        when (actionId) {
            EditorInfo.IME_ACTION_NEXT -> {
            }
            EditorInfo.IME_ACTION_DONE -> login(v)
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ui()
    }

    private fun ui() {
        userName = findViewById(R.id.userName)
        password = findViewById(R.id.password)
        userName.setOnEditorActionListener(editorListener)
        password.setOnEditorActionListener(editorListener)
    }


    fun login(view: View) {
        // validating credentials
        if (validate()) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun validate(): Boolean {
        if (Objects.requireNonNull<Editable>(userName.text).toString() == "") {
            userName.error = "enter user name"
            userName.requestFocus()
            return false
        } else if (Objects.requireNonNull<Editable>(password.text).toString() == "") {
            password.error = "enter password"
            password.requestFocus()
            return false
        } else {
            if (userName.text.toString().matches(Utils.MobilePattern.toRegex())) {
                if (userName.text.toString().length < 10 && userName.text.toString().length > 10) {
                    userName.error = "please enter valid phone number"
                    userName.requestFocus()
                    return false
                }
            } else {
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userName.text.toString()).matches()) {
                    userName.error = "please enter valid email"
                    userName.requestFocus()
                    return false
                }
            }
        }
        return true
    }

    fun register(view: View) {}

    fun forgot(view: View) {}
}
