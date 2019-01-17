package com.rajatv.surajv.roshank.sac;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.SubeventsDetailsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ByDateOnclick extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<SubeventDetails> subeventDetailsList;
    Tracker mTracker;
    DatabaseReference  subeventsDatabase2;
    private ArrayList<String> demolist;
    SubeventsDetailsRecyclerViewAdapter recyclerViewAdapter;
    String[] eventdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_date_onclick);
        //loadList();
        recyclerView = (RecyclerView) findViewById(R.id.blog_recycler_view);
        //recyclerViewAdapter = new ByDateonclickAdapter(this, subeventDetailsList);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setAdapter(recyclerViewAdapter);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        try {
            final String eventName = getIntent().getStringExtra("event_name");
            eventdate = eventName.split("@sac@");
        }catch (Exception e){

        }
        subeventDetailsList = new ArrayList<>();
        for (int i = 0; i < eventdate.length; i++) {
            String k = eventdate[i];
            subeventsDatabase2 = FirebaseDatabase.getInstance().getReference().child("events").child(k);
            try {
                subeventsDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot postSnapshot1) {
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
                        recyclerViewAdapter = new SubeventsDetailsRecyclerViewAdapter(getApplicationContext(), subeventDetailsList);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }catch (Exception e){
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    }
}



