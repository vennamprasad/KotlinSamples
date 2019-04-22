package com.example.myapplication.frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.EditProfileActivity
import com.example.myapplication.R

class Profile : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        // Get the text view widget reference from custom layout
        val my_profile = view.findViewById<TextView>(R.id.my_profile)
        my_profile.setOnClickListener {
            val splash_intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(splash_intent)
        }
        return view
    }
}
