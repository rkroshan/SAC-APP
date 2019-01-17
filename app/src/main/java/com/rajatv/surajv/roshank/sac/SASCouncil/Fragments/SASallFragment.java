package com.rajatv.surajv.roshank.sac.SASCouncil.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SASCouncil.Adapters.SASAppAndMediaAdapter;
import com.rajatv.surajv.roshank.sac.SASCouncil.SwipeToDeleteCallback;
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

public class SASallFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Contacts> contactsList;
    DatabaseReference delete, contactsSubDatabase, databaseReference;
    private ArrayList<String> demolist = new ArrayList<>();
    RelativeLayout sasRelativeLayot;
    String userUID = "";
    SASAppAndMediaAdapter recyclerViewAdapter;
    CardView a, b, c, d;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.contacts_events_recycler_view);
        recyclerViewAdapter = new SASAppAndMediaAdapter(getContext(), contactsList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        sasRelativeLayot = view.findViewById(R.id.sasfragmentRelative);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            contactsList = new ArrayList<>();
            databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.SASCOUNCILS);
            delete = FirebaseDatabase.getInstance().getReference().child(StringVariable.SASCOUNCILS);
            try {
                userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            } catch (Exception e) {

            }
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    contactsList.clear();
                    if (!String.valueOf(dataSnapshot.getValue()).equals("null")) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            if (!(String.valueOf(postSnapshot.getValue()).equals("null") || String.valueOf(postSnapshot.getValue()).equals(""))) {
                                contactsSubDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(String.valueOf(postSnapshot.getValue()));
                                contactsSubDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String sas = "";
                                        try {
                                            sas = String.valueOf(dataSnapshot.child(StringVariable.USER_IS_SASMEMBER).getValue());
                                            int value = 6;
                                            switch (sas.split("@SAC2.0@")[1]) {

                                                case "President": {
                                                    value = 1;
                                                    break;
                                                }
                                                case "Vice President": {
                                                    value = 2;
                                                    break;
                                                }
                                                case "Technical Secretory": {
                                                    value = 3;
                                                    break;
                                                }

                                                case "Secretory": {
                                                    value = 3;
                                                    break;
                                                }
                                                case "Cordinator": {
                                                    value = 4;
                                                    break;
                                                }
                                                case "Co-Cordinator": {
                                                    value = 5;
                                                    break;
                                                }
                                                case "Member": {
                                                    value = 6;
                                                    break;
                                                }
                                                default: {
                                                    value = 0;
                                                }
                                            }

                                            if (!sas.equals("")) {


                                                sas = sas.split("@SAC2.0@")[1];


                                                contactsList.add(new Contacts(
                                                        String.valueOf(dataSnapshot.child("name").getValue()),
                                                        String.valueOf(dataSnapshot.child("photoURL").getValue()),
                                                        sas,
                                                        String.valueOf(dataSnapshot.child("phoneNumber").getValue()),
                                                        String.valueOf(dataSnapshot.child("phoneNumber").getValue()),
                                                        String.valueOf(dataSnapshot.getKey()), value
                                                ));
                                                Collections.sort(contactsList, new Comparator<Contacts>() {
                                                    @Override
                                                    public int compare(Contacts o1, Contacts o2) {
                                                        return o1.getValue() > o2.getValue() ? 1 : (o1.getValue() < o2.getValue()) ? -1 : 0;
                                                    }
                                                });
                                            }
                                        } catch (Exception e) {
                                        }
                                        try {
                                            //add user uid of supper users.
                                            if (userUID.equals("JtN8uvLS3sbwTHt6THyVbGGfnLG2")) {
                                                enableSwipeToDeleteAndUndo();

                                            }
                                        } catch (Exception e) {

                                        }
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        recyclerViewAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
        }
    }

    private void enableSwipeToDeleteAndUndo() {
        try {
            SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(getActivity().getBaseContext()) {
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                    final int position = viewHolder.getAdapterPosition();
                    final Contacts item = recyclerViewAdapter.getData().get(position);
                    String userUID_delete = recyclerViewAdapter.getData().get(position).getUserUID();
                    recyclerViewAdapter.removeItem(position);
                  //  Log.e("DELETE USER", userUID_delete);
                    try {
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.getValue().toString().equals(userUID_delete)) {
                                        databaseReference.child(snapshot.getKey()).setValue(null);
                                      //  Log.e("DELETECOMPLETE", userUID_delete);
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } catch (Exception e) {
                        Log.e("EXe", e.getMessage());
                    }

                    Snackbar snackbar = Snackbar
                            .make(sasRelativeLayot, "SAS member removed.", Snackbar.LENGTH_LONG);
                    snackbar.setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            recyclerViewAdapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                            databaseReference.push().setValue(userUID_delete);
                        }
                    });

                    snackbar.setActionTextColor(Color.YELLOW);
                    snackbar.show();

                }
            };

            ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeToDeleteCallback);
            itemTouchhelper.attachToRecyclerView(recyclerView);
        } catch (Exception e) {

        }
    }
}



