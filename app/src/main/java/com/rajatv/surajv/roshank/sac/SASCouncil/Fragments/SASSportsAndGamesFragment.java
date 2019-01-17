package com.rajatv.surajv.roshank.sac.SASCouncil.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SASCouncil.Adapters.SASAppAndMediaAdapter;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Contacts;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SASSportsAndGamesFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Contacts> contactsList = new ArrayList<>();
    DatabaseReference contactsDatabase, contactsSubDatabase;
    private ArrayList<String> demolist = new ArrayList<>();
    SASAppAndMediaAdapter recyclerViewAdapter;
    ArrayList<String> sports = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_events_recycler_view);
        recyclerViewAdapter = new SASAppAndMediaAdapter(getContext(), contactsList);
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
        try{
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.SASCOUNCILS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    if(!(String.valueOf(postSnapshot.getValue()).equals("null")||String.valueOf(postSnapshot.getValue()).equals(""))){
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(String.valueOf(postSnapshot.getValue()));
                    db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String sas = String.valueOf(dataSnapshot.child(StringVariable.USER_IS_SASMEMBER).getValue());
                           try{
                               if (sas.split("@SAC2.0@")[0].equals("Sports & Games")) {
                                   int value = 4;
                                   switch (sas.split("@SAC2.0@")[1]) {
                                       case "President":{
                                           value = 1;
                                           break;
                                       }
                                       case "Vice President":{
                                           value = 2;
                                           break;
                                       }
                                       case "Technical Secretory":{
                                           value = 3;
                                           break;
                                       }

                                       case "Secretory":{
                                           value = 3;
                                           break;
                                       }
                                       case "Cordinator":{
                                           value = 4;
                                           break;
                                       }
                                       case "Co-Cordinator":{
                                           value = 5;
                                           break;
                                       }
                                       case "Member":{
                                           value = 6;
                                           break;
                                       }
                                   }
                                   contactsList.add(new Contacts(
                                           String.valueOf(dataSnapshot.child("name").getValue()),
                                           String.valueOf(dataSnapshot.child("photoURL").getValue()),
                                           sas.split("@SAC2.0@")[1],
                                           String.valueOf(dataSnapshot.child("phoneNumber").getValue()),
                                           String.valueOf(dataSnapshot.child("phoneNumber").getValue()),
                                           String.valueOf(dataSnapshot.child("UserUID").getValue()),value

                                   ));
                                   Collections.sort(contactsList, new Comparator<Contacts>() {
                                       @Override
                                       public int compare(Contacts o1, Contacts o2) {
                                           return o1.getValue()>o2.getValue()?1:(o1.getValue()<o2.getValue())?-1:0;
                                       }
                                   });
                                   recyclerViewAdapter.notifyDataSetChanged();
                               }
                           }catch (Exception e){

                           }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        }catch (Exception e){

        };

//
//        contactsList = new ArrayList<>();
//        contactsDatabase = FirebaseDatabase.getInstance().getReference().child("SASCOUNCIL");
//        contactsSubDatabase = FirebaseDatabase.getInstance().getReference().child("testUser");
//        try {
//            contactsDatabase.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//
//                        contactsSubDatabase.addValueEventListener(new ValueEventListener() {
//
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
//                                for (DataSnapshot post1Snapshot : dataSnapshot1.getChildren()) {
//                                    if (String.valueOf(postSnapshot.child("Category").getValue()).equalsIgnoreCase("Sports & Games"))
//
//                                        if (post1Snapshot.getKey().equalsIgnoreCase(String.valueOf(postSnapshot.child("UserUID").getValue()))) {
//                                            contactsList.add(new Contacts(
//                                                    String.valueOf(post1Snapshot.child("name").getValue()),
//                                                    String.valueOf(post1Snapshot.child("profile_pic").getValue()),
//                                                    String.valueOf(postSnapshot.child("Designation").getValue()),
//                                                    String.valueOf(postSnapshot.child("WhatsappNo").getValue()),
//                                                    String.valueOf(postSnapshot.child("WhatsappNo").getValue()),
//                                                    String.valueOf(postSnapshot.child("UserUID").getValue())
//                                            ));
//                                            recyclerViewAdapter.notifyDataSetChanged();
//                                        }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//
//        } catch (Exception e) {
//        }
        }
    }

