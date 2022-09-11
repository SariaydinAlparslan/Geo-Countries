package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityModesBinding
import com.sariaydinalparslan.coutries.ui.adapters.RandomAdapter
import com.sariaydinalparslan.coutries.ui.data.GameData
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_modes.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random
import kotlin.random.Random.Default.nextInt

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
