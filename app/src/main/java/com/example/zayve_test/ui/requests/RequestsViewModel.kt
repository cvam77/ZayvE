package com.example.zayve_test.ui.requests

import android.content.Context
import android.renderscript.Sampler
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zayve_test.models.Chat
import com.example.zayve_test.models.Friend
import com.example.zayve_test.models.Request
import com.example.zayve_test.ui.browse_friends.BrowseFriendsFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class RequestsViewModel : ViewModel() {

    lateinit var context: Context;
    private lateinit var database: DatabaseReference
    private lateinit var user: FirebaseUser

    private var _requestList = MutableLiveData<List<Request>>().apply {
        value = ArrayList<Request>()
    }
    val requestList = _requestList

    fun acceptRequest(request: Request): Unit {
//        todo: this uuid has to come from the user that sends the request.
        val chatId = UUID.randomUUID().toString()
        val friend = Friend(request.imageSrc, request.userId, request.userName, chatId)
        val participants = listOf<String>(friend.uid, user.uid)
        val chat = Chat("Congratulations! You are connected now!", participants)

//        save accepted users in friends hashmap
        database.child("users").child(user.uid).child("friends").child(friend.uid).setValue(friend)
//        retrieves user information from database
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.child("users").child(user.uid)
                val userName = userData.child("user_name").value.toString()
                Log.d("reached","reached here")
                val userProfilePic = userData.child("profile_image").value as String
                val userInfo = Friend(userProfilePic, user.uid, userName, chatId)
//                save user in the friends section of the friend
                database.child("users").child(friend.uid).child("friends").child(user.uid)
                        .setValue(userInfo)
                BrowseFriendsFragment().MakeNotification(context,userName,request.interest);
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error", "Error while reading data")
            }
        })
//        removes accepted user from request list
        deleteRequest(request)
//        creates a new chat collection for connected users
        database.child("chats").child(chatId).setValue(chat)

    }

    //    fetches request list from realtime database
    fun fetchRequestList(): Unit {
        user = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val responseData =
                        dataSnapshot.child("users").child(user.uid).child("interest_requests")

                val requestList = ArrayList<Request>()
//                load request into the requestList arraylist
                for (interest in responseData.children) {
//                    Log.d("responseData", "${interest.key} : ${interest.value.toString()}")
                    for (requestId in interest.children) {
//                        get user data from interest collection
                        val userData = dataSnapshot.child("users").child(requestId.value.toString())
                        val profileImage = userData.child("profile_image").value.toString()
                        val userName = userData.child("user_name").value.toString()
                        val userId = requestId.value.toString()
                        val request = Request(profileImage, userName, userId, interest.key.toString())
                        requestList += request
                    }
                }
//                // loads the data into the request list livedata
                _requestList.value = requestList
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }


    fun deleteRequest(request: Request) {
        database.child("users").child(user.uid).child("interest_requests").addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children) {
                    for (interestData in data.children) {
                        val requestUserId = interestData.value.toString()
                        if (requestUserId == request.userId) {
                            Log.d("delete", requestUserId)
                            interestData.ref.removeValue()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}