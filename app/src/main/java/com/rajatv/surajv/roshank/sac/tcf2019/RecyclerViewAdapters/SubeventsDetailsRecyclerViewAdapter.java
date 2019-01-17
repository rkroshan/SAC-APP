package com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Description;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubeventsDetailsRecyclerViewAdapter extends RecyclerView.Adapter<SubeventsDetailsRecyclerViewAdapter.ViewHolder> implements Filterable {

    DatabaseReference databaseReference;
    private Context context;
    List<SubeventDetails> subEventList, subEventListfull;
    private String userUID, wishlistString = "";
    DatabaseReference subeventDatabase, userDatabase, userDatabase2, registrationMapping, eventDatabase;
    private boolean found = false;
    private String noPackage="true";
    Snackbar mySnackbar;
    private Dialog gotoWeb;


    public SubeventsDetailsRecyclerViewAdapter(Context context, List<SubeventDetails> subeventDetailsList) {
        this.context = context;
        this.subEventList = subeventDetailsList;
        subEventListfull = subeventDetailsList;

    }

    public SubeventsDetailsRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_details_subevents, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        Log.e("final1", "reach");

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        gotoWeb = new Dialog(context);

        Long currts_long = System.currentTimeMillis();
        String currts_string = String.valueOf(currts_long);
        try {
            Long event_ts = Long.parseLong(subEventList.get(i).getReg_startTime());
            Long event_ts_end = Long.parseLong(subEventList.get(i).getReg_endTime());

            Drawable buttonDrawable = viewHolder.registration_open.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);
            if (currts_long < event_ts) {
                viewHolder.registration_open.setText("Registration Starting soon");


                DrawableCompat.setTint(buttonDrawable, Color.parseColor("#da812d"));
                viewHolder.registration_open.setBackground(buttonDrawable);

            } else if (currts_long > event_ts && currts_long < event_ts_end) {
                viewHolder.registration_open.setText("Registrations Open");
                DrawableCompat.setTint(buttonDrawable, Color.parseColor("#1c7900"));
                viewHolder.registration_open.setBackground(buttonDrawable);
            } else if (event_ts_end < currts_long) {
                viewHolder.registration_open.setText("Registrations Closed");
                DrawableCompat.setTint(buttonDrawable, Color.parseColor("#696969"));
                viewHolder.registration_open.setBackground(buttonDrawable);

            }
        } catch (NumberFormatException e) {
            viewHolder.registration_open.setVisibility(View.GONE);
        }


        eventDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child(subEventList.get(i).getEventId()).child(StringVariable.EVENTS_ROUNDS);

        eventDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            if (Long.valueOf(String.valueOf(ds.child(StringVariable.EVENT_REGISTRATION_DETAILS_STARTS_AT).getValue())) > currts_long) {
                                String j = getDate(ds.child(StringVariable.EVENT_REGISTRATION_DETAILS_STARTS_AT).getValue(Long.class));
                                subEventList.get(i).setCurrenttime(j);
                                subEventList.get(i).setCurrentplace(String.valueOf(ds.child("venue").getValue()));
//                                notifyDataSetChanged();
                                viewHolder.currenttime.setText(subEventList.get(i).getCurrenttime());
                                if (!String.valueOf(subEventList.get(i).getCurrentplace()).equalsIgnoreCase("null")) {
                                    viewHolder.location.setText(subEventList.get(i).getCurrentplace());

                                } else {
                                    viewHolder.location.setText("Not decided yet");

                                }


                                Log.e("date", ds.toString());
                                break;
                            }
                        } catch (Exception e) {
                            Log.e("Subevents timestmp err", e.getMessage());
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewHolder.subeventname.setText(subEventList.get(i).getSubeventname());

        //  viewHolder.currenttime.setText(dateString);
//        viewHolder.location.setText(subEventList.get(i).getCurrentplace());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());

        viewHolder.currentItem = subEventList.get(i);

        SharedPreferences sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());
//        userUID = mainMap.get(StringVariable.USER_USER_UID).toString();
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        // SharedPreferences.Editor editor = sharedPreferences.edit();
        Map<String, Object> app = new HashMap<>();
        try {
            app = (Map<String, Object>) mainMap.get(StringVariable.APP);
            wishlistString = (String) app.get(StringVariable.USER_WISHLIST).toString();
        } catch (Exception e) {
        }
        userDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child("packages");
        userDatabase2 = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child("registrations");

        userDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                if (String.valueOf(dataSnapshot.getValue()).contains(subEventList.get(i).getEventId())) {
                    viewHolder.register_yourself.setText("Registered");
                    viewHolder.ic_addition.setImageResource(R.drawable.ic_registered);
                } else {
                    viewHolder.register_yourself.setText("Register Yourself");
                    viewHolder.ic_addition.setImageResource(R.drawable.ic_register);

                }}catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        registrationMapping = FirebaseDatabase.getInstance().getReference().child("registration-mapping").child("solo");

        if (subEventList.get(i).getRegistration_type().equalsIgnoreCase("solo")) {
            viewHolder.register_yourself.setAlpha((float) 1);
            viewHolder.ic_addition.setAlpha((float) 1);
        } else {
            viewHolder.register_yourself.setAlpha((float) 0.45);
            viewHolder.ic_addition.setAlpha((float) 0.45);
        }
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    found = false;
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        if (subEventList.get(i).getPackages().contains(String.valueOf(ds.getValue()))) {
                            found = true;
                        }

                    }
                    if (!found) {
                        viewHolder.register_yourself.setAlpha((float) 0.45);
                        viewHolder.ic_addition.setAlpha((float) 0.45);
                    }
                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        viewHolder.register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(viewHolder.registration_open.getText().toString().equalsIgnoreCase("Registration Starting soon"))
                {
                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                    mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                    mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
                    mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setText("Registrations not yet started for this event!");

                    mySnackbar.show();
                }
                else if(viewHolder.registration_open.getText().toString().equalsIgnoreCase("Registrations Closed"))
                {
                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                    mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                    mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
                    mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setText("Sorry, Registrations have closed!");

                    mySnackbar.show();
                }
                else{

                    userDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            try {
                                found = false;
                                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                    if (subEventList.get(i).getPackages().contains(String.valueOf(ds.getValue()))) {
                                        found = true;
                                        noPackage = "false";
                                    }
                                }
                                if (found == false) {

                                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                                mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                                mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
                                mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                tv.setTypeface(Typeface.DEFAULT_BOLD);
                                tv.setText("Sorry, You don't have the package for this event!");

                                    mySnackbar.show();
                                }
                            }catch (Exception e){

                            }
                            //TODO UPDATE IN EVENT NODE
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    if(noPackage.equalsIgnoreCase("false"))
                    {

                        if (subEventList.get(i).getRegistration_type().equalsIgnoreCase("solo")) {
                            if (subEventList.get(i).getRestricted().equalsIgnoreCase("true")) {

                                userDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            found = false;
                                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                                if (subEventList.get(i).getPackages().contains(String.valueOf(ds.getValue()))) {

                                                    registrationMapping.child(subEventList.get(i).getEventId()).child("0").child(userUID).setValue(currts_long);
                                                    userDatabase2.child("solo").child(subEventList.get(i).getEventId()).setValue(0);
                                                    viewHolder.register_yourself.setText("Registered");
                                                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                                                mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                                                mySnackbar.getView().setBackgroundColor(Color.parseColor("#00ba4e"));
                                                mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                                                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                tv.setTypeface(Typeface.DEFAULT_BOLD);
                                                tv.setText("successfully Registered");

                                                viewHolder.register_yourself.setText("Registered");
                                                viewHolder.ic_addition.setImageResource(R.drawable.ic_registered);

                                                    mySnackbar.show();

                                                    found = true;

                                                }
                                                if (found == false) {
                                                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                                                    mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                                                    mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
                                                    mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                    TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                                                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                                                    tv.setText("Sorry, You don't have this package");

                                                    mySnackbar.show();
                                                }
                                                //TODO UPDATE IN EVENT NODE
                                            }
                                        }catch (Exception e){

                                        }

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else {
                                Toast.makeText(context, "AnyoneCanRegister", Toast.LENGTH_SHORT).show();
                               // String soloeventid = subEventList.get(i).getEventId()

                                registrationMapping.child(subEventList.get(i).getEventId()).child("0").child(userUID).setValue(currts_long);
                                userDatabase2.child("registrations").child("solo").child(subEventList.get(i).getEventId()).setValue(0);
                                viewHolder.register_yourself.setText("Registered");
                            }
                        } else {
                            //   Toast.makeText(context,"Register yourself through website",Toast.LENGTH_SHORT).show();

                            mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                            if(viewHolder.register_yourself.getText().toString().equalsIgnoreCase("Register Yourself")) {
                                openDialog();

                            }
                            else {
                                gotoWeb.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

                                gotoWeb.setContentView(R.layout.popup_register_team);
                                gotoWeb.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                gotoWeb.show();
                                TextView webButton = gotoWeb.findViewById(R.id.web_button);
                                TextView tcf  = gotoWeb.findViewById(R.id.new_dersion);

//                                webButton.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tcf.nitp.tech/"));
//                                        context.startActivity(intent);
//                                    }
//                                });
                                webButton.setVisibility(View.GONE);

                                DatabaseReference dbref = userDatabase2.child("team");
                                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Log.e("teamref", dataSnapshot.toString() );
                                        for (DataSnapshot ds : dataSnapshot.getChildren()){

                                            tcf.setText("Team ID :"+String.valueOf(ds.getValue()));

                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                            }
//
//                            mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
//                            mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
//                            mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                            TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
//                            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//                            tv.setTypeface(Typeface.DEFAULT_BOLD);
//                            tv.setText("For group-events, please register through Website");
//
//                            mySnackbar.show();

                            viewHolder.register_yourself.setAlpha((float) 0.45);
                            viewHolder.ic_addition.setAlpha((float) 0.45);
                        }
                    }
                }
            }
        });

        if (wishlistString.contains(subEventList.get(i).getEventId()) && !subEventList.get(i).getEventId().equals("raaga")) {
            viewHolder.ic_heart.setImageResource(R.drawable.ic_heart2);
            viewHolder.added_to_wishlist.setText("Added to Wishlist");
        } else {
            viewHolder.ic_heart.setImageResource(R.drawable.ic_heart);
            viewHolder.added_to_wishlist.setText("Add to Wishlist");

        }
        String eventId = subEventList.get(i).getEventId();
        Map<String, Object> finalOthersdata = app;
        Map<String, Object> finalOthersdata1 = app;
        viewHolder.addToWishlistView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("bolly ",viewHolder.added_to_wishlist.getText().toString());
                if (viewHolder.added_to_wishlist.getText().toString().equalsIgnoreCase("Added to Wishlist")) {
                    viewHolder.ic_heart.setImageResource(R.drawable.ic_heart);
                    viewHolder.added_to_wishlist.setText("Add to Wishlist");
                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                    mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                    mySnackbar.getView().setBackgroundColor(Color.parseColor("#da812d"));
                    mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setText("Removed from Wishlist");

                    mySnackbar.show();


                    //      wishlistString=wishlistString.replaceAll("@arsundram@"+subEventList.get(i).getEventId()+"@arsundram".toCharArray(),"");
                    wishlistString = wishlistString.replace("@arsundram@" + subEventList.get(i).getEventId(), "");

//                                    Log.e("AMANAMAN",wishlistString);
                    finalOthersdata.put(StringVariable.USER_WISHLIST, wishlistString);
                    mainMap.put(StringVariable.APP, finalOthersdata);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data1 = gson.toJson(mainMap);
                    editor.putString(StringVariable.UserData_Object_SharedPref, data1);
                    editor.commit();

                    subeventDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_WISHLIST);

                    subeventDatabase.setValue(wishlistString);
//                    notifyDataSetChanged();
                } else if(viewHolder.added_to_wishlist.getText().toString().equalsIgnoreCase("Add to Wishlist")) {
                    viewHolder.ic_heart.setImageResource(R.drawable.ic_heart2);
                    viewHolder.added_to_wishlist.setText("Added to Wishlist");
                    wishlistString = wishlistString + "@arsundram@" + subEventList.get(i).getEventId();
                    mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);

                    finalOthersdata1.put(StringVariable.USER_WISHLIST, wishlistString);
                    mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
                    mySnackbar.getView().setBackgroundColor(Color.parseColor("#da812d"));
                    mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    tv.setTypeface(Typeface.DEFAULT_BOLD);
                    tv.setText("Added to Wishlist");

                    mySnackbar.show();

                    mainMap.put(StringVariable.APP, finalOthersdata);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data1 = gson.toJson(mainMap);
                    editor.putString(StringVariable.UserData_Object_SharedPref, data1);
                    editor.commit();

                    subeventDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_WISHLIST);

                    subeventDatabase.setValue(wishlistString);
//                    notifyDataSetChanged();

                }
            }
        });


    }

    private void openDialog() {
        gotoWeb.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        gotoWeb.setContentView(R.layout.popup_register_team);
        gotoWeb.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gotoWeb.show();
        TextView webButton = gotoWeb.findViewById(R.id.web_button);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://tcf.nitp.tech/"));
                context.startActivity(intent);
            }
        });
    }

    private String getDate(Long value) {
        String dateString = "null";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            dateString = formatter.format(new Date(value));
            SimpleDateFormat formatter2 = new SimpleDateFormat("d MMM, YYYY");
            dateString = dateString + " | " + formatter2.format(new Date(value));
        } catch (NumberFormatException n) {

        }
        return dateString;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public View view;
        public SubeventDetails currentItem;
        Intent intent;

        private final String SUB_EVENT_NAME = "event-name";
        private final String SUB_EVENT_VENUE1 = "event venue1";
        private final String SUB_EVENT_VENUE2 = "event venue2";
        private final String SUB_EVENT_TIME1 = "event time1";
        private final String SUB_EVENT_TIME2 = "event time2";
        private final String SUB_EVENT_DATE1 = "event date1";
        private final String SUB_EVENT_DATE2 = "event date2";


        //ADD ELEMENT VARIABLES
        private ImageView ic_time, ic_location, ic_addition, ic_heart;
        private TextView subeventname, currenttime, location, register_yourself, registration_open, added_to_wishlist;
        private View addToWishlistView, register_view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            recyclerView = (RecyclerView) itemView.findViewById(R.id.subevent_details_recyclerView);

            //ADD ELEMENTS VARIABLES HERE
            subeventname = itemView.findViewById(R.id.tv_subeventName);
            currenttime = itemView.findViewById(R.id.tv_event_time_suvevent);
            location = itemView.findViewById(R.id.tv_venue_subEvent);
            ic_time = itemView.findViewById(R.id.clocksymbol);
            ic_location = itemView.findViewById(R.id.locationsymbol);
            ic_addition = itemView.findViewById(R.id.additionsymbol);
            ic_heart = itemView.findViewById(R.id.heartsymbol);
            register_yourself = itemView.findViewById(R.id.registrationpath);
            registration_open = itemView.findViewById(R.id.tv_registrationOpen_subEvent);
            added_to_wishlist = itemView.findViewById(R.id.addToWishlist_tv);
            addToWishlistView = itemView.findViewById(R.id.addToWishlistView);
            register_view = itemView.findViewById(R.id.registerYourselfView);
            //click event.

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(context, Description.class);
                    intent.putExtra(SUB_EVENT_NAME, currentItem.getEventId());
                    intent.putExtra("eventType", currentItem.getRegistration_type());
                    intent.putExtra("restrictedOrNot", currentItem.getRestricted());
                    intent.putExtra("packages", currentItem.getPackages());
                    intent.putExtra("toolbarTitle", currentItem.getSubeventname());
                    intent.putExtra("registrationTime",registration_open.getText().toString());

//                    intent.putExtra(SUB_EVENT_VENUE1,currentItem.getCurrentplace());
//                    intent.putExtra(SUB_EVENT_VENUE2,currentItem.getCurrentplace());
//                    intent.putExtra(SUB_EVENT_TIME1,currentItem.getCurrenttime());
//                    intent.putExtra(SUB_EVENT_TIME2,currentItem.getCurrenttime());
//                    intent.putExtra(SUB_EVENT_DATE1,currentItem.getCurrenttime());
//                    intent.putExtra(SUB_EVENT_DATE2,currentItem.getCurrenttime());
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return subEventList.size();
    }

    @Override
    public Filter getFilter() {
        return personalityfilter;
    }

    private Filter personalityfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<SubeventDetails> filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist = subEventListfull;
                Log.e("FilterList", filterlist.size() + "");
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (SubeventDetails item : subEventListfull) {
                    if (item.getSubeventname().toLowerCase().contains(filterpattern)) {
                        filterlist.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            subEventList = (List<SubeventDetails>) results.values;
          //  notifyDataSetChanged();
        }
    };
}


