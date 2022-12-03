package com.sariaydinalparslan.coutries.ui.ui

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentHomeBinding
import com.sariaydinalparslan.coutries.ui.SelectCountryActivity
import com.sariaydinalparslan.coutries.ui.mySingleton


class HomeFragment : Fragment() {
    var prefs: String? = null
    var chosen: String? = null
    var id: String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPreferences : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         sharedPreferences = this.requireActivity().getSharedPreferences("com.sariaydinalparslan.coutries",
            Context.MODE_PRIVATE)


        id = sharedPreferences!!.getString("nickname","")
        binding.nameText.text = id
        mySingleton.hostName =id

        prefs = sharedPreferences!!.getString("pref", "")
        mySingleton.avatarId = prefs
        Log.e("alp",prefs.toString())

        chosen = sharedPreferences!!.getString("chosen", "")
        mySingleton.chosenCountry = chosen

        binding.homeChosenCountry.text =getString(R.string.chosen_country)+":" +"${mySingleton.chosenCountry}"

        if(prefs!!.isNotEmpty()){
            val resourceID = getResources().getIdentifier(
                "${mySingleton.avatarId}",
                "drawable",
                this.requireContext().packageName
            )
            binding.avatarView.setImageResource(resourceID)
        }else{
            binding.avatarView.setImageResource(R.drawable.person_24)
        }

        binding.selectCountry.setOnClickListener {
            val intent = Intent(requireActivity(), SelectCountryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}