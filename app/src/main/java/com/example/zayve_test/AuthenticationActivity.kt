package com.example.zayve_test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.zayve_test.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityAuthenticationBinding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
    }
}