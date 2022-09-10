package com.sariaydinalparslan.coutries.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.MainActivity
import com.sariaydinalparslan.coutries.ui.NotificationActivity
import com.sariaydinalparslan.coutries.ui.data.GameData
import kotlinx.android.synthetic.main.reycler_row.view.*

class RandomAdapter(private val empList : ArrayList<GameData>): RecyclerView.Adapter<RandomAdapter.LandmarkHolder>() {

    class LandmarkHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.reycler_row,parent,false)
        return LandmarkHolder(itemView)
    }
    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {
        val current=empList[position]
        holder.itemView.nickname.text = current.userName

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,NotificationActivity::class.java)

        }
    }
    override fun getItemCount(): Int {
        return empList.size
    }
}