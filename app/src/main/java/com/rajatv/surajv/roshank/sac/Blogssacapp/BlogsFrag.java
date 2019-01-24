package com.rajatv.surajv.roshank.sac.Blogssacapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class BlogsFrag  extends Fragment {

    private RecyclerView recyclerView;
    private List<Blogs> blogsList;
    private ArrayList<String> demolist;
    BlogsRecyclerViewAdapter recyclerViewAdapter;
    String UserUid = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_blog,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.blog_recycler_view);
        recyclerViewAdapter=new BlogsRecyclerViewAdapter(getContext(),blogsList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        blogsList=new ArrayList<>();
        DatabaseReference blogsDatabase;
        blogsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS);
try {
    blogsDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            blogsList.clear();
            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                int liked = 0;
                for (DataSnapshot ds : postSnapshot.child(StringVariable.BLOG_LIKES_BY).getChildren()) {
                    if (ds.getKey().equals(UserUid)) {
                        liked = 1;
                        break;
                    }
                }

                int likes = 0;
                try {
                    likes = (int) postSnapshot.child(StringVariable.BLOG_LIKES_BY).getChildrenCount();
                } catch (Exception e) {
                }
                Log.e("null",postSnapshot.getKey());
                blogsList.add(0, new Blogs(
                        liked,
                        String.valueOf(postSnapshot.getKey()),
                        String.valueOf(postSnapshot.child(StringVariable.BLOG_CONTENT).getValue()),
                        String.valueOf(postSnapshot.child(StringVariable.BLOG_PUBLISHER_NAME).getValue()),
                        String.valueOf(postSnapshot.child(StringVariable.BLOG_USERUID).getValue()),
                        likes + "",
                        FeedsResultFrag.getTimestamp(Long.parseLong(String.valueOf(postSnapshot.child(StringVariable.BLOG_TIMEPSTAMP).getValue()))),
                        String.valueOf(postSnapshot.child(StringVariable.BLOG_USER_IMAGEURL).getValue())));
            }
            recyclerViewAdapter.notifyDataSetChanged();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
}catch (Exception e){
    }
}}

