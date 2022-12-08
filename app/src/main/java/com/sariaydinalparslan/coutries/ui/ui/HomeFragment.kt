package com.sariaydinalparslan.coutries.ui.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentHomeBinding
import com.sariaydinalparslan.coutries.ui.MainActivity
import com.sariaydinalparslan.coutries.ui.guess.Guess
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
            replaceFragment(Guess())
            binding.chooseCountryLayout.visibility = View.VISIBLE
            binding.lottie1.visibility= View.GONE
            binding.avatarView.visibility = View.GONE
            binding.nameText.visibility = View.GONE
            binding.homeChosenCountry.visibility = View.GONE
            binding.selectCountry.visibility = View.GONE
            binding.selectBtn.visibility = View.VISIBLE
        }
        binding.selectBtn.setOnClickListener {
            sharedPreferences!!.edit().putString("chosen",mySingleton.singlePlayerCountryGuessCode).apply()

            val intent = Intent(Intent(requireActivity(),MainActivity::class.java))
            startActivity(intent)
            finishAffinity(requireActivity())
        }
        chosen = sharedPreferences!!.getString("chosen", "")
        mySingleton.chosenCountry = chosen
        binding.homeChosenCountry.text =getString(R.string.chosen_country)+":" +"${mySingleton.chosenCountry}"+getString(R.string.foronlinemode)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment (fragment : Fragment){

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.choose_country_layout,fragment)
        fragmentTransaction.commit()
    }
}