package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.data.UsersData
import kotlinx.android.synthetic.main.activity_main.*
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let {
           safeUserId->
           db.getReference("Users").child(safeUserId).child("userName").get().addOnCompleteListener {
               val alp = it.result
               name_text.text=alp.value.toString()
               
           }
       }
    }
    fun findmatch(view: View) {
        val find = Intent(this@MainActivity, ModesActivity::class.java)
        startActivity(find)
    }
    fun score(view: View) {
        val score = Intent(this@MainActivity, ScoreActivity::class.java)
        startActivity(score)
        finish()
    }
    fun notification(view: View) {
        val notification = Intent(this@MainActivity, NotificationActivity::class.java)
        startActivity(notification)
        finish()
    }
}