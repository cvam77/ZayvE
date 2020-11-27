package com.example.zayve_test.ui.requests;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zayve_test.R;
import com.example.zayve_test.search_by_interest.EachUserInFragmentSearchResults;
import com.example.zayve_test.search_by_interest.SearchByInterestRvAdapter;
import com.example.zayve_test.search_by_interest.SearchInterestResultsFragmentDirections;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeePeopleWhereRequestSent extends Fragment implements RequestedUserAdapter.ItemClickListener {

    DatabaseReference mRtDatabase  = FirebaseDatabase.getInstance().getReference();
    FirebaseUser getCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
    RecyclerView recyclerViewRequestedUser;
    RequestedUserAdapter requestedUserAdapter;
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
        return inflater.inflate(R.layout.fragment_see_people_where_request_sent, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();

        requestedUserAdapter = new RequestedUserAdapter(getContext(),this);

        recyclerViewRequestedUser = getView().findViewById(R.id.recycler_view_requested_user);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewRequestedUser.setLayoutManager(mLinearLayoutManager);

        recyclerViewRequestedUser.setAdapter(requestedUserAdapter);

        mRtDatabase.child("users").child(getCurrentUser.getUid()).child("requests_sent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String requestedInterest = dataSnapshot.getKey();

                    for(DataSnapshot secondSnapshot : dataSnapshot.getChildren())
                    {
                        String requestedUserId = secondSnapshot.getValue().toString();

                        CallAdapter(requestedUserId,requestedInterest);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CallAdapter(String requestedUserId, String requestedInterest) {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(requestedUserId);
        arrayList.add(requestedInterest);
        requestedUserAdapter.AddToTheEndAl(arrayList);
    }

    @Override
    public void onItemClickListener(String userId) {
        Log.d("userIdCheck",userId);

        Navigation.findNavController(getActivity(),getView().getId()).navigate(SeePeopleWhereRequestSentDirections.actionNavSentRequestToNavUser(userId));
    }
}