package com.rajatv.surajv.roshank.sac.OthersProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.rajatv.surajv.roshank.sac.R;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class OthersProfile extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView back;
    Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_others_profile_tabs);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        tabLayout = (TabLayout) findViewById(R.id.othersProfile_tabLayout);
        viewPager = (ViewPager) findViewById(R.id.othersProfile_viewPager_id);
        back=(ImageView)findViewById(R.id.others_back);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new OthersProfile.pageAdapter(getSupportFragmentManager()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public class pageAdapter extends FragmentPagerAdapter {

        public pageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new OthersProfileFragment();
                case 1:
                    return new OthersBlogs();

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String[] title = {"Profile", "Blogs"};

            return title[position];
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

