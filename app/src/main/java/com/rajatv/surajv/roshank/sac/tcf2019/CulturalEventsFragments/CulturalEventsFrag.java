package com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments;

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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.List;

public class CulturalEventsFrag extends Fragment {
    private RecyclerView recyclerView;
    private List<CulturalEvents> eventDetailsList;
    DatabaseReference eventsDatabase,eventdatabase2;
    CulturalEventsRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.freg_cultural_events,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.cultural_event_recyclerview);
        recyclerViewAdapter=new CulturalEventsRecyclerViewAdapter(getContext(),eventDetailsList);
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

        eventDetailsList=new ArrayList<>();
        eventdatabase2=FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child(StringVariable.EVENTS_CULTURAL).child(StringVariable.EVENTS_CHILDREN);
        eventsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS);  //ADD THE DATABASE REFERENCE

        try {
            eventdatabase2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    eventDetailsList.clear();
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        //ADD THE DATA FROM DATABASE AS PER THE FIELD
                        String name = String.valueOf(postSnapshot.child("name").getValue());
                        final String id = String.valueOf(postSnapshot.child("id").getValue());
//                        Log.d(id, "dataSnapshot");
                        eventsDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                for (DataSnapshot postSnapshot1 : dataSnapshot1.getChildren()) {
                                    if (String.valueOf(postSnapshot1.getKey()).equalsIgnoreCase(id)) {
                                        //Log.d(String.valueOf(postSnapshot1.getValue()),"dataSnapshot");
                                        //          Log.e("hjkl",String.valueOf(postSnapshot1.getKey()));
                                        String tag = "----";
                                        try {
                                            tag = String.valueOf(postSnapshot1.child("Tagline").getValue());
                                            if (tag.equals("null")) tag = "----";
                                        } catch (Exception e) {
                                        }
                                        eventDetailsList.add(new CulturalEvents(
                                                String.valueOf(postSnapshot1.child("name").getValue()),
                                                tag,
                                                String.valueOf(postSnapshot1.getKey())


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
            });
        }
        catch (Exception e){}
    }
}
