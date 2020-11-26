package com.example.zayve_test.ui.browse_friends;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zayve_test.R;
import com.example.zayve_test.ui.requests.NotificationHandler;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BrowseFriendsFragment extends Fragment {

    int timeCounter = 0, secondTimeCounter = 0;

    private String universalName = "";

    private StorageReference mStorage;

    ArrayList<String> requestArrayList = new ArrayList<>();

    private DatabaseReference mDatabaseRef = FirebaseDatabase.getInstance().getReference();

    RecyclerView mRecyclerViewBrowse;

    FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

    ArrayList<String> userIdArrayList = new ArrayList<>();

    FusedLocationProviderClient fusedLocationProviderClient;

    AdapterBrowseFriends adapterBrowseFriends;

    ArrayList<String> userIdRequestArrayList = new ArrayList<>();

    NotificationHandler notificationHandler = new NotificationHandler();

    boolean firstRun = true;

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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {


            SetLocationCity();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        mStorage = FirebaseStorage.getInstance().getReference();

        mRecyclerViewBrowse = getView().findViewById(R.id.rvBrowseFriends);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        mRecyclerViewBrowse.setLayoutManager(mLinearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerViewBrowse.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerViewBrowse.addItemDecoration(dividerItemDecoration);

        adapterBrowseFriends = new AdapterBrowseFriends(getContext());
        mRecyclerViewBrowse.setAdapter(adapterBrowseFriends);

        final ArrayList<String> sarrayList = new ArrayList<>();

        Log.d("watchForNotification","watch notification called");

        WatchForNotification();

        fillUserIdArrayList();

    }

    public void WatchForNotification() {

        mDatabaseRef.child("users").child(getCurrentUser.getUid()).child("interest_requests").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                String keyName = snapshot.getKey();

                if(snapshot.exists())
                {
                    for(DataSnapshot childSnapshot : snapshot.getChildren())
                    {
                        String userIdReq = childSnapshot.getValue().toString();

                        userIdRequestArrayList.add(userIdReq);

                        GetUserName(userIdReq, keyName);
                    }

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists())
                {
                    String keyName = snapshot.getKey();

                    for(DataSnapshot childSnapshot : snapshot.getChildren())
                    {
                        String userIdReq = childSnapshot.getValue().toString();

                        if(!userIdRequestArrayList.contains(userIdReq))
                        {
                            GetUserName(userIdReq, keyName);
                        }
                    }

                }
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

    }

    private void GetUserName(String userIdReq, String interestName) {
        mDatabaseRef.child("users").child(userIdReq).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(dataSnapshot.getKey().equals("user_name")){
                        String name = dataSnapshot.getValue().toString();

                        String message = "Sent by " + name + " for " + interestName;
                        MakeNotification(getContext(), message);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void MakeNotification(Context context, String message) {
        NotificationHandler.notificationCreator(context,message);
    }

    private void SetLocationCity() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();

                if (location != null) {

                    try {
                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        String locality = addresses.get(0).getLocality();
                        String adminArea = addresses.get(0).getAdminArea();
                        String locationUser = locality + ", " + adminArea;

                        mDatabaseRef.child("users").child(getCurrentUser.getUid()).child("location").setValue(locationUser);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public void fillUserIdArrayList()
    {
        mDatabaseRef.child("users").addChildEventListener(new ChildEventListener() {
              @Override
              public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                  if (snapshot.exists()) {

                      boolean cannotBrowseItSwitch = false;

                      String keyOrUserId = snapshot.getKey();

                      for(DataSnapshot childSnapshot : snapshot.getChildren())
                      {
                          String key = childSnapshot.getKey();
                          if(key.equals("deleted_by"))
                          {
                              for(DataSnapshot secondChildSnapshot : childSnapshot.getChildren())
                              {
                                  String deletedByUserKey = secondChildSnapshot.getKey();
                                  if(deletedByUserKey.equals(deletedByUserKey))
                                  {
                                      cannotBrowseItSwitch = true;
                                  }
                              }
                          }
                      }
                      if(!snapshot.getKey().equals(getCurrentUser.getUid()))
                      {

                          if(!cannotBrowseItSwitch)
                          {
                              adapterBrowseFriends.AddToTheEndAl(keyOrUserId);
                          }

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

//        long timeStart = System.currentTimeMillis();
//
//        while(userIdArrayList.isEmpty())
//        {
//            long timeEnd = System.currentTimeMillis();
//
//            if(timeEnd - timeStart > 2000)
//            {
//                timeCounter = 5;
//            }
//
//        }
//
//        gettingArray(userIdArrayList);
    }

}

