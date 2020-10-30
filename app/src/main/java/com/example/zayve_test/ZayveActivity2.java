package com.example.zayve_test;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.zayve_test.BrowseFriendsFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ZayveActivity2 extends AppCompatActivity {


    Button mBrowseFriendsButton;
    Button mSeeProfileButton;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private DatabaseReference mDatabaseReferenco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_for_test);

        fragmentManager = getSupportFragmentManager();

        mDatabaseReferenco = FirebaseDatabase.getInstance().getReference();

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

        Button mUploadImageButton = (Button) findViewById(R.id.upload_image);
        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActionPick();
            }
        });
    }

    //Ignore this method
    private void ActionPick() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, 2);

    }

    //Ignore this method part 2
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d("successno", "success");
        if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri uri = data.getData();


            StorageReference mStorage = FirebaseStorage.getInstance().getReference();

            final StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            //                    Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_LONG);
//
//                    Uri downloadUri = taskSnapshot.getUploadSessionUri();

                            Log.d("watchingUriano", String.valueOf(uri));
                            mDatabaseReferenco.child("BrowseFriendsList").push().child("ProfilePicture").setValue(String.valueOf(uri));
                        }
                    });
                }
            });
        }
    }

    //Going from ZayveActivity to See Profile Fragment
    private void seeProfileButtonClicked() {
        Fragment fragment = new ProfileSetupFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, fragment, "demofragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    //Going from ZayveActivity to BrowseFriendsFragment
    private void BrowseFriendsButtonClicked() {

        Fragment fragment = new BrowseFriendsFragment();

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutContainer, fragment, "demofragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}

