package com.rajatv.surajv.roshank.sac.MyDashboard.Registered;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.SubeventsDetailsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisteredEventsFragment  extends Fragment {

    private RecyclerView recyclerView;
    private List<SubeventDetails> subeventDetailsList;
    private DatabaseReference subeventsDatabase,subeventsDatabase2,userDatabase;
    private SubeventsDetailsRecyclerViewAdapter recyclerViewAdapter;
    private String userUID;
    private final static String EVENT_NAME="cultural-events";
    private ArrayList<String> registeredEvents;

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
        registeredEvents=new ArrayList<>();


        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> mainMap = gson.fromJson(data,StringVariable.RetriveClass.getClass());
//        Log.e("mappp",mainMap.toString());

        userUID= mainMap.get(StringVariable.USER_USER_UID).toString();



        userDatabase=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child("registrations").child("solo");

        subeventsDatabase = FirebaseDatabase.getInstance().getReference().child("events");  //ADD THE DATABASE REFERENCE
//        Log.d("hello","dataSnapshot");
        subeventsDatabase2=FirebaseDatabase.getInstance().getReference().child("events");


        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                registeredEvents.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    registeredEvents.add(ds.getKey());

                    subeventsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                            DataSnapshot postSnapshot1=dataSnapshot1.child(ds.getKey());

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
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        Log.e("RegisteredEvents",registeredEvents.toString());

//        Log.e("REGISTERED BVF","klo");


            subeventsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                        //ADD THE DATA FROM DATABASE AS PER THE FIELDS
                        String name=String.valueOf(postSnapshot.child("name").getValue());
                        final String id=String.valueOf(postSnapshot.child("id").getValue());
//                        Log.d(id,"dataSnapshot");


                        recyclerViewAdapter.notifyDataSetChanged();

                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });}

    }



