package com.example.zayve_test.adapters.reyclerviewAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.zayve_test.databinding.RequestItemCardBinding
import com.example.zayve_test.models.Request
import com.squareup.picasso.Picasso


class RequestListListAdapter(private val acceptButtonListner: AcceptButtonListner,private val deleteButtonListner: DeleteButtonListner) :
    ListAdapter<Request, RequestListListAdapter.RequestViewHolder>(RequestListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        return RequestViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(getItem(position)!!,acceptButtonListner,deleteButtonListner)
    }

    class RequestViewHolder private constructor(val binding: RequestItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
                currentItem: Request?,
                acceptButtonListner: AcceptButtonListner,deleteButtonListner: DeleteButtonListner
        ) { //        downloads and populate imageview with user image from firebase image storage
            if (currentItem != null) {
                Picasso.get().load(currentItem.imageSrc).into(binding.userCardImage)
//                todo: change here when you succeed in fetching the data
                binding.request = Request(currentItem.imageSrc,currentItem.userName,currentItem.userId,currentItem.interest)
                binding.acceptBtnListner = acceptButtonListner
                binding.declineBtnListner= deleteButtonListner

            }
        }

        companion object {
            fun from(
                parent: ViewGroup
            ): RequestViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RequestItemCardBinding.inflate(layoutInflater, parent, false)
                return RequestViewHolder(binding)
            }
        }
    }

}

class RequestListDiffCallback : DiffUtil.ItemCallback<Request>() {
    override fun areItemsTheSame(oldItem: Request, newItem: Request): Boolean {
        return oldItem.userName == newItem.userName
    }

    override fun areContentsTheSame(
        oldItem: Request,
        newItem: Request
    ): Boolean {
        return oldItem == newItem
    }

}

//click listner
class AcceptButtonListner(val clickListener: (request: Request) -> Unit) {
    fun onClick(request: Request) = clickListener(request)
}

//click listner
class DeleteButtonListner(val clickListener: (request: Request) -> Unit) {
    fun onClick(request: Request) = clickListener(request)
}
