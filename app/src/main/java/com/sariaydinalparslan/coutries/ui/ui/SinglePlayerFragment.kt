package com.sariaydinalparslan.coutries.ui.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentSinglePlayerBinding
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.SinglePlayerActivity
import kotlinx.android.synthetic.main.fragment_single_player.*

class SinglePlayerFragment : Fragment() {

    private var _binding: FragmentSinglePlayerBinding? = null
    private val binding get() = _binding!!
    private val SPLASH_TIME_OUT:Long=2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSinglePlayerBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var hand = Handler()
        var c = Runnable {
            val intent = Intent(requireContext(),SinglePlayerActivity::class.java)
            startActivity(intent)
        }
        binding.btnStart.setOnClickListener {
            binding.btnStart.visibility=View.GONE
            binding.startCount.visibility = View.VISIBLE
            binding.lottieCount.playAnimation()
            hand.postDelayed(c,SPLASH_TIME_OUT)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}