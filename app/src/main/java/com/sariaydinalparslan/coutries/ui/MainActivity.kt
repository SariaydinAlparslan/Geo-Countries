package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.activity_modes.*
private lateinit var databaseReference: DatabaseReference
private  var firebaseUser: FirebaseUser? = null
private lateinit var auth : FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)









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