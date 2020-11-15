package com.example.zayve_test.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zayve_test.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<User>().apply {
        value = User("","https://image.flaticon.com/icons/png/512/38/38002.png",java.util.ArrayList())
    }
    val user = _user

    //  fetches user information from database and updates the profile view
     fun fetchUserData() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val valueListner = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val profilePic = dataSnapshot.child("profile_image").value
                            ?: "https://upload.wikimedia.org/wikipedia/commons/3/3e/Android_logo_2019.png"
                    val interests = dataSnapshot.child("interests").value  ?: ArrayList<String>()
                    val userName = user.displayName
//                    this user is our model User
                    _user.value= userName?.let { User(it, profilePic.toString(),interests as ArrayList<String> ) }

                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    Log.w( "loadPost:onCancelled", databaseError.toException())
                }
            }
            user.uid.let { FirebaseDatabase.getInstance().reference.child("users").child(it) }.addListenerForSingleValueEvent(valueListner)
        }
    }
}