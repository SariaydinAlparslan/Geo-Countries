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
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityGameBinding
import com.sariaydinalparslan.coutries.ui.ui.code
import com.sariaydinalparslan.coutries.ui.ui.isCodeMaker
import com.sariaydinalparslan.coutries.ui.ui.keyValue
import kotlinx.android.synthetic.main.activity_game.*

var isMyMove = isCodeMaker
var playerTurn = true
class GameActivity : AppCompatActivity() {
    var player2 = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1
    val max = 100
    val min = 0
    val total : Int = max - min
    private lateinit var binding: ActivityGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Toast.makeText(this, mySingleton.createRoomId.toString() , Toast.LENGTH_SHORT).show()
        setUpSlider()
        hostCountry()
        visitorCountry()
        data()
    }
    override fun onBackPressed() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Are You Quit The Match")
        alert.setMessage("If you quit match is over and score update,Are You Sure")
        alert.setPositiveButton("yes") {dialog, which->
            reset()
            removeCode()
            deleteGamersCountries()
            deleteRoom()
            goBack()

        }
        alert.setNegativeButton("No") {dialog, which->
            Toast.makeText(applicationContext, "DevamKe", Toast.LENGTH_SHORT).show()
        }
        alert.show()
    }
    private fun data(){
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var data = snapshot.value
                    if (isMyMove == true){
                        isMyMove = false
                        moveOnline(data.toString(), isMyMove)
                    }else{
                        isMyMove = true
                        moveOnline(data.toString(), isMyMove)
                    }
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {
                    // 1 değişiklik
                    //reset()
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun visitorCountry(){
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .addChildEventListener(object :ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyVisitorCountry =snapshot.value.toString()
                    giris.visibility=View.GONE
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
    private fun hostCountry(){
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .addChildEventListener(object :ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    mySingleton.readyhostCountry =snapshot.value.toString()
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
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
        fluid_slider.positionListener = {pos -> fluid_slider.bubbleText="${min+(total*pos).toInt()}";
            result_guess.text = "${min+(total*pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }
    private fun reset() {
        player1.clear()
        player2.clear()
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
                7->button7
                8->button8
                else -> {button}
            }
            buttonSelected.isEnabled = true
            buttonSelected.text =""
            isMyMove = isCodeMaker
            if (isCodeMaker){
                FirebaseDatabase.getInstance().reference.child("data")
                    .child(code).removeValue()
            }
        }
    }
    private fun goBack(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun removeCode(){
        if (isCodeMaker){
            FirebaseDatabase.getInstance().reference.child("codes")
                .child(keyValue).removeValue()
        }
    }
    fun updateDatabase(cellId : Int){
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .push().setValue(cellId)
    }
    // buttona host bastığında ne olduğu
    fun playNow(buttonSelected : Button, currCell : Int){
        binding.countrytips.visibility = View.VISIBLE
        binding.tips1Text.text = mySingleton.readyVisitorCountry
        binding.tips1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.fire))
        binding.tips2Text.text = mySingleton.readyhostCountry
        binding.tips2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.water))
        val key1 = FirebaseDatabase.getInstance().reference.child("Room")
            .child("AllPick").child(code).root
        Toast.makeText(this, key1.toString(), Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, mySingleton.readyVisitorCountry, Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
               binding.countrytips.visibility = View.GONE
                emptyCells.remove(currCell)
                player1.add(currCell)
                emptyCells.add(currCell)
                buttonSelected.isEnabled = false
                buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            }, 600)
    }
    //butona visitor oyuncu bastığında rakipte ne gözüktüğü
    fun moveOnline(data : String,move : Boolean){
        if (move){
            var buttonSelected : Button ?
            buttonSelected = when(data.toInt()){
                1-> button
                2-> button2
                3-> button3
                4-> button4
                5-> button5
                6-> button6
                7-> button7
                8-> button8
                else-> { button }
            }
           binding.countrytips.visibility = View.VISIBLE
           binding.tips1Text.text = mySingleton.readyVisitorCountry
           binding.tips1.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.fire))
           binding.tips2Text.text = mySingleton.readyhostCountry
           binding.tips2.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.water))
            Handler().postDelayed({
                countrytips.visibility =View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
                buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            },600)
        }
    }
    //basma halinde data gitmesi, hangi butonlar olacağı, sıra geçimi,basma özellikleri
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
                R.id.button7 -> cellOnline =7
                R.id.button8 -> cellOnline =8
                else -> {cellOnline = 0}
            }
            playerTurn = false
            Handler().postDelayed(Runnable {
                playerTurn = true },600)
            playNow(but,cellOnline)
           updateDatabase(cellOnline)
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
            //firebase e cevap gidecek on child change olunca diğer rakip you lose olacak
            Toast.makeText(this, "You Win ", Toast.LENGTH_SHORT).show()
            reset()
            removeCode()
            deleteGamersCountries()
            deleteRoom()
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
            //firebase e cevap gidecek on child change olunca diğer rakip you lose olacak
            Toast.makeText(this, "You Win ", Toast.LENGTH_SHORT).show()
            reset()
            removeCode()
            deleteGamersCountries()
            deleteRoom()
            goBack()

        }else{
            //2.yanlış
            //1.kaybetme yolu
            binding.guessCountryView.visibility = View.GONE
            Toast.makeText(this, "You Lose 2. yanlış Oyun Biter", Toast.LENGTH_SHORT).show()
            //you lose + intent + score
            //firebase e cevap gidecek on child change olunca diğer rakip you win olacak
            reset()
            removeCode()
            deleteGamersCountries()
            deleteRoom()
            goBack()
        }
    }
    fun deleteGamersCountries(){
        FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
            .removeValue()
        FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
            .removeValue()
    }
    fun deleteRoom(){
       FirebaseDatabase.getInstance().reference.child("Room").child("AllPick")
           .child(mySingleton.createRoomId.toString()).removeValue()
        FirebaseDatabase.getInstance().reference.child("Room").child("AllRandom")
            .child(mySingleton.createRoomId.toString()).removeValue()


    }


}