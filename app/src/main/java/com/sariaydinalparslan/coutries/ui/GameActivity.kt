package com.sariaydinalparslan.coutries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sariaydinalparslan.coutries.R
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent = intent
        val roomName = intent.getStringExtra("roomName")
        val roomUserId = intent.getStringExtra("roomUserId")
        val roomUserName = intent.getStringExtra("roomUserName")

        a.text= roomName
        al.text = roomUserId
        alp.text= roomUserName

    }
}