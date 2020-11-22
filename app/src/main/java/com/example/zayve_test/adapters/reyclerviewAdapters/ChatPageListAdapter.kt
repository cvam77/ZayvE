package com.example.zayve_test.adapters.reyclerviewAdapters

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kurakani.model.Message
import com.example.zayve_test.R
import com.example.zayve_test.databinding.MessageItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso


class ChatPageListAdapter() :
    ListAdapter<Message, ChatPageListAdapter.MessageViewHolder>(ChatMessagesDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatPageListAdapter.MessageViewHolder {
        return ChatPageListAdapter.MessageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChatPageListAdapter.MessageViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    class MessageViewHolder private constructor(val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val userId = FirebaseAuth.getInstance().currentUser?.uid

        @SuppressLint("ResourceAsColor")
        fun bind(
            currentItem: Message?
        ) { //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) {
                Picasso.get().load(currentItem.profile_pic).into(binding.userCardImage)
                if (userId==currentItem.userId){
                    Log.d("user", currentItem.userId)
                    binding.messageLayout.layoutDirection = View.LAYOUT_DIRECTION_RTL
                    binding.messageItem.setBackgroundResource(R.drawable.message_sent)
                    binding.messageItem.setTextColor(R.color.white)
                }
                else {
                    binding.messageLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR
                    binding.messageItem.setBackgroundResource(R.drawable.message_received)

                }
                binding.message =
                    Message(
                        currentItem.message,
                        currentItem.sender_name,
                        currentItem.profile_pic,
                        currentItem.time,
                        currentItem.userId
                    )
            }
        }

        companion object {
            fun from(
                parent: ViewGroup
            ): ChatPageListAdapter.MessageViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MessageItemBinding.inflate(layoutInflater, parent, false)
                return ChatPageListAdapter.MessageViewHolder(binding)
            }
        }
    }

}

class ChatMessagesDiffCallback : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.time == newItem.time
    }

    override fun areContentsTheSame(
        oldItem: Message,
        newItem: Message
    ): Boolean {
        return oldItem == newItem
    }

}

