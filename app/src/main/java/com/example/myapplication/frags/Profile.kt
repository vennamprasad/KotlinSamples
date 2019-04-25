package com.example.myapplication.frags

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.EditProfileActivity
import com.example.myapplication.R
import com.example.myapplication.ReferAndEarn
import kotlinx.android.synthetic.main.profile_fragment.view.*

class Profile : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the custom view for this fragment layout
        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        // Get the text view widget reference from custom layout

        view.my_profile?.setOnClickListener {
            val splash_intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(splash_intent)
        }
        view.refer_earn?.setOnClickListener {
            val splash_intent = Intent(activity, ReferAndEarn::class.java)
            startActivity(splash_intent)
        }
        return view
    }
}
