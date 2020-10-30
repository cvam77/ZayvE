package com.example.zayve_test.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zayve_test.R
import com.example.zayve_test.databinding.FragmentProfileBinding
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
                ViewModelProvider(this).get(ProfileViewModel::class.java)
        val binding:FragmentProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        profileViewModel.fetchUserData()
//        changes profile view when the user data changes in the profile view model
        profileViewModel.user.observe(viewLifecycleOwner, Observer {
            Picasso.get().load(it.profile_pic).into(binding.profileAvatar);
            binding.user = it
        })
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_profileSetupFragment3)
        }
        return binding.root
    }
}