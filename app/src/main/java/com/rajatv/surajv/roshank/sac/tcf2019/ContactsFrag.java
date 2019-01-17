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

public class ContactsFrag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Contacts> contactsList = new ArrayList<>();
    DatabaseReference speedDialDatabase, usersDatabase;
    SASAppAndMediaAdapter recyclerViewAdapter;
    private ArrayList<String> demolist;
    private String userUID,culturaleventname="";

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
        Log.e("check","contact fragment check");
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        demolist = new ArrayList<String>();
//        getUserData();
        try {
            culturaleventname = getArguments().getString("event_name");
            Log.e("nameeeeeee",culturaleventname);
        }catch (Exception e){
        }

            Log.e("notnulll",culturaleventname);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.SASCOUNCILS);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactsList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DatabaseReference db2 = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(String.valueOf(postSnapshot.getValue()));
                    db2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.e("AvlokanSnapshot", dataSnapshot.toString() );
                            if(dataSnapshot.child(StringVariable.USER_IS_TCFMEMBER).getValue()!=null) {
                                String sas = String.valueOf(dataSnapshot.child(StringVariable.USER_IS_TCFMEMBER).getValue());
                                Log.e("Avlokan", sas);
                                try {
                                    if (culturaleventname.equalsIgnoreCase(sas.split("@SAC2.0@")[1])) {
                                        int value = 4;
                                        switch (sas.split("@SAC2.0@")[1]) {
                                            case "Secretory": {
                                                value = 1;
                                            }
                                            case "Coordinator": {
                                                value = 2;
                                            }
                                            case "Co-Coordinator": {
                                                value = 3;
                                            }
                                            case "Senior Member": {
                                                value=4;
                                            }
                                            case "Member": {
                                                value = 5;
                                            }
                                        }
                                        Log.e("found","found");
                                        contactsList.add(new Contacts(
                                                String.valueOf(dataSnapshot.child("name").getValue()),
                                                String.valueOf(dataSnapshot.child(StringVariable.USER_IMAGE).getValue()),
                                                sas.split("@SAC2.0@")[2],
                                                String.valueOf(dataSnapshot.child(StringVariable.USER_PHONENUMBER).getValue()),
                                                String.valueOf(dataSnapshot.child(StringVariable.USER_PHONENUMBER).getValue()),
                                                String.valueOf(dataSnapshot.getKey()),value
                                        ));

                                        Collections.sort(contactsList, new Comparator<Contacts>() {
                                            @Override
                                            public int compare(Contacts o1, Contacts o2) {
                                                return o1.getValue()>o2.getValue()?1:(o1.getValue()<o2.getValue())?-1:0;
                                            }
                                        });

                                        Log.e("eventContact",contactsList.get(0).getName());
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }
                                }
                                catch (Exception e){}
//                                try {
//
//                                    if (culturaleventname.equalsIgnoreCase(sas.split("@SAC2.0@")[1])) {
//                                        contactsList.add(new Contacts(
//                                                String.valueOf(dataSnapshot.child("name").getValue()),
//                                                String.valueOf(dataSnapshot.child("profile_pic").getValue()),
//                                                sas.split("@SAC2.0@")[2],
//                                                String.valueOf(dataSnapshot.child("WhatsappNo").getValue()),
//                                                String.valueOf(dataSnapshot.child("WhatsappNo").getValue()),
//                                                String.valueOf(dataSnapshot.child("UserUID").getValue())
//                                        ));
//                                    }
//                                    Log.e("received",dataSnapshot.toString() );
//                                    recyclerViewAdapter.notifyDataSetChanged();
//                                }catch (Exception e){
//                                    Toast.makeText(getContext(), "Exception Error", Toast.LENGTH_LONG).show();
//
//                                }
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                Log.e("demolist",dataSnapshot.toString());
//                recyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        contactsList.clear();

//    private void getUserData() {
//        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
//        Gson gson = new Gson();
//        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
//        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
//
//        userUID= obj.get(StringVariable.USER_USER_UID).toString();
//        Log.e("useruid",userUID);
//    }

    }
}



