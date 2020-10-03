package com.example.zayve_test.models;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.zayve_test.BrowseFriendsFragment;
import com.example.zayve_test.ClassForGettingSnapshots;
import com.example.zayve_test.EachUserInFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter
{

    ArrayList<String> arrayList = new ArrayList<>();

    String string;

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        EachUserInFragment eachUserInFragment = new EachUserInFragment();
        Bundle bundle = new Bundle();

        bundle.putString("globalName","string");


        eachUserInFragment.setArguments(bundle);
        return eachUserInFragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }
}
