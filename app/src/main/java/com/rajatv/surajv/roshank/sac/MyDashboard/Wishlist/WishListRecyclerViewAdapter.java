package com.rajatv.surajv.roshank.sac.MyDashboard.Wishlist;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Description;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class WishListRecyclerViewAdapter extends RecyclerView.Adapter<WishListRecyclerViewAdapter.ViewHolder> {
    DatabaseReference wishlistdatabase;
    private Context context;
    List<SubeventDetails> WishList;
    private String userUID;
    DatabaseReference wishlistDatabase;

    private final String SUB_EVENT_NAME = "event name";
    private final String SUB_EVENT_VENUE1 = "event venue1";
    private final String SUB_EVENT_VENUE2 = "event venue2";
    private final String SUB_EVENT_TIME1 = "event time1";
    private final String SUB_EVENT_TIME2 = "event time2";
    private final String SUB_EVENT_DATE1 = "event date1";
    private final String SUB_EVENT_DATE2 = "event date2";
    private String wishlistString;
    Intent intent;


    public WishListRecyclerViewAdapter(Context context, List<SubeventDetails> wishlist) {
        this.context = context;
        this.WishList = wishlist;
//        Log.e("wishlist:day2",wishlist.get(0).getCurrentplace());
    }
    public WishListRecyclerViewAdapter(){}

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_details_subevents, viewGroup, false);
        WishListRecyclerViewAdapter.ViewHolder holder = new WishListRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String dateString="null";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh;mm;ss dd/MM/yyyy");
            dateString = formatter.format(new Date(Long.parseLong(WishList.get(i).getCurrenttime())));
        }
        catch(NumberFormatException n){

        }
        viewHolder.subeventname.setText(WishList.get(i).getSubeventname());
//        Log.e("Wishlist:adapter",WishList.get(i).toString());
//        Log.e("yyyyydt",dateString);
//        Log.e("yyyyypl",WishList.get(i).getCurrentplace());

        viewHolder.currenttime.setText(dateString);
        viewHolder.location.setText(WishList.get(i).getCurrentplace());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());

        viewHolder.ic_heart.setImageResource(R.drawable.ic_heart2);
        String eventId=WishList.get(i).getEventId();
//        Log.e("yyyyyid",eventId);



        viewHolder.ic_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
                    Gson gson = new Gson();
                    String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
                    Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());

                    userUID = String.valueOf(mainMap.get(StringVariable.USER_USER_UID));
//                    Log.e("WISHLIST:useruid", userUID);
                    wishlistDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_WISHLIST);

                    Map<String, Object> othersdata = (Map<String, Object>) mainMap.get(StringVariable.USER_OTHERDATA);
                    Map<String, Object> profiledata = (Map<String, Object>) othersdata.get(StringVariable.USER_PROFILE);

                    wishlistString = String.valueOf(profiledata.get(StringVariable.USER_WISHLIST));


                    viewHolder.ic_heart.setImageResource(R.drawable.ic_heart);

//                    wishlistDatabase.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for(DataSnapshot postsnapshot: dataSnapshot.getChildren())
//                            {
//                                if(String.valueOf(postsnapshot.getValue()).equalsIgnoreCase(eventId))
//                                {
//                                    wishlistDatabase.child(postsnapshot.getKey()).removeValue();
//                                    notifyDataSetChanged();
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });

                    if (wishlistString.equalsIgnoreCase("@arsundram@" + WishList.get(i).getEventId() + "@arsundram@")) {
                        wishlistString = "";
                    } else {
                        String[] temp = {"", ""};

                        temp = wishlistString.split(Pattern.quote("@arsundram@" + WishList.get(i).getEventId() + "@arsundram@"));

                        try {
                            wishlistString = temp[0] + temp[1];

                        } catch (ArrayIndexOutOfBoundsException e) {
                        }

                    }
//                                    Log.e("AMANAMAN",wishlistString);
                    profiledata.put(StringVariable.USER_WISHLIST, wishlistString);

                    othersdata.put(StringVariable.USER_PROFILE, profiledata);
                    mainMap.put(StringVariable.USER_OTHERDATA, othersdata);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    String data1 = gson.toJson(mainMap);
                    editor.putString(StringVariable.UserData_Object_SharedPref, data1);
                    editor.commit();
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE);

                    dbref.child(StringVariable.USER_WISHLIST).setValue(wishlistString);

                    notifyDataSetChanged();

                }catch (Exception e){

                }

            }
        });
        viewHolder.relativeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,Description.class);
                intent.putExtra(SUB_EVENT_NAME,WishList.get(i).getSubeventname());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
//        Log.e("WishlistSize:",String.valueOf(WishList.size()));
        return WishList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;




        private ImageView ic_time,ic_location,ic_addition,ic_heart;
        private TextView subeventname,currenttime,location,register_yourself,registration_open,added_to_wishlist;
        private RelativeLayout relativeCard;
        private View addToWishlistView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            recyclerView = (RecyclerView) itemView.findViewById(R.id.funevents_recyclerView);
            subeventname=itemView.findViewById(R.id.tv_subeventName);
            currenttime = itemView.findViewById(R.id.tv_event_time_suvevent);
            location = itemView.findViewById(R.id.tv_venue_subEvent);
            subeventname=itemView.findViewById(R.id.tv_subeventName);
            currenttime = itemView.findViewById(R.id.tv_event_time_suvevent);
            location = itemView.findViewById(R.id.tv_venue_subEvent);
            ic_time = itemView.findViewById(R.id.clocksymbol);
            ic_location = itemView.findViewById(R.id.locationsymbol);
            ic_addition = itemView.findViewById(R.id.additionsymbol);
            ic_heart = itemView.findViewById(R.id.heartsymbol);
            register_yourself = itemView.findViewById(R.id.registrationpath);
            registration_open = itemView.findViewById(R.id.tv_registrationOpen_subEvent);
            added_to_wishlist = itemView.findViewById(R.id.wishlist);
            addToWishlistView=itemView.findViewById(R.id.addToWishlistView);
            relativeCard=itemView.findViewById(R.id.relativecard);

        }
    }
}
