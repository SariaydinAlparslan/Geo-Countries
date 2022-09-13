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
import com.sariaydinalparslan.coutries.ui.data.RoomData
import kotlinx.android.synthetic.main.activity_match_room.*
import kotlinx.android.synthetic.main.reycler_row.view.nickname
import kotlinx.android.synthetic.main.reycler_row.view.roomname
import kotlinx.android.synthetic.main.reycler_row_all_pick.view.*

class PickAdapter(private val empList : ArrayList<RoomData>): RecyclerView.Adapter<PickAdapter.LandmarkHolder>() {

    class LandmarkHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reycler_row_all_pick,parent,false)
        codeFound = false
        checkTemp = true
        keyValue = "null"
        isCodeMaker = false
        return LandmarkHolder(itemView)
    }
    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {

        val current=empList[position]
        holder.itemView.nickname.text = current.userName
        holder.itemView.roomname.text= current.roomName
        code = current.roomName.toString()
        holder.itemView.setOnClickListener {
         FirebaseDatabase.getInstance().reference.child("Room").child("AllPick")
             .addValueEventListener(object : ValueEventListener {
                 override fun onDataChange(snapshot: DataSnapshot) {
                     var data: Boolean = isValueAvaliable(snapshot, code)
                     Handler().postDelayed({
                         if (data == true) {
                             codeFound = true
                             val intent = Intent(holder.itemView.context,GameActivity::class.java)
                             //pickten geldiğnini göster
                             holder.itemView.context.startActivity(intent)
                         } else {
                         }
                     }, 600)
                 }
                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }
             })
            Toast.makeText(holder.itemView.context, code, Toast.LENGTH_SHORT).show()
        }
}
override fun getItemCount(): Int {
return empList.size
}

}