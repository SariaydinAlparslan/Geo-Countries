package com.sariaydinalparslan.coutries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityMainBinding
import com.sariaydinalparslan.coutries.ui.ui.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(HomeFragment())

        bottom.background =null
        bottom.menu.getItem(2).isEnabled = false

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomHome -> replaceFragment(HomeFragment())
                R.id.bottomProfile ->replaceFragment(ProfileFragment())
                R.id.bottomList ->replaceFragment(SearchFragment())
                R.id.bottomSingle ->replaceFragment(SinglePlayerFragment())
                else ->{
                }
            }
            true
        }
        binding.fab.setOnClickListener {
            replaceFragment(CreateFragment())
        }


    }

    private fun replaceFragment (fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}