package com.example.zayve_test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.zayve_test.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.StorageReference


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user:FirebaseUser


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        user = FirebaseAuth.getInstance().currentUser!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        fetchUsername()

    }

    //  fetch user name from cloud firestore
    private fun fetchUsername() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            for (profile in user.providerData) {
                binding.profileUsername.text = profile.displayName
            }

        }
    }


}