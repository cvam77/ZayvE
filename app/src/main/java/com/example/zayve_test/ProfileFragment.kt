package com.example.zayve_test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.zayve_test.databinding.FragmentProfileBinding
import com.example.zayve_test.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var user:FirebaseUser
    private lateinit var database: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        user = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference.child("users").child(user.uid);
        fetchUserData()
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_profile_to_profileSetupFragment)
        }
        return binding.root
    }

    //  fetches user name from cloud firestore and updates the profile view
    private fun fetchUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val valueListner = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val avatarUrl = dataSnapshot.child("avatar_image").value
                    val interests = dataSnapshot.child("interests").value
                    Log.d("response",interests.toString())
//                    this user is our model User
                    binding.user=User(user.displayName.toString(),avatarUrl as String, interests as ArrayList<String>)
                    Picasso.get().load(avatarUrl).into(binding.profileAvatar);
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException())
                }
            }
            database.addListenerForSingleValueEvent(valueListner)
        }
    }


}