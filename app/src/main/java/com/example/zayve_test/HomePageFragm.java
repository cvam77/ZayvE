package com.example.zayve_test;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.zayve_test.databinding.ActivityMainBinding;

import static androidx.navigation.fragment.NavHostFragment.findNavController;

public class HomePageFragm extends Fragment implements View.OnClickListener
{

    private clickHandlerHomePageFragm mClickHandlerHomePageFragm;

    private Button seeProfileButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home_page, container, false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.browseFriendsButton:
                BrowseFriendButtonClicked();
        }
    }

    public void BrowseFriendButtonClicked()
    {


    }

    public class clickHandlerHomePageFragm
    {



    }


}