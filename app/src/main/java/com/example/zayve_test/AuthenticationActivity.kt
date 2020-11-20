package com.example.zayve_test

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.zayve_test.databinding.ActivityAuthenticationBinding
import com.google.firebase.auth.FirebaseAuth

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            val intent = Intent(this, ZayveActivity::class.java)
            startActivity(intent)
            finish()
        }
        val binding:ActivityAuthenticationBinding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
    }
}