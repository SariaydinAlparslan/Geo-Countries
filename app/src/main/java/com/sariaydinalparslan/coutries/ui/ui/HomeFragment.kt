package com.sariaydinalparslan.coutries.ui.ui

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    var prefs : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = FirebaseDatabase.getInstance()
            FirebaseAuth.getInstance().uid?.let {
                    safeUserId->
                db.getReference("Users").child(safeUserId).child("userName").get().addOnCompleteListener {
                    val alp = it.result
                    name_text.text=alp.value.toString()
                }
            }

        val sharedPreferences = this.activity?.getSharedPreferences("com.sariaydinalparslan.coutries",
            MODE_PRIVATE)
        prefs = sharedPreferences!!.getString("pref","")
        mySingleton.avatarId = prefs

        val resourceID = getResources().getIdentifier("${mySingleton.avatarId}", "drawable", this.requireContext().packageName)
        Log.e(  "alp", resourceID.toString())
        avatarView.setImageResource(resourceID)
    }
}