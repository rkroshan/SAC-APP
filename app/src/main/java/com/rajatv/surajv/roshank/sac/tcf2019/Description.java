package com.rajatv.surajv.roshank.sac.tcf2019;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.CustomGridLayoutManager;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Descriptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public class Description extends AppCompatActivity {

    private final String EVENT_NAME = "event-name";
    Tracker mTracker;

    private DatabaseReference reference,db,subeventDatabase,userDatabase,registrationMapping;
    private TextView aboutEventBox,time1,date1,venue1,time2,date2,venue2,attach,about_EVENT,temp4,rules_tv,register_yourself,add_to_wushlist;
    private RecyclerView eventRecyclerView,attachRecycler,timelineBox;
    private RelativeLayout addToWishlist;
    private ConstraintLayout register_view;
    private AppCompatImageView ic_heart_view,ic_register_view;
    ArrayList<Descriptions> eventList;
    ArrayList<String> attachList,roundTitleList,roundTimeList,roundDateList,roundVenueList,discriptionList;
    private AttachmentRecyclerViewAdapter attachmentRecyclerViewAdapter;



    DescriptionRecyclerViewAdapter descriptionRecyclerViewAdapter;
    DescriptionStatusRecyclerviewAdapter statusRecyclerviewAdapter;
    private String userUID,wishlistString="",registeredString="";
    private boolean found=false;


    private ImageView descriptionback;
    private TextView eventname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.frag_description2);

        aboutEventBox = findViewById(R.id.about_event_box);
        eventRecyclerView = findViewById(R.id.recyclerview_events);
        attachRecycler = findViewById(R.id.attachment_recycler);
        timelineBox = findViewById(R.id.timeline_box);
        attach = findViewById(R.id.attach);
        about_EVENT = findViewById(R.id.about_event);
        temp4 = findViewById(R.id.description_temp4);
        rules_tv = findViewById(R.id.rules_tv);
        descriptionback=findViewById(R.id.description_back);
        eventname=findViewById(R.id.event_name);
        ic_register_view=findViewById(R.id.ic_addition_desc);
        ic_heart_view=findViewById(R.id.ic_heart_desc);
        ic_register_view=findViewById(R.id.ic_addition_desc);
        addToWishlist=findViewById(R.id.addToWishlist_constr);
        register_view=findViewById(R.id.registerConstraint);
        register_yourself=findViewById(R.id.register_yourself_tv);
        add_to_wushlist=findViewById(R.id.addToWishlist_tv);


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        eventList = new ArrayList<>();
        attachList = new ArrayList<>();
        roundDateList = new ArrayList<>();
        roundTimeList = new ArrayList<>();
        roundTitleList = new ArrayList<>();
        roundVenueList = new ArrayList<>();
        discriptionList = new ArrayList<>();

        descriptionRecyclerViewAdapter = new DescriptionRecyclerViewAdapter(this,eventList);
        statusRecyclerviewAdapter = new DescriptionStatusRecyclerviewAdapter(roundTitleList,roundDateList,roundTimeList,roundVenueList);
        attachmentRecyclerViewAdapter = new AttachmentRecyclerViewAdapter(this,attachList,discriptionList);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        eventRecyclerView.setLayoutManager(layoutManager);
        eventRecyclerView.setAdapter(descriptionRecyclerViewAdapter);
        LinearLayoutManager layoutManager3 = new CustomGridLayoutManager(this);
        layoutManager3 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        timelineBox.setLayoutManager(layoutManager3);
        timelineBox.setAdapter(statusRecyclerviewAdapter);

        AttachmentRecyclerViewAdapter attachmentRecyclerViewAdapter = new AttachmentRecyclerViewAdapter(this,attachList,discriptionList);
        LinearLayoutManager layoutManager2 = new CustomGridLayoutManager(this);
        layoutManager2 = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        attachRecycler.setLayoutManager(layoutManager2);
        attachRecycler.setAdapter(attachmentRecyclerViewAdapter);

        Long currts_long = System.currentTimeMillis();

        //check wishlist state//
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        try {
            Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());
            Log.e("mappp", mainMap.toString());

            userUID = mainMap.get(StringVariable.USER_USER_UID).toString();
            Log.e("sharedprefs", sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, ""));

            Map<String, Object> app = (Map<String, Object>) mainMap.get(StringVariable.APP);

            wishlistString = (String) app.get(StringVariable.USER_WISHLIST).toString();
            //check wishlist state//

        }catch (Exception e){
            Log.e("wishlist not working",wishlistString);
        }
        try{
        if(wishlistString.contains(getIntent().getStringExtra(EVENT_NAME))){
            ic_heart_view.setImageResource(R.drawable.ic_heart2);
            add_to_wushlist.setText("Added to Wishlist");

        }
        else
            ic_heart_view.setImageResource(R.drawable.ic_heart);
            add_to_wushlist.setText("Add to Wishlist");
        }catch (Exception e){

        }

            try {
                userDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID);
                registrationMapping = FirebaseDatabase.getInstance().getReference().child("registration-mapping").child("solo");

                userDatabase.child("registrations").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (String.valueOf(dataSnapshot.getValue()).contains(getIntent().getStringExtra(EVENT_NAME))) {
                            register_yourself.setText("Registered");
                        } else {
                            register_yourself.setText("Register Now");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }catch (Exception e){

            }
        addToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(add_to_wushlist.getText().toString().equalsIgnoreCase("Added to Wishlist")){
                    ic_heart_view.setImageResource(R.drawable.ic_heart);

                    //      wishlistString=wishlistString.replaceAll("@arsundram@"+subEventList.get(i).getEventId()+"@arsundram".toCharArray(),"");

//                    if(wishlistString.equalsIgnoreCase("@arsundram@"+getIntent().getStringExtra(EVENT_NAME)))
//                    {
//                        wishlistString="";
//                    }
//                    else
//                    {
//                        String[] temp={"",""};
//
//                        temp = wishlistString.split(Pattern.quote("@arsundram@"+getIntent().getStringExtra(EVENT_NAME)));
//                        //      Log.e("Gotzeeearray0",temp[0]);
//
//                        try {
//                            wishlistString=temp[0]+temp[1];
//
//                        }
//
//                        catch (ArrayIndexOutOfBoundsException e){
//                        }
//
//                    }

                    wishlistString=wishlistString.replace("@arsundram@"+getIntent().getStringExtra(EVENT_NAME),"");


                    Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());
                    Map<String, Object> app = (Map<String, Object>) mainMap.get(StringVariable.APP);
                    app.put(StringVariable.USER_WISHLIST,wishlistString);
                    mainMap.put(StringVariable.APP,app);

//                    othersdata.put(StringVariable.USER_PROFILE,profiledata);
//                    mainMap.put(StringVariable.USER_OTHERDATA,othersdata);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data1 = gson.toJson(mainMap);
                    editor.putString(StringVariable.UserData_Object_SharedPref,data1);
                    editor.commit();
                    subeventDatabase=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_WISHLIST);

                    subeventDatabase.setValue(wishlistString);
                }
                else {
                    ic_heart_view.setImageResource(R.drawable.ic_heart2);

                    wishlistString=wishlistString+"@arsundram@"+getIntent().getStringExtra(EVENT_NAME);

                    Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());
                    Map<String, Object> app = (Map<String, Object>) mainMap.get(StringVariable.APP);
                    app.put(StringVariable.USER_WISHLIST,wishlistString);
                    mainMap.put(StringVariable.APP,app);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data1 = gson.toJson(mainMap);
                    editor.putString(StringVariable.UserData_Object_SharedPref,data1);
                    editor.commit();

                    subeventDatabase=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_WISHLIST);

                    subeventDatabase.setValue(wishlistString);

                }
            }
        });

//        register_view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Drawable.ConstantState unregistered = getDrawable(R.drawable.ic_register).getConstantState();
//                if(ic_heart_view.getDrawable().getConstantState() == unregistered){
//                    ic_heart_view.setImageResource(R.drawable.ic_registered);
//                if(getIntent().getStringExtra("eventType").equalsIgnoreCase("solo")){
//
//                    if(getIntent().getStringExtra("restrictedOrNot").equalsIgnoreCase("true")){
//
//                        userDatabase.child("packages").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                found=false;
//
//                                for(DataSnapshot ds: dataSnapshot.getChildren())
//                                {
//                                    if(getIntent().getStringExtra("packages").contains(String.valueOf(ds.getValue()))){
//
//                                        registrationMapping.child(getIntent().getStringExtra(EVENT_NAME)).child("0").child(userUID).setValue(currts_long);
//                                        userDatabase.child("registrations").child("solo").child(getIntent().getStringExtra(EVENT_NAME)).setValue(0);
//                                        register_yourself.setText("Registered");
//                                        ic_register_view.setImageResource(R.drawable.ic_registered);
//                                        found=true;
//                                    }
//                                    if(found==false)
//                                    {
//                                        Toast.makeText(Description.this,"Sorry, you dont have this package",Toast.LENGTH_SHORT).show();
//
//                                    }
//                                    //TODO UPDATE IN EVENT NODE
//                                }
//
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//                    }
//                    else {
//                        Toast.makeText(Description.this,"AnyoneCanRegister",Toast.LENGTH_SHORT).show();
//
//                        registrationMapping.child(getIntent().getStringExtra(EVENT_NAME)).child("0").child(userUID).setValue(currts_long);
//                        userDatabase.child("registrations").child("solo").child(getIntent().getStringExtra(EVENT_NAME)).setValue(0);
//                        register_yourself.setText("Registered");
//                    }
//                }
//                else {
//                    Toast.makeText(Description.this,"Register yourself through website",Toast.LENGTH_SHORT).show();
//                    register_yourself.setAlpha((float) 0.45);
//                    ic_register_view.setAlpha((float)0.45);
//                }
////                registrationMapping.child(subEventList.get(i).getEventId()).child("0").child(userUID).setValue(currts_long);
////                userDatabase2.child("registrations").child("solo").child(subEventList.get(i).getEventId()).setValue(0);
////                viewHolder.register_yourself.setText("Registered");
//
//            }
//            else{
//
//                }
//        }});

        String eventName = "";
        try {
            eventName = getIntent().getStringExtra(EVENT_NAME);
            Log.e("log33",eventName);
        } catch (Exception e) {

        }
        eventname.setText( getIntent().getStringExtra("toolbarTitle"));

        descriptionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
try{
        if (!eventName.equals("")) {
            reference = FirebaseDatabase.getInstance().getReference(StringVariable.EVENTS).child(eventName);
        }

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long attachmentNu = dataSnapshot.child("links").getChildrenCount();
                    int noRounds = (int)dataSnapshot.child(StringVariable.EVENTS_ROUNDS).getChildrenCount();
                    if(attachmentNu==0){

                        attachRecycler.setVisibility(View.INVISIBLE);
                        attach.setVisibility(View.INVISIBLE);

                    }else {
                        attachRecycler.setVisibility(View.VISIBLE);
                        attach.setVisibility(View.VISIBLE);
                        for(int i = 0;i<attachmentNu;i++){
                            attachList.add(String.valueOf(dataSnapshot.child("links").child(Integer.toString(i)).child("url").getValue()));
                            String description = String.valueOf(dataSnapshot.child("links").child(Integer.toString(i)).child("description").getValue());
                            if(description.equals("null")||description.equals("")){
                                description = "External Link";
                            }
                            discriptionList.add(description);
                        }
                    }

                    if(noRounds==0){
                        temp4.setVisibility(View.GONE);
                        timelineBox.setVisibility(View.GONE);
                    }
                    else {
                        temp4.setVisibility(View.VISIBLE);
                        timelineBox.setVisibility(View.VISIBLE);
                    }

                    String aboutEvent = String.valueOf(dataSnapshot.child(StringVariable.EVENTS_DESCRIPTION).getValue());
                    if(aboutEvent.equals("null")||aboutEvent.equals("")){
                        aboutEventBox.setVisibility(View.INVISIBLE);
                        about_EVENT.setVisibility(View.INVISIBLE);
                    }
                    else{
                        about_EVENT.setVisibility(View.VISIBLE);
                        aboutEventBox.setVisibility(View.VISIBLE);
                        aboutEventBox.setText(aboutEvent);
                    }


//                time1.setText(String.valueOf(dataSnapshot.child(StringVariable.EVENT_REGISTRATION_DETAILS_STARTS_AT).getValue()));
//                time2.setText(String.valueOf(dataSnapshot.child()));
//                date1.setText(String.valueOf(dataSnapshot.child()));
//                date2.setText(String.valueOf(dataSnapshot.child()));
//                venue1.setText(String.valueOf(dataSnapshot.child()));
//                venue2.setText(String.valueOf(dataSnapshot.child()));

                    descriptionRecyclerViewAdapter.notifyDataSetChanged();
                    statusRecyclerviewAdapter.notifyDataSetChanged();
                    attachmentRecyclerViewAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    descriptionRecyclerViewAdapter.notifyDataSetChanged();
                    statusRecyclerviewAdapter.notifyDataSetChanged();
                    attachmentRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {

        }
        try {
            db = FirebaseDatabase.getInstance().getReference(StringVariable.EVENTS).child(eventName).child(StringVariable.EVENTS_ROUNDS);
            db.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    roundDateList.clear();
                    roundTimeList.clear();
                    roundTitleList.clear();
                    roundVenueList.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        long rulesLines = dataSnapshot1.child("rules").getChildrenCount();
                        Log.e("rulesLines",Long.toString(rulesLines));
                        if(rulesLines==0){
                            eventRecyclerView.setVisibility(View.GONE);
                            rules_tv.setVisibility(View.GONE);
                        }
                        else {
                            eventRecyclerView.setVisibility(View.VISIBLE);
                            rules_tv.setVisibility(View.VISIBLE);
                        }

                        StringBuilder rules = new StringBuilder();
                        for (int i = 0; i < rulesLines; i++) {
                            rules.append("\u2022 Bullet").append(String.valueOf(dataSnapshot1.child("rules").child(Integer.toString(i)).child("description").getValue())).append("\n");
                        }
                        String roundTitle = "Next Round";
                        if(dataSnapshot1.child("name").getValue()!=null){
                            roundTitle = String.valueOf(dataSnapshot1.child("name").getValue());
                        }
                        eventList.add(new Descriptions(roundTitle,
                                String.valueOf(dataSnapshot1.child("description").getValue()),
                                rules.toString()
                        ));

                        Long timelong = Long.parseLong(String.valueOf(dataSnapshot1.child("startsAt").getValue()));
                        SimpleDateFormat timeformat = new SimpleDateFormat("hh:mm aaa");
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(timelong);
                        String time = timeformat.format(calendar.getTime());
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        Calendar calendar2 = Calendar.getInstance();
                        calendar2.setTimeInMillis(timelong);
                        String date = dateFormat.format(calendar.getTime());


                        roundTitleList.add(String.valueOf(dataSnapshot1.child("name").getValue()));
                        roundTimeList.add(time);
                        roundDateList.add(date);
                        roundVenueList.add(String.valueOf(dataSnapshot1.child("venue").getValue()));

                        descriptionRecyclerViewAdapter.notifyDataSetChanged();
                        statusRecyclerviewAdapter.notifyDataSetChanged();
                        attachmentRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    descriptionRecyclerViewAdapter.notifyDataSetChanged();
                    statusRecyclerviewAdapter.notifyDataSetChanged();
                    attachmentRecyclerViewAdapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
        }

    }

    public class DescriptionStatusRecyclerviewAdapter extends RecyclerView.Adapter<DescriptionStatusRecyclerviewAdapter.ViewHolder>{
        ArrayList<String> titleList,dateList,timeList,venueList;

        public DescriptionStatusRecyclerviewAdapter(ArrayList<String> titleList, ArrayList<String> dateList, ArrayList<String> timeList, ArrayList<String> venueList) {
            this.titleList = titleList;
            this.dateList = dateList;
            this.timeList = timeList;
            this.venueList = venueList;
        }

        @NonNull
        @Override
        public DescriptionStatusRecyclerviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(Description.this);
           View view =  inflater.inflate(R.layout.element_event_status,viewGroup,false);
           return new ViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull DescriptionStatusRecyclerviewAdapter.ViewHolder viewHolder, int i) {

            String roundNameText = titleList.get(i);
            if(roundNameText.equals("null")|| roundNameText.equals("")){
                roundNameText = "Round "+Integer.toString(i+1);
            }
            viewHolder.roundName.setText(roundNameText);

            String dateText = dateList.get(i);
            if(dateText.equals("null")||dateText.equals("")){
                dateText = "Not Decided Yet";
            }
            viewHolder.date.setText(dateText);

            String timeText = timeList.get(i);
            if(timeText.equals("null")||timeText.equals("")){
                timeText = "Not Decided Yet";
            }
            viewHolder.time.setText(timeText);

            String venue = venueList.get(i);
            if(venue.equals("null")||venue.equals("")){
                venue = "Not Decided Yet";
            }
            viewHolder.venue.setText(venue);
            if(i==getItemCount()-1){
                viewHolder.bar.setVisibility(View.GONE);

            }
            else{
                viewHolder.bar.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {
            return titleList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView roundName,time,venue,date;
            View bar;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                roundName = itemView.findViewById(R.id.round_name);
                time = itemView.findViewById(R.id.time);
                date = itemView.findViewById(R.id.date);
                venue = itemView.findViewById(R.id.venue);
                venue.setSelected(true);
                bar = itemView.findViewById(R.id.filter_main_view);

            }

        }
    }
}
