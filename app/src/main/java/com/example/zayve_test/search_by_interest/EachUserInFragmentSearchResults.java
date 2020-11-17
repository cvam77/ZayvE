package com.example.zayve_test.search_by_interest;

import android.graphics.Color;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.TreeMap;


public class EachUserInFragmentSearchResults extends Fragment {

    TreeMap<String, ArrayList<String>> requestTreeMap = new TreeMap<>();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();

    String colorHexCode = "#2E7D32";

    int timeCounter = 0;

    ArrayList<String> keyInterestRequestedUserAl = new ArrayList<>();
    boolean arrayListDone = false;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
            mFourthInterestTextView,mFifthInterestTextView,introTv;
    private ImageView mProfilePictureImageView, mFirstInterestIv,mSecondInterestIv, mThirdInterestIv,
        mFourthInterestIv,mFifthInterestIv;

    String userId = "",globalName = "",firstInterest = "",secondInterest = "",
            thirdInterest = "",fourthInterest = "",fifthInterest = "",profilePictureString = "";
    String intro = "";

    boolean switchRequestFirstInt = false, switchRequestSecondInt, switchRequestThirdInt,
            switchRequestFourthInt, switchRequestFifthInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        userId = getArguments().getString("userId");
        Log.d("bahubali","userId = " + userId);
        globalName = getArguments().getString("globalName");
        firstInterest = getArguments().getString("firstInterest");
        secondInterest = getArguments().getString("secondInterest");
        thirdInterest = getArguments().getString("thirdInterest");
        fourthInterest = getArguments().getString("fourthInterest");
        fifthInterest = getArguments().getString("fifthInterest");
        profilePictureString = getArguments().getString("profilePictureString");
        intro = getArguments().getString("intro");

        return inflater.inflate(R.layout.fragment_each_user_in_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchRequestsSent();

        if(arrayListDone)
        {
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

                    toggleSwitchRequest(1);
                    if(switchRequestFirstInt)
                    {
                        mFirstInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                        Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                        mFirstInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                        addRequest(firstInterest);
                    }
                    else
                    {
                        mFirstInterestTextView.setTextColor(Color.parseColor("#000000"));
                        Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                        mFirstInterestIv.setImageResource(android.R.color.transparent);
                        cancelRequest(firstInterest);
                    }
                }
            });

            mSecondInterestTextView.setText(secondInterest);
            mSecondInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSwitchRequest(2);
                    if(switchRequestSecondInt)
                    {
                        mSecondInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                        Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                        mSecondInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                        addRequest(secondInterest);
                    }
                    else
                    {
                        mSecondInterestTextView.setTextColor(Color.parseColor("#000000"));
                        Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                        mSecondInterestIv.setImageResource(android.R.color.transparent);
                        cancelRequest(secondInterest);
                    }
                }
            });

            mThirdInterestTextView.setText(thirdInterest);
            mThirdInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSwitchRequest(3);
                    if(switchRequestThirdInt)
                    {
                        mThirdInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                        Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                        mThirdInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                        addRequest(thirdInterest);
                    }
                    else
                    {
                        mThirdInterestTextView.setTextColor(Color.parseColor("#000000"));
                        Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                        mThirdInterestIv.setImageResource(android.R.color.transparent);
                        cancelRequest(thirdInterest);
                    }
                }
            });

            mFourthInterestTextView.setText(fourthInterest);
            mFourthInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSwitchRequest(4);
                    if(switchRequestFourthInt)
                    {
                        mFourthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                        Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                        mFourthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                        addRequest(fourthInterest);
                    }
                    else
                    {
                        mFourthInterestTextView.setTextColor(Color.parseColor("#000000"));
                        Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                        mFourthInterestIv.setImageResource(android.R.color.transparent);
                        cancelRequest(fourthInterest);
                    }
                }
            });

            mFifthInterestTextView.setText(fifthInterest);
            mFifthInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleSwitchRequest(5);
                    if(switchRequestFifthInt)
                    {
                        mFifthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                        Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                        mFifthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                        addRequest(fifthInterest);
                    }
                    else
                    {
                        mFifthInterestTextView.setTextColor(Color.parseColor("#000000"));
                        Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                        mFifthInterestIv.setImageResource(android.R.color.transparent);
                        cancelRequest(fifthInterest);
                    }
                }
            });

            mFirstInterestIv = getView().findViewById(R.id.first_interest_iv);

            mSecondInterestIv = getView().findViewById(R.id.second_interest_iv);
            mThirdInterestIv = getView().findViewById(R.id.third_interest_iv);

            mFourthInterestIv = getView().findViewById(R.id.fourth_interest_iv);
            mFifthInterestIv = getView().findViewById(R.id.fifth_interest_iv);
            introTv.setText(intro);
        }
    }

    private String getTextColor(String interestName) {
        String color = "#FFFFFF";
        Log.d("prabhas","interest name = " + interestName);
        Log.d("prabhas","arrayList has " + keyInterestRequestedUserAl.size());
        if(keyInterestRequestedUserAl.contains(interestName))
            color = colorHexCode;

        return color;
    }

    private void SearchRequestsSent() {
        mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String userIdRequestSentKey = snapshot.getKey();

                    if(userIdRequestSentKey.equals(userId))
                    {
                        for(DataSnapshot childSnapshot: snapshot.getChildren())
                        {
                            String requestSentInterestKey = childSnapshot.getKey();

                            if(!keyInterestRequestedUserAl.contains(requestSentInterestKey))
                            {
                                Log.d("villiers",requestSentInterestKey);
                                keyInterestRequestedUserAl.add(requestSentInterestKey);
                            }
                        }
                    }

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        arrayListDone = true;
    }

    private void cancelRequest(String interestName)
    {
        mRtDatabase.child("users").child(userId).child("requests_received").child(currentUser.getUid()).child(interestName).setValue(null);
        mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").child(userId).child(interestName).setValue(null);
    }

    private void addRequest(String interestName)
    {
       mRtDatabase.child("users").child(userId).child("requests_received").child(currentUser.getUid()).child(interestName).setValue("true");
       mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").child(userId).child(interestName).setValue("true");
    }

    public void toggleSwitchRequest(int interestNumber)
    {
        switch (interestNumber)
        {
            case 1:
                switchRequestFirstInt = !switchRequestFirstInt;
                break;
            case 2:
                switchRequestSecondInt = !switchRequestSecondInt;
                break;
            case 3:
                switchRequestThirdInt = !switchRequestThirdInt;
                break;
            case 4:
                switchRequestFourthInt = !switchRequestFourthInt;
                break;
            case 5:
                switchRequestFifthInt = !switchRequestFifthInt;
                break;
            default:
                break;


        }

    }
}