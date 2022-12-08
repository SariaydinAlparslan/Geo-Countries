package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentAfricaBinding
import com.sariaydinalparslan.coutries.databinding.FragmentAmericaBinding
import com.sariaydinalparslan.coutries.ui.mySingleton

class Africa : Fragment() {

    private var _binding: FragmentAfricaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentAfricaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guess3.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "3"}
        binding.guess5.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "5"}
        binding.guess17.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "17"}
        binding.guess25.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "25"}
        binding.guess23.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "23"}
        binding.guess34.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "34"}
        binding.guess41.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "41"}
        binding.guess46.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "46"}
        binding.guess40.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "40"}
        binding.guess56.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "56"}
        binding.guess62.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "62"}
        binding.guess72.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "72"}
        binding.guess77.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "77"}
        binding.guess85.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "85"}
        binding.guess86.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "86"}
        binding.guess90.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "90"}
        binding.guess91.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "91"}
        binding.guess94.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "94"}
        binding.guess96.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "96"}
        binding.guess102.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "102"}
        binding.guess104.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "104"}
        binding.guess109.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "109"}
        binding.guess125.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "125"}
        binding.guess128.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "128"}
        binding.guess133.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "133"}
        binding.guess134.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "134"}
        binding.guess143.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "143"}
        binding.guess145.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "145"}
        binding.guess158.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "158"}
        binding.guess159.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "159"}
        binding.guess49.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "49"}
        binding.guess117.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "117"}
        binding.guess29.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "29"}
        binding.guess28.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "28"}
        binding.guess31.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "31"}
        binding.guess26.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "26"}
        binding.guess103.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "103"}


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}