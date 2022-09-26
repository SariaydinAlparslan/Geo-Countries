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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.databinding.FragmentCreateBinding
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.fragment_create.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val alpulke = "Hollanda"
        mySingleton.chosenCountry = alpulke

        setUpSlider()
        btn_choose_country.setOnClickListener {
            includecountrypick.visibility = View.VISIBLE
        }

        btn_create.setOnClickListener {
            val db = FirebaseDatabase.getInstance()

            if (binding.createRoomText.text.isEmpty()){
                Toast.makeText(requireContext(), "Please Give a RoomName ", Toast.LENGTH_SHORT).show()
            }
            if (binding.radioOne.isChecked && binding.createRoomText.text.isNotEmpty()){
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
                                                    val gamedata=db.getReference("RoomList").child("AllPick")
                                                    val newroom = RoomData(code,mySingleton.hostName)
                                                    gamedata.push().setValue(newroom)
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
                code =binding.createRoomText.text.toString()
                codeFound = false
                checkTemp = true
                keyValue = "null"
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
}
fun isValueAvaliable(snapshot : DataSnapshot,code:String):Boolean{
    var data = snapshot.children
    data.forEach{
        var value = it.getValue().toString()
        if (value == code){
            keyValue = it.key.toString()
            var alp = it.key
            mySingleton.createRoomId = alp
            return true
        }
    }
    return false
}