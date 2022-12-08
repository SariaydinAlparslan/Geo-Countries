package com.sariaydinalparslan.coutries.ui.guess

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentGuessBinding
import kotlinx.android.synthetic.main.activity_single_player.*

class Guess : Fragment() {

    private var _binding: FragmentGuessBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGuessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(Asia())

        binding.asia.setOnClickListener {  replaceFragment(Asia()) }
        binding.europe.setOnClickListener { replaceFragment(Europe())  }
        binding.africa.setOnClickListener {  replaceFragment(Africa()) }
        binding.america.setOnClickListener {  replaceFragment(America()) }
        binding.avustralya.setOnClickListener {  replaceFragment(Australia()) }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment (fragment : Fragment){

        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.continent_layout,fragment)
        fragmentTransaction.commit()
    }
}