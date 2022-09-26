package com.sariaydinalparslan.coutries.ui.adapters

import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.*
import com.sariaydinalparslan.coutries.ui.data.ApRoomId
import com.sariaydinalparslan.coutries.ui.data.ArRoomId
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.ui.*
import kotlinx.android.synthetic.main.reycler_row.view.*

class RandomAdapter(private val empList : ArrayList<RoomData>
,private val arRoomIdList : ArrayList<ArRoomId>): RecyclerView.Adapter<RandomAdapter.LandmarkHolder>() {

    class LandmarkHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reycler_row,parent,false)
        codeFound = false
        checkTemp = true
        keyValue = "null"
        isCodeMaker = false
        return LandmarkHolder(itemView)
    }
    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {
        val current=empList[position]
        val list = arRoomIdList[position]

        holder.itemView.nickname.text = current.userId
        holder.itemView.roomname.text= current.roomName
        code = current.roomName.toString()
        holder.itemView.setOnClickListener {
            //RoomList delete
            FirebaseDatabase.getInstance().reference
                .child("RoomList")
                .child("AllRandom")
                .child(list.arRoomId.toString()).removeValue()

            FirebaseDatabase.getInstance().reference.child("Room").child("AllRandom")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var data: Boolean = isValueAvaliable(snapshot, code)
                        Handler().postDelayed({
                            if (data == true) {
                                codeFound = true
                                val intent = Intent(holder.itemView.context,GameActivity::class.java)
                                //randomdan geldiğnini göster
                                holder.itemView.context.startActivity(intent)
                            } else {
                            }
                        }, 100)
                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }
    override fun getItemCount(): Int {
        return empList.size
    }
}