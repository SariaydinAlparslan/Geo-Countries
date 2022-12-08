package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentAmericaBinding
import com.sariaydinalparslan.coutries.databinding.FragmentAustraliaBinding
import com.sariaydinalparslan.coutries.ui.mySingleton

class America : Fragment() {
    private var _binding: FragmentAmericaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAmericaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guess6.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "1"}
        binding.guess12.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "12"}
        binding.guess15.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "15"}
        binding.guess16.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "16"}
        binding.guess20.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "20"}
        binding.guess27.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "27"}
        binding.guess30.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "30"}
        binding.guess32.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "32"}
        binding.guess35.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "35"}
        binding.guess42.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "42"}
        binding.guess43.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "43"}
        binding.guess45.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "45"}
        binding.guess47.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "47"}
        binding.guess37.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "37"}
        binding.guess58.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "58"}
        binding.guess59.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "59"}
        binding.guess60.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "60"}
        binding.guess61.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "61"}
        binding.guess73.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "73"}
        binding.guess22.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "22"}
        binding.guess97.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "97"}
        binding.guess108.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "108"}
        binding.guess114.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "114"}
        binding.guess115.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "115"}
        binding.guess121.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "121"}
        binding.guess126.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "126"}
        binding.guess151.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "151"}
        binding.guess152.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "152"}
        binding.guess155.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "155"}
        binding.guess117.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "117"}
        binding.guess158.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "158"}

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}