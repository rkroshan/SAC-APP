package com.rajatv.surajv.roshank.sac.tcf2019;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.firebase.database.DatabaseReference;
import com.rajatv.surajv.roshank.sac.R;


public class FragmentEventDetailsActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private EventsDetailsViewPagerAdapter eventsDetailsViewPagerAdapter;
    Tracker mTracker;



    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        tabLayout=(TabLayout) findViewById(R.id.tabLayout_event_details);
        viewPager=(ViewPager)findViewById(R.id.viewPager_eventDetails);
        eventsDetailsViewPagerAdapter=new EventsDetailsViewPagerAdapter(getSupportFragmentManager());

        final String eventName = getIntent().getStringExtra("event_id");
        Bundle args = new Bundle();
        args.putString("event_name", eventName);
        SubeventDetailsFrag fragobj = new SubeventDetailsFrag();
        fragobj.setArguments(args);
        ContactsFrag fragobjcontact = new ContactsFrag();
        fragobjcontact.setArguments(args);

        //Fragments
        eventsDetailsViewPagerAdapter.addFragment(fragobj,"Sub-Events", args);
        eventsDetailsViewPagerAdapter.addFragment(fragobjcontact,"Contacts", args);




        viewPager.setAdapter(eventsDetailsViewPagerAdapter);

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

/* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_event_details,container,false);
        tabLayout=v.findViewById(R.id.tabLayout_event_details);
        viewPager=v.findViewById(R.id.viewPager_eventDetails);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        eventsDetailsViewPagerAdapter= new EventsDetailsViewPagerAdapter(getSupportFragmentManager());

        eventsDetailsViewPagerAdapter.addFragment(new SubeventDetailsFrag(),"Sub-Events");
        eventsDetailsViewPagerAdapter.addFragment(new ContactsFrag(),"Contacts");

        viewPager.setAdapter(eventsDetailsViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }*/
}