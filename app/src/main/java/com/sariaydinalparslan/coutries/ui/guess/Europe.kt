package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.databinding.FragmentEuropeBinding
import com.sariaydinalparslan.coutries.ui.mySingleton

class Europe : Fragment() {

    private var _binding: FragmentEuropeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEuropeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guess2.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "2"}
        binding.guess4.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "4"}
        binding.guess18.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "18"}
        binding.guess19.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "19"}
        binding.guess21.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "21"}
        binding.guess39.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "39"}
        binding.guess40.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "40"}
        binding.guess48.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "48"}
        binding.guess51.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "51"}
        binding.guess52.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "52"}
        binding.guess54.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "54"}
        binding.guess55.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "55"}
        binding.guess57.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "57"}
        binding.guess63.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "63"}
        binding.guess64.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "64"}
        binding.guess69.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "69"}
        binding.guess71.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "71"}
        binding.guess78.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "78"}
        binding.guess83.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "83"}
        binding.guess87.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "87"}
        binding.guess88.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "88"}
        binding.guess89.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "89"}
        binding.guess95.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "95"}
        binding.guess98.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "98"}
        binding.guess99.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "99"}
        binding.guess101.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "101"}
        binding.guess106.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "106"}
        binding.guess110.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "110"}
        binding.guess111.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "111"}
        binding.guess117.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "117"}
        binding.guess118.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "118"}
        binding.guess123.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "123"}
        binding.guess129.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "129"}
        binding.guess131.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "131"}
        binding.guess132.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "132"}
        binding.guess139.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "139"}
        binding.guess146.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "146"}
        binding.guess148.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "148"}
        binding.guess150.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "150"}
        binding.guess154.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "154"}
        binding.guess10.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "10"}
        binding.guess24.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "24"}
        binding.guess135.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "135"}
        binding.guess138.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "138"}
        binding.guess36.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "36"}

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}