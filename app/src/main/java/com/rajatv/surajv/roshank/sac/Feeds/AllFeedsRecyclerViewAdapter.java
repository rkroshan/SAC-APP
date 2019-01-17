package com.rajatv.surajv.roshank.sac.Feeds;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.rajatv.surajv.roshank.sac.Feeds.Notice.DownloadRecyclerViewAdapterNotice;
import com.rajatv.surajv.roshank.sac.OthersProfile.OthersProfile;
import com.rajatv.surajv.roshank.sac.TrendingFragment.BloggerFragment.BloggerAdapter;
import com.rajatv.surajv.roshank.sac.Feeds.Notice.NoticeRecyclerViewAdapter;
import com.rajatv.surajv.roshank.sac.Feeds.Result.ResultsRecyclerViewAdapter;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.TrendingFragment.TrendingAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllFeedsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<PostsModalClass> mlist = new ArrayList<>();
    private String useruid;


    final int photoView = 0, noticeView = 1, resultView = 2, bloggerView = 3, personalityView = 4;


    public AllFeedsRecyclerViewAdapter(Context mcontext, ArrayList<PostsModalClass> list) {
        context = mcontext;
        mlist = list;
        useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        final RecyclerView.ViewHolder holder;
        View view;
//        Log.e("viewType--", viewType + "");
        switch (viewType) {
            case photoView:
                view = LayoutInflater.from(context).inflate(R.layout.activity_photos_feeds, null);
                holder = new PhotosViewHolder(view);
                //Log.e("holder", "new PhotosViewHolder(view);");
                break;

            case noticeView:
                view = LayoutInflater.from(context).inflate(R.layout.element_notice_feeds, null);
                holder = new NoticeRecyclerViewAdapter.ViewHolder(view);
                //Log.e("holder", " new NoticeRecyclerViewAdapter.ViewHolder(view);");
                break;

            case resultView:
                view = LayoutInflater.from(context).inflate(R.layout.element_result_feeds, null);
                holder = new ResultsRecyclerViewAdapter.ViewHolder(view);
                //Log.e("holder", " new ResultsRecyclerViewAdapter.ViewHolder(view);");
                break;

            case bloggerView:
                view = LayoutInflater.from(context).inflate(R.layout.element_card_blogger, null);
                holder = new BloggerAdapter.ViewHolder1(view);
                //Log.e("holder", " new BloggerAdapter.ViewHolder1(view);");
                break;

            case personalityView:
                view = LayoutInflater.from(context).inflate(R.layout.element_card_trending, null);
                holder = new TrendingAdapter.ViewHolder1(view);
                //Log.e("holder", "new TrendingAdapter.ViewHolder1(view);");
                break;

            default:
                holder = null;
//                Log.e("holder", "null");
                break;
        }
        return holder;
    }


    @Override
    public int getItemViewType(int position) {
        int type = mlist.get(position).getTYPE();

        switch(type){
            case 0:return photoView;
            case 1: return noticeView;
            case 2: return  resultView;
            case 3: return  bloggerView;
            case 4: return  personalityView;
            default: return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int type = mlist.get(i).getTYPE();
       // Log.e("Type taken",mlist.get(i).toString());
        switch (type){
            case photoView:
                setPOSTS(mlist.get(i),(PhotosViewHolder) viewHolder,i);
                break;
            case noticeView:
                setNOTICE(mlist.get(i),(NoticeRecyclerViewAdapter.ViewHolder) viewHolder,i);
                break;
            case resultView:
                setRESULT(mlist.get(i),(ResultsRecyclerViewAdapter.ViewHolder) viewHolder,i);
                break;
            case personalityView:
                setPersonality(mlist.get(i),(TrendingAdapter.ViewHolder1) viewHolder,i);
                break;
            case bloggerView:
                setBlogger(mlist.get(i),(BloggerAdapter.ViewHolder1) viewHolder,i);
                break;
        }
    }

    private void setRESULT(PostsModalClass postsModalClass, ResultsRecyclerViewAdapter.ViewHolder viewHolder, int j) {
     //   Log.e("Result----",postsModalClass.toString());
        viewHolder.result_eventName.setText(postsModalClass.getPOSTS_EVENTNAME());
        viewHolder.result_subEventName.setText(postsModalClass.getPOSTS_SUBEVENTNAME());
        viewHolder.resultUploadTime.setText(getTimestamp(Long.parseLong(postsModalClass.getPOSTS_TIMESTAMP())));
        viewHolder.resultMessage.setText(postsModalClass.getPOSTS_CONTENT());
        viewHolder.resultUploaderName.setText(postsModalClass.getPOSTS_PUBLISHER_NAME());
        try {
            viewHolder.resultUploaderImage.setImageURI(postsModalClass.getPOSTS_PUBLISHER_USERIMAGEURL());
        }catch (Exception e){
            Log.e("resultUploaderImage",e.getMessage());
        }

        if(postsModalClass.getLIKEDBYUSER()==1){
            viewHolder.result_card_likesview.setImageResource(R.drawable.ic_excited_2);
        }else {
            viewHolder.result_card_likesview.setImageResource(R.drawable.ic_excited);

        }
        viewHolder.resultLikes.setText(postsModalClass.getPOSTS_LIKES());
        String USERUID = postsModalClass.getPOSTS_PUBLISHER_USERUID();
        String POSTUID = postsModalClass.getPOSTUID();
        int downloadItem = postsModalClass.getDOWNLOADITEM();

        if(downloadItem>0){
            viewHolder.divider.setVisibility(View.GONE);
            viewHolder.downloadBar.setVisibility(View.VISIBLE);
            DownloadRecyclerViewAdapterNotice downloadRecyclerViewAdapterNotice = new DownloadRecyclerViewAdapterNotice(context,postsModalClass.getDOWNLOADURIS());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayout.VERTICAL,true);
            viewHolder.downloadBar.setLayoutManager(layoutManager);
            viewHolder.downloadBar.setAdapter(downloadRecyclerViewAdapterNotice);
        }
        else{
            viewHolder.divider.setVisibility(View.VISIBLE);
            viewHolder.downloadBar.setVisibility(View.GONE);
        }

        viewHolder.resultUploaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });
        viewHolder.resultUploaderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });

        viewHolder.result_card_likesview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULT).child(POSTUID);
                DatabaseReference db_t = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(POSTUID);

                Drawable.ConstantState notlike = context.getDrawable(R.drawable.ic_excited).getConstantState();
                // Drawable.ConstantState like = context.getDrawable(R.drawable.ic_excited_2).getConstantState();
                if (viewHolder.result_card_likesview.getDrawable().getConstantState() == notlike) {

                    viewHolder.result_card_likesview.setImageResource(R.drawable.ic_excited_2);
                    changingLike(db,db_t, 1, useruid,j, USERUID);
                    mlist.get(j).setLIKEDBYUSER(1);
                    notifyDataSetChanged();
                } else {

                    viewHolder.result_card_likesview.setImageResource(R.drawable.ic_excited);
                    changingLike(db,db_t, -1, useruid,j, USERUID);
                    mlist.get(j).setLIKEDBYUSER(0);
                    notifyDataSetChanged();
                }

            }
        });

    }

    private void setNOTICE(PostsModalClass postsModalClass, NoticeRecyclerViewAdapter.ViewHolder viewHolder, int j) {
        viewHolder.eventName_Notice.setText(postsModalClass.getPOSTS_EVENTNAME());
        viewHolder.subEventName_notice.setText(postsModalClass.getPOSTS_SUBEVENTNAME());
        viewHolder.postTime_notice.setText(getTimestamp(Long.parseLong(postsModalClass.getPOSTS_TIMESTAMP())));
        viewHolder.content_notice.setText(postsModalClass.getPOSTS_CONTENT());
        viewHolder.username_notice.setText(postsModalClass.getPOSTS_PUBLISHER_NAME());
        try {
            viewHolder.profilePicture.setImageURI(postsModalClass.getPOSTS_PUBLISHER_USERIMAGEURL());
        }catch (Exception e){
            Log.e("Profile Picture",e.getMessage());
        }

        if(postsModalClass.getLIKEDBYUSER()==1){
            viewHolder.exited_Button.setImageResource(R.drawable.ic_excited_2);
        }else {
            viewHolder.exited_Button.setImageResource(R.drawable.ic_excited);

        }
        viewHolder.noOfLikes_notice.setText(postsModalClass.getPOSTS_LIKES());
        String USERUID = postsModalClass.getPOSTS_PUBLISHER_USERUID();
        String POSTUID = postsModalClass.getPOSTUID();
        int downloadItem = postsModalClass.getDOWNLOADITEM();

        if(downloadItem>0){
            viewHolder.divider.setVisibility(View.GONE);
            viewHolder.downloadBar.setVisibility(View.VISIBLE);
            DownloadRecyclerViewAdapterNotice downloadRecyclerViewAdapterNotice = new DownloadRecyclerViewAdapterNotice(context,postsModalClass.getDOWNLOADURIS());
            LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayout.VERTICAL,true);
            viewHolder.downloadBar.setLayoutManager(layoutManager);
            viewHolder.downloadBar.setAdapter(downloadRecyclerViewAdapterNotice);
        }
        else{
            viewHolder.divider.setVisibility(View.VISIBLE);
            viewHolder.downloadBar.setVisibility(View.GONE);
        }

        viewHolder.profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });
        viewHolder.username_notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });

        viewHolder.exited_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.NOTICE).child(POSTUID);
                DatabaseReference db_t = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(POSTUID);

                Drawable.ConstantState notlike = context.getDrawable(R.drawable.ic_excited).getConstantState();
                // Drawable.ConstantState like = context.getDrawable(R.drawable.ic_excited_2).getConstantState();
                if (viewHolder.exited_Button.getDrawable().getConstantState() == notlike) {

                    viewHolder.exited_Button.setImageResource(R.drawable.ic_excited_2);
                    changingLike(db,db_t, 1, useruid,j, USERUID);
                    mlist.get(j).setLIKEDBYUSER(1);
                    notifyDataSetChanged();
                } else {

                    viewHolder.exited_Button.setImageResource(R.drawable.ic_excited);
                    changingLike(db,db_t, -1, useruid,j, USERUID);
                    mlist.get(j).setLIKEDBYUSER(0);
                    notifyDataSetChanged();
                }

            }
        });


    }

    private void setPOSTS(PostsModalClass postsModalClass, PhotosViewHolder viewHolder, int i) {
        viewHolder.Post_Content.setText(postsModalClass.getPOSTS_CONTENT());
        viewHolder.Post_Likes.setText(postsModalClass.getPOSTS_LIKES());
        try {
            if(!postsModalClass.getPOSTS_PHOTOURL().equals("")) {
                viewHolder.feed_photo_main_photo.setVisibility(View.VISIBLE);
                viewHolder.feed_photo_main_photo.setImageURI(postsModalClass.getPOSTS_PHOTOURL());
            }else{
                viewHolder.feed_photo_main_photo.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Log.e("setPOSTS---",e.getMessage());
            viewHolder.feed_photo_main_photo.setVisibility(View.GONE);
        }
        viewHolder.Post_Timestamp.setText(getTimestamp(Long.parseLong(postsModalClass.getPOSTS_TIMESTAMP())));
        viewHolder.Post_SubEventName.setText(postsModalClass.getPOSTS_SUBEVENTNAME());
        viewHolder.Post_EventName.setText(postsModalClass.getPOSTS_EVENTNAME());
        viewHolder.Post_Publisher.setText(postsModalClass.getPOSTS_PUBLISHER_NAME());
        try{
            if (mlist.get(i).getPOSTS_PUBLISHER_USERUID().equals(useruid)) {
                viewHolder.delete.setVisibility(View.VISIBLE);
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.POSTS);
                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(mlist.get(i).getPOSTUID());
                        databaseReference.child(mlist.get(i).getPOSTS_PUBLISHER_USERUID()).removeValue();
                        db.setValue(null);

                    }
                });
            }
            else {
                viewHolder.delete.setVisibility(View.GONE);
            }
        }catch (Exception e){

        }
        try {
            viewHolder.feed_photos_profile_picture.setImageURI(postsModalClass.getPOSTS_PUBLISHER_USERIMAGEURL());
        }catch (Exception e){Log.e("setPost User photo",e.getMessage());}

        if(postsModalClass.getLIKEDBYUSER()==1){
            viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited_2);
        }else {
            viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited);

        }
        String USERUID = postsModalClass.getPOSTS_PUBLISHER_USERUID();
        String POSTUID = postsModalClass.getPOSTUID();

        viewHolder.feed_photos_profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });
        viewHolder.Post_Publisher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,USERUID);
            }
        });

        viewHolder.extended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.POSTS).child(POSTUID);
                DatabaseReference db_t = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE).child(POSTUID);

                Drawable.ConstantState notlike = context.getDrawable(R.drawable.ic_excited).getConstantState();
               // Drawable.ConstantState like = context.getDrawable(R.drawable.ic_excited_2).getConstantState();
                if (viewHolder.excitedImageButton.getDrawable().getConstantState() == notlike) {

                    viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited_2);
                    changingLike(db,db_t, 1, useruid,i,USERUID);
                    mlist.get(i).setLIKEDBYUSER(1);
                    notifyDataSetChanged();
                } else {

                    viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited);
                    changingLike(db,db_t, -1, useruid,i,USERUID);
                    mlist.get(i).setLIKEDBYUSER(0);
                    notifyDataSetChanged();
                }

            }
        });
    }

    private void setPersonality(PostsModalClass postsModalClass, TrendingAdapter.ViewHolder1 viewHolder,int j) {
        viewHolder.rankview.setText("#1");
        viewHolder.todaylikescount.setText(postsModalClass.getTODAYLIKESCOUNT());
        viewHolder.totallikecount.setText(postsModalClass.getTOTALLIKESCOUNT());
        viewHolder.username.setText(postsModalClass.getPOSTS_PUBLISHER_NAME());
        try {
            viewHolder.userprofileimage.setImageURI(postsModalClass.getPOSTS_PUBLISHER_USERIMAGEURL());
        }catch (Exception e){
            Log.e("Personality Image",e.getMessage());
        }
        viewHolder.userprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,postsModalClass.getPOSTS_PUBLISHER_USERUID());
            }
        });
        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,postsModalClass.getPOSTS_PUBLISHER_USERUID());
            }
        });
    }

    private void setBlogger(PostsModalClass postsModalClass, BloggerAdapter.ViewHolder1 viewHolder,int j) {
        viewHolder.rankview.setText("#1");
        viewHolder.username.setText(postsModalClass.getPOSTS_PUBLISHER_NAME());
        viewHolder.todaylikescount.setText(postsModalClass.getTODAYLIKESCOUNT());
        viewHolder.totallikecount.setText(postsModalClass.getTOTALLIKESCOUNT());
        try {
            viewHolder.userprofileimage.setImageURI(postsModalClass.getPOSTS_PUBLISHER_USERIMAGEURL());
        }catch (Exception e){
            Log.e("Blogger Image",e.getMessage());
        }

        viewHolder.userprofileimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,postsModalClass.getPOSTS_PUBLISHER_USERUID());
            }
        });
        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(context,postsModalClass.getPOSTS_PUBLISHER_USERUID());
            }
        });
    }

    @Override
    public int getItemCount() {
       // Log.e("mlist",mlist.size()+"");
        return mlist.size();
    }


    public class PhotosViewHolder extends RecyclerView.ViewHolder {

        public SimpleDraweeView feed_photos_profile_picture, feed_photo_main_photo;
        public ImageView excitedImageButton;
        public TextView Post_Publisher, Post_Timestamp, Post_Content, Post_Likes, Post_SubEventName, Post_EventName;
        public RelativeLayout extended,delete;

        public PhotosViewHolder(View v) {
            super(v);
            feed_photos_profile_picture = v.findViewById(R.id.feed_photos_profile_picture);
            feed_photo_main_photo = v.findViewById(R.id.feed_photo_main_photo);
            excitedImageButton = v.findViewById(R.id.excitedImageButton);
            Post_Publisher = v.findViewById(R.id.Post_Publisher);
            Post_Timestamp = v.findViewById(R.id.Post_Timestamp);
            Post_Content = v.findViewById(R.id.Post_Content);
            Post_Likes = v.findViewById(R.id.Post_Likes);
            Post_SubEventName = v.findViewById(R.id.Post_SubEventName);
            Post_EventName = v.findViewById(R.id.Post_EventName);
            extended = v.findViewById(R.id.extended_photo);
            delete = v.findViewById(R.id.delete_button_photo_new);

            feed_photo_main_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String mainimageuri = mlist.get(getAdapterPosition()).getPOSTS_PHOTOURL();
                    Dialog mdialog = new Dialog(context);
                    mdialog.setContentView(R.layout.pop_up_main_photo);
                    mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    SimpleDraweeView main_pic = mdialog.findViewById(R.id.mainphoto);
                    ImageView close=mdialog.findViewById(R.id.close_view);

                    main_pic.setImageURI(mainimageuri);
                    mdialog.show();

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mdialog.dismiss();
                        }
                    });



                }
            });

        }
    }

    public String getTimestamp(long timestamp) {

        String time = "";

        long difference = System.currentTimeMillis()-timestamp;

        double days = Math.floor(difference / 1000 / 60 / 60 / 24);
        difference -= days * 1000 * 60 * 60 * 24;

        double hours = Math.floor(difference / 1000 / 60 / 60);
        difference -= hours * 1000 * 60 * 60;

        double minutes = Math.floor(difference / 1000 / 60);
        difference -= minutes * 1000 * 60;

        double seconds = Math.floor(difference / 1000);

        if ((int) days > 1) {
            time += String.valueOf((int)days) + "days ";
        }
        else if((int) days>0){
            time += String.valueOf((int)days) + "day ";
        }
        else if ((int) hours > 1) {
            time += String.valueOf((int)hours) + "hrs ";
        }
        else if ((int) hours > 0) {
            time += String.valueOf((int)hours) + "hr ";
        }
        else if ((int) minutes > 1) {
            time += String.valueOf((int)minutes) + "mins ";
        }
        else if ((int) minutes > 0) {
            time += String.valueOf((int)minutes) + "min ";
        }
        else if ((int) seconds >= 0) {
            time += String.valueOf((int)seconds) + "secs ";
        }
        time += "ago";

        return time;
    }

    public  void goToDashBoard(Context context,String s) {
        Intent intent=new Intent(context,OthersProfile.class);
        intent.putExtra("userUID",s);
        context.startActivity(intent);
    }

    public void changingLike(DatabaseReference datref, DatabaseReference db_t, final int val, String USERUID, int adapterPosition, String publisher) {
        datref.child(StringVariable.NOTICE_LIKES).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    Log.e("mutableData", mutableData + "");
                    return Transaction.success(mutableData);
                } else {
                    //  Log.e("doTransaction--", mutableData.getKey());
                    //Log.e("POST LIKES---", mutableData.getValue().toString());
                    int count = Integer.valueOf(mutableData.getValue().toString());
                    count = (count) + val;
                    //Log.e("Post likes count---", count+"");

                    if(count<0){
                        count=0;
                    }

                    //setting new points in posts as per the given criterion in posts as well as timeline
                    mutableData.setValue(count);
                    db_t.child(StringVariable.NOTICE_LIKES).setValue(count);

                 //   Log.e("Data set --", count +"");
                    final String c = count+"";

                    if(val==1) {
                        //added the user who liked the post in both post and timeline
                        addUser(datref, USERUID,db_t);
                        //adding points in publisherUID
                        addPoint(publisher);
                    }else {
                        deleteuser(datref, USERUID,db_t);
                        deletepoint(publisher);
                    }
                    /*Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {

                            if(val==1){
                                mlist.get(adapterPosition).setLIKEDBYUSER(1);
                                Log.e("mlist inside runnable","changed "+adapterPosition);

                            }
                            else {
                                mlist.get(adapterPosition).setLIKEDBYUSER(0);
                                Log.e("mlist inside runnable","changed "+adapterPosition);

                            }
                            mlist.get(adapterPosition).setPOSTS_LIKES(c);
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
                   // Log.e("dataSnapshot", dataSnapshot.toString());
                }
            }
        });
    }

    private void deletepoint(String publisher){
        int point = -1;

        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.PERSONALITY).child(publisher);

        db_p.child(StringVariable.PERSONALITY_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue()==null) {
                    return Transaction.success(mutableData);
                }else{
                    int count =Integer.valueOf(mutableData.getValue().toString());
                    count = (count)+point;
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
        final int point = 1;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(publisher);
        //overall likes will be updated with trending list updation via cloud function
//adding user points in trending list
        //checking user exists or not
        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.PERSONALITY).child(publisher);

        db_p.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               // Log.e("adding points",String.valueOf(dataSnapshot.getRef())+":"+publisher);
                if(!dataSnapshot.exists()){
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String,Object> map = new HashMap<>();
                            map.put(StringVariable.USER_NAME,String.valueOf(dataSnapshot.child(StringVariable.USER_NAME).getValue()));
                            map.put(StringVariable.PERSONALITY_USERUID,String.valueOf(dataSnapshot.child(StringVariable.USER_USER_UID).getValue()));
                            map.put(StringVariable.PERSONALITY_PROFILE_PIC,String.valueOf(dataSnapshot.child(StringVariable.USER_IMAGE).getValue()));
                            map.put(StringVariable.PERSONALITY_CURRENTDAYLIKES,1);
                            map.put(StringVariable.PERSONALITY_OVERALLIKES,0);

                            db_p.setValue(map);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else{
                    db_p.child(StringVariable.PERSONALITY_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            if(mutableData.getValue()==null) {
                                return Transaction.success(mutableData);
                            }else{
                                int count = Integer.valueOf(mutableData.getValue().toString());
                                count = (count)+point;
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

}

