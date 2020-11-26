package com.example.zayve_test.ui.browse_friends;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

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

public class AdapterBrowseFriends extends RecyclerView.Adapter<AdapterBrowseFriends.BrowseFriendsViewHolder> {

    String colorHexCode = "#2E7D32";

    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();
    FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    Context mContext;

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();

    public ArrayList<String> VarrayList = new ArrayList<>();

    public ArrayList<String> interestAl = new ArrayList<>();

    public AdapterBrowseFriends(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public BrowseFriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_each_user_in_search_results,
                parent,false);

        return new BrowseFriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrowseFriendsViewHolder holder, int position) {

        interestAl.clear();

        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        holder.mUsernameTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));

        holder.mFirstInterestTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.mSecondInterestTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.mThirdInterestTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.mFourthInterestTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));
        holder.mFifthInterestTextView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));

        mRtDatabase.child("users").child(VarrayList.get(position)).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String fieldKey = snapshot.getKey();
                    String value = snapshot.getValue().toString();

                    switch(fieldKey)
                    {
                        case "user_name":
                            if(value != null)
                            {
                                holder.mUsernameTextView.setText(value);
                            }
                            break;
                        case "about":
                            if(value != null)
                            {
                                holder.introTv.setText(value);
                            }
                            break;
                        case "profile_image":
                            if(value != null)
                            {
                                Uri ppUri = Uri.parse(value);
                                Picasso.get().load(ppUri).fit().centerCrop().into(holder.mProfilePictureImageView);
                            }
                            break;
                        case "location":
                            if(value != null)
                            {
                                holder.mLocationTextView.setVisibility(View.VISIBLE);
                                holder.mLocationTextView.setText(value);
                            }
                            break;
                        default:
                            break;
                    }



                    for(DataSnapshot secondLevelChildSnapshot: snapshot.getChildren()) {
                        String keySecondLevel = secondLevelChildSnapshot.getKey();

                        for(DataSnapshot thirdLevelSnapshot : secondLevelChildSnapshot.getChildren())
                        {
                            String valueThirdLevel = thirdLevelSnapshot.getValue().toString();
                            if(valueThirdLevel.equals(getCurrentUser.getUid()))
                            {
                                interestAl.add(keySecondLevel);
                            }
                        }
                    }

                    for(DataSnapshot secondLevelChildSnapshot: snapshot.getChildren())
                    {
                        String secondLevelKey = secondLevelChildSnapshot.getKey();
                        String keyValue = secondLevelChildSnapshot.getValue().toString();

                        switch (secondLevelKey)
                        {
                            case "0":
                                if(value != null)
                                {
                                    holder.mFirstInterestTextView.setText(keyValue);
                                    holder.mFirstInterestTextView.setTextColor(Color.parseColor(GetInterestColor(keyValue)));
                                }
                                break;
                            case "1":
                                if(value != null)
                                {
                                    holder.mSecondInterestTextView.setText(keyValue);
                                    holder.mSecondInterestTextView.setTextColor(Color.parseColor(GetInterestColor(keyValue)));
                                }
                                break;
                            case "2":
                                if(value != null)
                                {
                                    holder.mThirdInterestTextView.setText(keyValue);
                                    holder.mThirdInterestTextView.setTextColor(Color.parseColor(GetInterestColor(keyValue)));
                                }
                                break;
                            case "3":
                                if(value != null)
                                {
                                    holder.mFourthInterestTextView.setText(keyValue);
                                    holder.mFourthInterestTextView.setTextColor(Color.parseColor(GetInterestColor(keyValue)));
                                }
                                break;
                            case "4":
                                if(value != null)
                                {
                                    holder.mFifthInterestTextView.setText(keyValue);
                                    holder.mFifthInterestTextView.setTextColor(Color.parseColor(GetInterestColor(keyValue)));
                                }
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
    }

    @Override
    public int getItemCount() {
        if(VarrayList == null)
            return 0;
        return VarrayList.size();
    }

    public ArrayList<String> getVarrayList() {
        return VarrayList;
    }

    public void setVarrayList(ArrayList<String> varrayList) {
        VarrayList = varrayList;
        notifyDataSetChanged();
    }

    public void AddToTheEndAl(String lastId)
    {
        VarrayList.add(lastId);
        notifyDataSetChanged();
    }

    public void DeleteUser(int position)
    {
        mDatabaseRef.child("users").child(VarrayList.get(position)).child("deleted_by").child(getCurrentUser.getUid()).setValue("true");
        Toast.makeText(mContext, "User Deleted!", Toast.LENGTH_SHORT).show();
        VarrayList.remove(position);
        notifyDataSetChanged();

    }

    String GetInterestColor(String interestName) {
        String color = "#000000";

        Log.d("interestSize", String.valueOf(interestAl.size()));
        if(interestAl.contains(interestName))
        {
            color = colorHexCode;
        }

        return color;
    }

    public void addRequest(String interestName, int position)
    {
        mRtDatabase.child("users").child(VarrayList.get(position)).child("interest_requests").child(interestName).push().setValue(getCurrentUser.getUid());
        mRtDatabase.child("users").child(getCurrentUser.getUid()).child("requests_sent").child(interestName).push().child(VarrayList.get(position));
        Toast.makeText(mContext,"ZayvE Request Sent!", Toast.LENGTH_SHORT).show();

        DeleteUser(position);
    }

    class BrowseFriendsViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout linearLayout;

        TextView mUsernameTextView,mFirstInterestTextView,mSecondInterestTextView,mThirdInterestTextView,
                mFourthInterestTextView,mFifthInterestTextView,introTv, mLocationTextView;
        ImageView mProfilePictureImageView, mFirstInterestIv,mSecondInterestIv, mThirdInterestIv,
                mFourthInterestIv,mFifthInterestIv, mDeleteUserIv;

        public BrowseFriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.each_user_layout);

            mLocationTextView = itemView.findViewById(R.id.location);

            mProfilePictureImageView = itemView.findViewById(R.id.profile_picture_imageview_search_result);

            mUsernameTextView = itemView.findViewById(R.id.user_name_textview_search_result);

            introTv = itemView.findViewById(R.id.intro_tv_search_result);

            mDeleteUserIv = itemView.findViewById(R.id.delete_user);
            mDeleteUserIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DeleteUser(getAdapterPosition());
                }
            });

            mFirstInterestTextView = itemView.findViewById(R.id.first_interest_textview_search_result);
            mFirstInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRequest(mFirstInterestTextView.getText().toString(), getAdapterPosition());
                    mFirstInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                }
            });

            mSecondInterestTextView = itemView.findViewById(R.id.second_interest_textview_search_result);
            mSecondInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRequest(mSecondInterestTextView.getText().toString(), getAdapterPosition());
                    mSecondInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                }
            });
            mThirdInterestTextView = itemView.findViewById(R.id.third_interest_textview_search_result);
            mThirdInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRequest(mThirdInterestTextView.getText().toString(), getAdapterPosition());
                    mThirdInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                }
            });
            mFourthInterestTextView = itemView.findViewById(R.id.fourth_interest_textview_search_result);
            mFourthInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRequest(mFourthInterestTextView.getText().toString(), getAdapterPosition());
                    mFourthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                }
            });
            mFifthInterestTextView = itemView.findViewById(R.id.fifth_interest_textview_search_result);
            mFifthInterestTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addRequest(mFifthInterestTextView.getText().toString(), getAdapterPosition());
                    mFifthInterestTextView.setTextColor(Color.parseColor(colorHexCode));
                }
            });
            mFirstInterestIv = itemView.findViewById(R.id.first_interest_iv);
            mSecondInterestIv = itemView.findViewById(R.id.second_interest_iv);
            mThirdInterestIv = itemView.findViewById(R.id.third_interest_iv);
            mFourthInterestIv  =itemView.findViewById(R.id.fourth_interest_iv);
            mFifthInterestIv  = itemView.findViewById(R.id.fifth_interest_iv);
        }
    }

}
