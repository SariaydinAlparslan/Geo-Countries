package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sariaydinalparslan.coutries.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences : SharedPreferences
    var rememberSignIn : Boolean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = this.getSharedPreferences("com.sariaydinalparslan.coutries",
            MODE_PRIVATE)

        rememberSignIn = sharedPreferences.getBoolean("Finished",false)

        if (rememberSignIn==true){
            val intent = Intent(Intent(this,SplashActivity::class.java))
            startActivity(intent)
        }

        binding.signInBtn.setOnClickListener {
            if (binding.enterNameText.text.isEmpty()){
                Toast.makeText(this, "Please Enter A Name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(Intent(this,OnBoardingActivity::class.java))
                startActivity(intent)
                sharedPreferences.edit().putString("nickname",binding.enterNameText.text.toString()).apply()
            }
        }
    }
}

