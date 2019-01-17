package com.rajatv.surajv.roshank.sac.Blogssacapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MyDashboard.RegisterNowFragment;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.FunEvents.FunEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.SponsorFragment;
import com.rajatv.surajv.roshank.sac.tcf2019.TCF19HomeViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.TCFHomeFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents.TechnicalEventsFrag;

import java.util.HashMap;
import java.util.Map;

public class FragmentBlogsActivity extends Fragment {
    private ProgressBar spinner;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private BlogsViewPagerAdapter blogsViewPagerAdapter;
    private FloatingActionButton fb;
    private DatabaseReference dbProfile;
    private String currentUserUID;
    private Dialog mdialog;
    private SharedPreferences sharedPreferences;
    private String userUID = "", name = "", image = "", isProfileComplete = "";
    private ProgressDialog progressDialog;

    private Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_blogs, container, false);
        tabLayout = v.findViewById(R.id.tabLayout_blogs);
        viewPager = v.findViewById(R.id.viewPager_blogs);
        setHasOptionsMenu(false);
        fb = v.findViewById(R.id.write_blog_fab);
        mdialog = new Dialog(getActivity());
        mdialog.setContentView(R.layout.pop_up_blog);
        mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        mdialog.getWindow().setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Publishing...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        /*toolbar=(android.support.v7.widget.Toolbar)v.findViewById(R.id.appbar_blogs);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);*/

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentUserUID = "";
        try {

            currentUserUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
        }
        try {
            dbProfile = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(currentUserUID).child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED);
            dbProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("complete",dataSnapshot.toString());
                    Log.e("complete", String.valueOf(dataSnapshot.child(StringVariable.USER_IS_PROFILE_COMPLETED).getValue()));
                    if (String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0") ||
                            String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")) {
                        blogsViewPagerAdapter = new BlogsViewPagerAdapter(getChildFragmentManager());
                        blogsViewPagerAdapter.addFragment(new RegisterNowFragment(), "All");
                        blogsViewPagerAdapter.addFragment(new RegisterNowFragment(), "My Blogs");

                        viewPager.setAdapter(blogsViewPagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);


                    } else {
                        blogsViewPagerAdapter = new BlogsViewPagerAdapter(getChildFragmentManager());
                        blogsViewPagerAdapter.addFragment(new BlogsFrag(), "All");
                        blogsViewPagerAdapter.addFragment(new MyBlogs(), "My Blogs");

                        viewPager.setAdapter(blogsViewPagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
        }
//        blogsViewPagerAdapter=new BlogsViewPagerAdapter(getChildFragmentManager());
//        blogsViewPagerAdapter.addFragment(new BlogsFrag(),"All");
//        blogsViewPagerAdapter.addFragment(new MyBlogs(),"My Blogs");
//
//        viewPager.setAdapter(blogsViewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        Map<String, Object> app = (Map<String, Object>) obj.get("app");
        //   Log.e("xyz",app.toString());
        //for future use
        try {
            userUID = obj.get(StringVariable.USER_USER_UID).toString();
        } catch (Exception e) {
        }
        try {
            name = obj.get(StringVariable.USER_NAME).toString();
        } catch (Exception e) {
        }
        try {
            image = obj.get(StringVariable.USER_IMAGE).toString();
        } catch (Exception e) {
        }
        try {
            isProfileComplete = app.get(StringVariable.USER_IS_PROFILE_COMPLETED).toString();
        } catch (Exception e) {
        }

//        Log.e("profileCompleteeeee",isProfileComplete);
//        Log.e("profileCompleteeeee","");

        if (isProfileComplete.equalsIgnoreCase("1.0"))
            fb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TextView username = mdialog.findViewById(R.id.pop_up_blog_username);
                    username.setText(name);
                    final EditText blog = mdialog.findViewById(R.id.your_blog_here);
                    RelativeLayout close = mdialog.findViewById(R.id.close_relative_view);
                    Button publish = mdialog.findViewById(R.id.publish_btn);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mdialog.dismiss();
                        }
                    });

                    publish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String blogtext = blog.getText().toString();

                            if (!TextUtils.isEmpty(blogtext)) {
                                progressDialog.show();
                                DatabaseReference mDatabase;
                                mDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.BLOGS).push();
                                Map<String, Object> map = new HashMap<>();
                                map.put(StringVariable.BLOG_CONTENT, blogtext);
                                map.put(StringVariable.BLOG_PUBLISHER_NAME, name);
                                map.put(StringVariable.BLOG_TIMEPSTAMP, System.currentTimeMillis());
                                map.put(StringVariable.BLOG_USER_IMAGEURL, image);
                                map.put(StringVariable.BLOG_LIKES_BY, 0);
                                map.put(StringVariable.BLOG_LIKES, 0);
                                map.put(StringVariable.BLOG_USERUID, userUID);

                                mDatabase.setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Please wait", Toast.LENGTH_SHORT).show();
                                        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_BLOGS);
                                        db.push().setValue(mDatabase.getKey()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                progressDialog.dismiss();
                                                mdialog.dismiss();
                                            }
                                        });

                                    }
                                });
                                //dref.child("TimeStamp").setValue(time);

                            } else {
                                Toast.makeText(getContext(), "Please write Blog then publish", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    mdialog.show();

                }
            });
    }


}