package com.example.zayve_test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zayve_test.databinding.ActivityMainBinding;

public class HomePageFragm extends Fragment {

    private ActivityMainBinding activityMainBinding;
    private clickHandlerHomePageFragm mClickHandlerHomePageFragm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activityMainBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_home_page, container, false);
        View view = activityMainBinding.getRoot();

        mClickHandlerHomePageFragm = new clickHandlerHomePageFragm();

        return view;

    }

    public class clickHandlerHomePageFragm
    {

        public void BrowseFriendButtonClicked(View view)
        {
            Fragment hometoBrowseFragment = new BrowseFriendsFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.home_page_fragment,hometoBrowseFragment);

            fragmentTransaction.commit();
        }

    }


}