package com.example.zayve_test.ui.browse_friends;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.zayve_test.R;
import com.example.zayve_test.models.ViewPagerAdapter;
import com.example.zayve_test.ui.EachUserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import javax.xml.transform.Result;

public class BrowseFriendsFragment extends Fragment {

    int timeCounter = 0;

    private StorageReference mStorage;

    ArrayList<String> testArrayList;

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;

    private Uri imageUri;

    private static final int GALLERY_INTENT = 2;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    private ArrayList<String> a1;

    private ArrayAdapter<String> arrayAdapter;

    ListView mListView;

    FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    ArrayList<EachUserProfile> eachUserProfileArrayList = new ArrayList<>();

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


        mStorage = FirebaseStorage.getInstance().getReference();



//        a1.add("def");
//        a1.add("ghi");

        viewPager = getView().findViewById(R.id.view_pager);

        final ArrayList<String> sarrayList = new ArrayList<>();

        boolean backgroundDone = false;

        CallAsync callAsync = new CallAsync();
        callAsync.execute();

    }


    public void gettingArray(ArrayList<EachUserProfile> harrayList)
    {
        viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(),harrayList );
        viewPager.setAdapter(viewPagerAdapter);
    }


    class CallAsync extends AsyncTask<Void,Void,ArrayList<EachUserProfile>> {

        private DatabaseReference mDatabaseRef;

        @Override
        protected ArrayList<EachUserProfile> doInBackground(Void... voids) {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference();

            mDatabaseRef.child("users").addChildEventListener(new ChildEventListener() {
                  @Override
                  public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                      if (snapshot.exists()) {

                          String userId = snapshot.getKey();

                          if(!snapshot.getKey().equals(getCurrentUser.getUid()))
                          {
                              String userName = "", userAbout = "", firstInt = "", secondInt = "", thirdInt = "", fourthInt = "",
                                      fifthInt = "", userPicUrl = "";

                              for(DataSnapshot childSnapshot: snapshot.getChildren())
                              {
                                  String fieldKey = childSnapshot.getKey();
                                  String value = childSnapshot.getValue().toString();

                                  switch(fieldKey)
                                  {
                                      case "user_name":
                                          userName = value;
                                          break;
                                      case "about":
                                          userAbout = value;
                                          break;
                                      case "profile_image":
                                          userPicUrl = value;
                                          break;
                                      default:
                                          break;
                                  }

                                  for(DataSnapshot secondLevelChildSnapshot: childSnapshot.getChildren())
                                  {
                                      String interestKey = secondLevelChildSnapshot.getKey();
                                      String keyValue = secondLevelChildSnapshot.getValue().toString();
                                      switch (interestKey)
                                      {
                                          case "0":
                                              firstInt = keyValue;
                                              break;
                                          case "1":
                                              secondInt= keyValue;
                                              break;
                                          case "2":
                                              thirdInt= keyValue;
                                              break;
                                          case "3":
                                              fourthInt= keyValue;
                                              break;
                                          case "4":
                                              fifthInt= keyValue;
                                              break;
                                          default:
                                              break;
                                      }
                                  }
                              }
                              EachUserProfile eachUserProfile = new EachUserProfile(userId,userName, userAbout,
                                      firstInt,secondInt,thirdInt,fourthInt,fifthInt);
                              eachUserProfile.setProfilePicture(userPicUrl);
                              eachUserProfileArrayList.add(eachUserProfile);
                          }

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

              }

            );

            long timeStart = System.currentTimeMillis();

            while(eachUserProfileArrayList.isEmpty() && timeCounter < 2)
            {
                long timeEnd = System.currentTimeMillis();

                if(timeEnd - timeStart > 2000)
                {
                    timeCounter = 5;
                }

            }


            return eachUserProfileArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<EachUserProfile> eachUserProfiles) {
            super.onPostExecute(eachUserProfiles);
            gettingArray(eachUserProfiles);
        }
    }
}