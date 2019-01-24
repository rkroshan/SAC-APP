package com.rajatv.surajv.roshank.sac;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.Blogssacapp.FragmentBlogsActivity;
import com.rajatv.surajv.roshank.sac.MyDashboard.EditProfileActivity;
import com.rajatv.surajv.roshank.sac.NavDrawerActivities.AboutTCF;
import com.rajatv.surajv.roshank.sac.NavDrawerActivities.Intramurals2019;
import com.rajatv.surajv.roshank.sac.NavDrawerActivities.MapsActivity;
import com.rajatv.surajv.roshank.sac.Pings.PingRequestActivity;
import com.rajatv.surajv.roshank.sac.SASCouncil.SASCouncilActivity;
import com.rajatv.surajv.roshank.sac.SASCouncil.SASRegistration;
import com.rajatv.surajv.roshank.sac.SignUp_Login.GettingStarted;
import com.rajatv.surajv.roshank.sac.TrendingFragment.TrendingBloggerFragment;
import com.rajatv.surajv.roshank.sac.Feeds.SacFeeds.SacFeedsFragment;
import com.rajatv.surajv.roshank.sac.MyDashboard.FragmentMyDashboard;
import com.rajatv.surajv.roshank.sac.NavDrawerActivities.AboutNITPactivity;
import com.rajatv.surajv.roshank.sac.NavDrawerActivities.AboutSac;
import com.rajatv.surajv.roshank.sac.NitpClubs.NitpClubs;
import com.rajatv.surajv.roshank.sac.tcf2019.FragmentTCF19Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BottomNavigationActivity extends AppCompatActivity {
    Tracker mTracker;
    private Dialog updatenow;
    private android.support.v7.widget.Toolbar toolbar;
    private ActionBar action;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DrawerLayout mDrawerLayout;
    private DatabaseReference dbref2,dbProfile;
    FragmentManager fm;
    SacFeedsFragment sacFeedsFragment;
    FragmentBlogsActivity blogFragment;
    TrendingBloggerFragment trendingBloggerFragment;
   // RegisterNowFragment registerNowFragment;
    FragmentMyDashboard fragmentMyDashboard;
    FragmentTCF19Activity tcf19fragment;
    Fragment currentFragment;
    TextView titletext;
    TextView subtitle;
    ImageView toolbar_rightIcon,blueIcon;
    Typeface type2;
    BottomNavigationView navigation;
    Double actualVersion;
    DatabaseReference dbref,dbref1;
    String updateTitleString,updateDescriptionString;
    String profileComplete,currentUser;

    //private FirebaseAuth auth;
   // private FirebaseAuth.AuthStateListener authStateListener;
    ColorStateList ColorStateList1,ColorStateList2,ColorStateList3,ColorStateList4,ColorStateList5;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("BottomNavigationActivit", "Created");
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bottom_navigation);

        currentUser="";
        try{


            currentUser=FirebaseAuth.getInstance().getCurrentUser().getUid();


        }
        catch (Exception e){}


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.FORCE_UPDATE);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateTitleString = String.valueOf(dataSnapshot.child(StringVariable.FORCE_UPDATE_HEADING).getValue());
                updateDescriptionString = String.valueOf(dataSnapshot.child(StringVariable.FORCE_UPDATE_DESCRIPTION).getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        //Dialog box if force update
        updatenow = new Dialog(this);


//        String version_name=BuildConfig.VERSION_NAME;
        String version_name=BuildConfig.VERSION_NAME;
        Log.e("Version code",version_name);
        dbref = FirebaseDatabase.getInstance().getReference().child(StringVariable.FORCE_UPDATE).child(StringVariable.VERSION);
        try {
            dbref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.e("ACTUAL", dataSnapshot.toString());
                        String actualVersion = dataSnapshot.getValue().toString();
                        if (!version_name.equals(actualVersion)) {
                            firstDialogue();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){

        }

        Log.e("Version code",version_name);
        dbref1 = FirebaseDatabase.getInstance().getReference().child(StringVariable.FORCE_UPDATE);
        try {
            dbref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        Log.e("xyxyxy",dataSnapshot.toString());
                        Log.e("xyxyxy",String.valueOf(dataSnapshot.child("extra").getValue()));
                       if (String.valueOf(dataSnapshot.child("extra").getValue()).equalsIgnoreCase("1")){
                           updatenow.setContentView(R.layout.pop_up_update);
                           updatenow.setCancelable(false);
                           updatenow.setCanceledOnTouchOutside(false);
                           try {
                               updatenow.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                           }catch (Exception e){

                           }
                           updatenow.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
                           updatenow.show();
                           TextView yesbtn = updatenow.findViewById(R.id.yes_button);
                           TextView nobtn = updatenow.findViewById(R.id.no_button);
                           TextView updateDescription = updatenow.findViewById(R.id.update_line);
                           TextView updateTitle = updatenow.findViewById(R.id.new_dersion);

                           updateDescription.setText(updateDescriptionString);
                           updateTitle.setText(updateTitleString);
                           yesbtn.setText("OK");
                           nobtn.setVisibility(View.GONE);
                           updateTitle = findViewById(R.id.new_dersion);
                           yesbtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                  finish();
                               }
                           });
                           nobtn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   System.exit(0);
                               }
                           });
                       }
                        }
                    }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }catch (Exception e){

        }





        int[][] states = new int[][] {
                new int[] {-android.R.attr.state_checked}, // disabled
                new int[] { android.R.attr.state_checked}  // pressed

        };
        int[] colors2 = new int[] {
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#ff9315")

        };
        int[] colors4 = new int[] {
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#008ca5")

        };
        int[] colors3 = new int[] {
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#d60000")
        };
        int[] colors1 = new int[] {
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#008338")
        };
        int[] colors5 = new int[] {
                Color.parseColor("#b1b1b1"),
                Color.parseColor("#b70d9d")
        };
        ColorStateList1 = new ColorStateList(states, colors1);
        ColorStateList2 = new ColorStateList(states, colors2);
        ColorStateList3 = new ColorStateList(states, colors3);
        ColorStateList4 = new ColorStateList(states, colors4);
        ColorStateList5 = new ColorStateList(states, colors5);







        auth = FirebaseAuth.getInstance();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         titletext = findViewById(R.id.titletext);
         subtitle=findViewById(R.id.subtitle);
         toolbar_rightIcon=findViewById(R.id.toolbar_right_image);
         blueIcon=findViewById(R.id.blue_dot);
         blueIcon.setVisibility(View.GONE);
        type2 = Typeface.createFromAsset(getAssets(), "fonts/Chalkduster.ttf");
        toolbar_rightIcon.setBackgroundResource(R.drawable.ic_finger_color);
        toolbar_rightIcon.setVisibility(View.VISIBLE);

dbref2=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(StringVariable.USER_PING);
dbref2.addValueEventListener(new ValueEventListener() {
    String newNotification="false";
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds:dataSnapshot.getChildren()){
            if(String.valueOf(ds.getValue()).equalsIgnoreCase("0") && sacFeedsFragment.isInLayout()){
                blueIcon.setVisibility(View.VISIBLE);
                newNotification="true";
                break;
            }
        }
        if(newNotification.equalsIgnoreCase("false")){
            blueIcon.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});

        toolbar_rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Log.e("BottomNavigation","Open Ping Dialog");
                openPing();
                // Toast.makeText(BottomNavigationActivity.this,"Clicked",Toast.LENGTH_LONG).show();
            }
        });

       action = getSupportActionBar();
        titletext.setText("SAC Feeds");
        action.setDisplayHomeAsUpEnabled(true);
        action.setHomeAsUpIndicator(R.drawable.ic_menu);


        fm = getSupportFragmentManager();

         navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //loadFragment(new SacFeedsFragment());
        sacFeedsFragment = new SacFeedsFragment();
        fm.beginTransaction().add(R.id.frame_container, sacFeedsFragment, "1").commit();
        currentFragment = sacFeedsFragment;

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        int id=menuItem.getItemId();
                        if(id==R.id.sasCouncilMenu)
                        {
                           startActivity(new Intent(BottomNavigationActivity.this,SASCouncilActivity.class));
                        }
                        if (id == R.id.AboutNitp) {
                            startActivity(new Intent(BottomNavigationActivity.this, AboutNITPactivity.class));
                        }else if  (id == R.id.AboutSac) {
                            startActivity(new Intent(BottomNavigationActivity.this, AboutSac.class));
                        }else if  (id == R.id.nit_patna_clubs) {
                            startActivity(new Intent(BottomNavigationActivity.this, NitpClubs.class));
                        }else if (id==R.id.Intramurals_2019){
                            startActivity(new Intent(BottomNavigationActivity.this,Intramurals2019.class));
                        }else if  (id == R.id.About_tcf19) {
                            startActivity(new Intent(BottomNavigationActivity.this, AboutTCF.class));
                        }else if(id == R.id.nitp_maps){
                            startActivity(new Intent(BottomNavigationActivity.this,MapsActivity.class));
                        }else if(id==R.id.dev){
                            startActivity(new Intent(BottomNavigationActivity.this,Developer.class));
                        }else if(id ==R.id.sasfill){
                        startActivity(new Intent(BottomNavigationActivity.this,SASRegistration.class));
                        }
                        else if(id==R.id.logout){
                         FirebaseUser currentuser = auth.getCurrentUser();
                         if(currentuser!=null){
                             auth.signOut();

                         }
                        }


                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }

    /*private void updateSharedPrefs() {
        SharedPreferences sharedpreferences = getSharedPreferences("FORCEUPDATE", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();

        dbref=FirebaseDatabase.getInstance().getReference().child("FORCEUPDATE");

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    editor.putString(ds.getKey(),String.valueOf(ds.getValue()));
                }
                editor.commit();
                checkUpdate();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

//    private void checkUpdate() {
//        SharedPreferences sharedpreferences = getSharedPreferences("FORCEUPDATE", Context.MODE_PRIVATE);
//        Log.e("xxxx",sharedpreferences.getString("Version","0"));
//        if(versionCode<Float.parseFloat(sharedpreferences.getString("Version","0"))){
//
//            updatenow = new Dialog(this);
//            firstDialogue();
//        }
//    }


    private void firstDialogue() {
        updatenow.setContentView(R.layout.pop_up_update);
        updatenow.setCancelable(false);
        updatenow.setCanceledOnTouchOutside(false);
        try {
            updatenow.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }catch (Exception e){

        }
        updatenow.getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        updatenow.show();
        TextView yesbtn = updatenow.findViewById(R.id.yes_button);
        TextView nobtn = updatenow.findViewById(R.id.no_button);
        TextView updateDescription = updatenow.findViewById(R.id.update_line);
        TextView updateTitle = updatenow.findViewById(R.id.new_dersion);

        updateDescription.setText(updateDescriptionString);
        updateTitle.setText(updateTitleString);
        updateTitle = findViewById(R.id.new_dersion);
        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW,Uri.EMPTY.parse("https://play.google.com/store/apps/details?id=com.rajatv.surajv.roshank.sac"));
                startActivity(intent);
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_sacfeeds:

                    navigation.setItemIconTintList(ColorStateList1);
                    navigation.setItemTextColor(ColorStateList1);

                    titletext.setText("SAC Feeds");
                    toolbar_rightIcon.setBackgroundResource(R.drawable.ic_finger_color);
                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);

                    dbref2=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(StringVariable.USER_PING);
                    dbref2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                if(String.valueOf(ds.getValue()).equalsIgnoreCase("0")){
                                    blueIcon.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    toolbar_rightIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                            Log.e("BottomNavigation","Open Ping Dialog");
                            openPing();
                            // Toast.makeText(BottomNavigationActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                        }
                    });
                    subtitle.setVisibility(View.INVISIBLE);
                    if (sacFeedsFragment == null) {
                        Log.e("check", "1");
                        sacFeedsFragment = new SacFeedsFragment();
                        fm.beginTransaction().add(R.id.frame_container, sacFeedsFragment, "1").hide(currentFragment).show(sacFeedsFragment).commit();
                    } else {
                        Log.e("check", "2");
                        fm.beginTransaction().hide(currentFragment).show(sacFeedsFragment).commit();
                    }
                    currentFragment = sacFeedsFragment;
                    return true;
                case R.id.navigation_tcf_19:

                    navigation.setItemIconTintList(ColorStateList2);
                    navigation.setItemTextColor(ColorStateList2);

                    titletext.setText("TCF'19");
                    subtitle.setTypeface(type2);
                    subtitle.setVisibility(View.VISIBLE);
                    toolbar_rightIcon.setVisibility(View.GONE);
                    blueIcon.setVisibility(View.GONE);


                    if (tcf19fragment == null) {
                        Log.e("check", "1");
                        tcf19fragment = new FragmentTCF19Activity();
                        fm.beginTransaction().add(R.id.frame_container, tcf19fragment, "2").hide(currentFragment).show(tcf19fragment).commit();
                    } else {
                        Log.e("check", "2");
                        fm.beginTransaction().hide(currentFragment).show(tcf19fragment).commit();
                    }
                    currentFragment = tcf19fragment;
                    return true;
                case R.id.navigation_blogs:

                    navigation.setItemIconTintList(ColorStateList3);
                    navigation.setItemTextColor(ColorStateList3);

                    titletext.setText("Blogs");
                    toolbar_rightIcon.setVisibility(View.GONE);

                    subtitle.setVisibility(View.INVISIBLE);
                    blueIcon.setVisibility(View.GONE);

                    if (blogFragment == null) {
                        Log.e("check", "3");
                        blogFragment = new FragmentBlogsActivity();
                        fm.beginTransaction().add(R.id.frame_container, blogFragment, "3").hide(currentFragment).show(blogFragment).commit();
                    } else {
                        Log.e("check", "4");
                        fm.beginTransaction().hide(currentFragment).show(blogFragment).commit();
                    }
                    currentFragment = blogFragment;

                    return true;
                case R.id.navigation_trending:

                    navigation.setItemIconTintList(ColorStateList4);
                    navigation.setItemTextColor(ColorStateList4);

                    titletext.setText("#Trending");
                    subtitle.setVisibility(View.INVISIBLE);
                    toolbar_rightIcon.setVisibility(View.GONE);
                    blueIcon.setVisibility(View.GONE);


                    if (trendingBloggerFragment == null) {
                        Log.e("check", "5");
                        trendingBloggerFragment = new TrendingBloggerFragment();
                        fm.beginTransaction().add(R.id.frame_container, trendingBloggerFragment, "4").hide(currentFragment).show(trendingBloggerFragment).commit();
                    } else {
                        Log.e("check", "6");
                        fm.beginTransaction().hide(currentFragment).show(trendingBloggerFragment).commit();
                    }
                    currentFragment = trendingBloggerFragment;

                    return true;
                case R.id.navigation_dashboard:
                    navigation.setItemIconTintList(ColorStateList5);
                    navigation.setItemTextColor(ColorStateList5);
                            //Color.parseColor("#b70d9d"));

                    titletext.setText("My Dashboard");
                    subtitle.setVisibility(View.INVISIBLE);
                    toolbar_rightIcon.setBackgroundResource(R.drawable.ic_settings);
                    toolbar_rightIcon.setVisibility(View.VISIBLE);
                    blueIcon.setVisibility(View.GONE);


                    toolbar_rightIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v){
                         openDashboardSettings();
                           // Toast.makeText(BottomNavigationActivity.this,"Clicked",Toast.LENGTH_LONG).show();
                        }
                    });
                    if (fragmentMyDashboard == null) {
                        Log.e("check", "7");
                        fragmentMyDashboard = new FragmentMyDashboard();
                        fm.beginTransaction().add(R.id.frame_container, fragmentMyDashboard, "4").hide(currentFragment).show(fragmentMyDashboard).commit();
                    }
                    else {
                        Log.e("check", "8");
                        fm.beginTransaction().hide(currentFragment).show(fragmentMyDashboard).commit();
                    }
                    currentFragment=fragmentMyDashboard;

                    return true;

            }

            return false;
        }
    };

    private void openDashboardSettings() {
      //  DatabaseReference db=FirebaseDatabase.getInstance().getReference()......
        Intent intent=new Intent(getApplicationContext(),EditProfileActivity.class);
        startActivity(intent);
    }
    private void openPing() {
        //  DatabaseReference db=FirebaseDatabase.getInstance().getReference()......
        Intent intent=new Intent(getApplicationContext(),PingRequestActivity.class);
        startActivity(intent);
    }

    /*private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/


    private void backActivity() {
        startActivity(new Intent(getApplicationContext(), GettingStarted.class));
        finish();
    }

    @Override
    public void onStart() {

        Log.e("BottomNavigationActivit", "started");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    backActivity();
                    finish();
                }
                // ...
            }

        };

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        auth.addAuthStateListener(authStateListener);
        super.onStart();
        try {
            SharedPreferences sharedPreferences = this.getSharedPreferences("loadonce", Context.MODE_PRIVATE);
            int data = sharedPreferences.getInt("once", 0);
            if (data == 0) {
                //Not registered.
                navigation.setSelectedItemId(R.id.navigation_dashboard);
                navigation.findViewById(R.id.navigation_dashboard).performClick();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("once", 1);
                editor.apply();
            }
//            if (data == 1) {
//                //registered.
//                navigation.setSelectedItemId(R.id.navigation_sacfeeds);
//                navigation.findViewById(R.id.navigation_sacfeeds).performClick();
//            }

        }catch (Exception e){

        }
    }

    @Override
    public void onStop() {

        Log.e("BottomNavigationActivit", "stoped");
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }
}

//000AAAuserIdFake8587984980




