package com.example.zayve_test.search_by_interest;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zayve_test.R;
import com.example.zayve_test.ui.EachUserProfile;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchByInterestRvAdapter extends RecyclerView.Adapter<SearchByInterestRvAdapter.SearchResultsViewHolder>
{

    private Context mContext;
    List<EachUserProfile> mUserList;

    final private ItemClickListener mItemClickListener;

    public SearchByInterestRvAdapter(Context mContext, ItemClickListener listener) {
        this.mContext = mContext;
        mItemClickListener = listener;
    }

    @NonNull
    @Override
    public SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.each_search_result_layout,
                parent,false);

        return new SearchResultsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultsViewHolder holder, int position) {
        EachUserProfile eachUserProfile = mUserList.get(position);

        String name = eachUserProfile.getUserName();
        if (!name.equals("")) {
            holder.mNameTv.setText("\"" + name + "\"" + " likes...");
        }

        String firstInterest = eachUserProfile.getFirstInterest();
        if (!firstInterest.equals("")) {
            holder.mFirstIntTv.setText(firstInterest);
        }

        String secondInterest = eachUserProfile.getSecondInterest();
        if (!secondInterest.equals(""))
        {
            holder.mSecondIntTv.setText(secondInterest);
        }

        String thirdInterest = eachUserProfile.getThirdInterest();
        if(!thirdInterest.equals(""))
        {
            holder.mThirdIntTv.setText(thirdInterest);
        }

        String fourthInterest = eachUserProfile.getFourthInterest();
        if(!fourthInterest.equals(""))
        {
            holder.mFourthIntTv.setText(fourthInterest);
        }

        String fifthInterest = eachUserProfile.getFifthInterest();
        if(!fifthInterest.equals(""))
        {
            holder.mFifthIntTv.setText(fifthInterest);
        }

        String profileUrl = eachUserProfile.getProfilePicture();
        if(!profileUrl.equals(""))
        {
            Uri uri = Uri.parse(profileUrl);
            Picasso.get().load(uri).fit().centerCrop().into(holder.mProfilePicIv);
        }
    }

    @Override
    public int getItemCount() {
        if(mUserList == null)
        {
            return 0;
        }
        return mUserList.size();
    }

    public List<EachUserProfile> getmUserList() {
        return mUserList;
    }

    public void setmUserList(List<EachUserProfile> mUserList) {
        this.mUserList = mUserList;
        notifyDataSetChanged();
    }

    public interface ItemClickListener
    {
        void onItemClickListener(String userId, String name, String profilePicUrlString, String about,
                                 String firstInt, String secondInt, String thirdInt, String fourthInt,
                                 String fifthInt);
    }

    class SearchResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mNameTv, mFirstIntTv, mSecondIntTv, mThirdIntTv, mFourthIntTv, mFifthIntTv;
        ImageView mProfilePicIv;

        public SearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);

            mNameTv = itemView.findViewById(R.id.tvName);
            mFirstIntTv = itemView.findViewById(R.id.tvFirstInterest);
            mSecondIntTv = itemView.findViewById(R.id.tvSecondInterest);
            mThirdIntTv = itemView.findViewById(R.id.tvThirdInterest);
            mFourthIntTv = itemView.findViewById(R.id.tvFourthInterest);
            mFifthIntTv = itemView.findViewById(R.id.tvFifthInterest);

            mProfilePicIv = itemView.findViewById(R.id.logo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String userId = mUserList.get(getAdapterPosition()).getUserId();
            String userName = mUserList.get(getAdapterPosition()).getUserName();
            String userProfilePicUrlString = mUserList.get(getAdapterPosition()).getProfilePicture();
            String userAbout = mUserList.get(getAdapterPosition()).getUserAbout();
            String firstInterest = mUserList.get(getAdapterPosition()).getFirstInterest();
            String secondInterest = mUserList.get(getAdapterPosition()).getSecondInterest();
            String thirdInterest = mUserList.get(getAdapterPosition()).getThirdInterest();
            String fourthInterest = mUserList.get(getAdapterPosition()).getFourthInterest();
            String fifthInterest = mUserList.get(getAdapterPosition()).getFifthInterest();
            mItemClickListener.onItemClickListener(userId,userName,userProfilePicUrlString,userAbout,
                    firstInterest,secondInterest,thirdInterest,fourthInterest,fifthInterest);
        }
    }
}
