package com.example.zayve_test.ui.Chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Chat Fragment"
    }
    val text: LiveData<String> = _text
}