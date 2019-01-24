package com.rajatv.surajv.roshank.sac.Blogssacapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import java.util.Map;

public class BlogsRecyclerViewAdapter extends RecyclerView.Adapter<BlogsRecyclerViewAdapter.ViewHolder> {

    DatabaseReference blogsDatabase;
    private static Context context;
    List<Blogs> blogsList;
    String userUID;
    private SharedPreferences sharedPreferences;

    public BlogsRecyclerViewAdapter(Context context, List<Blogs> blogsList) {
        this.context = context;
        this.blogsList = blogsList;
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public BlogsRecyclerViewAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_blog_item, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final String s = blogsList.get(i).getBlogUID();

        sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
        try {
            userUID = obj.get(StringVariable.USER_USER_UID).toString();
        }catch (Exception e){}

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS);
        viewHolder.profilePhoto.setImageURI(blogsList.get(i).getUserImageURI());
        viewHolder.username.setText(blogsList.get(i).getPublisher());
        viewHolder.noOfLikes.setText(String.valueOf(blogsList.get(i).getLikes()));
        viewHolder.postTime.setText(blogsList.get(i).getTimeStamp());
        viewHolder.content.setText(blogsList.get(i).getContent());

        if(blogsList.get(i).getLiked()==1){
            viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited_2);
        }else{
            viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited);
        }

        if(blogsList.get(i).getUserUID().equals(userUID)){
            viewHolder.delete.setVisibility(View.VISIBLE);
            viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseReference.child(blogsList.get(i).BlogUID).removeValue();

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_BLOGS);
                    db.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                if(ds.getValue().toString().equals(s)){
                                    db.child(ds.getKey()).removeValue();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            });
        }
        else {
            viewHolder.delete.setVisibility(View.GONE);
        }

        viewHolder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(blogsList.get(i).getUserUID());
            }
        });

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDashBoard(blogsList.get(i).getUserUID());
            }
        });
        viewHolder.extended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ChangingLikes(viewHolder,StringVariable.photoView,s);
                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS).child(s);
                Drawable.ConstantState notlike = ContextCompat.getDrawable(context,R.drawable.ic_excited).getConstantState();
                if (viewHolder.excitedImageButton.getDrawable().getConstantState() == notlike) {

                    viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited_2);
                    changingLike(db, 1, userUID,i, blogsList.get(i).getUserUID());
                    blogsList.get(i).setLiked(1);
                    notifyDataSetChanged();
                } else {

                    viewHolder.excitedImageButton.setImageResource(R.drawable.ic_excited);
                    changingLike(db, -1, userUID,i, blogsList.get(i).getUserUID());
                    blogsList.get(i).setLiked(0);
                    notifyDataSetChanged();
                }

            }
        });

        }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public SimpleDraweeView profilePhoto;
        public ImageView excitedImageButton;
        public TextView username,content,noOfLikes,postTime;
        public View delete;
        public RelativeLayout extended;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            excitedImageButton = itemView.findViewById(R.id.excitedImageButton);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.blog_recycler_view);
            profilePhoto=itemView.findViewById(R.id.iv_profilePicture_blog_item);
            username=itemView.findViewById(R.id.tv_username_blog_item);
            postTime=itemView.findViewById(R.id.tv_postTime_blog_item);
            content=itemView.findViewById(R.id.tv_content_blog_item);
            noOfLikes=itemView.findViewById(R.id.tv_no_of_likes_blog_item);
            delete = itemView.findViewById(R.id.delete_button);
            extended = itemView.findViewById(R.id.extended_blog);

        }
    }

    @Override
    public int getItemCount() {
        return blogsList.size();
    }

    public void changingLike(DatabaseReference datref,  final int val, String USERUID, int adapterPosition, String publisher) {
        datref.child(StringVariable.BLOG_LIKES).runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    Log.e("mutableData", mutableData + "");
                    return Transaction.success(mutableData);
                } else {
                    //  Log.e("doTransaction--", mutableData.getKey());
                    Log.e("POST LIKES---", mutableData.getValue().toString());
                    int count = Integer.valueOf(mutableData.getValue().toString());

                    count = (count) + val;
                    Log.e("Post likes count---", count+"");

                    if(count<0){
                        count = 0;
                    }

                    //setting new points in posts as per the given criterion in posts as well as timeline
                    mutableData.setValue(count);

                    Log.e("Data set --", count +"");
                    final String c = count+"";

                    if(val==1) {
                        //added the user who liked the post in both post and timeline
                        addUser(datref, USERUID);
                        //adding points in publisherUID
                        addPoint(publisher);
                    }else {
                        deleteuser(datref, USERUID);
                        deletepoint(publisher);
                    }
                    /*Handler mainHandler = new Handler(context.getMainLooper());

                    Runnable myRunnable = new Runnable() {
                        @Override
                        public void run() {

                            if(val==1){
                                blogsList.get(adapterPosition).setLiked(1);
                                Log.e("mlist inside runnable","changed "+adapterPosition);

                            }
                            else {
                                blogsList.get(adapterPosition).setLiked(0);
                                Log.e("mlist inside runnable","changed "+adapterPosition);

                            }
                            blogsList.get(adapterPosition).setLikes(c);
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
                    Log.e("databaseError", databaseError.toString());
                } else {
                    Log.e("dataSnapshot", dataSnapshot.toString());
                }
            }
        });
    }

    private void deletepoint(String publisher){
        int point = -1;

        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.BLOGGER).child(publisher);

        db_p.child(StringVariable.BLOGGER_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
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
        int point = 1;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(publisher);
        //overall likes will be updated with trending list updation via cloud function
        //adding user points in trending list
        //checking user exists or not
        DatabaseReference db_p = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.BLOGGER).child(publisher);
try{
        db_p.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("adding points",String.valueOf(dataSnapshot.getRef())+":"+publisher);
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
                    db_p.child(StringVariable.BLOGGER_CURRENTDAYLIKES).runTransaction(new Transaction.Handler() {
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
    }catch (Exception e){
}
    }

    private void deleteuser(DatabaseReference datref, String useruid) {
        datref.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(null);
    }


    private void addUser(DatabaseReference datref, String useruid) {
        datref.child(StringVariable.NOTICE_LIKES_BY).child(useruid).setValue(0);
    }

    public  void goToDashBoard(String s) {
        Intent intent=new Intent(context,OthersProfile.class);
        intent.putExtra("userUID",s);
        context.startActivity(intent);
        //TODO: Send to dashboard with useruid so that, that useruid person dashboard will open.
        Log.e("Going to DashBoard","Nahi Jaunga");
    }
}
