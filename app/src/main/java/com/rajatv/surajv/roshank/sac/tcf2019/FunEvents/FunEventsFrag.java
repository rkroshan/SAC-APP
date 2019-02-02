package com.rajatv.surajv.roshank.sac.tcf2019.FunEvents;

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

import com.rajatv.surajv.roshank.sac.MyDashboard.Registered.SubeventDetails1;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FunEventsFrag extends Fragment {
    private RecyclerView recyclerView;
    private List<SubeventDetails1> subeventDetailsList;
    DatabaseReference subeventsDatabase,subeventsDatabase2,getSubeventsDatabase3;
    FunEventRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_fun_event,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.funevents_recyclerView);
        recyclerViewAdapter=new FunEventRecyclerViewAdapter(getContext(),subeventDetailsList);
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
        subeventsDatabase = FirebaseDatabase.getInstance().getReference().child("events").child("fun-events").child("children");  //ADD THE DATABASE REFERENCE
        Log.d("hello","dataSnapshot");
        subeventsDatabase2=FirebaseDatabase.getInstance().getReference().child("events");
try {
    subeventsDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            subeventDetailsList.clear();

            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                //ADD THE DATA FROM DATABASE AS PER THE FIELDS
                String name = String.valueOf(postSnapshot.child("name").getValue());
                final String id = String.valueOf(postSnapshot.child("id").getValue());
//                Log.d(id, "dataSnapshot");
                subeventsDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                        for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                            //  Log.d(String.valueOf(postSnapshot1.getKey()),"dataSnapshot");

                            if (String.valueOf(postSnapshot1.getKey().toString()).equalsIgnoreCase(id)) {
                                Log.d(String.valueOf(postSnapshot1.getValue()), "dataSnapshot");


                                String startTime, endTime;
                                if (String.valueOf(postSnapshot1.child("registrationDetail").child("startsAt").getValue()).equalsIgnoreCase("null"))
                                    subeventDetailsList.add(new SubeventDetails1(
                                            String.valueOf(postSnapshot1.child("name").getValue()),
                                            String.valueOf(postSnapshot1.child("Venue").getValue()),
                                            String.valueOf(postSnapshot1.child(StringVariable.EVENT_REGISTRATION_DETAILS_STARTS_AT).getValue()),
                                            String.valueOf(postSnapshot1.getKey()),
                                            String.valueOf(postSnapshot1.child(StringVariable.EVENT_REGISTRATION_DETAILS_ENDS_AT).getValue()),
                                            String.valueOf(postSnapshot1.child(StringVariable.EVENT_REGISTRATION_DETAILS_STARTS_AT).getValue()),
                                            String.valueOf(postSnapshot1.child(StringVariable.EVENT_REGISTRATION_DETAILS_ENDS_AT).getValue()),
                                            "desk",
                                            "contact-reg-desk",
                                            "true","fun","",""


                                    ));
                                else {

                                    subeventDetailsList.add(new SubeventDetails1(
                                            String.valueOf(postSnapshot1.child("name").getValue()),
                                            String.valueOf(postSnapshot1.child("Venue").getValue()),
                                            String.valueOf(postSnapshot1.child("startsAt").getValue()),
                                            String.valueOf(postSnapshot1.getKey()),
                                            String.valueOf(postSnapshot1.child("endsAt").getValue()),
                                            String.valueOf(postSnapshot1.child("registrationDetail").child("startsAt").getValue()),
                                            String.valueOf(postSnapshot1.child("registrationDetail").child("endsAt").getValue()),
                                            String.valueOf(postSnapshot1.child("registrationDetail").child("type").getValue()),
                                            String.valueOf(postSnapshot1.child("registrationDetail").child("packages").getValue()),
                                            String.valueOf(postSnapshot1.child("registrationDetail").child("restricted").getValue()),
                                            "fun","",""
                                    ));
                                }
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
    });
}catch (Exception e){
}

    }

    @Override
    public void onResume() {
        super.onResume();
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
