package com.rajatv.surajv.roshank.sac.Blogssacapp;

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

public class MyBlogs extends Fragment {

    private RecyclerView recyclerView;
    private List<Blogs> myBlogsList;
    BlogsRecyclerViewAdapter recyclerViewAdapter;
    private String UserUid;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_blog,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.blog_recycler_view);
        recyclerViewAdapter=new BlogsRecyclerViewAdapter(getContext(),myBlogsList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //  layoutManager.setStackFromEnd(true);
  //
        //      layoutManager.setReverseLayout(true);
        //   Collections.reverse(blogsList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myBlogsList=new ArrayList<>();
        DatabaseReference blogsDatabase;
        blogsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS);

       /* SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
        try {
            UserUid = obj.get(StringVariable.USER_USER_UID).toString();
        }catch (Exception e){}*/


        blogsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myBlogsList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    if(String.valueOf(postSnapshot.child(StringVariable.BLOG_USERUID).getValue()).equals(UserUid)) {

                        int liked=0;
                        for(DataSnapshot ds:postSnapshot.child(StringVariable.BLOG_LIKES_BY).getChildren()){
                            //  Log.e("posttyuisanpshot---",ds.toString());
                            if(ds.getKey().equals(UserUid)){
                                liked=1;
                                break;
                            }
                        }

                        int likes = 0;
                        try {
                            likes = (int) postSnapshot.child(StringVariable.BLOG_LIKES_BY).getChildrenCount();
                        } catch (Exception e) {
                        }

                        myBlogsList.add(0,new Blogs(
                                liked,
                                String.valueOf(postSnapshot.getKey()),
                                String.valueOf(postSnapshot.child(StringVariable.BLOG_CONTENT).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.BLOG_PUBLISHER_NAME).getValue()),
                                UserUid,
                                likes+"",
                                FeedsResultFrag.getTimestamp(Long.parseLong(String.valueOf(postSnapshot.child(StringVariable.BLOG_TIMEPSTAMP).getValue()))),
                                String.valueOf(postSnapshot.child(StringVariable.BLOG_USER_IMAGEURL).getValue())));
                    }

                }
                recyclerViewAdapter.notifyDataSetChanged();


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
