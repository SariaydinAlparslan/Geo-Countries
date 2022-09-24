package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityRegisterBinding
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var firebaseUser: FirebaseUser? = null
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

    }
    fun signup(view: View) {
            val userName = binding.etName.text.toString()
            val email =binding.etEmail.text.toString()
            val password =binding.etPassword.text.toString()
            val confirmPassword =binding.etConfirmPassword.text.toString()
            if (userName.isEmpty()) {
                Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT)
                    .show()
            }
            if (email.isEmpty()) {
                Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT).show()
            }
            if (password.isEmpty()) {
                Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT)
                    .show()
            }
            if (confirmPassword.isEmpty()) {
                Toast.makeText(
                    applicationContext,
                    "confirm password is required",
                    Toast.LENGTH_SHORT
                ).show()
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(applicationContext, "password not match", Toast.LENGTH_SHORT).show()
            }
            registerUser(userName, email, password)
        }

        fun login(view: View) {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        private fun registerUser(userName: String, email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val user: FirebaseUser? = auth.currentUser
                        val userIx = user?.uid

                        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userIx!!)

                        val hashMap: HashMap<String, String> = HashMap()
                        hashMap.put("email", email)
                        hashMap.put("userName", userName)
                        hashMap.put("uuid",userIx)

                        databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                            if (it.isSuccessful) {
                                //open home activity
                               binding.etName.setText("")
                               binding.etEmail.setText("")
                               binding.etPassword.setText("")
                               binding.etConfirmPassword.setText("")
                                val intent = Intent(
                                    this@RegisterActivity,
                                    MainActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
        }
    }

