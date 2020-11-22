package com.example.zayve_test.ui.chats

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.zayve_test.R
import com.example.zayve_test.adapters.reyclerviewAdapters.MessageListAdapter
import com.example.zayve_test.adapters.reyclerviewAdapters.UserChatClickListner
import com.example.zayve_test.databinding.FragmentChatHomepageBinding
import com.google.firebase.auth.FirebaseAuth


class ChatHomePage : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var chatHomeHomePageViewModel: ChatHomePageViewModel
    private lateinit var binding: FragmentChatHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatHomeHomePageViewModel = ViewModelProvider(this).get(ChatHomePageViewModel::class.java)
        chatHomeHomePageViewModel.fetchUserData()
    }

    @SuppressLint("ShowToast")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_homepage, container, false)
        val adapter = MessageListAdapter(UserChatClickListner { user ->
            Log.d("chatId","${user.chatId} chat clicked")
//            todo: figure out why toast is not displaying
            makeText(context,"${user} chat clicked",Toast.LENGTH_LONG)
            findNavController().navigate(ChatHomePageDirections.actionNavChatPageToChatPage(user.chatId))
        })
        binding.recyclerView.adapter = adapter
        chatHomeHomePageViewModel.messageList.observe(viewLifecycleOwner, Observer {
            it.let {
                adapter.submitList(it)
            }
        })
        binding.recyclerView.setHasFixedSize(true)
        val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true /* enabled by default */) {
                    override fun handleOnBackPressed() {
                        // Handle the back button even
                        Log.d("BACKBUTTON", "Back button clicks")
                    }
                }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                auth.signOut()
                findNavController().navigate(R.id.action_nav_chat_page_to_chatPage)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}