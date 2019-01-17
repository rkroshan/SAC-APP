package com.rajatv.surajv.roshank.sac.NavDrawerActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;



public class AboutNITPactivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView aboutnitp_back, aboutnitp_visitsite;
    Tracker mTracker;
    private static final String TAG = "RegisterActivity.java";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_about_nitpactivity);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        aboutnitp_back = (ImageView) findViewById(R.id.aboutnitp_back);
        aboutnitp_visitsite = (ImageView) findViewById(R.id.aboutnitp_visitsite);
        aboutnitp_back.setOnClickListener(this);
        aboutnitp_visitsite.setOnClickListener(this);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.aboutnitp_back:
                finish();
                break;
            case R.id.aboutnitp_visitsite:
                try{
                    Intent
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.nitp.ac.in"));
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(this,"Could not find the Link",Toast.LENGTH_SHORT).show();}
                break;
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
