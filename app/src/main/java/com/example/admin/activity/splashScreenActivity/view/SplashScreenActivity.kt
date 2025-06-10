package com.example.admin.activity.splashScreenActivity.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.admin.activity.loginActivity.view.loginActivity
import com.example.admin.activity.mainActivity.view.MainActivity
import com.example.admin.databinding.ActivitySplashScreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class splashScreenActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var firebaseAuth = FirebaseAuth.getInstance()
        var user = firebaseAuth.currentUser

        Handler().postDelayed({
            checkUser(user)
            finish()
        }, 3000)
    }

    private fun checkUser(user: FirebaseUser?) {
        if (user != null) {

            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {

            var intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
        }
    }
}

