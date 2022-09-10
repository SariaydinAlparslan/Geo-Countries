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
    val max = 195
    val min = 1
    val total: Int = max - min
    private lateinit var binding: ActivityModesBinding
    private lateinit var randomRecyclerView: RecyclerView
    private lateinit var auth : FirebaseAuth
    private lateinit var db : DatabaseReference
    private lateinit var rAdapter : Adapter
    private lateinit var empList : ArrayList<GameData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        randomRecyclerView = findViewById(R.id.recyclerRandom)
        randomRecyclerView.layoutManager = LinearLayoutManager(this)
        empList = ArrayList<GameData>()
        slider()
        auth = Firebase.auth

    }

    private fun getRandomPlayers() {
     db = FirebaseDatabase.getInstance().getReference("Game")
        db.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                empList.clear()
                if (snapshot.exists()){
                    for (d in snapshot.children){
                        val e = d.getValue(GameData::class.java)
                        empList.add(e!!)
                    }
                    val rAdapter = RandomAdapter(empList)
                    randomRecyclerView.adapter = rAdapter
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    private fun slider(){
        fluid_slider.positionListener = { pos ->fluid_slider.bubbleText = "${min + (total * pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
        fluid_slider.endTrackingListener = {
            pick_country.text = "Your Country : ${fluid_slider.bubbleText.toString()}"
        }
    }


    fun countryPick(view: View) {
        list.visibility = View.VISIBLE
    }
    fun choose_country(view :View){
        list.visibility = View.GONE
        //2reycler list gelmeli aynı zamanda 1sen online listeye eklenmelisin // ap mode da seçtiğin ülke firebaseye gitmeli
    }
    fun randomPick(view: View) {
        //2reycler list gelmeli aynı zamanda 1sen online listeye eklenmelisin
        val random = (0..100).shuffled().last()
        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let {
                safeUserId->
            db.getReference("Users").child(safeUserId).get().addOnCompleteListener {
                val userNamex = it.result.child("userName")
                val userId= it.result.child("uuid")
                val gamedata=db.getReference("Game")
                val newuser = GameData(random,userId.value.toString(),userNamex.value.toString())
                gamedata.push().setValue(newuser)
            }
        }
        getRandomPlayers()

    }
    fun back_main(view: View) {
        val back = Intent(this@ModesActivity, MainActivity::class.java)
        startActivity(back)
    }
    fun invite(view: View){
        val invite = Intent(this@ModesActivity, MainActivity::class.java)
        startActivity(invite)
    }

}