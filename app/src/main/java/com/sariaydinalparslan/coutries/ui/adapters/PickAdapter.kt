package com.sariaydinalparslan.coutries.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.GameActivity
import com.sariaydinalparslan.coutries.ui.data.RoomData
import com.sariaydinalparslan.coutries.ui.mySingleton
import kotlinx.android.synthetic.main.reycler_row.view.nickname
import kotlinx.android.synthetic.main.reycler_row.view.roomname

class PickAdapter(private val empList : ArrayList<RoomData>): RecyclerView.Adapter<PickAdapter.LandmarkHolder>() {

    class LandmarkHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reycler_row_all_pick,parent,false)
        return LandmarkHolder(itemView)
    }
    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {

        val current=empList[position]
        holder.itemView.nickname.text = current.userName
        holder.itemView.roomname.text= current.roomName
        holder.itemView.setOnClickListener {
                    val intent = Intent(holder.itemView.context, GameActivity::class.java)
                    intent.putExtra("roomName",current.roomName)
                    intent.putExtra("roomUserId",current.userId)
                    intent.putExtra("roomUserName",current.userName)
                    intent.putExtra("visitorName",mySingleton.chosenlandmark)
                    intent.putExtra("pick","pick")
                    //randomdan geldiğnini göster
                    holder.itemView.context.startActivity(intent)

         /*   val intent = Intent(holder.itemView.context, GameActivity::class.java)
            intent.putExtra("roomName",current.roomName)
            intent.putExtra("roomUserId",current.userId)
            intent.putExtra("roomUserName",current.userName)

            intent.putExtra("pick","pick")

            //randomdan geldiğnini göster
            holder.itemView.context.startActivity(intent)*/
        }
    }
    override fun getItemCount(): Int {
        return empList.size
    }

}