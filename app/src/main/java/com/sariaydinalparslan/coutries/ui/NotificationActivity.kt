package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sariaydinalparslan.coutries.R

class NotificationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
    }
//mesajları burda paylaşabilirsin welcome "username"
    fun back(view :View){
        val back = Intent(this@NotificationActivity,MainActivity::class.java)
        startActivity(back)
        finish()
    }
}