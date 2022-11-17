package com.sariaydinalparslan.coutries.ui.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentSettingsBinding
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.include_avatar_layout.*

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        change_avatar.setOnClickListener {
            binding.changeAvatar.visibility=View.GONE
            binding.share.visibility = View.GONE
            binding.rate.visibility=View.GONE
          includeavatar.visibility = View.VISIBLE
        }
        share.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.setType("text/plain")
            val string3 = "https://play.google.com/store/apps/details?id=com.sariaydinalparslan.coutries&pli=1"
            shareIntent.putExtra(Intent.EXTRA_TEXT,string3)
            startActivity(Intent.createChooser(shareIntent,"Deneme"))

        }
        rate.setOnClickListener {
            val uri : Uri = Uri.parse("market://details?id=com.sariaydinalparslan.coutries&pli=1")
            val gotoMarket = Intent(Intent.ACTION_VIEW,uri)

            gotoMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
            Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
            Intent.FLAG_ACTIVITY_MULTIPLE_TASK
            )
            try {
                startActivity(gotoMarket)
            }catch (e : ActivityNotFoundException){
                startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id=com.sariaydinalparslan.coutries&pli=1")))
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
