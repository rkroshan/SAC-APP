package com.rajatv.surajv.roshank.sac.Feeds.Notice;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedsNoticeFrag extends Fragment {
    private RecyclerView recyclerView;
    private List <Notice> listNotice;
    DatabaseReference noticeDatabase,databaseDownload;
    NoticeRecyclerViewAdapter noticeRecyclerViewAdapter;
    private LinearLayoutManager layoutManager;
    String uid ;

    public FeedsNoticeFrag(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_notices,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.notices_recycler_view);
        noticeRecyclerViewAdapter=new NoticeRecyclerViewAdapter(getContext(),listNotice);
        final FragmentActivity f = getActivity();
        layoutManager= new LinearLayoutManager(f);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(noticeRecyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listNotice=new ArrayList<>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        noticeDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.NOTICE);
        DatabaseReference rootref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference imageref = rootref.child("Notice");

        noticeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    listNotice.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        int liked = 0;

                        for (DataSnapshot ds : postSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildren()) {
                            if (ds.getKey().equals(uid)) {
                                liked = 1;
                                break;
                            }
                        }


                        int likes = 0;

                        try {
                            likes = (int) postSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildrenCount();
                        } catch (Exception e) {
                        //    Log.e("Posts likes by", e.getMessage());
                        }

                        long downloadItem = postSnapshot.child("Links").getChildrenCount();
                        ArrayList<String> downloadList = new ArrayList<>();
                        for (int i = 0; i < downloadItem; i++) {
                            downloadList.add(String.valueOf(postSnapshot.child("Links").child(Integer.toString(i)).getValue()));
                        }
                        listNotice.add(0, new Notice(
                                liked,
                                String.valueOf(postSnapshot.getKey()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_CONTENT).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_EVENT_NAME).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_HEADING).getValue()),
                                likes + "",
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_POSTED_BY).child(StringVariable.NOTICE_USER_IMAGE).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_POSTED_BY).child(StringVariable.NOTICE_NAME).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_SUB_EVENT_NAME).getValue()),
                                FeedsResultFrag.getTimestamp(Long.parseLong(String.valueOf(postSnapshot.child(StringVariable.NOTICE_TIMESTAMP).getValue()))),
                                // String.valueOf(getTimestamp(Long.parseLong(postSnapshot.child(StringVariable.NOTICE_TIMESTAMP).getValue().toString()))),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_LIKES_BY).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.NOTICE_POSTED_BY).child(StringVariable.NOTICE_USER_UID).getValue()),
                                downloadList, (int) downloadItem));
                        noticeRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }catch (Exception e){
        //            Log.e("Notice frag",e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                noticeRecyclerViewAdapter.notifyDataSetChanged();
            }
        });


    }
}