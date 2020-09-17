package com.example.zayve_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.zayve_test.databinding.FragmentLoginBinding
import java.util.*


class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        binding.signupButton.setOnClickListener((Navigation.createNavigateOnClickListener(R.id.action_loginFragment2_to_signupFragment2)))
        return binding.root
    }

}
