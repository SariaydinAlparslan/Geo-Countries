package com.sariaydinalparslan.coutries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.android.synthetic.main.activity_game.view.*

var isMyMove = isCodeMaker
var playerTurn = true
class GameActivity : AppCompatActivity() {
    var player2 = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //  btnreset.setOnClickListener {
        //     reset()
        //     removeCode()
        // }

        val intent = intent
        val visitorName = intent.getStringExtra("visitorName")
        visitornickname.text = visitorName

        hostCountry()
        visitorCountry()
        data()

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
    //oyun bitiminde ya hepsi disable hali yada o durumdan bahsediyor
    fun buttonDisable(){
        for (i in 1..9){
            val buttonSelected = when(i){
                1 ->button
                2 ->button2
                3 ->button3
                4 ->button4
                5 ->button5
                6 ->button6
                7 ->button7
                8 ->button8
                else -> { button }
            }
            if (buttonSelected.isEnabled == true)
                buttonSelected.isEnabled = false
        }
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
    fun removeCode(){
        if (isCodeMaker){
            FirebaseDatabase.getInstance().reference.child("codes")
                .child(keyValue).removeValue()
        }
    }

    fun updateDatabase(cellId : Int){
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .push().setValue(cellId)
    }
    // buttona bastığında ne olduğu
    fun playNow(buttonSelected : Button, currCell : Int){
        tips.visibility = View.VISIBLE
        tips.setImageDrawable(ContextCompat.getDrawable(applicationContext, R.drawable.fire))
        Toast.makeText(this, mySingleton.readyVisitorCountry, Toast.LENGTH_SHORT).show()
            Handler().postDelayed({
                tips.visibility = View.GONE
                emptyCells.remove(currCell)
                player1.add(currCell)
                emptyCells.add(currCell)
                buttonSelected.isEnabled = false
                buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            }, 2000)
       // checkWinner()
    }
    //butona bir oyuncu bastığında rakipte ne gözüktüğü
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
           Toast.makeText(this, mySingleton.readyhostCountry, Toast.LENGTH_SHORT).show()
           tips2.setImageDrawable( ContextCompat.getDrawable(applicationContext,R.drawable.water))
            tips2.visibility = View.VISIBLE
            Handler().postDelayed({
                tips2.visibility =View.GONE
                player2.add(data.toInt())
                emptyCells.add(data.toInt())
                buttonSelected.isEnabled = false
                buttonSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
            },2000)
            //checkWinner()
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
}