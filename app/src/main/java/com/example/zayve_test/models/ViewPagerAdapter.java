package com.example.zayve_test.models;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.zayve_test.ui.EachUserInFragment;
import com.google.firebase.database.DatabaseReference;
import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{

    ArrayList<String> arrayList = new ArrayList<>();

    ArrayList<ArrayList<String>> VarrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef;


    public ViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<ArrayList<String>> a) {
        super(fm);
        this.VarrayList = a;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        EachUserInFragment eachUserInFragment = new EachUserInFragment();
        Bundle bundle = new Bundle();

        bundle.putString("globalName",VarrayList.get(0).get(position));
        bundle.putString("firstInterest",VarrayList.get(1).get(position));
        bundle.putString("secondInterest",VarrayList.get(2).get(position));
        bundle.putString("thirdInterest",VarrayList.get(3).get(position));
        bundle.putString("fourthInterest",VarrayList.get(4).get(position));
        bundle.putString("fifthInterest",VarrayList.get(5).get(position));
        bundle.putString("profilePictureString",VarrayList.get(6).get(position));
        bundle.putString("intro",VarrayList.get(7).get(position));
        eachUserInFragment.setArguments(bundle);
        return eachUserInFragment;
    }

    @Override
    public int getCount() {
        return VarrayList.get(0).size();
    }



    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
