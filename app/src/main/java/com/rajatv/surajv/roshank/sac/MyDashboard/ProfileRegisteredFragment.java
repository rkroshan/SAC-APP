package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileRegisteredFragment extends Fragment implements View.OnClickListener {

    private SimpleDraweeView profile_image;
    private TextView profile_name,tcfId_textView,college_name,Branch_name,rollno,year,phoneno,email,paying_status_textview,trending_b,trending_p,quoteTextView;
    private ImageView recheck_button;
    private Button digital_id_button;
    private RelativeLayout FOLLOW_STEPS_BOX,crownsBar;
    boolean rotated=false;
    private String key = "";
    private Snackbar mySnackbar;
    private ProgressDialog progressDialog;
    private RelativeLayout rel_b,rel_p;
    private DatabaseReference crowndb;
//    LinearLayout crownLayout;
//    private TextView totalCrowns;
    TextView totalCrowns1,totalCrowns2;
    LinearLayout crownLayout1;
    ImageView crownsImageView1,crownsImageView2;


    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.element_profile_paid, container, false);

        init(view);
        return view;
    }

    private void init(View v) {
        quoteTextView = v.findViewById(R.id.quoteTextView);
        trending_p = v.findViewById(R.id.trending_p);
        trending_b = v.findViewById(R.id.trending_b);
        rel_b = v.findViewById(R.id.rel1);
        rel_p = v.findViewById(R.id.rel2);
        profile_image = v.findViewById(R.id.profile_image);
        profile_name = v.findViewById(R.id.profile_name);
        tcfId_textView = v.findViewById(R.id.tcfId_textView);
        college_name = v.findViewById(R.id.college_name);
        Branch_name = v.findViewById(R.id.Branch_name);
        rollno = v.findViewById(R.id.rollno);
        year = v.findViewById(R.id.year);
        phoneno = v.findViewById(R.id.phoneno);
        email = v.findViewById(R.id.email);
        paying_status_textview = v.findViewById(R.id.paying_status_textview);
        recheck_button = v.findViewById(R.id.recheck_button);
        digital_id_button = v.findViewById(R.id.digital_id_button);
        FOLLOW_STEPS_BOX = v.findViewById(R.id.FOLLOW_STEPS_BOX);
        //crownsBar=v.findViewById(R.id.crownsBar);
//        crownLayout=v.findViewById(R.id.crownLayout);
//        totalCrowns=v.findViewById(R.id.totalCrowns);
        totalCrowns1=view.findViewById(R.id.totalCrowns1);
        crownLayout1=view.findViewById(R.id.crownLayout1);
        totalCrowns2=view.findViewById(R.id.totalCrowns2);
        // crownLayout2=view.findViewById(R.id.crownLayout2);
        crownsImageView1=view.findViewById(R.id.crownsImageView1);
        crownsImageView2=view.findViewById(R.id.crownsImageView2);


        getData();

        digital_id_button.setOnClickListener(this);
        recheck_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.digital_id_button:
                getActivity().startActivity(new Intent(getActivity(),TCF_ID_Activity.class));
                break;

            case R.id.recheck_button:
                getDataFromFirebase();
                animate(v);
        break;
        }

    }

    private void getData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
        //for future use
        key = obj.get(StringVariable.USER_USER_UID).toString();
        //getting into other data object where our data resides
        setData(obj);
    }

    private void setData(Map<String,Object> obj) {

        //paid status
        ArrayList<String> paid = null;
        try {
            paid = (ArrayList<String>) obj.get(StringVariable.USER_PACKAGES);
        }catch (Exception e){
//            Log.e("paying status--",e.getMessage());
        }

        if(paid != null){
            paying_status_textview.setText("Paid");
            paying_status_textview.setTextColor(ContextCompat.getColor(getActivity(),R.color.paid_color));
            FOLLOW_STEPS_BOX.setVisibility(View.GONE);

        }else {
            paying_status_textview.setText("Unpaid");
            paying_status_textview.setTextColor(ContextCompat.getColor(getActivity(),R.color.unpaid_color));
            FOLLOW_STEPS_BOX.setVisibility(View.VISIBLE);
//            crownsBar.setVisibility(View.GONE);
        }



        //set Image
        try{
            profile_image.setImageURI(obj.get(StringVariable.USER_IMAGE).toString());
        }catch (Exception e){
//            Log.e("Image Url ---", e.getMessage());
        }

        //name data
        String name = "--";
        try{
            name = obj.get(StringVariable.USER_NAME).toString();
        }catch (Exception e){
//            Log.e("Name--",e.getMessage());
        }

        profile_name.setText(name);

        //Branch Data
        String branch = "--";
        try{
            branch = obj.get(StringVariable.USER_BRANCH).toString();
        }catch (Exception e){
//            Log.e("Branch--",e.getMessage());
        }

        Branch_name.setText(branch);

        //Year Data
        String yr = "--";
        try {
            yr = obj.get(StringVariable.USER_YEAR).toString();
        }catch (Exception e){
//            Log.e("Year--",e.getMessage());
        }
        year.setText(yr);

        //College Data
        String college = "--";
        try{
            college = obj.get(StringVariable.USER_COLLEGE).toString();
        }catch (Exception e){
//            Log.e("College--",e.getMessage());
        }

        college_name.setText(college);

        //Roll no
        String roll = "--";
        try{
            roll = obj.get(StringVariable.USER_ROLLNO).toString();
        }catch (Exception e){
//            Log.e("Roll no--",e.getMessage());
        }

        rollno.setText(roll);

        //Email id
        String id = "--";
        try{
            id = obj.get(StringVariable.USER_email).toString();
        }catch (Exception e){
//            Log.e("Email ID",e.getMessage());
        }

        email.setText(id);


        //TCF ID
        String tcfID = null;
        try{
            tcfID = obj.get(StringVariable.USER_TCFID).toString();
            tcfId_textView.setText(tcfID);
            tcfId_textView.setTextColor(ContextCompat.getColor(getActivity(),R.color.paid_tcfcolor));
        }catch (Exception e){
//            Log.e("TCF ID",e.getMessage());
            tcfId_textView.setText("UNPAID");
            tcfId_textView.setTextColor(ContextCompat.getColor(getActivity(),R.color.unpaid_tcfcolor));
        }

        if(!(tcfID==null)){
            digital_id_button.setEnabled(true);
            digital_id_button.setTextColor(ContextCompat.getColor(getActivity(),R.color.white));
//            crownsBar.setVisibility(View.VISIBLE);
        }else{
            digital_id_button.setEnabled(false);
            digital_id_button.setTextColor(ContextCompat.getColor(getActivity(),R.color.fade_white));
        }


        String phone = "--";
        try{

            phone = obj.get(StringVariable.USER_PHONENUMBER).toString();
        }catch (Exception e){
//            Log.e("Phone no",e.getMessage());
        }

        phoneno.setText(phone);


        //getting data from App

            obj = (Map<String, Object>) obj.get(StringVariable.APP);
        try{
            //getting Trend data
            Map<String, Object> objTrend = (Map<String, Object>) obj.get(StringVariable.TRENDING_DATA);
            Map<String, Object> blog_trend = (Map<String, Object>) objTrend.get(StringVariable.BLOGGER);
            //trend
            String rend = "-";
            try {
                double trend = Double.parseDouble((blog_trend.get(StringVariable.USER_TREND).toString()));
                int i = (int) trend;
             //   trend = trend.split(".")[0];
                trending_b.setText("#"+i);

            } catch (Exception e) {
//                Log.e("Trend --", e.getMessage());
                trending_b.setText(rend);
            }


        }catch (Exception e){
//            Log.e("APP object",obj.toString());
            trending_b.setText("-");
        }

        try{
            //getting Trend data
            Map<String, Object> objTrend = (Map<String, Object>) obj.get(StringVariable.TRENDING_DATA);
            Map<String, Object> person_trend = (Map<String, Object>) objTrend.get(StringVariable.PERSONALITY);
            //trend
            try {
                 double trend = Double.parseDouble(person_trend.get(StringVariable.USER_TREND).toString());
               trending_p.setText("#"+(int) trend);
            } catch (Exception e) {
//                Log.e("Trend --", e.getMessage());
                trending_p.setText("-");
            }


        }catch (Exception e){
//            Log.e("APP object",obj.toString());
            rel_p.setVisibility(View.GONE);
        }

        try{
            String quote ;
            quote = obj.get(StringVariable.QUOTE).toString();
            quoteTextView.setText(quote);
        }catch (Exception e){
//            Log.e("quote--",e.getMessage());
            quoteTextView.setText("-");




        }

        try {
            crowndb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(StringVariable.APP).child(StringVariable.TRENDING_DATA);

        }
        catch (Exception e){}
        try{

            crowndb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Log.e("crowns",String.valueOf(dataSnapshot.getValue()));
                    if(String.valueOf(dataSnapshot.child(StringVariable.BLOGGER).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("null")
                            || String.valueOf(dataSnapshot.child(StringVariable.BLOGGER).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("0"))
                    {
                        if(String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("null")
                                || String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("0"))
                            crownLayout1.setVisibility(View.GONE);

                    }



                    if(String.valueOf(dataSnapshot.child(StringVariable.BLOGGER).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("null") || String.valueOf(dataSnapshot.child(StringVariable.BLOGGER).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("0"))
                    {
                        crownsImageView2.setVisibility(View.GONE);
                        totalCrowns2.setVisibility(View.GONE);


                    }
                    else
                    {
                        crownLayout1.setVisibility(View.VISIBLE);
                        totalCrowns2.setText("X "+String.valueOf(dataSnapshot.child(StringVariable.BLOGGER).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()));
                    }
                    if(String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("null") || String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()).equalsIgnoreCase("0"))
                    {
                        crownsImageView1.setVisibility(View.GONE);
                        totalCrowns1.setVisibility(View.GONE);

                    }
                    else
                    {
                        crownLayout1.setVisibility(View.VISIBLE);
                        totalCrowns1.setText("X "+String.valueOf(dataSnapshot.child(StringVariable.PERSONALITY).child(StringVariable.USER_TRENDING_OFTHEDAY).getValue()));
                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch (Exception e){}
//        crowndb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(StringVariable.APP).child("Crowns");
//        crowndb.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("crowns",String.valueOf(dataSnapshot.getValue()));
//                if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null") || String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0"))
//                {
//                    crownLayout.setVisibility(View.GONE);
//
//                }
//                else
//                {
//                    crownLayout.setVisibility(View.VISIBLE);
//                    totalCrowns.setText("X "+String.valueOf(dataSnapshot.getValue()));
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });



    }
private  void animate(View v) {
    if (rotated == false) {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(500);
        rotate.setInterpolator(new LinearInterpolator());
        recheck_button.startAnimation(rotate);
        rotate.setFillAfter(true);
        mySnackbar = Snackbar.make(v, "", Snackbar.LENGTH_SHORT);
        if (paying_status_textview.getText().toString().equalsIgnoreCase("paid")) {

            mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
            mySnackbar.getView().setBackgroundColor(Color.parseColor("#019746"));
            mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setText("Payment Successful! Now you can see digital ID card");

            mySnackbar.show();


            digital_id_button.setEnabled(true);
            digital_id_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));

//            crownsBar.setVisibility(View.VISIBLE);

        } else {
            mySnackbar.setActionTextColor(Color.parseColor("#ffffff"));
            mySnackbar.getView().setBackgroundColor(Color.parseColor("#af0000"));
            mySnackbar.getView().setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            TextView tv = (TextView) mySnackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextAlignment(v.TEXT_ALIGNMENT_CENTER);
            tv.setTypeface(Typeface.DEFAULT_BOLD);
            tv.setText("Please pay at registration desk & try again");
            mySnackbar.show();

            //   mySnackbar.dismiss();

            rotate.setFillAfter(true);

            digital_id_button.setEnabled(false);
            digital_id_button.setTextColor(ContextCompat.getColor(getActivity(), R.color.fade_white));
            tcfId_textView.setText("UNPAID");
            tcfId_textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.unpaid_tcfcolor));
        }
    } else {

       // mySnackbar.dismiss();
        rotated = false;
    }



}
    private void getDataFromFirebase(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Login activity", "USER EXIST");
                Map<String, Object> dt = (HashMap<String, Object>) dataSnapshot.getValue();
                String data = gson.toJson(dt);
                SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                prefsEditor.putString(StringVariable.UserData_Object_SharedPref, data);
                prefsEditor.apply();

//                Log.e("Data user---",dt.toString());
//                progressDialog.dismiss();

                getData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Data error---",databaseError.toString());
          //      progressDialog.dismiss();
                Toast.makeText(getActivity(),"Error Please ty again later",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
