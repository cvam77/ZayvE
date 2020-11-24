package com.example.zayve_test.ui.chats

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zayve_test.R
import com.example.zayve_test.adapters.reyclerviewAdapters.ChatPageListAdapter
import com.example.zayve_test.databinding.ChatPageFragmentBinding


class ChatPage : Fragment() {

    private lateinit var chatPageViewModel: ChatPageViewModel
    private lateinit var binding: ChatPageFragmentBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.chat_page_fragment, container, false)
        chatPageViewModel = ViewModelProvider(this).get(ChatPageViewModel::class.java)
//      receive chatId from argument Bundle
        val chatId = arguments?.let { ChatPageArgs.fromBundle(it).chatId }
        if (chatId != null) {
            chatPageViewModel.setChatId(chatId)
            chatPageViewModel.fetchMessages()
            chatPageViewModel.loadUserData()
        }
        val adapter = ChatPageListAdapter()
        binding.recyclerView.adapter = adapter
        chatPageViewModel.messages.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })
//        adds listner to the send button and calls viewmodel accordingly
        binding.userMessage.setOnTouchListener(OnTouchListener { v, event ->
            val DRAWABLE_RIGHT: Int = 2
            if (event.action == ACTION_UP && event.rawX >= binding.userMessage.right - binding.userMessage.compoundDrawables[DRAWABLE_RIGHT].bounds.width()
            ) {
                val userEnteredMessage = binding.userMessage.text.toString()
                chatPageViewModel.sendMessage(userEnteredMessage.trim())
                binding.userMessage.text = null
                return@OnTouchListener true
            }
            false
        })
        binding.recyclerView.setHasFixedSize(true)
        return binding.root
    }
}