package com.rajatv.surajv.roshank.sac.Feeds.Result;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.OthersProfile.OthersProfile;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class ResultsRecyclerViewAdapter extends  RecyclerView.Adapter<ResultsRecyclerViewAdapter.ViewHolder> {
    private Context context;
    List<Results> resultsList;
    private String uid;


    public ResultsRecyclerViewAdapter() {

    }

    public ResultsRecyclerViewAdapter(Context context, ArrayList<Results> resultsList) {
        this.context = context;
        this.resultsList = resultsList;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public RecyclerView downloadBar;
        public SimpleDraweeView resultUploaderImage;
        public TextView resultUploaderName, resultUploadTime, resultMessage, result_subEventName, result_eventName, resultLikes, pdf_name;
        public View divider, delete;
        public ImageView result_card_likesview;
        public RelativeLayout extended;


        public ViewHolder(@NonNull View v) {
            super(v);
            result_card_likesview = v.findViewById(R.id.result_card_likesview);
            resultUploaderImage = v.findViewById(R.id.result_card_imageview);
            resultUploaderName = v.findViewById(R.id.result_card_name);
            resultUploadTime = v.findViewById(R.id.result_card_timeview);
            resultMessage = v.findViewById(R.id.result_card_message);
            result_eventName = v.findViewById(R.id.event_name);
            result_subEventName = v.findViewById(R.id.result_card_subevent);
            resultLikes = v.findViewById(R.id.result_card_likes);
            pdf_name = v.findViewById(R.id.pdfname);
            downloadBar = v.findViewById(R.id.download_bar);
            divider = v.findViewById(R.id.divider2);
            delete = v.findViewById(R.id.delete_button_result);
            extended = v.findViewById(R.id.result_card_likesview1);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_result_feeds, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

       // Log.e("inside result--",resultsList.get(position).toString());

        Results resultObject = resultsList.get(position);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULT);
        final String s = resultsList.get(position).getResultUID();
        SharedPreferences sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        String userUID = "";
        try {
            userUID = obj.get(StringVariable.USER_USER_UID).toString();
        } catch (Exception e) {
        }


        holder.resultUploaderImage.setImageURI(resultObject.getResultUploaderImage());
        holder.resultUploaderName.setText(resultObject.getResultUploaderName());
        holder.resultUploadTime.setText(resultObject.getResultUploadTime());
        holder.resultMessage.setText(resultObject.getResultMessage());
        holder.result_eventName.setText(resultObject.getEventName());
        holder.result_subEventName.setText(resultObject.getSubEventName());
        holder.resultLikes.setText(resultObject.getResultLikes());

        if (resultObject.getUserUID().equals(uid)) {
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(resultObject.getResultUID()).removeValue();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(resultObject.getResultUID());
                    db.setValue(null);
                }
            });
        } else {
            holder.delete.setVisibility(View.GONE);
        }

        Log.e("inside result",resultsList.get(position).getLiked()+"");
        if (resultsList.get(position).getLiked() == 1) {
            holder.result_card_likesview.setImageResource(R.drawable.ic_excited_2);
        } else {
            holder.result_card_likesview.setImageResource(R.drawable.ic_excited);

        }

        if (resultObject.getDownloadItem() == 0) {
            holder.resultLikes.setText((resultObject.getResultLikes()));
            holder.downloadBar.setVisibility(View.GONE);
            holder.divider.setVisibility(View.VISIBLE);
        } else {
            holder.downloadBar.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);

            DownloadRecyclerViewAdapter downloadRecyclerViewAdapter = new DownloadRecyclerViewAdapter(context, resultObject.getDownloadList());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
            //Collections.reverse(resultsList);
            holder.downloadBar.setLayoutManager(layoutManager);
            holder.downloadBar.setAdapter(downloadRecyclerViewAdapter);

        }

        holder.resultUploaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(resultsList.get(position).getUserUID());
            }
        });

        holder.resultUploaderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(resultsList.get(position).getUserUID());
            }
        });
        holder.extended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULT).child(s);
                DatabaseReference db_t = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(s);

                // ChangingLikes(viewHolder,StringVariable.photoView,s);
                Drawable.ConstantState notlike = ContextCompat.getDrawable(context,R.drawable.ic_excited).getConstantState();
                if (holder.result_card_likesview.getDrawable().getConstantState() == notlike) {

                    holder.result_card_likesview.setImageResource(R.drawable.ic_excited_2);
                   // Log.e("Image showing", "not Like");
                    changingLike(db, db_t,1, uid,position, resultsList.get(position).getUserUID());
                    resultsList.get(position).setLiked(1);
                    notifyDataSetChanged();
                } else {

                    holder.result_card_likesview.setImageResource(R.drawable.ic_excited);
                    //Log.e("Image Showing", "Like");
                    changingLike(db, db_t,-1, uid, position,resultsList.get(position).getUserUID());
                    resultsList.get(position).setLiked(0);
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }

    public void changingLike(DatabaseReference datref, DatabaseReference db_t, final int val, String USERUID, int adapterPosition, String publisher) {
        datref.child(StringVariable.NOTICE_LIKES).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                 //   Log.e("mutableData", mutableData + "");
                    return Transaction.success(mutableData);
                } else {
                    //  Log.e("doTransaction--", mutableData.getKey());
                    //Log.e("POST LIKES---", mutableData.getValue().toString());
                    int count = Integer.valueOf(mutableData.getValue().toString());
                    count = (count) + val;
                    //Log.e("Post likes count---", count + "");


                    if(count<0){
                        count=0;
                    }

                    //setting new points in posts as per the given criterion in posts as well as timeline
                    mutableData.setValue(count);
                    db_t.child(StringVariable.NOTICE_LIKES).setValue(count);

                 //   Log.e("Data set --", count + "");
                    final String c = count + "";

                    if (val == 1) {
                        //added the user who liked the post in both post and timeline
                        addUser(datref, USERUID, db_t);
                        //adding points in publisherUID
                        addPoint(publisher);
                    } else {
                        deleteuser(datref, USERUID, db_t);
                        deletepoint(publisher);
                    }
                    /*Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {

                            if (val == 1) {
                                resultsList.get(adapterPosition).setLiked(1);
                                Log.e("mlist inside runnable", "changed " + adapterPosition);

                            } else {
                                resultsList.get(adapterPosition).setLiked(0);
                                Log.e("mlist inside runnable", "changed " + adapterPosition);

                            }
                            resultsList.get(adapterPosition).setResultLikes(c);
                            notifyDataSetChanged();

                        }
                    };
                    mainHandler.post(myRunnable);*/

                    return Transaction.success(mutableData);

                }
            }

            @Override
            public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                if (databaseError != null) {
                    //Log.e("databaseError", databaseError.toString());
                } else {
//                    Log.e("dataSnapshot", dataSnapshot.toString());
                }
            }
        });
    }

    private void deletepoint(String publisher) {
        int point = -1;

        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.PERSONALITY).child(publisher);

        db_p.child(StringVariable.PERSONALITY_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    return Transaction.success(mutableData);
                } else {
                    int count = Integer.valueOf(mutableData.getValue().toString());
                    count = (count) + point;
                    mutableData.setValue(count);
                    return Transaction.success(mutableData);
                }
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    private void addPoint(String publisher) {
        int point = 1;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(publisher);
        //overall likes will be updated with trending list updation via cloud function
//adding user points in trending list
        //checking user exists or not
        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.PERSONALITY).child(publisher);

        db_p.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Log.e("adding points", String.valueOf(dataSnapshot.getRef()) + ":" + publisher);
                if (!dataSnapshot.exists()) {
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(StringVariable.USER_NAME, String.valueOf(dataSnapshot.child(StringVariable.USER_NAME).getValue()));
                            map.put(StringVariable.PERSONALITY_USERUID, String.valueOf(dataSnapshot.child(StringVariable.USER_USER_UID).getValue()));
                            map.put(StringVariable.PERSONALITY_PROFILE_PIC, String.valueOf(dataSnapshot.child(StringVariable.USER_IMAGE).getValue()));
                            map.put(StringVariable.PERSONALITY_CURRENTDAYLIKES, 1);
                            map.put(StringVariable.PERSONALITY_OVERALLIKES, 0);

                            db_p.setValue(map);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    db_p.child(StringVariable.PERSONALITY_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            if (mutableData.getValue() == null) {
                                return Transaction.success(mutableData);
                            } else {
                                int count = Integer.valueOf(mutableData.getValue().toString());
                                count = (count) + point;
                                mutableData.setValue(count);
                                return Transaction.success(mutableData);
                            }
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteuser(DatabaseReference datref, String useruid, DatabaseReference db_t) {

        datref.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(null);
        db_t.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(null);
    }
    private void addUser(DatabaseReference datref, String useruid, DatabaseReference db_t) {
        datref.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(0);
        db_t.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(0);
    }
    public  void goToDashBoard(String s) {
        Intent intent=new Intent(context,OthersProfile.class);
        intent.putExtra("userUID",s);
        context.startActivity(intent);
        //TODO: Send to dashboard with useruid so that, that useruid person dashboard will open.
       // Log.e("Going to DashBoard","Nahi Jaunga");
    }
}