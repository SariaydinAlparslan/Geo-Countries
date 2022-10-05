package com.sariaydinalparslan.coutries.ui.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sariaydinalparslan.coutries.R


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