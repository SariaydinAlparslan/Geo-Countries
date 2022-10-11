package com.sariaydinalparslan.coutries.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityMainBinding
import com.sariaydinalparslan.coutries.ui.ui.*
import kotlinx.android.synthetic.main.fragment_settings.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private  var backPressedTime = 0L

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        code = ""
        keyValue = ""
        replaceFragment(HomeFragment())

       binding.bottom.background =null
       binding.bottom.menu.getItem(2).isEnabled = false

        binding.bottom.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomHome -> replaceFragment(HomeFragment())
                R.id.bottomProfile ->replaceFragment(SettingsFragment())
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
    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
        }else{
            Toast.makeText(this," Press back again to exit app", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()

    }
    override fun onDestroy() {
        super.onDestroy()
    }
    private fun replaceFragment (fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

    fun avatar(view:View){
        val but = view as TextView
        when(but.id){
            R.id.radio_1 -> mySingleton.avatarId = "one"
            R.id.radio_2 -> mySingleton.avatarId = "two"
            R.id.radio_3 -> mySingleton.avatarId = "three"
            R.id.radio_4 -> mySingleton.avatarId = "four"
            R.id.radio_5 -> mySingleton.avatarId = "five"
            R.id.radio_6 -> mySingleton.avatarId = "six"
            R.id.radio_7 -> mySingleton.avatarId = "seven"
            R.id.radio_8 -> mySingleton.avatarId = "eight"
            R.id.radio_9 -> mySingleton.avatarId = "nine"
            R.id.radio_10 -> mySingleton.avatarId = "teen"
            R.id.radio_11 -> mySingleton.avatarId = "eleven"
            R.id.radio_12 -> mySingleton.avatarId = "twelve"
            R.id.radio_13 -> mySingleton.avatarId = "thirteen"
            R.id.radio_14 -> mySingleton.avatarId = "fourteen"
            R.id.radio_15 -> mySingleton.avatarId = "fifteen"
            R.id.radio_16 -> mySingleton.avatarId = "sixteen"
            R.id.radio_17 -> mySingleton.avatarId = "seventeen"
            R.id.radio_18 -> mySingleton.avatarId = "eighteen"
            R.id.radio_19 -> mySingleton.avatarId = "nineteen"
            R.id.radio_20 -> mySingleton.avatarId = "twenty"
            R.id.radio_21 -> mySingleton.avatarId = "twentyone"
            R.id.radio_22 -> mySingleton.avatarId = "twentytwo"
            R.id.radio_23 -> mySingleton.avatarId = "twentythree"
            R.id.radio_24 -> mySingleton.avatarId = "twentefour"
            R.id.radio_25 -> mySingleton.avatarId = "twentyfive"
            R.id.radio_26 -> mySingleton.avatarId = "twentysix"
            R.id.radio_27 -> mySingleton.avatarId = "twentyseven"
            R.id.radio_28 -> mySingleton.avatarId = "twentyeight"
            R.id.radio_29 -> mySingleton.avatarId = "twentynine"
            R.id.radio_30 -> mySingleton.avatarId = "thirty"
            R.id.radio_31 -> mySingleton.avatarId = "thirtyone"
            R.id.radio_32 -> mySingleton.avatarId = "thirtytwo"
            R.id.radio_33 -> mySingleton.avatarId = "thirtythree"
            R.id.radio_34 -> mySingleton.avatarId = "thirtyfour"
            R.id.radio_35 -> mySingleton.avatarId = "thirtyfive"
            R.id.radio_36 -> mySingleton.avatarId = "thirtysix"

        }
        val sharedPreferences = this.getSharedPreferences("com.sariaydinalparslan.coutries",
            Context.MODE_PRIVATE)
        sharedPreferences!!.edit().putString("pref",mySingleton.avatarId!!).apply()
        replaceFragment(HomeFragment())
        binding.bottom.selectedItemId = R.id.placeholder
        includeavatar.visibility = View.GONE
        change_avatar.visibility=View.VISIBLE
        share.visibility = View.VISIBLE
        rate.visibility=View.VISIBLE
    }
}
