package com.example.zayve_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JustForTestActivity extends AppCompatActivity {


    Button mBrowseFriendsButton;
    Button mSeeProfileButton;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_for_test);

        fragmentManager = getSupportFragmentManager();

        mBrowseFriendsButton = findViewById(R.id.browseFriendsButton);
        mBrowseFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BrowseFriendsButtonClicked();
            }
        });

        mSeeProfileButton = findViewById(R.id.see_profile_button);
        mSeeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeProfileButtonClicked();

            }
        });



    }

    private void seeProfileButtonClicked() {
        Fragment fragment = new ProfileFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutContainer,fragment,"demofragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void BrowseFriendsButtonClicked() {

        Fragment fragment = new BrowseFriendsFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayoutContainer,fragment,"demofragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}

