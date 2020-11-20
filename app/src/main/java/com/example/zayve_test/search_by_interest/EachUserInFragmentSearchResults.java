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
import com.example.zayve_test.ui.browse_friends.EachUserProfile;
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
    Boolean isDataLoaded = false;

    TreeMap<String, ArrayList<String>> requestTreeMap = new TreeMap<>();

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();

    String colorHexCode = "#2E7D32";

    int timeCounter = 0;
    int secondTimeCounter = 0;

    ArrayList<String> keyInterestRequestedUserAl = new ArrayList<>();


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
            mFourthInterestTextView,mFifthInterestTextView,introTv;
    private ImageView mProfilePictureImageView, mFirstInterestIv,mSecondInterestIv, mThirdInterestIv,
        mFourthInterestIv,mFifthInterestIv;

    String userId = "",globalName = "Pahadi",firstInterest = "Gaming",secondInterest = "",
            thirdInterest = "",fourthInterest = "",fifthInterest = "",profilePictureString = "";
    String intro = "";

    boolean switchRequestFirstInt, switchRequestSecondInt, switchRequestThirdInt,
            switchRequestFourthInt, switchRequestFifthInt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        userId = EachUserInFragmentSearchResultsArgs.fromBundle(getArguments()).getUserID();
        Log.d("userId",userId);
//        intro = getArguments().getString("intro");

        Log.d("onResume","on Create View called");
        return inflater.inflate(R.layout.fragment_each_user_in_search_results, container, false);
    }

    public void getUser(String userId)
    {
        mRtDatabase.child("users").child(userId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for(DataSnapshot childSnapshot : snapshot.getChildren())
                {

                    String fieldKey = childSnapshot.getKey();
                    String value = childSnapshot.getValue().toString();

                    switch (fieldKey) {
                        case "user_name":
                            globalName = value;
                            break;
                        case "about":
                            intro = value;
                            break;
                        case "profile_image":
                            profilePictureString = value;
                            break;
                        default:
                            break;
                    }

                    for (DataSnapshot secondLevelChildSnapshot : childSnapshot.getChildren()) {


                        String secondLevelKey = secondLevelChildSnapshot.getKey();
                        String keyValue = secondLevelChildSnapshot.getValue().toString();

                        switch (secondLevelKey) {
                            case "0":
                                firstInterest = keyValue;
                                break;
                            case "1":
                                secondInterest = keyValue;
                                break;
                            case "2":
                                thirdInterest = keyValue;
                                break;
                            case "3":
                                fourthInterest = keyValue;
                                break;
                            case "4":
                                fifthInterest = keyValue;
                                break;
                            default:
                                break;
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


        long timeStart = System.currentTimeMillis();
        while(globalName.equals("") && secondTimeCounter < 2)
        {
            long timeEnd = System.currentTimeMillis();

            if(timeEnd - timeStart > 1000)
            {
                secondTimeCounter = 5;
            }

        }
        isDataLoaded = true;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("onResume","on View Created called");

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

        mFirstInterestIv = getView().findViewById(R.id.first_interest_iv);
        mSecondInterestIv = getView().findViewById(R.id.second_interest_iv);
        mThirdInterestIv = getView().findViewById(R.id.third_interest_iv);
        mFourthInterestIv  = getView().findViewById(R.id.fourth_interest_iv);
        mFifthInterestIv  = getView().findViewById(R.id.fifth_interest_iv);

        introTv = getView().findViewById(R.id.intro_tv_search_result);

        mUsernameTextView.setText(globalName);
        mUsernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),globalName,Toast.LENGTH_SHORT).show();
            }
        });

        getUser(userId);
        if(isDataLoaded)
        {
            InitializeViews();
        }


        introTv.setText(intro);

    }

    private void InitializeViews() {
        mFirstInterestTextView.setText(GetInterestName(firstInterest));
        mFirstInterestTextView.setTextColor(Color.parseColor(GetInterestColor(firstInterest)));
        SetInterestSwitch(firstInterest);
        if(switchRequestFirstInt)
        {
            mFirstInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mFirstInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toggleSwitchRequest(1);
                if(switchRequestFirstInt)
                {
                    mFirstInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                    mFirstInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(firstInterest));
                }
                else
                {
                    mFirstInterestTextView.setTextColor(Color.parseColor("#000000"));
                    Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                    mFirstInterestIv.setImageResource(android.R.color.transparent);
                    cancelRequest(GetInterestName(firstInterest));
                }
            }
        });

        mSecondInterestTextView.setText(GetInterestName(secondInterest));
        mSecondInterestTextView.setTextColor(Color.parseColor(GetInterestColor(secondInterest)));
        SetInterestSwitch(secondInterest);
        if(switchRequestSecondInt)
        {
            mSecondInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mSecondInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSwitchRequest(2);
                if(switchRequestSecondInt)
                {
                    mSecondInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                    mSecondInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(secondInterest));
                }
                else
                {
                    mSecondInterestTextView.setTextColor(Color.parseColor("#000000"));
                    Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                    mSecondInterestIv.setImageResource(android.R.color.transparent);
                    cancelRequest(GetInterestName(secondInterest));
                }
            }
        });

        mThirdInterestTextView.setText(GetInterestName(thirdInterest));
        mThirdInterestTextView.setTextColor(Color.parseColor(GetInterestColor(thirdInterest)));
        SetInterestSwitch(thirdInterest);
        if(switchRequestThirdInt)
        {
            mThirdInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mThirdInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSwitchRequest(3);
                if(switchRequestThirdInt)
                {
                    mThirdInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                    mThirdInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(thirdInterest));
                }
                else
                {
                    mThirdInterestTextView.setTextColor(Color.parseColor("#000000"));
                    Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                    mThirdInterestIv.setImageResource(android.R.color.transparent);
                    cancelRequest(GetInterestName(thirdInterest));
                }
            }
        });

        mFourthInterestTextView.setText(GetInterestName(fourthInterest));
        mFourthInterestTextView.setTextColor(Color.parseColor(GetInterestColor(fourthInterest)));
        SetInterestSwitch(fourthInterest);
        if(switchRequestFourthInt)
        {
            mFourthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mFourthInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSwitchRequest(4);
                if(switchRequestFourthInt)
                {
                    mFourthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                    mFourthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(fourthInterest));
                }
                else
                {
                    mFourthInterestTextView.setTextColor(Color.parseColor("#000000"));
                    Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                    mFourthInterestIv.setImageResource(android.R.color.transparent);
                    cancelRequest(GetInterestName(fourthInterest));
                }
            }
        });

        mFifthInterestTextView.setText(GetInterestName(fifthInterest));
        mFifthInterestTextView.setTextColor(Color.parseColor(GetInterestColor(fifthInterest)));
        SetInterestSwitch(fifthInterest);
        if(switchRequestFifthInt)
        {
            mFifthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mFifthInterestTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSwitchRequest(5);
                if(switchRequestFifthInt)
                {
                    mFifthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    Toast.makeText(getContext(),"ZayvE Request Sent!",Toast.LENGTH_SHORT).show();
                    mFifthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(fifthInterest));
                }
                else
                {
                    mFifthInterestTextView.setTextColor(Color.parseColor("#000000"));
                    Toast.makeText(getContext(),"ZayvE Request Cancelled!",Toast.LENGTH_SHORT).show();
                    mFifthInterestIv.setImageResource(android.R.color.transparent);
                    cancelRequest(GetInterestName(fifthInterest));
                }
            }
        });
    }

    private void SetInterestSwitch(String interestName) {
        int length = interestName.length();
        String lastTwo = "";

        if(length>2)
        {
            lastTwo = interestName.substring(length-2);
        }

        if(lastTwo.equals("**"))
        {
            if(interestName.equals(firstInterest)){
                switchRequestFirstInt = true;
            }
            if(interestName.equals(secondInterest)){
                switchRequestSecondInt = true;
            }
            if(interestName.equals(thirdInterest)){
                 switchRequestThirdInt = true;
            }
            if(interestName.equals(fourthInterest)){
                 switchRequestFourthInt = true;
            }
            if(interestName.equals(fifthInterest)){
                 switchRequestFifthInt = true;
            }
        }
    }

    private String GetInterestName(String interestName)
    {
        String value = interestName;

        int length = interestName.length();

        String lastTwo = "";

        if(length>2)
        {
            lastTwo = interestName.substring(length-2);
        }


        if(lastTwo.equals("**"))
        {
            value = interestName.substring(0,length-2);
            Log.d("lastTwo",value);
        }

        return value;
    }
    private String GetInterestColor(String interestName) {
        String color = "#000000";
        int length = interestName.length();

        String lastTwo = "";

        if(length>2)
        {
            lastTwo = interestName.substring(length-2);
        }
        if(lastTwo.equals("**") || switchRequestFirstInt)
        {
            color = colorHexCode;
        }

        return color;
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