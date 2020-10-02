package com.example.zayve_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class BrowseFriendsFragment extends Fragment {

    private StorageReference mStorage;

    private DatabaseReference mDatabaseRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mStorage = FirebaseStorage.getInstance().getReference();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerAdapter<EachUserProfile, BrowseFriendsViewHolder> firebaseAdapter = new FirebaseRecyclerAdapter<EachUserProfile, BrowseFriendsViewHolder>() {
            @Override
            protected void populateViewHolder(BrowseFriendsViewHolder browseFriendsViewHolder, EachUserProfile eachUserProfile, int i) {

            }

            @Override
            public BrowseFriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_user,parent,false);
                BrowseFriendsViewHolder viewHolder = new BrowseFriendsViewHolder(view);
                return viewHolder;
            }
        };


    }


    public static class BrowseFriendsViewHolder extends RecyclerView.ViewHolder
    {

        TextView mNameTextview, mFirstInterestTextview, mSecondInterestTextview, mThirdInterestTextview,
        mFourthInterestTextview, mFifthInterestTextview;
        
        public BrowseFriendsViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

















