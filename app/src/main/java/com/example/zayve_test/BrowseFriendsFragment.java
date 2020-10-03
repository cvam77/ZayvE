package com.example.zayve_test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zayve_test.models.ViewPagerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class BrowseFriendsFragment extends Fragment {

    private StorageReference mStorage;

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;

    private Uri imageUri;

    private static final int GALLERY_INTENT = 2;

    private DatabaseReference mDatabaseRef;



    private ArrayList<String> a1;

    private ArrayAdapter<String> arrayAdapter;

    ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_browse_friends, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        Button mUploadImageButton = (Button) getView().findViewById(R.id.push_photo);
//        mUploadImageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ActionPick();
//            }
//        });

//        mListView = getView().findViewById(R.id.list_view);
//
//        a1 = new ArrayList<>();
//
//        a1.add("Shivam");
//
//        arrayAdapter = new ArrayAdapter<String>(getContext(),R.layout.each_user,R.id.user_name_textview,a1);
//        mListView.setAdapter(arrayAdapter);

        mStorage = FirebaseStorage.getInstance().getReference();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();


//        a1.add("def");
//        a1.add("ghi");

        viewPager = getView().findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        mDatabaseRef.child("BrowseFriendsList").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    for(DataSnapshot childSnapshot: snapshot.getChildren())
                    {
                        String name = childSnapshot.getValue().toString();



                    }





//                    for(DataSnapshot childSnapshot: snapshot.getChildren())
//                    {
//
//                    }

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        FirebaseRecyclerAdapter<EachUserProfile, BrowseFriendsViewHolder> firebaseAdapter = new FirebaseRecyclerAdapter<EachUserProfile, BrowseFriendsViewHolder>() {
//            @Override
//            protected void populateViewHolder(BrowseFriendsViewHolder browseFriendsViewHolder, EachUserProfile eachUserProfile, int i) {
//
//            }
//
//            @Override
//            public BrowseFriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_user,parent,false);
//                BrowseFriendsViewHolder viewHolder = new BrowseFriendsViewHolder(view);
//                return viewHolder;
//            }
//        };


    }


//    private void ActionPick() {
//
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//
//        startActivityForResult(intent,GALLERY_INTENT);
//
//    }
//
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.d("successno","success");
//        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
//        {
//            Uri uri = data.getData();
//
//            StorageReference filepath = mStorage.child("Photos").child(uri.getLastPathSegment());
//
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(getContext(),"Upload Successful",Toast.LENGTH_LONG);
//
//                    String downloadUri = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
//
//                    mDatabaseRef.child("BrowseFriendsList").push().child("ProfilePicture").setValue(downloadUri);
//
//                }
//            });
//        }
//    }
//
//
//    public static class BrowseFriendsViewHolder extends RecyclerView.ViewHolder
//    {
//
//        TextView mNameTextview, mFirstInterestTextview, mSecondInterestTextview, mThirdInterestTextview,
//        mFourthInterestTextview, mFifthInterestTextview;
//
//        public BrowseFriendsViewHolder(@NonNull View itemView) {
//            super(itemView);
//        }
//    }
}

















