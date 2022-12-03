package com.sariaydinalparslan.coutries.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.MainActivity
import com.sariaydinalparslan.tuto.MyApplication
import kotlinx.android.synthetic.main.fragment_third_screen.view.*

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.finish.setOnClickListener {
            onBoardingFinished()
            navigation()
        }
    }
    fun navigation(){
        val intent = Intent(requireActivity(),MainActivity::class.java)
        startActivity(intent)
    }
    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("com.sariaydinalparslan.coutries", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }
}