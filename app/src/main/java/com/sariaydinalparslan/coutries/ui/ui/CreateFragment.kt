package com.sariaydinalparslan.coutries.ui.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.adapters.PickAdapter
import com.sariaydinalparslan.coutries.ui.data.KeyData
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.fragment_create.radio_one
import kotlinx.android.synthetic.main.fragment_create.radio_two
import kotlinx.android.synthetic.main.include_avatar_layout.*
import kotlinx.android.synthetic.main.include_pick_country.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue : String = "null"
class CreateFragment : Fragment() {
    val max = 100
    val min = 0
    val total : Int = max - min

    private lateinit var pickList: ArrayList<KeyData>

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


        btn_choose_country.setOnClickListener {

            includecountrypick.visibility = View.VISIBLE
            setUpSlider()
        }

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
                                @SuppressLint("RestrictedApi")
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
                                                   // val gamedata = FirebaseDatabase.getInstance().reference
                                                  //      .child("RoomList").child("AllPick").
                                                    val gamedata=db.getReference("RoomList").child("AllPick")
                                                    val newroom = RoomData(code,userId.value.toString(),userNamex.value.toString())
                                                    gamedata.push().setValue(newroom)
                                                }
                                            isValueAvaliable(snapshot,code)
                                           checkTemp = false
                                            Handler().postDelayed({
                                                picktoast()
                                                nickname()
                                            },10)
                                        }
                                    },10)
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
                                                randomtoast()
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
    private fun setUpSlider() {
        fluid_slider.positionListener    = {pos -> fluid_slider.bubbleText="${min+(total*pos).toInt()}";countryTextView.text = "${min+(total*pos).toInt()}"}
        fluid_slider.position = 0.3f
        fluid_slider.startText = "$min"
        fluid_slider.endText = "$max"
    }

    private fun picktoast(){
        MotionToast.darkToast(
            requireContext() as Activity, "U Create The Pick Room","Room Name : ${code}" +
                    "Your Country :  ${mySingleton.hostCountry}",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_CENTER,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun randomtoast(){
        MotionToast.darkToast(
            requireContext() as Activity, "U Create The Room","Your Country Will Be Randomly Given ",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_CENTER,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun getKey() {
        val db = FirebaseDatabase.getInstance()
        db.getReference("RoomList").child("AllPick")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (d in snapshot.children){
                            val alp =d.key!!.get(0)
                            Toast.makeText(requireContext(), alp.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
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