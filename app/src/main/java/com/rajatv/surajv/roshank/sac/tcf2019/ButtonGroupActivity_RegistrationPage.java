package com.rajatv.surajv.roshank.sac.tcf2019;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;


public class ButtonGroupActivity_RegistrationPage extends Activity implements View.OnClickListener{

    private Button[] btn = new Button[3];
    private Button btn_unfocus;
    Tracker mTracker;
    private int[] btn_id = {R.id.Male_button_registration_page, R.id.Female_button_registration_page, R.id.trans_button_registration_page};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackgroundColor(Color.rgb(0,23,29));
            btn[i].setOnClickListener(this);
        }

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        btn_unfocus = btn[0];
    }

    @Override
    public void onClick(View v) {
        //setForcus(btn_unfocus, (Button) findViewById(v.getId()));
        //Or use switch
        switch (v.getId()){
            case R.id.Male_button_registration_page :
                setFocus(btn_unfocus, btn[0]);
                break;

            case R.id.Female_button_registration_page :
                setFocus(btn_unfocus, btn[1]);
                break;

            case R.id.trans_button_registration_page :
                setFocus(btn_unfocus, btn[2]);
                break;

        }
    }

    private void setFocus(Button btn_unfocus, Button btn_focus){
        btn_focus.setBackgroundColor(Color.rgb(37,50,55));
        btn_unfocus.setBackgroundColor(Color.rgb(0,23,29));
        this.btn_unfocus = btn_focus;
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