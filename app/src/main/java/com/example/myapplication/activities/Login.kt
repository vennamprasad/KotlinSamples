package com.example.myapplication.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.utils.Utils
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login : AppCompatActivity() {

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
        userName.setOnEditorActionListener(editorListener)
        password.setOnEditorActionListener(editorListener)
    }


    fun login(view: View) {
        // validating credentials
        if (validate()) {
            startActivity(Intent(this, Home::class.java))
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
