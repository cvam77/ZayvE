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

    private StorageReference mStorage;

    private ViewPagerAdapter viewPagerAdapter;

    private ViewPager viewPager;

    private Uri imageUri;

    private static final int GALLERY_INTENT = 2;



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


        mStorage = FirebaseStorage.getInstance().getReference();



//        a1.add("def");
//        a1.add("ghi");

        viewPager = getView().findViewById(R.id.view_pager);

        final ArrayList<String> sarrayList = new ArrayList<>();

        boolean backgroundDone = false;

        CallAsync callAsync = new CallAsync();
        callAsync.execute();

    }


    public void gettingArray(ArrayList<ArrayList<String>> harrayList)
    {
        viewPagerAdapter = new ViewPagerAdapter(getParentFragmentManager(),harrayList );
        viewPager.setAdapter(viewPagerAdapter);
    }

    class CallAsync extends AsyncTask<Void,Void,ArrayList<ArrayList<String>>> {

        private DatabaseReference mDatabaseRef;

        @Override
        protected ArrayList<ArrayList<String>> doInBackground(Void... voids) {

            mDatabaseRef = FirebaseDatabase.getInstance().getReference();



            final ArrayList<ArrayList<String>> secondLevelAl = new ArrayList<>();

            mDatabaseRef.child("users").addChildEventListener(new ChildEventListener() {
                ArrayList<String> nameAl = new ArrayList<>();
                ArrayList<String> firstInterestAl = new ArrayList<>();
                ArrayList<String> secondInterestAl = new ArrayList<>();
                ArrayList<String> thirdInterestAl = new ArrayList<>();
                ArrayList<String> fourthInterestAl = new ArrayList<>();
                ArrayList<String> fifthInterestAl = new ArrayList<>();
                ArrayList<String> profilePicAl = new ArrayList<>();
                ArrayList<String> introAl = new ArrayList<>();

                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.exists()) {

                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            if (childSnapshot.getKey().equals("user_name")) {
                                String name = childSnapshot.getValue().toString();
                                nameAl.add(name);
                            }
                            else if (childSnapshot.getKey().equals("profile_image")) {
                                String urlString = childSnapshot.getValue().toString();
                                Log.d("pinterestArraywaList", urlString);
                                profilePicAl.add(urlString);
                            }
                            else if (childSnapshot.getKey().equals("about")) {
                                String intro = childSnapshot.getValue().toString();
                                Log.d("kvayobey", intro);
                                introAl.add(intro);
                            }
//                            {
//                                String interest = childSnapshot.getValue().toString();
//                                Log.d("interestArraywaList", interest);
//                                ArrayList<String> firstLevelInterestAl = new ArrayList<>();
//                                firstLevelInterestAl.add(interest);
//                                secondLevelInterestAl.add(firstLevelInterestAl);
//                                thirdLevelAl.add(secondLevelInterestAl);
//                            }
                        }

                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                            for(DataSnapshot secondLevelSnapshot : childSnapshot.getChildren())
                            {
                                String interest = secondLevelSnapshot.getValue().toString();

                                switch (secondLevelSnapshot.getKey())
                                {

                                    case "0": firstInterestAl.add(interest); break;
                                    case "1": secondInterestAl.add(interest); break;
                                    case "2": thirdInterestAl.add(interest);  break;
                                    case "3": fourthInterestAl.add(interest);  break;
                                    case "4": fifthInterestAl.add(interest);  break;
                                    default:
                                        firstInterestAl.add("");
                                        secondInterestAl.add("");
                                        thirdInterestAl.add("");
                                        fourthInterestAl.add("");
                                        fifthInterestAl.add("");
                                }
                            }
                        }



                        secondLevelAl.add(nameAl);
                        secondLevelAl.add(firstInterestAl);
                        secondLevelAl.add(secondInterestAl);
                        secondLevelAl.add(thirdInterestAl);
                        secondLevelAl.add(fourthInterestAl);
                        secondLevelAl.add(fifthInterestAl);
                        secondLevelAl.add(profilePicAl);
                        secondLevelAl.add(introAl);
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

            while (secondLevelAl.isEmpty())
            {
            }
            return secondLevelAl;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<String>> arrayLists) {
            super.onPostExecute(arrayLists);
            gettingArray(arrayLists);
        }
    }
}