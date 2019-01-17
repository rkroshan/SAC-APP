package com.rajatv.surajv.roshank.sac.TrendingFragment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.MyDashboard.RegisterNowFragment;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.TrendingFragment.BloggerFragment.BloggerFragment;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.ViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.FunEvents.FunEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.SponsorFragment;
import com.rajatv.surajv.roshank.sac.tcf2019.TCF19HomeViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.TCFHomeFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents.TechnicalEventsFrag;

public class TrendingBloggerFragment extends Fragment {

    public TabLayout tablayout;
    public ViewPager viewPager;
    public ViewPagerAdapter viewPagerAdapter;
    DatabaseReference dbProfile;
    String currentUserUID;
    private TrendingAdapter adapter;

    //@Override
   /* public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trending_blogger);

        tablayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.view_pager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new TrendingFragment(),"Personality");
        viewPagerAdapter.addFragment(new BloggerFragment(),"Blogger");

        //Log.e("Title list", "Title: ", );

        viewPager.setAdapter(viewPagerAdapter);

        tablayout.setupWithViewPager(viewPager);

    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_trending_blogger,container,false);
        tablayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.view_pager_id);
        setHasOptionsMenu(true);


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentUserUID="";
        currentUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        Log.e("currentuseruid",currentUserUID);

        try {
            dbProfile=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(currentUserUID).child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED);
            dbProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("complete",dataSnapshot.toString());
                    Log.e("complete",String.valueOf(dataSnapshot.getValue()));
                    if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0") ||
                            String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")){

                        viewPagerAdapter.addFragment(new RegisterNowFragment(),StringVariable.PERSONALITY);
                        viewPagerAdapter.addFragment(new RegisterNowFragment(),StringVariable.BLOGGER);

                        viewPager.setAdapter(viewPagerAdapter);

                        tablayout.setupWithViewPager(viewPager);

                    }
                    else {
                        viewPagerAdapter.addFragment(new TrendingFragment(),StringVariable.PERSONALITY);
                        viewPagerAdapter.addFragment(new BloggerFragment(),StringVariable.BLOGGER);

                        viewPager.setAdapter(viewPagerAdapter);

                        tablayout.setupWithViewPager(viewPager);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){}
//        viewPagerAdapter.addFragment(new TrendingFragment(),StringVariable.PERSONALITY);
//        viewPagerAdapter.addFragment(new BloggerFragment(),StringVariable.BLOGGER);

//        viewPagerAdapter.addFragment(new TrendingFragment(),StringVariable.PERSONALITY);
//        viewPagerAdapter.addFragment(new BloggerFragment(),StringVariable.BLOGGER);
//
        viewPager.setAdapter(viewPagerAdapter);

        tablayout.setupWithViewPager(viewPager);
    }


}