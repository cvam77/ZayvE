package com.example.zayve_test.models;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.zayve_test.search_by_interest.EachUserInFragmentSearchResults;
import com.example.zayve_test.ui.browse_friends.EachUserProfile;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{

    ArrayList<EachUserProfile> arrayList = new ArrayList<>();

    ArrayList<EachUserProfile> VarrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;


    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<EachUserProfile> a) {
        super(fm);
        this.VarrayList = a;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        EachUserInFragmentSearchResults eachUserInFragment = new EachUserInFragmentSearchResults();
        Bundle bundle = new Bundle();

        bundle.putString("userId",VarrayList.get(position).getUserId());
        bundle.putString("globalName",VarrayList.get(position).getUserName());
        bundle.putString("firstInterest",VarrayList.get(position).getFirstInterest());
        bundle.putString("secondInterest",VarrayList.get(position).getSecondInterest());
        bundle.putString("thirdInterest",VarrayList.get(position).getThirdInterest());
        bundle.putString("fourthInterest",VarrayList.get(position).getFourthInterest());
        bundle.putString("fifthInterest",VarrayList.get(position).getFifthInterest());
        bundle.putString("profilePictureString",VarrayList.get(position).getProfilePicture());
        bundle.putString("intro",VarrayList.get(position).getUserAbout());
        eachUserInFragment.setArguments(bundle);
        return eachUserInFragment;
    }

    @Override
    public int getCount() {
        return VarrayList.size();
    }



    public void setArrayList(ArrayList<EachUserProfile> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
