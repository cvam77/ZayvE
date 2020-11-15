package com.example.zayve_test.search_by_interest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zayve_test.R;
import com.example.zayve_test.models.ViewPagerAdapter;
import com.example.zayve_test.models.ViewPagerAdapterSearchResults;
import com.example.zayve_test.ui.EachUserProfile;
import com.example.zayve_test.ui.browse_friends.BrowseFriendsFragment;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SearchInterestResultsFragment extends Fragment implements SearchByInterestRvAdapter.ItemClickListener{

    int timeCounter = 0, secondTimeCounter = 0;

    String searchWord;

    String stringForTv = "";

    String nameResults = "";

    FrameLayout frameLayout;
    TextView mSearchWordTextView, mTestTv, mYourSearchResults;

    EditText mSearchWordEditText;
    Button mSearchInterestButton;

    RecyclerView mRvSearchResults;
    SearchByInterestRvAdapter searchByInterestRvAdapter;

    ArrayList<EachUserProfile> globalResultArrayList = new ArrayList<>();
    ArrayList<String> idArraylist = new ArrayList<>();

    DatabaseReference mRealtimeDatabase  = FirebaseDatabase.getInstance().getReference();;

    private ViewPagerAdapterSearchResults viewPagerAdapterSearchResults;
    private ViewPager viewPagerSearchResults;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_interest_results, container, false);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        frameLayout = getView().findViewById(R.id.FrameLayout);

        mYourSearchResults = getView().findViewById(R.id.yourSearchResults);

        mSearchWordEditText = getView().findViewById(R.id.search_interest_edittext);

        mSearchInterestButton = getView().findViewById(R.id.search_interest_button);
        mSearchInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchButtonClicked();
            }
        });

        mRvSearchResults = view.findViewById(R.id.rvSearch);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRvSearchResults.setLayoutManager(mLinearLayoutManager);

        searchByInterestRvAdapter = new SearchByInterestRvAdapter(getContext(),this);

        mRvSearchResults.setAdapter(searchByInterestRvAdapter);

    }

    private void SearchButtonClicked() {
        String enteredSearchWord = mSearchWordEditText.getText().toString().trim();



        mYourSearchResults.setVisibility(View.VISIBLE);
        if(!enteredSearchWord.equals(""))
        {
            frameLayout.setVisibility(View.VISIBLE);

            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(mSearchWordEditText.getWindowToken(), 0);

            searchWord = enteredSearchWord;
            idArraylist.clear();
            globalResultArrayList.clear();
            ExecuteAsyncTasks();
        }
    }

    private void ExecuteAsyncTasks() {
        AsyncTaskIds asyncTaskIds = new AsyncTaskIds();
        AsyncTaskSearchResult asyncTaskSearchResult = new AsyncTaskSearchResult();

        asyncTaskIds.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        asyncTaskSearchResult.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

    }

    public ArrayList<EachUserProfile> getGlobalResultArrayList() {
        return globalResultArrayList;
    }

    public void setGlobalResultArrayList(ArrayList<EachUserProfile> globalResultArrayList) {
        this.globalResultArrayList = globalResultArrayList;
    }

    public ArrayList<String> getIdArraylist() {
        return idArraylist;
    }

    public void setIdArraylist(ArrayList<String> idArraylist) {
        this.idArraylist = idArraylist;
    }

    public void gettingArray(ArrayList<ArrayList<String>> harrayList) {
        viewPagerSearchResults.setAdapter(viewPagerAdapterSearchResults);
    }

    @Override
    public void onItemClickListener(String userId, String name, String profilePicUrlString,
                                    String about, String firstInt, String secondInt, String thirdInt,
                                    String fourthInt, String fifthInt)
    {
        Toast.makeText(getContext(),name + " Clicked", Toast.LENGTH_LONG).show();

//        Fragment fragment = new EachUserInFragmentSearchResults();
//
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.linearLayoutContainer, fragment, "demofragment");
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }

    class AsyncTaskIds extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            mRealtimeDatabase.child("users").addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   if (snapshot.exists()) {
                       for (DataSnapshot childSnapshot : snapshot.getChildren()) {

                           for (DataSnapshot secondLevelSnapshot : childSnapshot.getChildren()) {

                               String interest = secondLevelSnapshot.getValue().toString();

                               if (interest.toLowerCase().equals(searchWord.toLowerCase())) {
                                   String parent = childSnapshot.getRef().getParent().getKey();

                                   idArraylist.add(parent);
                                   stringForTv = stringForTv + " and \n" + parent;
                               }


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

            long timeStart = System.currentTimeMillis();

            while(idArraylist.isEmpty() && timeCounter < 2)
            {
                long timeEnd = System.currentTimeMillis();

                if(timeEnd - timeStart > 1000)
                {
                    timeCounter = 5;
                }

            }

            return null;
        }
    }

    class AsyncTaskSearchResult extends AsyncTask<Void,Void,ArrayList<EachUserProfile>>
    {

        @Override
        protected ArrayList<EachUserProfile> doInBackground(Void... voids) {
            mRealtimeDatabase.child("users").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists())
                    {
                        String userId = snapshot.getKey();

                        String userName = "", userAbout = "", firstInt = "", secondInt = "", thirdInt = "", fourthInt = "",
                                fifthInt = "", userPicUrl = "";
                        if(getIdArraylist().contains(userId))
                        {
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
                            globalResultArrayList.add(eachUserProfile);
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
            });

            long timeStart = System.currentTimeMillis();

            while(globalResultArrayList.isEmpty() && secondTimeCounter < 2)
            {
                long timeEnd = System.currentTimeMillis();

                if(timeEnd - timeStart > 1000)
                {
                    secondTimeCounter = 5;
                }

            }

            return globalResultArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<EachUserProfile> eachUserProfiles) {
            super.onPostExecute(eachUserProfiles);
            getActivity().runOnUiThread(
                    new Runnable() {
                        @Override
                        public void run() {
                            searchByInterestRvAdapter.setmUserList(globalResultArrayList);
                        }
                    }
            );
        }
    }
}