package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByNameFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.SubeventsDetailsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class BynameFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<SubeventDetails> subeventDetailsList;
    DatabaseReference subeventsDatabase;
    private ArrayList<String> demolist;
    SubeventsDetailsRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.freg_subeventdetails,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.subevent_details_recyclerView);
        recyclerViewAdapter=new SubeventsDetailsRecyclerViewAdapter(getContext(),subeventDetailsList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subeventDetailsList = new ArrayList<>();
        subeventsDatabase = FirebaseDatabase.getInstance().getReference().child("events");  //ADD THE DATABASE REFERENCE
        try {
            subeventsDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot1 : dataSnapshot.getChildren()) {

                        //ADD THE DATA FROM DATABASE AS PER THE FIELD
                        if (postSnapshot1.child("rounds").getValue() != null) {
                            subeventDetailsList.add(new SubeventDetails(
                                    String.valueOf(postSnapshot1.child("name").getValue()),
                                    String.valueOf(postSnapshot1.child("Venue").getValue()),
                                    String.valueOf(postSnapshot1.child("startsAt").getValue()),
                                    String.valueOf(postSnapshot1.getKey()),
                                    String.valueOf(postSnapshot1.child("endsAt").getValue()),
                                    String.valueOf(postSnapshot1.child("registrationDetail").child("startsAt").getValue()),
                                    String.valueOf(postSnapshot1.child("registrationDetail").child("endsAt").getValue()),
                                    String.valueOf(postSnapshot1.child("registrationDetail").child("type").getValue()),
                                    String.valueOf(postSnapshot1.child("registrationDetail").child("packages").getValue()),
                                    String.valueOf(postSnapshot1.child("registrationDetail").child("restricted").getValue())

                            ));
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });

        }catch (Exception e){
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.searchmenu);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                recyclerViewAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
}
