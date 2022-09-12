package com.sariaydinalparslan.coutries.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.data.UsersData
import kotlinx.android.synthetic.main.activity_game.*

var isMyMove = isCodeMaker
var playerTurn = true
var code = "null"
var keyValue : String = "null"
class GameActivity : AppCompatActivity() {
    var player2 = ArrayList<Int>()
    var player1 = ArrayList<Int>()
    var emptyCells = ArrayList<Int>()
    var activeUser = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //code = room açtığın oda
       // code = "hello motherfucker"
        keyValue = code

        val intent = intent
        val roomName = intent.getStringExtra("roomName")
        val roomUserId = intent.getStringExtra("roomUserId")
        val roomUserName = intent.getStringExtra("roomUserName")
        val visitorName = intent.getStringExtra("visitorName")
        visitornickname.text = visitorName

        val intent2 = intent
        val hostname = intent2.getStringExtra("hostname")
        hostnametext.text = hostname


        btnreset.setOnClickListener {
            reset()
            removeCode()
        }

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
                   // reset()
                   // Toast.makeText(this@OnlineMultiPlayerActivity, "Gamereset", Toast.LENGTH_SHORT).show()
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

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
                9->button9
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
                9-> button9
                else-> { button }
            }
            buttonSelected.text = "x"
          //  idTurn.text = "Turn : Player 1"
            buttonSelected.setTextColor(Color.parseColor("#0222B884"))
            player2.add(data.toInt())
            emptyCells.add(data.toInt())
            buttonSelected.isEnabled = false
            //checkWinner()
        }
    }
    fun updateDatabase(cellId : Int){
        FirebaseDatabase.getInstance().reference.child("data").child(code)
            .push().setValue(cellId)
    }

    fun playNow(buttonSelected : Button, currCell : Int){
        buttonSelected.text = "x"
        emptyCells.remove(currCell)
        //idTurn.text = "Turn : Player 2"
        buttonSelected.setTextColor(Color.parseColor("#EC0C0C"))
        player1.add(currCell)
        emptyCells.add(currCell)
        buttonSelected.isEnabled = false
       // checkWinner()
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
                R.id.button7 -> cellOnline =7
                R.id.button8 -> cellOnline =8
                R.id.button9 -> cellOnline =9
                else -> {cellOnline = 0}
            }
            playerTurn = false
            Handler().postDelayed(Runnable {
                playerTurn = true },600)
            playNow(but,cellOnline)
           updateDatabase(cellOnline)
        }
    }
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
                9 ->button9
                else -> { button }
            }
            if (buttonSelected.isEnabled == true)
                buttonSelected.isEnabled = false
        }
    }
    fun isValueAvaliable(snapshot : DataSnapshot,code:String):Boolean{
        var data = snapshot.children
        data.forEach{
            var value = it.getValue().toString()
            if (value == code){
                keyValue = it.key.toString()
                return true
            }
        }
        return false
    }
}