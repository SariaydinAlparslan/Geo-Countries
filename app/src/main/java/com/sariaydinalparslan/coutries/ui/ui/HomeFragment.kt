package com.sariaydinalparslan.coutries.ui.ui

import android.content.Context.MODE_PRIVATE
import android.content.Intent
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
import com.sariaydinalparslan.coutries.databinding.FragmentHomeBinding
import com.sariaydinalparslan.coutries.ui.SelectCountryActivity
import com.sariaydinalparslan.coutries.ui.mySingleton


class HomeFragment : Fragment() {
    var prefs : String? = null
    var chosen : String? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


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

        val acct = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if (acct != null) {
            val personName = acct.displayName
            binding.nameText.text = personName
            mySingleton.hostName = personName
        }
        val sharedPreferences = this.activity?.getSharedPreferences("com.sariaydinalparslan.coutries",
            MODE_PRIVATE)
        prefs = sharedPreferences!!.getString("pref","")
        mySingleton.avatarId = prefs

        chosen = sharedPreferences!!.getString("chosen","")
        mySingleton.chosenCountry = chosen

        binding.homeChosenCountry.text = "Your Country : ${mySingleton.chosenCountry}"

        val resourceID = getResources().getIdentifier("${mySingleton.avatarId}", "drawable", this.requireContext().packageName)
        Log.e(  "alp", resourceID.toString())
        binding.avatarView.setImageResource(resourceID)

        binding.selectCountry.setOnClickListener {
            val intebt = Intent(requireActivity(),SelectCountryActivity::class.java)
            startActivity(intebt)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}