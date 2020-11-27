package com.example.zayve_test.ui.requests;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zayve_test.R;

public class TemporaryTestActivity extends AppCompatActivity {

    Button button;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary_test);

        fragmentManager = getSupportFragmentManager();

        button = findViewById(R.id.search_requested_user);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new SeePeopleWhereRequestSent();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.temporary_test_container,fragment,"testing");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}