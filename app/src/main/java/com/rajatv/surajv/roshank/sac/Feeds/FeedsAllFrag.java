package com.rajatv.surajv.roshank.sac.Feeds;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FeedsAllFrag extends Fragment {
    private Dialog updatenow;
    private RecyclerView recyclerView;
    private String timelineType = "";
    private String timelinePostUID = "";
    private AllFeedsRecyclerViewAdapter recyclerViewAdapter;
    private String OldKey = "";
    private boolean userScrolled = false;
    private LinearLayoutManager linearLayout;


    private ArrayList<PostsModalClass> mlist = new ArrayList<>();
    int type = 0;


    private DatabaseReference databaseReference;
    String useruid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_all, container, false);


        recyclerView = view.findViewById(R.id.all_recycler_view);
        useruid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        try {
            settimeline();
        } catch (Exception e) {

        }
        recyclerViewAdapter = new AllFeedsRecyclerViewAdapter(getContext(), mlist);
        linearLayout = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }


    private void settimeline() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE);
        databaseReference.orderByChild(StringVariable.NOTICE_TIMESTAMP).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mlist.clear();
                if (dataSnapshot.exists() && dataSnapshot.hasChildren()) {
                    try {
                        // Log.e("Feed Frag --", dataSnapshot.toString());

                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            //Log.e("Frag", ds.getValue().toString());
                            OldKey = ds.getKey();
                            String Type = "";
                            Type = String.valueOf(ds.child(StringVariable.POST_TYPE).getValue());
                            switch (Type) {
                                case "0":
                                    addPost(ds);
                                    break;
                                case "1":
                                    addNotice(ds);
                                    break;
                                case "2":
                                    addResult(ds);
                                    break;
                                case "3":
                                    addBlogger(ds);
                                    break;
                                case "4":
                                    addPersonality(ds);
                                    break;
                                default:
                                    break;
                            }

                        }
                    } catch (Exception e) {
//                        Log.e("All frag---",e.getMessage());
                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

/*
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    userScrolled = true;

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = linearLayout.getChildCount();
                int totalItemCount = linearLayout.getItemCount();
                int pastVisiblesItems = linearLayout.findFirstVisibleItemPosition();

                // Now check if userScrolled is true and also check if
                // the item is end then update recycler view and set
                // userScrolled to false
                if (userScrolled && (visibleItemCount + pastVisiblesItems) == totalItemCount) {
                    userScrolled = false;

                    RefreshData(OldKey);
                }
            }
        });
*/
    }


    private void RefreshData(String oldKey) {

        databaseReference.orderByChild(StringVariable.NOTICE_TIMESTAMP).startAt(oldKey).limitToFirst(10).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Log.e("Feed Frag --","Data exist");
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
//                            Log.e("datasnapshot", ds.getKey());
                            OldKey = ds.getKey();
                            String Type = "";
                            Type = String.valueOf(ds.child(StringVariable.POST_TYPE).getValue());
                            switch (Type) {
                                case "0":
                                    addPost(ds);
                                    break;
                                case "1":
                                    addNotice(ds);
                                    break;
                                case "2":
                                    addResult(ds);
                                    break;
                                case "3":
                                    addBlogger(ds);
                                    break;
                                case "4":
                                    addPersonality(ds);
                                    break;
                            }

                        } catch (Exception e) {
//                            Log.e("All frag---",e.getMessage());
                        }

                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void addPersonality(DataSnapshot dataSnapshot) {

        mlist.add(0, new PostsModalClass(
                4,
                dataSnapshot.getKey(),
                null,
                null,
                null,
                null,
                null,
                null,
                String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY_PROFILE_PIC).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.USER_USER_UID).getValue()),
                null,
                0,
                String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY_OVERALLIKES).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY_CURRENTDAYLIKES).getValue()),
                0

        ));
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void addBlogger(DataSnapshot dataSnapshot) {
        mlist.add(0, new PostsModalClass(
                3,
                dataSnapshot.getKey(),
                null,
                null,
                null,
                null,
                null,
                null,
                String.valueOf(dataSnapshot.child(StringVariable.BLOGGER_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.BLOGGER_PROFILE_PIC).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.USER_USER_UID).getValue()),
                null,
                0,
                String.valueOf(dataSnapshot.child(StringVariable.BLOGGER_OVERALLIKES).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.BLOGGER_CURRENTDAYLIKES).getValue()),
                0
        ));
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void addResult(DataSnapshot dataSnapshot) {


        int liked = 0;

        for (DataSnapshot ds : dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildren()) {
//            Log.e("user1",ds.getKey());
            if (ds.getKey().equals(useruid)) {
//                Log.e("user",ds.getKey());
                liked = 1;
                break;
            }
        }

        int likes = 0;

        try {
            likes = (int) dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildrenCount();
            Log.e("Likes", likes + "");
        } catch (Exception e) {
            Log.e("Posts likes by", e.getMessage());
        }


        long downloadItem = 0;
        downloadItem = dataSnapshot.child(StringVariable.NOTICE_LINKS).getChildrenCount();
//        Log.e("download list--",downloadItem+"");

        ArrayList<String> downloadList = new ArrayList<>();


        for (int i = 0; i < downloadItem; i++) {
            downloadList.add(String.valueOf(dataSnapshot.child(StringVariable.NOTICE_LINKS).child(Integer.toString(i)).getValue()));
        }


        mlist.add(0, new PostsModalClass(
                2,
                dataSnapshot.getKey(),
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_DATA).getValue()),
                likes + "",
                null,
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_TIMESTAMP).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_SUB_EVENT_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_EVENT_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_POSTED_BY).child(StringVariable.NOTICE_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.RESULT_POSTED_BY).child(StringVariable.RESULT_USERIMAGEURL).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_POSTEDBY).child(StringVariable.POSTS_PUBLISHER_USERUID).getValue()),
                downloadList,
                (int) downloadItem,
                null,
                null,
                liked

        ));
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void addNotice(DataSnapshot dataSnapshot) {

        int liked = 0;

        for (DataSnapshot ds : dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildren()) {

            if (ds.getKey().equals(useruid)) {
                liked = 1;
                break;
            }
        }

        int likes = 0;

        try {
            likes = (int) dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildrenCount();
        } catch (Exception e) {
//            Log.e("Posts likes by",e.getMessage());
        }

        long downloadItem = 0;
        downloadItem = dataSnapshot.child(StringVariable.NOTICE_LINKS).getChildrenCount();
//        Log.e("download list--",downloadItem+"");

        ArrayList<String> downloadList = new ArrayList<>();


        for (int i = 0; i < downloadItem; i++) {
            downloadList.add(String.valueOf(dataSnapshot.child(StringVariable.NOTICE_LINKS).child(Integer.toString(i)).getValue()));
        }


        mlist.add(0, new PostsModalClass(
                1,
                dataSnapshot.getKey(),
               String.valueOf( dataSnapshot.child(StringVariable.NOTICE_CONTENT).getValue()),
                String.valueOf(likes),
                null,
                String.valueOf(dataSnapshot.child(StringVariable.NOTICE_TIMESTAMP).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.NOTICE_SUB_EVENT_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.NOTICE_EVENT_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.NOTICE_POSTED_BY).child(StringVariable.NOTICE_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.NOTICE_POSTED_BY).child(StringVariable.NOTICE_USER_IMAGE).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_POSTEDBY).child(StringVariable.POSTS_PUBLISHER_USERUID).getValue()),
                downloadList,
                (int) downloadItem,
                null,
                null,
                liked

        ));
        recyclerViewAdapter.notifyDataSetChanged();

    }

    private void addPost(DataSnapshot dataSnapshot) {
        int liked = 0;

        for (DataSnapshot ds : dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildren()) {
            if (ds.getKey().equals(useruid)) {
                liked = 1;
                break;
            }
        }

        int likes = 0;

        try {
            likes = (int) dataSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildrenCount();
        } catch (Exception e) {
            //  Log.e("Posts likes by",e.getMessage());
        }

        mlist.add(0, new PostsModalClass(
                0,
                dataSnapshot.getKey(),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_CONTENT).getValue()),
                String.valueOf(likes),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_PHOTOURL).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_TIMESTAMP).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_SUBEVENTNAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_EVENTNAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_POSTEDBY).child(StringVariable.POSTS_PUBLISHER_NAME).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_POSTEDBY).child(StringVariable.POSTS_PUBLISHER_USERIMAGEURL).getValue()),
                String.valueOf(dataSnapshot.child(StringVariable.POSTS_POSTEDBY).child(StringVariable.POSTS_PUBLISHER_USERUID).getValue()),
                null,
                0,
                null,
                null,
                liked

        ));
//        Log.e("mlist--",mlist.get(1).toString());
        recyclerViewAdapter.notifyDataSetChanged();
    }


}