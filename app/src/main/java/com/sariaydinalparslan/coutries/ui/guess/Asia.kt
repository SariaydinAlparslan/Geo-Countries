package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.databinding.FragmentAsiaBinding
import com.sariaydinalparslan.coutries.ui.mySingleton

class Asia : Fragment() {
    private var _binding: FragmentAsiaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAsiaBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.guess1.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "1"}
        binding.guess8.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "8"}
        binding.guess13.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "13"}
        binding.guess33.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "33"}
        binding.guess11.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "11"}
        binding.guess65.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "65"}
        binding.guess66.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "66"}
        binding.guess67.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "67"}
        binding.guess68.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "68"}
        binding.guess14.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "14"}
        binding.guess74.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "74"}
        binding.guess76.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "76"}
        binding.guess79.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "79"}
        binding.guess80.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "80"}
        binding.guess75.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "75"}
        binding.guess81.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "81"}
        binding.guess82.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "82"}
        binding.guess84.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "84"}
        binding.guess92.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "92"}
        binding.guess93.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "93"}
        binding.guess157.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "157"}
        binding.guess100.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "100"}
        binding.guess105.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "105"}
        binding.guess112.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "112"}
        binding.guess113.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "113"}
        binding.guess116.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "116"}
        binding.guess119.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "119"}
        binding.guess120.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "120"}
        binding.guess122.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "122"}
        binding.guess124.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "124"}
        binding.guess127.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "127"}
        binding.guess137.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "137"}
        binding.guess140.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "140"}
        binding.guess141.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "141"}
        binding.guess142.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "142"}
        binding.guess130.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "130"}
        binding.guess144.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "144"}
        binding.guess147.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "147"}
        binding.guess149.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "149"}
        binding.guess153.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "153"}
        binding.guess156.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "156"}
        binding.guess44.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "44"}
        binding.guess70.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "70"}
        binding.guess38.setOnClickListener {mySingleton.singlePlayerCountryGuessCode = "38"}

    }
override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
}
}