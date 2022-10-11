package com.sariaydinalparslan.coutries.ui.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.DataSnapshot
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.ui.ui.keyValue


fun ImageView.downloadfromUrl(url : String?,progressDrawable: CircularProgressDrawable){
    val options =RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.eleven)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}

fun placeholderProgressBar (context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}
fun isValueAvaliable(snapshot : DataSnapshot, code:String):Boolean{
    var data = snapshot.children
    data.forEach{
        var value = it.getValue().toString()
        if (value == code){
            keyValue = it.key.toString()
            /* var alp = it.key.toString()
             mySingleton.createRoomId = alp*/
            return true
        }
    }
    return false
}