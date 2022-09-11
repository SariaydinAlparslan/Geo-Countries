package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.data.RoomData
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*

class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)


    }
    //roomname i text yapmak zrunda bırak
    fun create(view: View){
        val alp = create_room_text.text
        val db = FirebaseDatabase.getInstance()
        if (create_room_text.text.isEmpty()){
            Toast.makeText(this, "Please Give a RoomName ", Toast.LENGTH_SHORT).show()
        }
        if (radio_one.isChecked && create_room_text.text.isNotEmpty()){
            FirebaseAuth.getInstance().uid?.let {
                    safeUserId->
                db.getReference("Users").child(safeUserId).get().addOnCompleteListener {
                    val userId= it.result.child("uuid")
                    val userNamex = it.result.child("userName")

                    val gamedata=db.getReference("Room").child("AllPick")
                    val newroom = RoomData(alp.toString(),userId.value.toString(),userNamex.value.toString())
                    gamedata.push().setValue(newroom)
                    nickname()
                }
            }
        }else if (radio_two.isChecked&& create_room_text.text.isNotEmpty()){
            FirebaseAuth.getInstance().uid?.let {
                    safeUserId->
                db.getReference("Users").child(safeUserId).get().addOnCompleteListener {
                    val userId= it.result.child("uuid")
                    val userNamex = it.result.child("userName")

                    val gamedata=db.getReference("Room").child("AllRandom")
                    val newroom = RoomData(alp.toString(),userId.value.toString(),userNamex.value.toString())
                    gamedata.push().setValue(newroom)
                    nickname()
                }
            }
        }
    }
    fun back(view: View){
        val back = Intent(this@CreateActivity, ModesActivity::class.java)
        startActivity(back)
    }
    private fun nickname(){
        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let {
                safeUserId->
            db.getReference("Users").child(safeUserId).child("userName").get().addOnCompleteListener {
                val alp = it.result

                val intent = Intent(this@CreateActivity,GameActivity::class.java)
                intent.putExtra("hostname",alp.value.toString())
                startActivity(intent)
            }
        }

    }
}