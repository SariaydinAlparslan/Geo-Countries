package com.sariaydinalparslan.coutries.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sariaydinalparslan.coutries.databinding.ActivitySelectCountryBinding
import kotlinx.android.synthetic.main.activity_select_country.*

val max = 159
val min = 0
val total : Int = max - min
class SelectCountryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySelectCountryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectCountryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpSlider()
        val sharedPreferences = this.getSharedPreferences("com.sariaydinalparslan.coutries",
            Context.MODE_PRIVATE)

        binding.btnSelect.setOnClickListener {
            sharedPreferences!!.edit().putString("chosen",binding.selectValue.text.toString()).apply()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
    private fun setUpSlider() {
        fluid_slider.positionListener = {pos -> fluid_slider.bubbleText="${min+(total*pos).toInt()}";
            select_value.text = "${min+(total*pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }

}