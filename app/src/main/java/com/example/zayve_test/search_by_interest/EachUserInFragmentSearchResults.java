package com.example.zayve_test.search_by_interest;

import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zayve_test.R;
import com.example.zayve_test.ui.browse_friends.EachUserProfile;
import com.google.android.gms.auth.api.credentials.internal.DeleteRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.TreeMap;


public class EachUserInFragmentSearchResults extends Fragment {

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();

    int secondTimeCounter = 0;

    String colorHexCode = "#2E7D32";

    private TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
            mFourthInterestTextView,mFifthInterestTextView,introTv,mLocationTextView;
    private ImageView mProfilePictureImageView, mFirstInterestIv,mSecondInterestIv, mThirdInterestIv,
            mFourthInterestIv,mFifthInterestIv, mDeleteUserIv;
    CardView mCardView1, mCardView2, mCardView3, mCardView4, mCardView5;

    String userId = "",globalName = "",firstInterest = "",secondInterest = "",
            thirdInterest = "",fourthInterest = "",fifthInterest = "",profilePictureString = "", location = "";
    String intro = "";

    boolean switchRequestFirstInt, switchRequestSecondInt, switchRequestThirdInt,
            switchRequestFourthInt, switchRequestFifthInt;

    boolean lockRequest = false;

    ArrayList<String> requestArrayList = new ArrayList<>();

    LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        userId = EachUserInFragmentSearchResultsArgs.fromBundle(getArguments()).getUserID();

        ExecuteAsyncTasks();

        return inflater.inflate(R.layout.fragment_each_user_in_search_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("sequencenumber","on View Created called");

        mDeleteUserIv = getView().findViewById(R.id.delete_user);
        mDeleteUserIv.setVisibility(View.GONE);

        linearLayout = getView().findViewById(R.id.each_user_layout);
        linearLayout.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.fade_transition_animation));

        mProfilePictureImageView = getView().findViewById(R.id.profile_picture_imageview_search_result);

        mUsernameTextView = getView().findViewById(R.id.user_name_textview_search_result);

        mLocationTextView = getView().findViewById(R.id.location);

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

        mCardView5 = getView().findViewById(R.id.cardview5);
        mCardView4 = getView().findViewById(R.id.cardview4);
        mCardView3 = getView().findViewById(R.id.cardview3);
        mCardView2 = getView().findViewById(R.id.cardview2);
        mCardView1 = getView().findViewById(R.id.cardview1);

        introTv = getView().findViewById(R.id.intro_tv_search_result);

        Log.d("sequencenumber", "1");

        requestArrayList.clear();
        ExecuteAsyncTasks();

    }

    public void ExecuteAsyncTasks() {
        Log.d("sequencenumber", "2");
        AsyncTaskGetUser asyncTaskGetUser = new AsyncTaskGetUser();
        AsyncTaskInitializeViews asyncTaskInitializeViews = new AsyncTaskInitializeViews();

        asyncTaskGetUser.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        asyncTaskInitializeViews.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    public String CheckIfInterestExist(String interestName)
    {
        String value = interestName;
        if(requestArrayList.contains(interestName))
        {
            Log.d("arrayListSize", String.valueOf(requestArrayList.size()));
            value = interestName + "**";
        }

        return value;
    }

    class AsyncTaskGetUser extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("sequencenumber", "3");
            mRtDatabase.child("users").child(userId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists())
                    {

                        String fieldKey = snapshot.getKey();
                        String value = snapshot.getValue().toString();

                        for(DataSnapshot secondLevelChildSnapshot: snapshot.getChildren())
                        {

                            String secondLevelKey = secondLevelChildSnapshot.getKey();

                            String keyValue = secondLevelChildSnapshot.getValue().toString();

                            for(DataSnapshot thirdLevel : secondLevelChildSnapshot.getChildren())
                            {
                                String interestUserKey = thirdLevel.getValue().toString();
                                if(interestUserKey.equals(currentUser.getUid()))
                                {
                                    requestArrayList.add(secondLevelKey);
                                }
                            }

                            String intToSend = CheckIfInterestExist(keyValue);

                            switch (secondLevelKey)
                            {
                                case "0":
                                    firstInterest = intToSend;
                                    break;
                                case "1":
                                    secondInterest= intToSend;
                                    break;
                                case "2":
                                    thirdInterest= intToSend;
                                    break;
                                case "3":
                                    fourthInterest= intToSend;
                                    break;
                                case "4":
                                    fifthInterest= intToSend;
                                    break;
                                default:
                                    break;
                            }

                        }

                        switch(fieldKey)
                        {
                            case "user_name":
                                globalName = value;
                                break;
                            case "about":
                                intro = value;
                                break;
                            case "profile_image":
                                profilePictureString = value;
                                break;
                            case "location":
                                location = value;
                                break;
                            default:
                                break;
                        }


                        Log.d("sequencenumber", "4");
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

            while((globalName.equals("") || intro.equals(""))  && secondTimeCounter < 2)
            {
                Log.d("sequencenumber", "5");
                long timeEnd = System.currentTimeMillis();

                if(timeEnd - timeStart > 1000)
                {
                    secondTimeCounter = 5;
                }

            }

            Log.d("sequencenumber", "6");
            return null;
        }
    }


    class AsyncTaskInitializeViews extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    InitializeViews();
                }
            });
            return null;
        }
    }

    private void InitializeViews()
    {

        Log.d("sequencenumber", "9");

        mUsernameTextView.setText(globalName);

        introTv.setText(intro);

        if(!location.equals(""))
        {
            mLocationTextView.setVisibility(View.VISIBLE);
            mLocationTextView.setText(location);
        }

        if(!profilePictureString.equals(""))
        {
            Uri ppUri = Uri.parse(profilePictureString);
            Picasso.get().load(ppUri).fit().centerCrop().into(mProfilePictureImageView);
        }

        mFirstInterestTextView.setText(GetInterestName(firstInterest));
        mFirstInterestTextView.setTextColor(Color.parseColor(GetInterestColor(firstInterest)));
        SetInterestSwitch(firstInterest);

        if(switchRequestFirstInt)
        {
            mFirstInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
        }
        mCardView1.setOnClickListener(v -> {
            if(!lockRequest)
            {
                mFirstInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                mFirstInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                addRequest(GetInterestName(firstInterest));
                toggleSwitchRequest(1);
            }
            else
            {
                if(switchRequestFirstInt)
                {
                    mFirstInterestTextView.setTextColor(Color.parseColor("#000000"));
                    mFirstInterestIv.setImageResource(R.drawable.ic_baseline_person_add_24);
                    DeleteRequest(GetInterestName(firstInterest));
                    toggleSwitchRequest(1);
                }
                else
                {
                    Toast.makeText(getContext(),"Previous request still pending!",Toast.LENGTH_SHORT).show();
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
        mCardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lockRequest)
                {
                    mSecondInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    mSecondInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(secondInterest));
                    toggleSwitchRequest(2);
                }
                else
                {
                    if(switchRequestSecondInt)
                    {
                        mSecondInterestTextView.setTextColor(Color.parseColor("#000000"));
                        mSecondInterestIv.setImageResource(R.drawable.ic_baseline_person_add_24);
                        DeleteRequest(GetInterestName(secondInterest));
                        toggleSwitchRequest(2);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Previous request still pending!",Toast.LENGTH_SHORT).show();
                    }
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
        mCardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lockRequest)
                {
                    mThirdInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    mThirdInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(thirdInterest));
                    toggleSwitchRequest(3);
                }
                else
                {
                    if(switchRequestThirdInt)
                    {
                        mThirdInterestTextView.setTextColor(Color.parseColor("#000000"));
                        mThirdInterestIv.setImageResource(R.drawable.ic_baseline_person_add_24);
                        DeleteRequest(GetInterestName(thirdInterest));
                        toggleSwitchRequest(3);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Previous request still pending!",Toast.LENGTH_SHORT).show();
                    }
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
        mCardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lockRequest)
                {
                    mFourthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    mFourthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(fourthInterest));
                    toggleSwitchRequest(4);
                }
                else
                {
                    if(switchRequestFourthInt)
                    {
                        mFourthInterestTextView.setTextColor(Color.parseColor("#000000"));
                        mFourthInterestIv.setImageResource(R.drawable.ic_baseline_person_add_24);
                        DeleteRequest(GetInterestName(fourthInterest));
                        toggleSwitchRequest(4);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Previous request still pending!",Toast.LENGTH_SHORT).show();
                    }

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
        mCardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lockRequest)
                {
                    mFifthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                    mFifthInterestIv.setImageResource(android.R.drawable.checkbox_on_background);
                    addRequest(GetInterestName(fifthInterest));
                    toggleSwitchRequest(5);
                }
                else
                {
                    if(switchRequestFifthInt)
                    {
                        mFifthInterestTextView.setTextColor(Color.parseColor("#000000"));
                        mFifthInterestIv.setImageResource(R.drawable.ic_baseline_person_add_24);
                        DeleteRequest(GetInterestName(fifthInterest));
                        toggleSwitchRequest(5);
                    }
                    else
                    {
                        Toast.makeText(getContext(),"Previous request still pending!",Toast.LENGTH_SHORT).show();
                    }
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
            lockRequest = true;
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
        if(lastTwo.equals("**"))
        {
            color = colorHexCode;
        }

        return color;
    }

    private void addRequest(String interestName)
    {
        Log.d("lagado","add request called");
        lockRequest = true;
        mRtDatabase.child("users").child(userId).child("interest_requests").child(interestName).push().setValue(currentUser.getUid());
        mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").child(interestName).push().setValue(userId);
        Toast.makeText(getContext(),"ZayvE Request Sent!", Toast.LENGTH_SHORT).show();
        mRtDatabase.child("users").child(userId).child("deleted_by").child(currentUser.getUid()).setValue("true");
    }

    private void DeleteRequest(String interestName)
    {
        Log.d("lagado","delete request called");
        lockRequest = false;
        String currentInterest = interestName;
        mRtDatabase.child("users").child(userId).child("interest_requests").child(interestName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    if((childSnapshot.getValue().toString()).equals(currentUser.getUid()))
                    {
                        Log.d("gulabo",childSnapshot.getValue().toString());
                        mRtDatabase.child("users").child(userId).child("interest_requests").child(currentInterest).child(childSnapshot.getKey()).setValue(null);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").child(interestName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    if((childSnapshot.getValue().toString()).equals(userId))
                    {
                        mRtDatabase.child("users").child(currentUser.getUid()).child("requests_sent").child(currentInterest).child(childSnapshot.getKey()).setValue(null);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Toast.makeText(getContext(),"ZayvE Request Deleted", Toast.LENGTH_SHORT).show();

        mRtDatabase.child("users").child(userId).child("deleted_by").child(currentUser.getUid()).setValue(null);
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