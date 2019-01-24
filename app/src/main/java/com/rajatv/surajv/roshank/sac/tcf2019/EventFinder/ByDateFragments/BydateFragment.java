package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByDateFragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BydateFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<SubeventDetails> eventDetailsList = new ArrayList<SubeventDetails>();
    private List<String> fDate = new ArrayList<>();
    private List<String> event = new ArrayList<>();
    DatabaseReference eventsDatabase;
    BydateAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventfinder_bydate, container, false);
        setHasOptionsMenu(false);

        fDate = new ArrayList<>();
        event = new ArrayList<>();
        eventDetailsList = new ArrayList<SubeventDetails>();

        final FragmentActivity c = getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.bydate_recyclerview);



        recyclerViewAdapter = new BydateAdapter(getContext(), fDate,event);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        setHasOptionsMenu(true);

        init();
        return view;
    }

    private void init() {
        eventDetailsList = new ArrayList<>();
        eventsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS);  //ADD THE DATABASE REFERENCE

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yy");
        Calendar calendar = Calendar.getInstance();

    eventsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fDate.clear();
                event.clear();
                try {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(postSnapshot.child("startsAt").getValue() != null) {
                        //getting date in required format
                        long timestamp = (long) postSnapshot.child("startsAt").getValue();
                        calendar.setTimeInMillis(timestamp);
                        String formattedDate = dateFormat.format(calendar.getTime());
                        if(fDate.contains(formattedDate)){
                            int i = fDate.indexOf(formattedDate);
                            String data = event.get(i);
                            data += postSnapshot.getKey()+"@sac@";
                            event.add(i,data);
                        }

                        else {
                            fDate.add(0,formattedDate);
                            event.add(0,postSnapshot.getKey()+"@sac@");
//                            Log.e("bydate4",fDate.toString());
//                            Log.e("bydate4",event.toString());
                        }
                    }

                }
//                    Log.e("bydate5",fDate.toString());
//                    Log.e("bydate5",event.toString());
                recyclerViewAdapter.notifyDataSetChanged();

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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

