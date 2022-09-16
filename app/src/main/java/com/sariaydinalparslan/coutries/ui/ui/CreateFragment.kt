package com.sariaydinalparslan.coutries.ui.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.fragment_create.btn_create
import kotlinx.android.synthetic.main.fragment_create.create_room_text
import kotlinx.android.synthetic.main.fragment_create.radio_one
import kotlinx.android.synthetic.main.fragment_create.radio_two

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue : String = "null"
class CreateFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_create.setOnClickListener {

            val db = FirebaseDatabase.getInstance()
            if (create_room_text.text.isEmpty()){
                Toast.makeText(requireContext(), "Please Give a RoomName ", Toast.LENGTH_SHORT).show()
            }
            if (radio_one.isChecked && create_room_text.text.isNotEmpty()){
                code = create_room_text.text.toString()
                codeFound = false
                checkTemp = true
                keyValue = "null"
                isCodeMaker = true
                //country aktarımı
                val hostCountry = "France"
                mySingleton.hostCountry= hostCountry

                FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
                    .push().setValue(mySingleton.hostCountry)

                //allpick, game activity e geçiş
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
                                            isValueAvaliable(snapshot,code)
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
                                    var check = isValueAvaliable(snapshot,code)
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
    }
    private fun nickname(){
        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let {
                safeUserId->
            db.getReference("Users").child(safeUserId).child("userName").get().addOnCompleteListener {
                val alp = it.result
                val intent = Intent(requireContext(), GameActivity::class.java)
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