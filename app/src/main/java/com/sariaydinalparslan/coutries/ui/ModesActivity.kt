package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.activity_modes.*

class ModesActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modes)

        val db = FirebaseDatabase.getInstance()

        FirebaseAuth.getInstance().uid?.let {
                safeUserId->
            db.getReference("Users").child(safeUserId).child("userName").get().addOnCompleteListener {
                val alp = it.result
                name_text_modes.text=alp.value.toString()
            }
        }
    }
    fun create(view: View) {
        val find = Intent(this@ModesActivity, CreateActivity::class.java)
        startActivity(find)
    }
    fun findmatch(view :View){
        val find = Intent(this@ModesActivity, MatchRoomActivity::class.java)
        startActivity(find)
    }
    fun invite(view: View) {
        /*val find = Intent(this@ModesActivity, InviteRoom::class.java)
        startActivity(find)*/
    }
}
