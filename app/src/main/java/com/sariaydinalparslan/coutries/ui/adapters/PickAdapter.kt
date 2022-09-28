package com.sariaydinalparslan.coutries.ui.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.*
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.data.ApRoomId
import com.sariaydinalparslan.coutries.ui.ui.*
import kotlinx.android.synthetic.main.reycler_row.view.*

class PickAdapter(private val empList : ArrayList<RoomData>,
                  private val apRoomIdList : ArrayList<ApRoomId>,

                  ) : RecyclerView.Adapter<PickAdapter.LandmarkHolder>() {

    class LandmarkHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        val list = apRoomIdList[position]
        //Log.e("alp",alpi.toString())
        Log.e("alp",current.toString())
        holder.itemView.nickname.text = current.roomName
        holder.itemView.roomname.text= current.userId
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
                             holder.itemView.context.startActivity(intent)
                         }
                     }, 100)
                 }
                 override fun onCancelled(error: DatabaseError) {
                     TODO("Not yet implemented")
                 }
             })
            //ap taki ülkeyi oyun acitivitye geçiş ve ready ayarlama
            FirebaseDatabase.getInstance().reference.child("visitorscountry").child(code)
                .push().setValue(mySingleton.chosenCountry)
            //RoomList delete
            FirebaseDatabase.getInstance().reference
                .child("RoomList")
                .child("AllPick")
                .child(list.apRoomId.toString()).removeValue()
        }

}
override fun getItemCount(): Int {
return empList.size
}


}