package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.adapters.PickAdapter
import com.sariaydinalparslan.coutries.ui.adapters.RandomAdapter
import com.sariaydinalparslan.coutries.ui.data.RoomData
import kotlinx.android.synthetic.main.activity_match_room.*

class MatchRoomActivity : AppCompatActivity() {
    private lateinit var roomlistRecyclerView: RecyclerView
    private lateinit var pickroomlistRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var empList: ArrayList<RoomData>
    private lateinit var pickList: ArrayList<RoomData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_room)

        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let { safeUserId ->
            db.getReference("Users").child(safeUserId).child("userName").get()
                .addOnCompleteListener {
                    val alp = it.result
                    mySingleton.chosenlandmark = alp.value.toString()
                }
        }

        auth = Firebase.auth
        roomlistRecyclerView = findViewById(R.id.recyclerandomroomlist)
        roomlistRecyclerView.layoutManager = LinearLayoutManager(this)
        pickroomlistRecyclerView = findViewById(R.id.recycleroomlist)
        pickroomlistRecyclerView.layoutManager = LinearLayoutManager(this)
        empList = ArrayList<RoomData>()
        pickList = ArrayList<RoomData>()
        getRandomRoomList()
        getPickRoomList()
    }

    fun gir(view: View) {
        codeFound = false
        checkTemp = true
        keyValue = "null"
        code = edittext.text.toString()
        if (code != "null" && code != ""){
            isCodeMaker = false
        FirebaseDatabase.getInstance().reference.child("codes")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var data: Boolean = isValueAvaliable(snapshot, code)
                    Handler().postDelayed({
                        if (data == true) {
                            codeFound = true
                            val intent = Intent(this@MatchRoomActivity,GameActivity::class.java)
                            startActivity(intent)
                        } else {
                        }
                    }, 2000)
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }else
    { }
    }

    private fun getRandomRoomList() {
        val db = FirebaseDatabase.getInstance()
        db.getReference("Room").child("AllRandom")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    empList.clear()
                    if (snapshot.exists()){
                        for (d in snapshot.children){
                            val e = d.getValue(RoomData::class.java)
                            empList.add(e!!)
                        }
                        val rAdapter = RandomAdapter(empList)
                        roomlistRecyclerView.adapter = rAdapter
                       // rAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun getPickRoomList() {
        val db = FirebaseDatabase.getInstance()
        db.getReference("Room").child("AllPick")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    pickList.clear()
                    if (snapshot.exists()){
                        for (d in snapshot.children){
                            val e = d.getValue(RoomData::class.java)
                            pickList.add(e!!)
                        }
                        val pAdapter = PickAdapter(pickList)
                        pickroomlistRecyclerView.adapter = pAdapter
                        // rAdapter.notifyDataSetChanged()
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
}