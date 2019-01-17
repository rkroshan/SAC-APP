package com.rajatv.surajv.roshank.sac.tcf2019;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.CirclePagerIndicatorDecoration;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Highlights;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.TodayTomorrowModalClass;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.HighlightsAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.TodayAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.TomorrowAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TCFHomeFrag extends Fragment {

    private RecyclerView recyclerViewToday,recyclerViewTomorrow,recyclerViewHighlights;
    private List<TodayTomorrowModalClass> todayEventsList,tomorrowEventsList;
    private List<Highlights> highlightsList;
    DatabaseReference TodayDatabase,TomorrowDatabase,highLightsDatabase;
    TodayAdapter todayAdapter;
    TomorrowAdapter tomorrowAdapter;
    HighlightsAdapter highlightsAdapter;
    private TextView todayDate,tommorrowDate;
    String useruid="";

    public TCFHomeFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_tcf_home,container,false);

        recyclerViewToday=(RecyclerView)view.findViewById(R.id.tcfHome_recyclerView_todaysEvents);
        recyclerViewTomorrow=(RecyclerView)view.findViewById(R.id.tcfHome_recyclerView_tomosEvents);
        recyclerViewHighlights=(RecyclerView)view.findViewById(R.id.highlight_recycler_view);
        todayDate = view.findViewById(R.id.tv_TodaysDate);
        tommorrowDate = view.findViewById(R.id.tv_tomorrow_date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendar = Calendar.getInstance();

        long currentTimeMili = System.currentTimeMillis();
        calendar.setTimeInMillis(currentTimeMili);
        String formattedTodayDate = dateFormat.format(calendar.getTime());

        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String formattedTommorowDate = dateFormat.format(calendar.getTime());

        todayDate.setText(formattedTodayDate);
        tommorrowDate.setText(formattedTommorowDate);

        todayAdapter=new TodayAdapter(getContext(),todayEventsList);
        tomorrowAdapter=new TomorrowAdapter(getContext(),tomorrowEventsList);
        highlightsAdapter=new HighlightsAdapter(getContext(),highlightsList);

        final FragmentActivity f = getActivity();

        final LinearLayoutManager layoutManager= new LinearLayoutManager(f);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager layoutManager2= new LinearLayoutManager(f);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager layoutManager3= new LinearLayoutManager(f);
        layoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);


        recyclerViewToday.setLayoutManager(layoutManager);
        recyclerViewTomorrow.setLayoutManager(layoutManager2);
        recyclerViewHighlights.setLayoutManager(layoutManager3);

        PagerSnapHelper snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewHighlights);
        recyclerViewHighlights.setBackgroundColor(000000);
        recyclerViewHighlights.addItemDecoration(new CirclePagerIndicatorDecoration());


        recyclerViewToday.setAdapter(todayAdapter);
        recyclerViewTomorrow.setAdapter(tomorrowAdapter);
        recyclerViewHighlights.setAdapter(highlightsAdapter);

        useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayEventsList=new ArrayList<>();
        tomorrowEventsList=new ArrayList<>();
        highlightsList=new ArrayList<>();

        TodayDatabase=FirebaseDatabase.getInstance().getReference().child("TodayEvent");
        TomorrowDatabase=FirebaseDatabase.getInstance().getReference().child("TomorrowEvent");
        highLightsDatabase=FirebaseDatabase.getInstance().getReference().child(StringVariable.HIGHLIGHTHS);


        TodayDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             //   TodayTomorrowModalClass tomorrowModalClass;

                for(DataSnapshot ds : dataSnapshot.getChildren()){

//                   tomorrowModalClass = ds.getValue(TodayTomorrowModalClass.class);
//                   todayEventsList.add(tomorrowModalClass);


                    String subEventName = String.valueOf(ds.child("subeventname").getValue());
                    String eventName = String.valueOf(ds.child("eventname").getValue());
                    String timeLongString = String.valueOf(ds.child("time").getValue());
                    String location = String.valueOf(ds.child("location").getValue());
                    String round = String.valueOf(ds.child("round").getValue());

                    Long timelong = Long.parseLong(timeLongString);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timelong);
                    String time = dateFormat.format(calendar.getTime());

                    if(subEventName.equals("null")||subEventName.equals("")){
                        subEventName = "Not Decided Yet";
                    }
                    if(eventName.equals("null")||eventName.equals("")){
                        eventName = "Not Decided Yet";
                    }
                    if(location.equals("null")||location.equals("")){
                        location = "Not Decided Yet";
                    }
                    if(round.equals("null")||round.equals("")){
                        round = "Not Decided Yet";
                    }


                    todayEventsList.add(new TodayTomorrowModalClass(subEventName,eventName,time,location,round));
                    //todayEventsList.add(new TodayTomorrowModalClass())
//                    todayAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                todayAdapter.notifyDataSetChanged();
            }
        });

        TomorrowDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                TodayTomorrowModalClass tomorrowModalClass;

                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String subEventName = String.valueOf(ds.child("subeventname").getValue());
                    String eventName = String.valueOf(ds.child("eventname").getValue());
                    String timelongString = String.valueOf(ds.child("time").getValue());
                    String location = String.valueOf(ds.child("location").getValue());
                    String round = String.valueOf(ds.child("round").getValue());

                    Long timelong = Long.parseLong(timelongString);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aaa");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(timelong);
                    String time = dateFormat.format(calendar.getTime());

                    if (subEventName.equals("null") || subEventName.equals("")) {
                        subEventName = "Not Decided Yet";
                    }
                    if (eventName.equals("null") || eventName.equals("")) {
                        eventName = "Not Decided Yet";
                    }
                    if (location.equals("null") || location.equals("")) {
                        location = "Not Decided Yet";
                    }
                    if (round.equals("null") || round.equals("")) {
                        round = "Not Decided Yet";
                    }


//                    tomorrowModalClass = ds.getValue(TodayTomorrowModalClass.class);
//                    tomorrowEventsList.add(tomorrowModalClass);
                    tomorrowEventsList.add(new TodayTomorrowModalClass(subEventName, eventName, time, location, round));
//                    tomorrowAdapter.notifyDataSetChanged();

                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        highLightsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Highlights highlight;
                highlightsList.clear();

                for(DataSnapshot post1Snapshot : dataSnapshot.getChildren()){
                    int liked = 0;
                    for(DataSnapshot ds:post1Snapshot.child(StringVariable.PEOPLE).getChildren()){
                        if(ds.getKey().equals(useruid)){
                            liked=1;
                        }
                    }
                    highlightsList.add(new Highlights(
                            liked,
                            String.valueOf(post1Snapshot.child("EventName").getValue()),
                            String.valueOf(post1Snapshot.child("DateTime").getValue()),
                            "Going?",
                            String.valueOf(post1Snapshot.child("PeopleGoing").getValue()),
                            String.valueOf(post1Snapshot.child("imageURI").getValue()),
                            post1Snapshot.getKey()
                    ));

                   // highlightsList.add(highlight);
                }
                highlightsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                todayAdapter.notifyDataSetChanged();
            }
        });

    }
}