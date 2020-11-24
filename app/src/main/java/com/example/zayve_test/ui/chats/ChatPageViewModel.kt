package com.example.zayve_test.ui.chats

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kurakani.model.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class ChatPageViewModel : ViewModel() {
    private lateinit var user: FirebaseUser
    private lateinit var database: DatabaseReference
    private lateinit var userName: String
    private lateinit var profilePic: String


    private val _messageList = MutableLiveData<List<Message>>().apply {
//        load empty array list to instantiate the message list live data
        value = ArrayList<Message>()
    }

    val messages = _messageList

    private val _chatId = MutableLiveData<String>().apply {
        value = ""
    }

    fun setChatId(id: String): Unit {
        _chatId.value = id
    }

    //  fetches messages from chat database
    fun fetchMessages() {
        user = FirebaseAuth.getInstance().currentUser!!
        database = FirebaseDatabase.getInstance().reference
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadData(dataSnapshot)
            }

            //            loads messageInfo into the messagelist live data
            private fun loadData(dataSnapshot: DataSnapshot) {
                val messages = ArrayList<Message>()
                val responseData = dataSnapshot.child("chats").child(_chatId.value.toString())
                    .child("messages")
                var titleName = ""
                for (messageData in responseData.children) {
                    val message = messageData.child("message").value.toString()
                    val profilePic = messageData.child("profile_pic").value.toString()
                    val senderName = messageData.child("sender_name").value.toString()
                    val time = messageData.child("time").value.toString()
                    val userId:String = messageData.child("userId").value.toString()
                    if(userId!=user.uid){
                        titleName = senderName
                    }
                    messages += Message(message, senderName, profilePic, time,userId)
                }
//                val messageDummy = messages.sortedWith(compareBy { it.time })
                _messageList.value = messages.sortedByDescending { it.time }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }

    //    loads user data the first time viewmodel is called
    fun loadUserData(): Unit {
        val valueListner = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userData = dataSnapshot.child("users").child(user.uid)
                userName = userData.child("user_name").value.toString()
                profilePic = userData.child("profile_image").value as String
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(valueListner)
    }

    //uploads user message into the chats database
    fun sendMessage(message: String): Unit {
//        val time = (System.currentTimeMillis() / 1000L).toString()
        val time =  System.currentTimeMillis().toString()
        database.child("chats").child(_chatId.value.toString()).child("messages").child(time).setValue(Message(message,userName,profilePic,time,user.uid))
        database.child("chats").child(_chatId.value.toString()).child("last_message").setValue(message)
    }
}