package com.sariaydinalparslan.coutries.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sariaydinalparslan.coutries.R
import com.sariaydinalparslan.coutries.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private var auth: FirebaseAuth? = null
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private companion object{
        private const val  RC_SIGN_IN = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.alp))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        binding.signin.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
            val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }catch (e:Exception){
                Toast.makeText(this, "U fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        auth!!.signInWithCredential(credential)
            .addOnSuccessListener { authResult->
                if (authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show()
                }else{
                }
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Fail Login", Toast.LENGTH_SHORT).show()
            }
    }

}

