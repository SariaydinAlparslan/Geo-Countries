package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityGameBinding
import com.sariaydinalparslan.coutries.ui.ui.code
import com.sariaydinalparslan.coutries.ui.ui.isCodeMaker
import com.sariaydinalparslan.coutries.ui.ui.keyValue
import com.sariaydinalparslan.coutries.ui.utils.downloadfromUrl
import com.sariaydinalparslan.coutries.ui.utils.placeholderProgressBar
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.include_guess_view.*
import kotlinx.android.synthetic.main.include_waiting_player.*
import kotlinx.android.synthetic.main.reycler_row.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import kotlin.system.exitProcess

var isMyMove = isCodeMaker
var playerTurn = true

class GameActivity : AppCompatActivity() {
    var player2 = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    val max = 100
    val min = 0
    val total: Int = max - min
    private lateinit var timer: CountDownTimer
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        timer = object : CountDownTimer(140000, 1000) {
            override fun onTick(p0: Long) {
                binding.txtTimer.text = " ${p0 / 1000}"
            }
            override fun onFinish() {
                Toast.makeText(this@GameActivity, "U Draw", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@GameActivity, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
        //bunu onchild change e koy   timer.start()
        setUpSlider()
        hostCountry()
        visitorCountry()
        data()
        someoneQuitReady()
    }
    override fun onBackPressed() {
        //2.kaybetme yolu
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Are You Quit The Match")
        alert.setMessage("If you quit match is over and score update,Are You Sure")
        alert.setPositiveButton("yes") { dialog, which ->

            someoneQuit()
            failToast()
            // removeCode()
            goBack()
            Handler().postDelayed({
                reset()
                //removeCode()
                deleteGamersCountries()
            }, 2000)
        }
        alert.setNegativeButton("No") { dialog, which ->
            Toast.makeText(applicationContext, "DevamKe", Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
    private fun someoneQuit(){
        FirebaseDatabase.getInstance().reference.child("someonequit").child(code)
            .push().setValue("someonequit")
    }

    private fun someoneQuitReady(){
        FirebaseDatabase.getInstance().reference.child("someonequit").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    reset()
                    goBack()

                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun data() {
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var data = snapshot.value
                    if (isMyMove == true) {
                        isMyMove = false
                        moveOnline(data.toString(), isMyMove)
                    } else {
                        isMyMove = true
                        moveOnline(data.toString(), isMyMove)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    reset()

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun visitorCountry() {
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyVisitorCountry = snapshot.value.toString()
                    binding.includeWaiting.giris.visibility = View.GONE
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun hostCountry() {
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyhostCountry = snapshot.value.toString()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {

                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun setUpSlider() {
        fluid_slider.positionListener = { pos ->
            fluid_slider.bubbleText = "${min + (total * pos).toInt()}";
            result_guess.text = "${min + (total * pos).toInt()}"
        }
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }

    private fun reset() {
        player1.clear()
        player2.clear()
        emptyCells.clear()
        activeUser = 1
        for (i in 1..9) {
            var buttonSelected: Button?
            buttonSelected = when (i) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                else -> {
                    button
                }
            }
            buttonSelected.isEnabled = true
            buttonSelected.text = ""
            isMyMove = isCodeMaker
            if (isCodeMaker) {
                FirebaseDatabase.getInstance().reference.child("data")
                    .child(code).removeValue()
            }
        }
    }

    private fun goBack() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        finishAffinity()
    }

    private fun removeCode() {
        if (isCodeMaker) {
            FirebaseDatabase.getInstance().reference.child("Room").child("AllPick")
                .child(keyValue).removeValue()
        }
    }

    fun updateDatabase(cellId: Int) {
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .push().setValue(cellId)
    }

    // buttona basan kiside görünen
    fun playNow(buttonSelected: Button, currCell: Int) {
        val random = (1..10).shuffled().last()
        binding.countryTips.countrytips.visibility = View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("images").child("1")
                .child(random.toString()).get().addOnSuccessListener {
                    binding.countryTips.tips1.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            binding.countryTips.tips1Text.text = random.toString()
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                emptyCells.remove(currCell)
                player1.add(currCell)
                emptyCells.add(currCell)
                buttonSelected.isEnabled = false
            }, 6000)
        //
        FirebaseDatabase.getInstance().reference.child("images").child("2")
            .child("3").get().addOnSuccessListener {
                binding.countryTips.tips2.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
            }
        binding.countryTips.tips2Text.text = random.toString()
        buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        Handler().postDelayed({
            binding.countryTips.countrytips.visibility = View.GONE
            emptyCells.remove(currCell)
            player1.add(currCell)
            emptyCells.add(currCell)
            buttonSelected.isEnabled = false
        }, 6000)
    }
    // buttona basan kisi değilde visitorda görünen
    fun moveOnline(data: String, move: Boolean) {
        val random = (1..10).shuffled().last()
        if (move) {
            var buttonSelected: Button?
            buttonSelected = when (data.toInt()) {
                1 -> button
                2 -> button2
                3 -> button3
                4 -> button4
                5 -> button5
                6 -> button6
                else -> {
                    button
                }
            }
            binding.countryTips.countrytips.visibility = View.VISIBLE
            FirebaseDatabase.getInstance().reference.child("images").child("1")
                .child(random.toString()).get().addOnSuccessListener {
                    binding.countryTips.tips1.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            binding.countryTips.tips1Text.text = random.toString()
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
            }, 6000)
            //
            FirebaseDatabase.getInstance().reference.child("images").child("2")
                .child("3").get().addOnSuccessListener {
                    binding.countryTips.tips2.downloadfromUrl(it.value.toString(), placeholderProgressBar(this))
                }
            binding.countryTips.tips2Text.text = random.toString()
            buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            Handler().postDelayed({
                binding.countryTips.countrytips.visibility = View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
            }, 6000)

        }
    }
    //basma halinde data gitmesi, hangi butonlar olacağı, sıra geçimi,basma özellikleri
    fun clickfun(view: View) {
        if (isMyMove) {
            val but = view as Button
            var cellOnline = 0
            when (but.id) {
                R.id.button -> cellOnline = 1
                R.id.button2 -> cellOnline = 2
                R.id.button3 -> cellOnline = 3
                R.id.button4 -> cellOnline = 4
                R.id.button5 -> cellOnline = 5
                R.id.button6 -> cellOnline = 6
                else -> {
                    cellOnline = 0
                }
            }
            playerTurn = false
            Handler().postDelayed(Runnable {
                playerTurn = true
            }, 600)
            playNow(but, cellOnline)
            updateDatabase(cellOnline)
        }
    }

    fun guess(view: View) {
        binding.guessCountryView.guessCountryView.visibility = View.VISIBLE
        binding.guess.visibility = View.GONE
        binding.guess2.visibility = View.VISIBLE
    }

    fun guess2(view: View) {
        binding.guessCountryView.guessCountryView.visibility = View.VISIBLE
        binding.guessCountryView.btnResultGuess2.visibility = View.VISIBLE
    }

    fun list_guess(view: View) {
        if (binding.guessCountryView.resultGuess.text == mySingleton.chosenCountry) {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            //1. kazanma yolu
            //firebase e cevap gidecek on child change olunca diğer rakip you lose olacak
            binding.winViewOnline.winner.visibility = View.VISIBLE
            winToast()
            Handler().postDelayed({
                reset()
                goBack()
                removeCode()
                deleteGamersCountries()
            }, 4500)
        } else {
            //1.yanlış
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            binding.guessCountryView.btnResultGuess1.visibility = View.GONE
            Toast.makeText(this, "You Lose 1. yanlış : alp 1 oldu", Toast.LENGTH_SHORT).show()
        }
    }
    fun list_guess2(view: View) {
        if (binding.guessCountryView.resultGuess.text == mySingleton.chosenCountry) {
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            //2. kazanma yolu
            //firebase e cevap gidecek on child change olunca diğer rakip you lose olacak
            binding.winViewOnline.winner.visibility = View.VISIBLE
            winToast()
            Handler().postDelayed({
                reset()
                goBack()
                removeCode()
               // deleteGamersCountries()
            }, 4500)
        } else {
            //2.yanlış
            //1.kaybetme yolu
            //firebase e cevap gidecek on child change olunca diğer rakip you win olacak
            binding.guessCountryView.guessCountryView.visibility = View.GONE
            failToast()
            binding.loseViewOnline.loser.visibility = View.VISIBLE
            reset()
            removeCode()
            deleteGamersCountries()
            Handler().postDelayed({
                goBack()
            }, 4500)
        }
    }

    fun deleteGamersCountries() {
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .removeValue()
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .removeValue()
    }

    //hatalı kod u fail olunca main activity dönmüyor

    private fun winToast(){
        MotionToast.darkToast(
            this, "U WİN","" ,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun failToast(){
        MotionToast.darkToast(
            this, "U FAİL","" ,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, www.sanju.motiontoast.R.font.helvetica_regular))
    }
}
