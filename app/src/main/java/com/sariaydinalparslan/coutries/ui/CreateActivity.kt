package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.data.RoomData
import kotlinx.android.synthetic.main.activity_create.*
import kotlinx.android.synthetic.main.activity_main.*

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue : String = "null"
class CreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)




    }
    //roomname i text yapmak zrunda bırak
    fun create(view: View){
        val db = FirebaseDatabase.getInstance()
        if (create_room_text.text.isEmpty()){
            Toast.makeText(this, "Please Give a RoomName ", Toast.LENGTH_SHORT).show()
        }
        if (radio_one.isChecked && create_room_text.text.isNotEmpty()){
                    code = create_room_text.text.toString()
                    codeFound = false
                    checkTemp = true
                    keyValue = "null"
                    isCodeMaker = true

            val hostCountry = "France"
            mySingleton.hostCountry= hostCountry

            FirebaseAuth.getInstance().uid?.let {
                    safeUserId->
                db.getReference("Users").child(safeUserId).get().addOnCompleteListener {
                    val userId= it.result.child("uuid")
                    val userNamex = it.result.child("userName")
                    FirebaseDatabase.getInstance().reference.child("Room")
                        .child("AllPick")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var check = isValueAvaliable(snapshot, code)
                                Handler().postDelayed({
                                    if (check == true){
                                    }else{
                                        FirebaseDatabase.getInstance().reference.child("Room")
                                            .child("AllPick")
                                            .push()
                                            .setValue(code)
                                            .addOnCompleteListener {
                                                val gamedata=db.getReference("RoomList").child("AllPick")
                                                val newroom = RoomData(code,userId.value.toString(),userNamex.value.toString())
                                                gamedata.push().setValue(newroom)
                                            }
                                        isValueAvaliable(snapshot, code)
                                        checkTemp = false
                                        Handler().postDelayed({
                                            nickname()
                                        },300)
                                    }
                                },400)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
                }
            }
        }else if (radio_two.isChecked&& create_room_text.text.isNotEmpty()){
            code = create_room_text.text.toString()
            codeFound = false
            checkTemp = true
            keyValue = "null"
            isCodeMaker = true
            FirebaseAuth.getInstance().uid?.let {
                    safeUserId->
                db.getReference("Users").child(safeUserId).get().addOnCompleteListener {
                    val userId= it.result.child("uuid")
                    val userNamex = it.result.child("userName")
                    FirebaseDatabase.getInstance().reference.child("Room")
                        .child("AllRandom")
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var check = isValueAvaliable(snapshot, code)
                                Handler().postDelayed({
                                    if (check == true){
                                    }else{
                                        FirebaseDatabase.getInstance().reference.child("Room")
                                            .child("AllRandom")
                                            .push()
                                            .setValue(code)
                                            .addOnCompleteListener {
                                                val gamedata=db.getReference("RoomList").child("AllRandom")
                                                val newroom = RoomData(code,userId.value.toString(),userNamex.value.toString())
                                                gamedata.push().setValue(newroom)
                                            }
                                        isValueAvaliable(snapshot, code)
                                        checkTemp = false
                                        //roomadapter sistemi kurulabilir
                                        Handler().postDelayed({
                                            nickname()
                                        },300)
                                    }
                                },400)
                            }
                            override fun onCancelled(error: DatabaseError) {
                            }
                        })
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