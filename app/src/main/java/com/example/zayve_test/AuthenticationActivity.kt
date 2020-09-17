package com.example.zayve_test

import android.database.DatabaseUtils
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.zayve_test.databinding.ActivityMainBinding

class AuthenticationActivity : AppCompatActivity() {
    private val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

    }

}