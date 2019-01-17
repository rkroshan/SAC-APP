package com.rajatv.surajv.roshank.sac.MyDashboard.SpeedDial;

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

import com.google.firebase.auth.FirebaseAuth;
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

public class SpeedDialFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Contacts> contactsList;
    DatabaseReference speedDialDatabase, databaseReference;
    SASAppAndMediaAdapter recyclerViewAdapter;
    String userUID,sas;

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

        contactsList = new ArrayList<>();

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

         databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_SPEEDDIAL);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //name, profile_pic, designation, phone, whatsapp,userUID;
                    speedDialDatabase= FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(String.valueOf(postSnapshot.getKey()));
                    speedDialDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot ds) {
                            try{
                                sas="";
                                try{
                                    sas=String.valueOf(ds.child(StringVariable.USER_IS_TCFMEMBER).getValue()).split("@SAC2.0@")[2];
                                }
                                catch (Exception e){}
                                contactsList.add(
                                        new Contacts(
                                                String.valueOf(ds.child(StringVariable.USER_NAME).getValue()),
                                                String.valueOf(ds.child(StringVariable.USER_IMAGE).getValue()),
                                                sas,
                                                String.valueOf(ds.child(StringVariable.USER_PHONENUMBER).getValue()),
                                                String.valueOf(ds.child(StringVariable.USER_PHONENUMBER).getValue()),
                                                String.valueOf(ds.getKey()),1)
                                );
                            }catch (Exception e){
                                Log.e("xxxxxxx",e.getMessage());
                            }
//                            Log.e("Full list",String.valueOf(ds));
                            recyclerViewAdapter.notifyDataSetChanged();


                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


////        contactsList = new ArrayList<>();
////        contactsDatabase = FirebaseDatabase.getInstance().getReference().child("SASCOUNCIL");
////        contactsSubDatabase = FirebaseDatabase.getInstance().getReference().child("testUser");
////
////        contactsDatabase.addValueEventListener(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
////
////                    contactsSubDatabase.addValueEventListener(new ValueEventListener() {
////
////                        @Override
////                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
////                            try {
////                                for (DataSnapshot post1Snapshot : dataSnapshot1.getChildren()) {
////                                    if (post1Snapshot.getKey().equalsIgnoreCase(String.valueOf(postSnapshot.child("UserUID").getValue()))) {
////                                        contactsList.add(new Contacts(
////                                                String.valueOf(post1Snapshot.child("name").getValue()),
////                                                String.valueOf(post1Snapshot.child("profile_pic").getValue()),
////                                                String.valueOf(postSnapshot.child("Designation").getValue()),
////                                                String.valueOf(postSnapshot.child("WhatsappNo").getValue()),
////                                                String.valueOf(postSnapshot.child("WhatsappNo").getValue()),
////                                                String.valueOf(postSnapshot.child("UserUID").getValue())
////
////                                        ));
////                                        recyclerViewAdapter.notifyDataSetChanged();
////                                    }
////                                }
////                            } catch (Exception e) {
////
////                            }
////                        }
////
////                        @Override
////                        public void onCancelled(@NonNull DatabaseError databaseError) {
////                            recyclerViewAdapter.notifyDataSetChanged();
////                        }
////                    });
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


}



