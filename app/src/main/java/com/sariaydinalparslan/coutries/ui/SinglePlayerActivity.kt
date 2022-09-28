package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivitySinglePlayerBinding
import com.sariaydinalparslan.coutries.ui.ui.isCodeMaker
import kotlinx.android.synthetic.main.activity_game.*

class SinglePlayerActivity : AppCompatActivity() {
    var emptyCells = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    val max = 100
    val min = 0
    var activeUser = 1
    val total : Int = max - min
    private lateinit var binding: ActivitySinglePlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySinglePlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpSlider()

    }
    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Are You Quit The Match")
        alert.setMessage("If you quit match is over and score update,Are You Sure")
        alert.setPositiveButton("yes") {dialog, which->
            reset()
            //intent
            goBack()
        }
        alert.setNegativeButton("No") {dialog, which->
            Toast.makeText(applicationContext, "DevamKe", Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
    private fun setUpSlider() {
        fluid_slider.positionListener = {pos -> fluid_slider.bubbleText="${min+(total*pos).toInt()}";
            result_guess.text = "${min+(total*pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }
    private fun reset() {
        player1.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9){
            var buttonSelected : Button?
            buttonSelected= when(i){
                1->button
                2->button2
                3->button3
                4->button4
                5->button5
                6->button6
                else -> {button}
            }
            buttonSelected.isEnabled = true
            buttonSelected.text =""
            isMyMove = isCodeMaker
        }
    }
    private fun goBack(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun playNow(buttonSelected : Button, currCell : Int){
        binding.countrytips.visibility = View.VISIBLE
        binding.tips2Text.text = "mySingleton.readyVisitorCountry"
        binding.tips2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.fire))
        Toast.makeText(this, mySingleton.readyVisitorCountry, Toast.LENGTH_SHORT).show()
        Handler().postDelayed({
            binding.countrytips.visibility = View.GONE
            emptyCells.remove(currCell)
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }, 3000)
    }
    fun clickfun(view: View) {
        if (isMyMove){
            val but = view as Button
            var cellOnline = 0
            when (but.id){
                R.id.button -> cellOnline =1
                R.id.button2 -> cellOnline =2
                R.id.button3 -> cellOnline =3
                R.id.button4 -> cellOnline =4
                R.id.button5 -> cellOnline =5
                R.id.button6 -> cellOnline =6
                else -> {cellOnline = 0}
            }
            playerTurn = false
            Handler().postDelayed(Runnable {
                playerTurn = true },600)
            playNow(but,cellOnline)
        }
    }
    fun guess(view: View){
        binding.guessCountryView.visibility = View.VISIBLE
        binding.guess.visibility = View.GONE
        binding.guess2.visibility = View.VISIBLE
    }
    fun guess2(view: View){
        binding.guessCountryView.visibility = View.VISIBLE
        binding.btnResultGuess2.visibility = View.VISIBLE
    }
    fun list_guess(view: View){
        if (binding.resultGuess.text == mySingleton.chosenCountry){
            binding.guessCountryView.visibility = View.GONE
            //1. kazanma yolu
            //you win + score artışı
            Toast.makeText(this, "You Win ", Toast.LENGTH_SHORT).show()
            reset()
            //intent
            goBack()
        }else{
            //1.yanlış
            binding.guessCountryView.visibility = View.GONE
            binding.btnResultGuess1.visibility = View.GONE
            Toast.makeText(this, "You Lose 1. yanlış : alp 1 oldu", Toast.LENGTH_SHORT).show()
        }
    }
    fun list_guess2(view: View){
        if (binding.resultGuess.text == mySingleton.chosenCountry){
            binding.guessCountryView.visibility = View.GONE
            //2. kazanma yolu
            //you win + score
            Toast.makeText(this, "You Win ", Toast.LENGTH_SHORT).show()
            //Room silme işlemi
            reset()
            goBack()
        }else{
            //2.yanlış
            //1.kaybetme yönü
            binding.guessCountryView.visibility = View.GONE
            Toast.makeText(this, "You Lose 2. yanlış Oyun Biter", Toast.LENGTH_SHORT).show()
            //you lose + intent + score
            reset()
            //Room silme işlemi
            goBack()
        }
    }

}