package com.rajatv.surajv.roshank.sac.Intramurals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ActivityEventFinder;
import com.rajatv.surajv.roshank.sac.tcf2019.FunEvents.FunEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.SponsorFragment;
import com.rajatv.surajv.roshank.sac.tcf2019.TCF19HomeViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.TCFHomeFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents.TechnicalEventsFrag;


public class FragmentIntramurals extends Fragment {


    private TabLayout tabLayout;
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

        tcfViewPagerAdapter = new TCF19HomeViewPagerAdapter(getChildFragmentManager());
        tcfViewPagerAdapter.addFragment(new TCFHomeFrag(), "Home");
        tcfViewPagerAdapter.addFragment(new IntramuralsGamesMenFragment(), "Men");
        tcfViewPagerAdapter.addFragment(new IntramuralsGamesWomenFragment(), "Women");
        tcfViewPagerAdapter.addFragment(new SponsorFragment(), "Sponsors");

        viewPager.setAdapter(tcfViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
//        toolbar=((AppCompatActivity)getActivity()).getSupportActionBar();
//        toolbar.setDisplayShowCustomEnabled(true);
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