package com.example.zayve_test.ui.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zayve_test.models.UserMessageInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


class ChatHomePageViewModel : ViewModel() {
    private lateinit var userName: String
    private lateinit var user: FirebaseUser
    private lateinit var database: DatabaseReference

    private val _messageList = MutableLiveData<List<UserMessageInfo>>().apply {
//        load empty array list to instantiate the message list live data
        value = ArrayList<UserMessageInfo>()
    }
    val messageList = _messageList

    //  fetches user name from cloud firestore and updates the profile view
    fun fetchUserData() {
        user = FirebaseAuth.getInstance().currentUser!!
        userName = user.displayName.toString()
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadData(dataSnapshot)
            }

            //            loads messageInfo into the messagelist live data
            private fun loadData(dataSnapshot: DataSnapshot) {
                val messages = ArrayList<UserMessageInfo>()
                val chatData = dataSnapshot.child("chats")
                for (childSnapshot in chatData.children) {
                    //                    finds the last chat
                    val lastMessage = childSnapshot.child("last_message").value as String

                    //                    find the second participant other than the user
                    val participants = childSnapshot.child("participants").value as List<String>
//                    only fill the messagelist with user chat
                    if (participants.contains(user.uid)){
                        var friendUID = ""
                        for (uid in participants) {
                            if (uid == user.uid) continue
                            friendUID = uid
                        }

                        //finds the friend profile picture
                        val friendInfo = dataSnapshot.child("users").child(friendUID)
                        val friendName =
                                friendInfo.child("user_name").value as String
                        val friendImgSrc = friendInfo.child("profile_image").value as String
                        Log.d("name",friendName)
                        val chatId = friendInfo.child("friends").child(user.uid).child("chatId").value as String
                        messages += UserMessageInfo(friendImgSrc, friendName, lastMessage,chatId)
                    }
                }
                _messageList.value = messages
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }
}