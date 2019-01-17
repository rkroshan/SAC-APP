package com.rajatv.surajv.roshank.sac.OthersProfile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.Blogssacapp.Blogs;
import com.rajatv.surajv.roshank.sac.Blogssacapp.BlogsRecyclerViewAdapter;
import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OthersBlogs extends Fragment {

    private RecyclerView recyclerView;
    private List<Blogs> myBlogsList;
    DatabaseReference blogsDatabase;
    BlogsRecyclerViewAdapter recyclerViewAdapter;
    private String userUID;
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_blog, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.blog_recycler_view);
        recyclerViewAdapter = new BlogsRecyclerViewAdapter(getContext(), myBlogsList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBlogsList = new ArrayList<>();
        blogsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS);
        try {
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
            userUID = getActivity().getIntent().getStringExtra("userUID");
        } catch (Exception e) {

        }
        try {
            blogsDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myBlogsList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        int liked = 0;
                        for (DataSnapshot ds : postSnapshot.child(StringVariable.BLOG_LIKES_BY).getChildren()) {
                            if (ds.getValue().toString().equals(userUID)) {
                                liked = 1;
                                break;
                            }
                        }

                        if (String.valueOf(postSnapshot.child("UserUID").getValue()).equals(userUID)) {
                            myBlogsList.add(0, new Blogs(
                                    liked,
                                    String.valueOf(postSnapshot.getKey()),
                                    String.valueOf(postSnapshot.child(StringVariable.BLOG_CONTENT).getValue()),
                                    String.valueOf(postSnapshot.child(StringVariable.BLOG_PUBLISHER_NAME).getValue()),
                                    String.valueOf(postSnapshot.child(StringVariable.BLOG_USERUID).getValue()),
                                    String.valueOf(postSnapshot.child(StringVariable.BLOG_LIKES).getValue()),
                                    FeedsResultFrag.getTimestamp(Long.valueOf(postSnapshot.child(StringVariable.BLOG_TIMEPSTAMP).getValue().toString())),
                                    String.valueOf(postSnapshot.child(StringVariable.BLOG_USER_IMAGEURL).getValue())));
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {
        }
    }
}
