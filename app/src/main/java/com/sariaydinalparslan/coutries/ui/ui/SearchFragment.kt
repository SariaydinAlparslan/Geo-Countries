package com.sariaydinalparslan.coutries.ui.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.FragmentSearchBinding
import com.sariaydinalparslan.coutries.ui.adapters.PickAdapter
import com.sariaydinalparslan.coutries.ui.adapters.RandomAdapter
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.data.ApRoomId
import com.sariaydinalparslan.coutries.ui.data.ArRoomId
import com.sariaydinalparslan.coutries.ui.mySingleton

class SearchFragment : Fragment() {
    private lateinit var roomlistRecyclerView: RecyclerView
    private lateinit var pickroomlistRecyclerView: RecyclerView
    private lateinit var db: DatabaseReference
    private lateinit var empList: ArrayList<RoomData>
    private lateinit var pickList: ArrayList<RoomData>
    private lateinit var apRoomIdList : ArrayList<ApRoomId>
    private lateinit var arRoomIdList : ArrayList<ArRoomId>
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        roomlistRecyclerView = view.findViewById(R.id.recyclerandomroomlist)
        roomlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        pickroomlistRecyclerView =view.findViewById(R.id.recycleroomlist)
        pickroomlistRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        empList = ArrayList<RoomData>()
        pickList = ArrayList<RoomData>()
        getRandomRoomList()
        getPickRoomList()
        apRoomIdList = ArrayList<ApRoomId>()
        arRoomIdList = ArrayList<ArRoomId>()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
                            val alp = d.key
                            val dd = ArRoomId(alp)
                            arRoomIdList.add(dd)
                            Log.e("alp",alp.toString())
                        }
                        val rAdapter = RandomAdapter(empList,arRoomIdList)
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
                            val key = d.key
                            val dKey = ApRoomId(key)
                            apRoomIdList.add(dKey)
                        }
                        val pAdapter = PickAdapter(pickList,apRoomIdList)
                        pickroomlistRecyclerView.adapter = pAdapter
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

}