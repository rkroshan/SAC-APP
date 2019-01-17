package com.rajatv.surajv.roshank.sac.SASCouncil;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.MyDashboard.MyDashboardViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASAppAndMediaFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASCulturalAndArtsFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASDesigningFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASDisciplineFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASEditorialFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASPRandMediaFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASSportsAndGamesFragment;
import com.rajatv.surajv.roshank.sac.SASCouncil.Fragments.SASallFragment;

public class SASCouncilActivity extends AppCompatActivity implements View.OnClickListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyDashboardViewPagerAdapter adapter;
    Toolbar toolbar;
    ImageView back;
    Tracker mTracker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_sas_council);
        tabLayout = findViewById(R.id.SASCouncil_tabLayout);
        viewPager = findViewById(R.id.SASCouncil_viewPager_id);
        adapter=new MyDashboardViewPagerAdapter(getSupportFragmentManager());

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.appbar_sascouncil);
        setSupportActionBar(toolbar);
        back=(ImageView)findViewById(R.id.sascouncilback);
        back.setOnClickListener(this);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        adapter.addFragment(new SASallFragment(), "All");
        adapter.addFragment(new SASAppAndMediaFragment(), "App & Web");
        adapter.addFragment(new SASDesigningFragment(), "Designing");
        adapter.addFragment(new SASPRandMediaFragment(), "PR & Media");
        adapter.addFragment(new SASEditorialFragment(), "Editorial");
        adapter.addFragment(new SASSportsAndGamesFragment(), "Sports & Games");
        adapter.addFragment(new SASCulturalAndArtsFragment(), "Cultural & Arts");
        adapter.addFragment(new SASDisciplineFragment(), "Discipline");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onClick(View v) {
        finish();
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