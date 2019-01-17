package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.Map;

public class TCF_ID_Activity extends AppCompatActivity {
//TextView muserName,mCollegeName,mTCFid;
    Tracker mTracker;
    private static final String TAG = "TCF_ID_Activity.java";

    private SimpleDraweeView userimage_digital_id;
    private TextView user_name_digital_id,college_digitalId,tcf_id;
    private TextView technical_events,cultural_events,pratibimb,parliamentary_debate,hackathon,robowar;
    private AppCompatImageView backButton;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_digital_id_card);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();

        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());


        init();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        userimage_digital_id = findViewById(R.id.userimage_digital_id);
        user_name_digital_id = findViewById(R.id.user_name_digital_id);
        college_digitalId = findViewById(R.id.college_digitalId);
        tcf_id = findViewById(R.id.tcf_id);
        technical_events = findViewById(R.id.technical_events);
        technical_events.setVisibility(View.GONE);
        cultural_events = findViewById(R.id.cultural_events);
        cultural_events.setVisibility(View.GONE);
        pratibimb = findViewById(R.id.pratibimb);
        pratibimb.setVisibility(View.GONE);
        parliamentary_debate = findViewById(R.id.parliamentary_debate);
        parliamentary_debate.setVisibility(View.GONE);
        hackathon = findViewById(R.id.hackathon);
        hackathon.setVisibility(View.GONE);
        robowar = findViewById(R.id.robowar);
        robowar.setVisibility(View.GONE);
        backButton=findViewById(R.id.close_id);

        getData();
    }

    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());

        setData(obj);
    }

    private void setData(Map<String,Object> obj) {

        String fee = null;
        try{
            fee = obj.get(StringVariable.USER_PACKAGES).toString();
//            Log.e("fee---",obj.toString());
        }catch (Exception e){
//            Log.e("Fee---",e.getMessage());
        }
        setPlan(fee);

        //getting name
        String name = "--";
        try{
            name = obj.get(StringVariable.USER_NAME).toString();
        }catch (Exception e){
//            Log.e("Name ---",e.getMessage());
        }
        user_name_digital_id.setText(name);

        //getting college
        String college = "--";
        try {
            college = obj.get(StringVariable.USER_COLLEGE).toString();
        }catch (Exception e){
//            Log.e("College---",e.getMessage());
        }
        college_digitalId.setText(college);

        //getting TCFID
        String tcfId = "---";
        try{
            tcfId = obj.get(StringVariable.USER_TCFID).toString();
        }catch (Exception e){
//            Log.e("TCF ID---",e.getMessage());
        }
        tcf_id.setText(tcfId);

        //getting image
        String Image = "---";
        try{
            Image = obj.get(StringVariable.USER_IMAGE).toString();
            userimage_digital_id.setImageURI(Image);
        }catch (Exception e){
//            Log.e("Image",e.getMessage());
        }
    }

    private void setPlan(String fee) {
        if(fee.toLowerCase().contains("technical")){
            technical_events.setVisibility(View.VISIBLE);
        }
        if(fee.toLowerCase().contains("cultural")){
            cultural_events.setVisibility(View.VISIBLE);
        }
        if(fee.toLowerCase().contains("pratibimb")){
            pratibimb.setVisibility(View.VISIBLE);
        }
        if(fee.toLowerCase().contains("parliamentary")){
            parliamentary_debate.setVisibility(View.VISIBLE);
        }
        if(fee.toLowerCase().contains("hackathon")){
            hackathon.setVisibility(View.VISIBLE);
        }
        if(fee.toLowerCase().contains("robowar")){
            robowar.setVisibility(View.VISIBLE);
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
