package com.rajatv.surajv.roshank.sac.NitpClubs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;


/**
 * Created by CREATOR on 11/26/2017.
 */

public class NitpClubs extends AppCompatActivity implements View.OnClickListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    Tracker mTracker;
    private static final String TAG = "NitpClubs.java";
    ImageView nitp_club_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setupWindowAnimations();
        setContentView(R.layout.nitp_clubs);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        tabLayout = (TabLayout)findViewById(R.id.nitp_clubs_tabLayout);
        viewPager = (ViewPager)findViewById(R.id.nitp_clubs_viewpager);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new pageAdapter(getSupportFragmentManager()));

        nitp_club_back = (ImageView) findViewById(R.id.nitp_club_details_back);
        nitp_club_back.setOnClickListener(this);

    }

    public class pageAdapter extends FragmentPagerAdapter {

        public pageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NitpClubsFragment();
                case 1:
                   return new NitpClubEventsFragment();

                default:return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"Club Info","Club Events"};

            return title[position];
        }
    }



    @Override
    public void onClick(View view) {
        finish();
    }

    private void setupWindowAnimations() {
        Fade fade = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            fade = new Fade();
            fade.setDuration(1000);
            Log.e("Dangal","AnimationStarts");
            getWindow().setEnterTransition(fade);
            //Log.e("Dangal","Animationends");
            //getWindow().setExitTransition(fade);
        }
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
