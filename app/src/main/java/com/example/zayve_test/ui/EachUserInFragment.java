package com.example.zayve_test.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zayve_test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class EachUserInFragment extends Fragment {

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuthentication;

    private TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
            mFourthInterestTextView,mFifthInterestTextView,introTv;
    private ImageView mProfilePictureImageView;

    String globalName,firstInterest,secondInterest,thirdInterest,fourthInterest,fifthInterest,profilePictureString;
    String intro = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        globalName = getArguments().getString("globalName");
        firstInterest = getArguments().getString("firstInterest");
        secondInterest = getArguments().getString("secondInterest");
        thirdInterest = getArguments().getString("thirdInterest");
        fourthInterest = getArguments().getString("fourthInterest");
        fifthInterest = getArguments().getString("fifthInterest");
        profilePictureString = getArguments().getString("profilePictureString");
        intro = getArguments().getString("intro");
        return inflater.inflate(R.layout.fragment_each_user_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        Uri ppUri = Uri.parse(profilePictureString);

        mUsernameTextView = getView().findViewById(R.id.user_name_textview);

        mFirstInterestTextView = getView().findViewById(R.id.first_interest_textview);
        mSecondInterestTextView = getView().findViewById(R.id.second_interest_textview);
        mThirdInterestTextView = getView().findViewById(R.id.third_interest_textview);
        mFourthInterestTextView = getView().findViewById(R.id.fourth_interest_textview);
        mFifthInterestTextView = getView().findViewById(R.id.fifth_interest_textview);
        mProfilePictureImageView = getView().findViewById(R.id.profile_picture_imageview);

        introTv = getView().findViewById(R.id.intro_tv);

        mUsernameTextView.setText(globalName);
        mUsernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),globalName,Toast.LENGTH_SHORT).show();
            }
        });

        mFirstInterestTextView.setText(firstInterest);
        mFirstInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),firstInterest,Toast.LENGTH_SHORT).show();
                String id = mFirebaseAuthentication.getCurrentUser().getUid();
                Log.d("gettingids",id);
                //                mDatabaseReference.child("BrowsedUsers").child(mFirebaseAuthentication.getCurrentUser().getUid()).setValue(firstInterest);

            }
        });
        mSecondInterestTextView.setText(secondInterest);
        mThirdInterestTextView.setText(thirdInterest);
        mFourthInterestTextView.setText(fourthInterest);
        mFifthInterestTextView.setText(fifthInterest);

        introTv.setText(intro);

        Picasso.get().load(ppUri).fit().centerCrop().into(mProfilePictureImageView);


    }
}