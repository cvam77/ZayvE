package com.example.zayve_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.zayve_test.databinding.FragmentSignupBinding


//sign up fragment
class SignupFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSignupBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_signup,container,false)
        binding.loginButton.setOnClickListener(Navigation.createNavigateOnClickListener((R.id.action_signupFragment2_to_loginFragment2)))
        return binding.root
    }
}