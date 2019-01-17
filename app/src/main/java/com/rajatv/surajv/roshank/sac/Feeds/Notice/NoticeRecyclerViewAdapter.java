package com.rajatv.surajv.roshank.sac.Feeds.Notice;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

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
import java.util.Map;


public class NoticeRecyclerViewAdapter extends  RecyclerView.Adapter<NoticeRecyclerViewAdapter.ViewHolder> {

    private static Context context;
    List<Notice> noticeList;
    String[] data;
   static String file1,file2;
   String uid;

String url;
    // ImageView profile_pic;

     TextView username,likes,event,subevent,linkes;


    public NoticeRecyclerViewAdapter(Context context,List<Notice> noticeList) {

        this.context = context;
        this.noticeList=noticeList;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public  NoticeRecyclerViewAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.element_notice_feeds,viewGroup,false);
        ViewHolder holder= new ViewHolder(view);

        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.NOTICE);
        SharedPreferences sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
        String userUID = "";
        try {
             userUID = String.valueOf(obj.get(StringVariable.USER_USER_UID));
        }catch (Exception e){ }
        final String s = noticeList.get(i).getNoticeUID();
        Notice resultObject = noticeList.get(i);
        holder.profilePicture.setImageURI(resultObject.getUserImage());
        holder.username_notice.setText(resultObject.getPostedBy());
        holder.postTime_notice.setText(resultObject.getTimeStamp());
        holder.content_notice.setText(resultObject.getContent());
        holder.noOfLikes_notice.setText((resultObject.getLikes()));
        holder.eventName_Notice.setText(resultObject.getEventName());
        holder.subEventName_notice.setText(resultObject.getSubEventName());
        if(noticeList.get(i).getUserUID().equals(uid)){
            holder.delete.setVisibility(View.VISIBLE);
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(resultObject.getNoticeUID()).removeValue();
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(resultObject.getNoticeUID());
                    db.setValue(null);
                }
            });
        }
        else {
            holder.delete.setVisibility(View.GONE);
        }

        if(noticeList.get(i).getLiked()==1){
            holder.exited_Button.setImageResource(R.drawable.ic_excited_2);
        }else {
            holder.exited_Button.setImageResource(R.drawable.ic_excited);

        }

        holder.username_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(noticeList.get(i).getUserUID());
            }
        });

        holder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(noticeList.get(i).getUserUID());
            }
        });

        holder.extendedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.NOTICE).child(s);
                DatabaseReference db_t = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(s);

                Drawable.ConstantState notlike = context.getDrawable(R.drawable.ic_excited).getConstantState();
                Drawable.ConstantState like = context.getDrawable(R.drawable.ic_excited_2).getConstantState();

                if (holder.exited_Button.getDrawable().getConstantState() == notlike) {

                    holder.exited_Button.setImageResource(R.drawable.ic_excited_2);
                    changingLike(db, db_t,1,uid,i,noticeList.get(i).getUserUID());
                    noticeList.get(i).setLiked(1);
                    notifyDataSetChanged();

                } else {

                    holder.exited_Button.setImageResource(R.drawable.ic_excited);
                    changingLike(db, db_t,-1,uid,i,noticeList.get(i).getUserUID());
                    noticeList.get(i).setLiked(0);
                    notifyDataSetChanged();
                }
            }
        });

        if(resultObject.getDownloadItem()==0){
            holder.downloadBar.setVisibility(View.GONE);
            holder.divider.setVisibility(View.VISIBLE);
        }
        else {
            holder.downloadBar.setVisibility(View.VISIBLE);
            holder.divider.setVisibility(View.GONE);

            DownloadRecyclerViewAdapterNotice downloadRecyclerViewAdapterNotice = new DownloadRecyclerViewAdapterNotice(context,resultObject.getDownloadList());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayout.VERTICAL,true);
           // Collections.reverse(noticeList);
            holder.downloadBar.setLayoutManager(layoutManager);
            holder.downloadBar.setAdapter(downloadRecyclerViewAdapterNotice);
        }



    }

    @Override
    public int getItemCount() {
        return noticeList.size();
    }
   // public String getitem(int argo){

  //  }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public RecyclerView downloadBar;

        public SimpleDraweeView profilePicture;
        public ImageView exited_Button, download_icon;
        public TextView username_notice,postTime_notice,content_notice,fileNsme_notice,noOfLikes_notice,eventName_Notice,subEventName_notice,pdf_name;
        public View divider,delete;
        public RelativeLayout extendedButton;


        public ViewHolder(@NonNull View itemView)  {
            super(itemView);

            profilePicture=itemView.findViewById(R.id.feed_photos_profile_picture);
            username_notice=itemView.findViewById(R.id.username);
            postTime_notice=itemView.findViewById(R.id.time);
            content_notice=itemView.findViewById(R.id.eventdetalis);
            noOfLikes_notice=itemView.findViewById(R.id.numberOfLikes);
            eventName_Notice=itemView.findViewById(R.id.mainEvent);
            subEventName_notice=itemView.findViewById(R.id.nameOfsubevent);
            exited_Button=itemView.findViewById(R.id.exited_Button);
            extendedButton = itemView.findViewById(R.id.extended_notice);
            downloadBar = itemView.findViewById(R.id.recyclerViewDownloads);
            pdf_name = itemView.findViewById(R.id.pdfname);
            divider = itemView.findViewById(R.id.divider5);
            delete = itemView.findViewById(R.id.delete_button_notice);
        }
        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(),"Downloading File",Toast.LENGTH_SHORT).show();
            DownloadManager mManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            try {
                Uri uri = Uri.parse(fileNsme_notice.getText().toString());
           //     Log.e("StaticFile", fileNsme_notice.getText().toString());
                DownloadManager.Request mrequest = new DownloadManager.Request(uri);
                mrequest.setTitle(uri.getLastPathSegment());
                mrequest.setDescription("File Downloading...");
                mrequest.allowScanningByMediaScanner();
                mrequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                mrequest.setDestinationInExternalFilesDir(v.getContext(), Environment.DIRECTORY_DOWNLOADS + "/", "");
                mManager.enqueue(mrequest);
                Toast.makeText(context,"Downloading File",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void changingLike(DatabaseReference datref, DatabaseReference db_t, final int val, String USERUID, int adapterPosition, String publisher) {
        datref.child(StringVariable.NOTICE_LIKES).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                  //  Log.e("mutableData", mutableData + "");
                    return Transaction.success(mutableData);
                } else {
                    //  Log.e("doTransaction--", mutableData.getKey());
//                    Log.e("POST LIKES---", mutableData.getValue().toString());
                    int count = Integer.valueOf(mutableData.getValue().toString());
                    count = (count) + val;
//                    Log.e("Post likes count---", count + "");


                    if(count<0){
                        count=0;
                    }

                    //setting new points in posts as per the given criterion in posts as well as timeline
                    mutableData.setValue(count);
                    db_t.child(StringVariable.NOTICE_LIKES).setValue(count);

//                    Log.e("Data set --", count + "");
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
                   /* Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {

                            if (val == 1) {
                                noticeList.get(adapterPosition).setLiked(1);
                                Log.e("mlist inside runnable", "changed " + adapterPosition);

                            } else {
                                noticeList.get(adapterPosition).setLiked(0);
                                Log.e("mlist inside runnable", "changed " + adapterPosition);

                            }
                            noticeList.get(adapterPosition).setLikes(c);
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
//                    Log.e("databaseError", databaseError.toString());
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
//                Log.e("adding points", String.valueOf(dataSnapshot.getRef()) + ":" + publisher);
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





