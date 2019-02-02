package com.rajatv.surajv.roshank.sac.tcf2019;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.SubeventsDetailsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SubeventDetailsFrag extends Fragment {

    private RecyclerView recyclerView;
    private List<SubeventDetails> subeventDetailsList;
    DatabaseReference subeventsDatabase,subeventsDatabase2,database3;
    private ArrayList<String> demolist;
    SubeventsDetailsRecyclerViewAdapter recyclerViewAdapter;
    String culturaleventname=null;
    ImageView descriptionback;

    private final static String EVENT_NAME="cultural-events";

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subeventDetailsList=new ArrayList<>();
        if (getArguments() != null) {
            culturaleventname = getArguments().getString("event_name");
            Log.e("xyz",culturaleventname);
//            Log.e("notnulll",culturaleventname);
        }
        Log.e("xyz",culturaleventname);

        //if(culturaleventname!=null){culturaleventname = getArguments().getString("event_name").toLowerCase();}


        if(culturaleventname==null) {
            subeventsDatabase = FirebaseDatabase.getInstance().getReference().child("events");  //ADD THE DATABASE REFERENCE

            subeventsDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                            if (postSnapshot.child("rounds").getValue() != null) {
                                subeventDetailsList.add(new SubeventDetails(
                                        String.valueOf(postSnapshot.child("name").getValue()),
                                        String.valueOf(postSnapshot.child("Venue").getValue()),
                                        String.valueOf(postSnapshot.child("startsAt").getValue()),
                                        String.valueOf(postSnapshot.getKey()),
                                        String.valueOf(postSnapshot.child("endsAt").getValue()),
                                        String.valueOf(postSnapshot.child("registrationDetail").child("startsAt").getValue()),
                                        String.valueOf(postSnapshot.child("registrationDetail").child("endsAt").getValue()),
                                        String.valueOf(postSnapshot.child("registrationDetail").child("type").getValue()),
                                        String.valueOf(postSnapshot.child("registrationDetail").child("packages").getValue()),
                                        String.valueOf(postSnapshot.child("registrationDetail").child("restricted").getValue())

                                ));
                            }
                        }                                recyclerViewAdapter.notifyDataSetChanged();

                    Log.e("ARRAY-LIST",subeventDetailsList.toString());
                    }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }

        else{


        subeventsDatabase = FirebaseDatabase.getInstance().getReference().child("events").child(culturaleventname).child("children");  //ADD THE DATABASE REFERENCE
//        Log.d("hello","dataSnapshot");
        subeventsDatabase2=FirebaseDatabase.getInstance().getReference().child("events");

        if(culturaleventname.equalsIgnoreCase("pratibimb")){
            database3=FirebaseDatabase.getInstance().getReference().child("events").child("pratibimb");
            database3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot postSnapshot1) {
               //     Log.e("loggedddd", postSnapshot1.toString());

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

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        subeventsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.e("idssss",String.valueOf(postSnapshot.child("id").getValue()));

                    //ADD THE DATA FROM DATABASE AS PER THE FIELDS
                    String name=String.valueOf(postSnapshot.child("name").getValue());
                    final String id=String.valueOf(postSnapshot.child("id").getValue());
                    Log.d(id,"dataSnapshot");
                    subeventsDatabase2.addListenerForSingleValueEvent(new ValueEventListener() {



                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            for (DataSnapshot postSnapshot1: dataSnapshot1.getChildren()) {

                                if(String.valueOf(postSnapshot1.getKey()).equalsIgnoreCase(id)) {

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

                        }
                    });

                    recyclerViewAdapter.notifyDataSetChanged();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });}

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewAdapter.notifyDataSetChanged();
    }
}


