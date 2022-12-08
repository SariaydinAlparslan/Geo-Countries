package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentAustraliaBinding
import com.sariaydinalparslan.coutries.ui.mySingleton

class Australia : Fragment() {
    private var _binding: FragmentAustraliaBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAustraliaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            binding.guess9.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "9"  }
            binding.guess50.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "50" }
            binding.guess107.setOnClickListener { mySingleton.singlePlayerCountryGuessCode = "107" }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}