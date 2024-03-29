package com.rajatv.surajv.roshank.sac.tcf2019;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.Feeds.FeedsAllFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Notice.FeedsNoticeFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.MyDashboard.RegisterNowFragment;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ActivityEventFinder;
import com.rajatv.surajv.roshank.sac.tcf2019.FunEvents.FunEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents.TechnicalEventsFrag;


public class FragmentTCF19Activity extends Fragment {


    private TabLayout tabLayout;
    private DatabaseReference dbProfile;
    String currentUserUID;
    private ViewPager viewPager;
    private TCF19HomeViewPagerAdapter tcfViewPagerAdapter;
private Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tcf_activity,container,false);
        tabLayout = v. findViewById(R.id.tcf_tabLayout);
        viewPager = v.findViewById(R.id.tcf_viewPager_id);
//      toolbar=(Toolbar)v.findViewById(R.id.appbartcf);

      //  ((AppCompatActivity)getActivity()).setContentView(toolbar);
     //`   ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
  //      View v1 = inflater.inflate(R.layout.app_bar_tcf,container,false);
//        toolbar.setCustomView(v1);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        currentUserUID="";
        try {
            currentUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch (Exception e){}
        try {
            dbProfile=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(currentUserUID).child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED);
            dbProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("complete",dataSnapshot.toString());
                    Log.e("complete",String.valueOf(dataSnapshot.getValue()));
                    if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0") ||
                            String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")){
                        tcfViewPagerAdapter = new TCF19HomeViewPagerAdapter(getChildFragmentManager());
                        tcfViewPagerAdapter.addFragment(new RegisterNowFragment(), "Home");
                        tcfViewPagerAdapter.addFragment(new RegisterNowFragment(), "Technical");
                        tcfViewPagerAdapter.addFragment(new RegisterNowFragment(), "Cultural");
                        tcfViewPagerAdapter.addFragment(new RegisterNowFragment(), "Fun Events"); //Testing
                        tcfViewPagerAdapter.addFragment(new SponsorFragment(), "Sponsors");
                        viewPager.setAdapter(tcfViewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);


                    }
                    else {
                        tcfViewPagerAdapter = new TCF19HomeViewPagerAdapter(getChildFragmentManager());
                        tcfViewPagerAdapter.addFragment(new TCFHomeFrag(), "Home");
                        tcfViewPagerAdapter.addFragment(new TechnicalEventsFrag(), "Technical");
                        tcfViewPagerAdapter.addFragment(new CulturalEventsFrag(), "Cultural");
                        tcfViewPagerAdapter.addFragment(new FunEventsFrag(), "Fun Events"); //Testing
                        tcfViewPagerAdapter.addFragment(new SponsorFragment(), "Sponsors");

                        viewPager.setAdapter(tcfViewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){}
//        tcfViewPagerAdapter = new TCF19HomeViewPagerAdapter(getChildFragmentManager());
//        tcfViewPagerAdapter.addFragment(new TCFHomeFrag(), "Home");
//        tcfViewPagerAdapter.addFragment(new TechnicalEventsFrag(), "Technical");
//        tcfViewPagerAdapter.addFragment(new CulturalEventsFrag(), "Cultural");
//        tcfViewPagerAdapter.addFragment(new FunEventsFrag(), "Fun Events"); //Testing
//        tcfViewPagerAdapter.addFragment(new SponsorFragment(), "Sponsors");
//
//        viewPager.setAdapter(tcfViewPagerAdapter);
//        tabLayout.setupWithViewPager(viewPager);
//        toolbar=((AppCompatActivity)getActivity()).getSupportActionBar();
 //       toolbar.setDisplayShowCustomEnabled(true);
//
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout=(TabLayout) findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager_id);
        tcf19HomeViewPagerAdapter=new TCF19HomeViewPagerAdapter(getSupportFragmentManager());

        //Fragments
        tcf19HomeViewPagerAdapter.addFragment(new TCFHomeFrag(),"Home");
        tcf19HomeViewPagerAdapter.addFragment(new SubeventDetailsFrag(),"Technical");
        tcf19HomeViewPagerAdapter.addFragment(new SubeventDetailsFrag(),"Cultural");
        tcf19HomeViewPagerAdapter.addFragment(new SubeventDetailsFrag(),"Fun Events"); //Testing
        tcf19HomeViewPagerAdapter.addFragment(new SubeventDetailsFrag(),"Sponsors");


        viewPager.setAdapter(tcf19HomeViewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);


    }



}
*/
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_finder_menu,menu);

        MenuItem eventfindermenuitem = menu.findItem(R.id.eventfindermenu);
        eventfindermenuitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.eventfindermenu){

                    Intent eventfinderintent = new Intent(getActivity(),ActivityEventFinder.class);
                    startActivity(eventfinderintent);
                }
                return false;
            }
        });

    }

}