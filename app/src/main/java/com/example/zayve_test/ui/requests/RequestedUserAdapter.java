package com.example.zayve_test.ui.requests;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zayve_test.R;
import com.example.zayve_test.search_by_interest.CircleTransform;
import com.example.zayve_test.search_by_interest.SearchByInterestRvAdapter;
import com.example.zayve_test.ui.browse_friends.AdapterBrowseFriends;
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

public class RequestedUserAdapter extends RecyclerView.Adapter<RequestedUserAdapter.RequestedUserViewHolder>{

    Context mContext;

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();

    public ArrayList<ArrayList<String>> requestedUserArraylist = new ArrayList<>();

    final private ItemClickListener mItemClickListener;

    public RequestedUserAdapter(Context mContext, ItemClickListener listener) {
        this.mContext = mContext;
        mItemClickListener = listener;
    }


    @NonNull
    @Override
    public RequestedUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.each_requested_user_layout,
                parent,false);

        return new RequestedUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestedUserViewHolder holder, int position) {
        holder.linearLayout.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        ArrayList<String> stringArrayList = requestedUserArraylist.get(position);
        String userId = stringArrayList.get(0);
        String interestName = stringArrayList.get(1);

        if(!interestName.isEmpty())
        {
            holder.mInterestTextview.setText(interestName);
        }

        mRtDatabase.child("users").child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals("user_name"))
                    {
                        String name = dataSnapshot.getValue().toString();
                        if(!name.isEmpty())
                        {
                            holder.mRequestedUsernameTextView.setText(name);
                        }
                    }

                    if(dataSnapshot.getKey().equals("profile_image"))
                    {
                        String url = dataSnapshot.getValue().toString();
                        if(!url.isEmpty())
                        {
                            Uri uri = Uri.parse(url);
                            Picasso.get().load(uri).fit().centerCrop().transform(new CircleTransform()).into(holder.mProfilePictureImageView);                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(requestedUserArraylist == null)
            return 0;
        return requestedUserArraylist.size();
    }

    public ArrayList<ArrayList<String>> getRequestedUserArraylist() {
        return requestedUserArraylist;
    }

    public void setRequestedUserArraylist(ArrayList<ArrayList<String>> requestedUserArraylist) {
        this.requestedUserArraylist = requestedUserArraylist;
    }

    public void AddToTheEndAl(ArrayList<String> arrayList)
    {
        requestedUserArraylist.add(arrayList);
        notifyDataSetChanged();
    }

    public interface ItemClickListener
    {
        void onItemClickListener(String userId);
    }

    class RequestedUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout linearLayout;

        TextView mRequestedUsernameTextView, mInterestTextview;
        ImageView mProfilePictureImageView;

        public RequestedUserViewHolder(@NonNull View itemView) {
            super(itemView);

            linearLayout = itemView.findViewById(R.id.requested_user_layout);

            mRequestedUsernameTextView = itemView.findViewById(R.id.tvRequestedUserName);
            mInterestTextview = itemView.findViewById(R.id.tvRequestedInterestName);
            mProfilePictureImageView = itemView.findViewById(R.id.requestedUserLogo);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            String userId = requestedUserArraylist.get(getAdapterPosition()).get(0);
            mItemClickListener.onItemClickListener(userId);
        }
    }
}
