package com.example.zayve_test.search_by_interest;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class EachUserInFragmentSearchResults extends Fragment {

    private DatabaseReference mDatabaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
            mFourthInterestTextView,mFifthInterestTextView,introTv;
    private ImageView mProfilePictureImageView;

    String globalName = "",firstInterest = "",secondInterest = "",
            thirdInterest = "",fourthInterest = "",fifthInterest = "",profilePictureString = "";
    String intro = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_each_user_in_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        mProfilePictureImageView = getView().findViewById(R.id.profile_picture_imageview_search_result);
        if(!profilePictureString.equals(""))
        {
            Uri ppUri = Uri.parse(profilePictureString);
            Picasso.get().load(ppUri).fit().centerCrop().into(mProfilePictureImageView);
        }

        mUsernameTextView = getView().findViewById(R.id.user_name_textview_search_result);

        mFirstInterestTextView = getView().findViewById(R.id.first_interest_textview_search_result);
        mSecondInterestTextView = getView().findViewById(R.id.second_interest_textview_search_result);
        mThirdInterestTextView = getView().findViewById(R.id.third_interest_textview_search_result);
        mFourthInterestTextView = getView().findViewById(R.id.fourth_interest_textview_search_result);
        mFifthInterestTextView = getView().findViewById(R.id.fifth_interest_textview_search_result);

        introTv = getView().findViewById(R.id.intro_tv_search_result);

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
                String id = user.getUid();
                Log.d("gettingids",id);
                //                mDatabaseReference.child("BrowsedUsers").child(mFirebaseAuthentication.getCurrentUser().getUid()).setValue(firstInterest);

            }
        });
        mSecondInterestTextView.setText(secondInterest);
        mThirdInterestTextView.setText(thirdInterest);
        mFourthInterestTextView.setText(fourthInterest);
        mFifthInterestTextView.setText(fifthInterest);

        introTv.setText(intro);
    }
}