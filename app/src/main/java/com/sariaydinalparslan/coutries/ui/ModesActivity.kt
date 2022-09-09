package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.activity_modes.*

class ModesActivity : AppCompatActivity() {
    val max = 190
    val min = 1
    val total: Int = max - min

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modes)
        slider()
    }
    private fun slider(){
        fluid_slider.positionListener = { pos ->fluid_slider.bubbleText = "${min + (total * pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
        fluid_slider.endTrackingListener = {
            textCountry.text = "Your Country : ${fluid_slider.bubbleText.toString()}"
        }
    }
    fun choose_country(view :View){
        pick_country.visibility = View.GONE
    }
    fun pick(view: View) {
        pick_country.visibility = View.VISIBLE
    }
    fun random(view: View) {
        val back = Intent(this@ModesActivity, MainActivity::class.java)
        startActivity(back)
    }
    fun back_main(view: View) {
        val back = Intent(this@ModesActivity, MainActivity::class.java)
        startActivity(back)
    }
    fun invite(view: View){
        val invite = Intent(this@ModesActivity, MainActivity::class.java)
        startActivity(invite)
    }

}