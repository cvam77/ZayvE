package com.example.zayve_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JustForTestActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_for_test);



    }

    public void BrowseFriendButtonClicked()
    {
        Fragment hometoBrowseFragment = new BrowseFriendsFragment();

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.home_page_fragment,hometoBrowseFragment);

        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.browseFriendsButton:
                BrowseFriendButtonClicked();
        }
    }
}