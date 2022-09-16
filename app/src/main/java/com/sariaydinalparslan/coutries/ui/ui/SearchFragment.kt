package com.sariaydinalparslan.coutries.ui.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.sariaydinalparslan.coutries.ui.mySingleton

class SearchFragment : Fragment() {
    private lateinit var roomlistRecyclerView: RecyclerView
    private lateinit var pickroomlistRecyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private lateinit var empList: ArrayList<RoomData>
    private lateinit var pickList: ArrayList<RoomData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val alpulke = "Hollanda"
        mySingleton.chosenCountry = alpulke

        val db = FirebaseDatabase.getInstance()
        FirebaseAuth.getInstance().uid?.let { safeUserId ->
            db.getReference("Users").child(safeUserId).child("userName").get()
                .addOnCompleteListener {
                    val alp = it.result
                    mySingleton.chosenlandmark = alp.value.toString()
                }
        }
        auth = Firebase.auth
        roomlistRecyclerView = view.findViewById(R.id.recyclerandomroomlist)
        roomlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        pickroomlistRecyclerView =view.findViewById(R.id.recycleroomlist)
        pickroomlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        empList = ArrayList<RoomData>()
        pickList = ArrayList<RoomData>()
        getRandomRoomList()
        getPickRoomList()
    }
    private fun getRandomRoomList() {
        val db = FirebaseDatabase.getInstance()
        db.getReference("RoomList").child("AllRandom")
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

                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }
    private fun getPickRoomList() {
        val db = FirebaseDatabase.getInstance()
        db.getReference("RoomList").child("AllPick")
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