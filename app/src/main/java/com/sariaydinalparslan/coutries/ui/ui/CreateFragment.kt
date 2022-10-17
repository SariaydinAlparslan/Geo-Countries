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
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentCreateBinding
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.mySingleton
import com.sariaydinalparslan.coutries.ui.utils.isValueAvaliable
import kotlinx.android.synthetic.main.fragment_create.*
import kotlinx.android.synthetic.main.include_pick_country.*
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

var isCodeMaker = true
var code = "null"
var codeFound = false
var checkTemp = true
var keyValue : String = "null"
class CreateFragment() : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val randomCountry = (1..159).shuffled().last()
        mySingleton.multiPlayerCountryCode= randomCountry.toString()

        binding.radioOne.text = getString(R.string.all_pick) +
                "("+ getString(R.string.chosen_country)  +  " : ${mySingleton.chosenCountry})"

        btn_create.setOnClickListener {
            val db = FirebaseDatabase.getInstance()

            if (binding.createRoomText.text.isEmpty()){
                Toast.makeText(requireContext(), getString(R.string.give_roomname), Toast.LENGTH_SHORT).show()
            }
            if (binding.radioOne.isChecked && binding.createRoomText.text.isNotEmpty()){
                code = "null"
                codeFound = false
                checkTemp = true
                keyValue = "null"
                code = create_room_text.text.toString()
                isCodeMaker = true
                //allpick, game activity e geçiş
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
                                                    val newroom = RoomData(code,mySingleton.hostName)
                                                    gamedata.push().setValue(newroom)
                                                    //hostname gönderme
                                                    FirebaseDatabase.getInstance().reference.child("hostname").child(code)
                                                        .push().setValue(mySingleton.hostName)
                                                    //hostcountry gönderme
                                                    FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
                                                        .push().setValue(mySingleton.chosenCountry)
                                                }
                                            isValueAvaliable(snapshot,code)
                                           checkTemp = false
                                            Handler().postDelayed({
                                                picktoast()
                                                intent()
                                            },10)
                                        }
                                    },10)
                                }
                                override fun onCancelled(error: DatabaseError) {
                                }
                            })


            }else if (binding.radioTwo.isChecked&& binding.createRoomText.text.isNotEmpty()){
                code = "null"
                codeFound = false
                checkTemp = true
                keyValue = "null"
                code =binding.createRoomText.text.toString()
                isCodeMaker = true
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
                                                    val newroom = RoomData(code,mySingleton.hostName)
                                                    gamedata.push().setValue(newroom)

                                                    FirebaseDatabase.getInstance().reference.child("hostname").child(code)
                                                        .push().setValue(mySingleton.hostName)
                                                    //hostcountry gönderme
                                                    FirebaseDatabase.getInstance().reference.child("hostcountry").child(code)
                                                        .push().setValue(mySingleton.multiPlayerCountryCode)
                                                }
                                            isValueAvaliable(snapshot, code)
                                            checkTemp = false
                                            Handler().postDelayed({
                                                randomtoast()
                                                intent()
                                            },10)
                                        }
                                    },10)
                                }
                                override fun onCancelled(error: DatabaseError) {
                                }
                            })
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun intent(){
        val intent = Intent(requireContext(), GameActivity::class.java)
        startActivity(intent)
        requireActivity().finishAffinity()
    }
    private fun picktoast(){
        MotionToast.darkToast(
            requireContext() as Activity, getString(R.string.create_room1), getString(R.string.room_name)+ ": ${code}" +
                    getString(R.string.chosen_country) +":  ${mySingleton.chosenCountry}",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_CENTER,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
    }
    private fun randomtoast(){
        MotionToast.darkToast(
            requireContext() as Activity, "U Create The Random Room",getString(R.string.random_room_message),
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_CENTER,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(requireContext(), www.sanju.motiontoast.R.font.helvetica_regular))
    }
}


