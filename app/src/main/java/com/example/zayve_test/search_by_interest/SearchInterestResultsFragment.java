package com.example.zayve_test.search_by_interest;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zayve_test.R;
import com.example.zayve_test.ui.browse_friends.EachUserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchInterestResultsFragment extends Fragment implements SearchByInterestRvAdapter.ItemClickListener{

    int timeCounter = 0, secondTimeCounter = 0;

    String searchWord;

    String stringForTv = "";

    String nameResults = "";

    TextView mSearchWordTextView, mTestTv, mYourSearchResults;

    EditText mSearchWordEditText;
    Button mSearchInterestButton;

    RecyclerView mRvSearchResults;
    Spinner choices;
    SearchByInterestRvAdapter searchByInterestRvAdapter;

    ArrayList<EachUserProfile> globalResultArrayList = new ArrayList<>();
    ArrayList<String> idArraylist = new ArrayList<>();
    ArrayList<String> requestArrayList = new ArrayList<>();

    DatabaseReference mRealtimeDatabase = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

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

        fragmentManager = getActivity().getSupportFragmentManager();

        mYourSearchResults = getView().findViewById(R.id.yourSearchResults);

        mSearchWordEditText = getView().findViewById(R.id.search_interest_edittext);

        mSearchInterestButton = getView().findViewById(R.id.search_interest_button);
        mSearchInterestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchButtonClicked();
            }
        });

        mRealtimeDatabase.child("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    if (childDataSnapshot.getKey().toString().equals("interests"))
                    {
                        ArrayList<String> items = new ArrayList<>();
                        items.add("");
                        for (DataSnapshot ing : childDataSnapshot.getChildren()) {
                            items.add(ing.getValue().toString());
                        }
                        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) { // Change the formula if Continuously is chosen.
                                String option = parentView.getItemAtPosition(position).toString();
                                if (!option.equals("")) {
                                    mSearchWordEditText.setText(option);
                                    SearchButtonClicked();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {
                            }

                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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


        if(!enteredSearchWord.equals(""))
        {
            mYourSearchResults.setVisibility(View.VISIBLE);
            mRvSearchResults.setVisibility(View.VISIBLE);

            InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(mSearchWordEditText.getWindowToken(), 0);

            searchWord = enteredSearchWord;
            idArraylist.clear();
            globalResultArrayList.clear();
            ExecuteAsyncTasks();
        }
    }

    public void ExecuteAsyncTasks() {
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

    @Override
    public void onItemClickListener(String userId, String name, String profilePicUrlString,
                                    String about, String firstInt, String secondInt, String thirdInt,
                                    String fourthInt, String fifthInt)
    {

//        Fragment fragment = new BrowseFriendsFragment();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("userId",userId);
//        fragment.setArguments(bundle);
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayoutContain, fragment, "demofragment");
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
        Navigation.findNavController(getActivity(),getView().getId()).navigate(SearchInterestResultsFragmentDirections.actionNavSearchToNavUser(userId));

    }

    @Override
    public void onResume() {
        super.onResume();
        SearchButtonClicked();
    }


    class AsyncTaskIds extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {

            mRealtimeDatabase.child("users").addChildEventListener(new ChildEventListener() {
               @Override
               public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                   if (snapshot.exists()) {
                       if(!snapshot.getKey().equals(currentUser.getUid()))
                       {
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
                                    requestArrayList.clear();

                                    String secondLevelKey = secondLevelChildSnapshot.getKey();
                                    String keyValue = secondLevelChildSnapshot.getValue().toString();

                                    if(secondLevelKey.equals(currentUser.getUid()))
                                    {
                                        for(DataSnapshot thirdLevel : secondLevelChildSnapshot.getChildren())
                                        {
                                            String requestedInterestNameByCurrentUser = thirdLevel.getKey();
                                            Log.d("requestedInteres",requestedInterestNameByCurrentUser);
                                            requestArrayList.add(requestedInterestNameByCurrentUser);
                                        }
                                    }


                                    switch (secondLevelKey)
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
                                    CheckIfInterestExist(firstInt),CheckIfInterestExist(secondInt),CheckIfInterestExist(thirdInt),
                                    CheckIfInterestExist(fourthInt),CheckIfInterestExist(fifthInt));
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

        public String CheckIfInterestExist(String interestName)
        {
            String value = interestName;
            if(requestArrayList.contains(interestName))
            {
                Log.d("arrayListSize", String.valueOf(requestArrayList.size()));
                value = interestName + "**";
            }

            return value;
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