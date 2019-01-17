package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByDateFragments.BydateFragment;
import com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByNameFragments.BynameFragment;
import com.rajatv.surajv.roshank.sac.ViewPagerAdapter;


public class ActivityEventFinder extends AppCompatActivity {

    Tracker mTracker;
    public TabLayout tablayout;
    public ViewPager viewPager;
    public ViewPagerAdapter viewPagerAdapter;
    Fragment fragment;
    private Toolbar toolbar;
    private ImageView back_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_viewpager_eventfinder);

        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.toolbar_dashboard);
        setSupportActionBar(actionBarToolBar);

       ActionBar action = getSupportActionBar();
        action.setDisplayShowTitleEnabled(false);

        //action.setDisplayHomeAsUpEnabled(true);
        //action.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_black_24dp);*/

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        tablayout = findViewById(R.id.eventfinder_tabLayout);
        viewPager = findViewById(R.id.eventfinder_view_pager_id);
        back_btn = findViewById(R.id.back_btn);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new BydateFragment(),"By Date");
        viewPagerAdapter.addFragment(new BynameFragment(),"By Name");

        viewPager.setAdapter(viewPagerAdapter);

        tablayout.setupWithViewPager(viewPager);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       // loadFragment();
    }

   /* private void loadFragment() {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.event_finder_frame, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }*/

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