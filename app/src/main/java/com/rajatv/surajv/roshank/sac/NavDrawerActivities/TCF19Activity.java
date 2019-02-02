package com.rajatv.surajv.roshank.sac.NavDrawerActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ActivityEventFinder;
import com.rajatv.surajv.roshank.sac.tcf2019.FunEvents.FunEventsFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.SponsorFragment;
import com.rajatv.surajv.roshank.sac.tcf2019.TCF19HomeViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.tcf2019.TCFHomeFrag;
import com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents.TechnicalEventsFrag;


public class TCF19Activity extends AppCompatActivity   {


    private TabLayout tabLayout;
    String currentUserUID;
    private ViewPager viewPager;
    private TCF19HomeViewPagerAdapter tcfViewPagerAdapter;
private Toolbar toolbar;
ImageView back,search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcf_activity);
        tabLayout =  findViewById(R.id.tcf_tabLayout);
        viewPager = findViewById(R.id.tcf_viewPager_id);
        back=findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
          search=findViewById(R.id.event_finder);
          search.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent eventfinderintent = new Intent(TCF19Activity.this,ActivityEventFinder.class);
                  startActivity(eventfinderintent);
              }
          });
        tcfViewPagerAdapter = new TCF19HomeViewPagerAdapter(getSupportFragmentManager());
        tcfViewPagerAdapter.addFragment(new TCFHomeFrag(), "Home");
        tcfViewPagerAdapter.addFragment(new TechnicalEventsFrag(), "Technical");
        tcfViewPagerAdapter.addFragment(new CulturalEventsFrag(), "Cultural");
        tcfViewPagerAdapter.addFragment(new FunEventsFrag(), "Fun Events"); //Testing
        tcfViewPagerAdapter.addFragment(new SponsorFragment(), "Sponsors");

        viewPager.setAdapter(tcfViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }






  /*  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.event_finder_menu,menu);

        MenuItem eventfindermenuitem = menu.findItem(R.id.eventfindermenu);
        eventfindermenuitem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()==R.id.eventfindermenu){

                    Intent eventfinderintent = new Intent(TCF19Activity.this,ActivityEventFinder.class);
                    startActivity(eventfinderintent);
                }
                return false;
            }
        });

    } */
}