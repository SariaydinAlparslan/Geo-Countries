package com.sariaydinalparslan.coutries.ui.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.include_avatar_layout.*

class SettingsFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        change_avatar.setOnClickListener {
            change_avatar.visibility=View.GONE
            share.visibility = View.GONE
            edit.visibility=View.GONE
            includeavatar.visibility = View.VISIBLE
        }
    }


}
