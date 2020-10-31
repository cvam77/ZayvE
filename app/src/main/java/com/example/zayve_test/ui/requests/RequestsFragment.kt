package com.example.zayve_test.ui.requests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.zayve_test.R
import com.example.zayve_test.adapters.reyclerviewAdapters.AcceptButtonListner
import com.example.zayve_test.adapters.reyclerviewAdapters.RequestListListAdapter
import com.example.zayve_test.databinding.FragmentRequestsBinding
import java.util.*

class RequestsFragment : Fragment() {

    private lateinit var requestsViewModel: RequestsViewModel
    private lateinit var binding: FragmentRequestsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_requests, container, false)
        requestsViewModel = ViewModelProvider(this).get(RequestsViewModel::class.java)
        requestsViewModel.fetchRequestList()
        val adapter = RequestListListAdapter(AcceptButtonListner { request ->
            requestsViewModel.acceptRequest(request)
            makeText(context, "friend request from ${request.userName.toUpperCase(Locale.ROOT)} accepted", Toast.LENGTH_LONG).show()
        })
        binding.requestRecyclerView.adapter = adapter
        if (requestsViewModel.requestList.value != null) {
            requestsViewModel.requestList.observe(viewLifecycleOwner, Observer {
                it.let {
                    adapter.submitList(it)
                }
            })
        }

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
}