package com.sariaydinalparslan.coutries.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.MainActivity

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed({
            if(onBoardingFinished()){
              val intent = Intent(requireActivity(),MainActivity::class.java)
                startActivity(intent)
            }else{
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 500)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun onBoardingFinished(): Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished", false)
    }

}