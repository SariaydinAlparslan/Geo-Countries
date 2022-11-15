package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.tuto.MyApplication

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            val application = application as? MyApplication

            if (application == null) {
                Log.e("alp", "Failed to cast application to MyApplication.")
                navigation()
            }
            application!!.showAdIfAvailable(
                this@SplashActivity,
                object : MyApplication.OnShowAdCompleteListener {
                    override fun onShowAdComplete() {
                        navigation()
                    }
                })
        }, 3000)
    }
    fun navigation(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}