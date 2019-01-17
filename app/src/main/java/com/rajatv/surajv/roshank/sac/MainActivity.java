package com.rajatv.surajv.roshank.sac;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.Feeds.FeedsAllFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Notice.FeedsNoticeFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Feeds_Home_ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Feeds_Home_ViewPagerAdapter feeds_home_viewPagerAdapter;
    Tracker mTracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager_id);
        feeds_home_viewPagerAdapter=new Feeds_Home_ViewPagerAdapter(getSupportFragmentManager());
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        //Fragments


        feeds_home_viewPagerAdapter.addFragment(new FeedsAllFrag(),"All");
        feeds_home_viewPagerAdapter.addFragment(new FeedsNoticeFrag(),"Notice");
        feeds_home_viewPagerAdapter.addFragment(new FeedsResultFrag(),"Results");
        //feeds_home_viewPagerAdapter.addFragment(new BlogsFragTeat(),"Blogs"); //Testing



        viewPager.setAdapter(feeds_home_viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        tabLayout.setupWithViewPager(viewPager);


}

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    }
}
