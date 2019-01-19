package com.rajatv.surajv.roshank.sac.OthersProfile;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OthersProfileFragment extends Fragment {

    private String userUID;
    DatabaseReference dbref,mydb,crowndb;
    TextView mUsername;
    SimpleDraweeView mUserImage;
    TextView mTcfID;
    TextView mQuote;
    TextView mCollege;
    TextView mBranch;
    TextView mRollNo;
    TextView mYear;
    TextView trend_p,trend_b;
    String him_her,ping;
    TextView pingHim;
    RelativeLayout pingButton;
    ImageView whatsAppButton,mailButton,callButton;
    private String myUserUID;
   // private String pinged;
    private ImageView fingerImage;
    TextView mPhone,mEmail;
    TextView mstaticText1,mstaticText2,mayKnow;
    TextView totalCrowns1,totalCrowns2;
    LinearLayout crownLayout1;
    ImageView crownsImageView1,crownsImageView2;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.others_profile_non_ping,container,false);

        mUserImage=view.findViewById(R.id.others_profile_image);
        mUsername=view.findViewById(R.id.others_profile_name);
        mTcfID=view.findViewById(R.id.others_profile_tcf_id);
        mQuote=view.findViewById(R.id.others_profile_quoteTextView);
        mCollege=view.findViewById(R.id.others_profile_college);
        mBranch=view.findViewById(R.id.others_profile_branch);
        mRollNo=view.findViewById(R.id.others_profile_roll);
        mYear=view.findViewById(R.id.others_profile_year);
        trend_p = view.findViewById(R.id.trend_p);
        trend_b = view.findViewById(R.id.trend_b);
        pingHim=view.findViewById(R.id.pingHim);
        pingButton=view.findViewById(R.id.pingButton_others_profile);
        fingerImage=view.findViewById(R.id.fingerImage);
        mPhone=view.findViewById(R.id.others_profile_phone);
        mEmail=view.findViewById(R.id.others_profile_email);
        mstaticText1=view.findViewById(R.id.others_profile_staticText1);
        mstaticText2=view.findViewById(R.id.others_profile_staticText2);
        mayKnow=view.findViewById(R.id.mayKnow);
        whatsAppButton=view.findViewById(R.id.whatsAppButton);
        mailButton=view.findViewById(R.id.mailButton);
        callButton=view.findViewById(R.id.callButton);
        totalCrowns1=view.findViewById(R.id.totalCrowns1);
        crownLayout1=view.findViewById(R.id.crownLayout1);
        totalCrowns2=view.findViewById(R.id.totalCrowns2);
       // crownLayout2=view.findViewById(R.id.crownLayout2);
        crownsImageView1=view.findViewById(R.id.crownsImageView1);
        crownsImageView2=view.findViewById(R.id.crownsImageView2);


        mstaticText1.setVisibility(View.GONE);
        mstaticText2.setVisibility(View.GONE);
        mPhone.setVisibility(View.GONE);
        mEmail.setVisibility(View.GONE);
        whatsAppButton.setVisibility(View.GONE);
        mailButton.setVisibility(View.GONE);
        callButton.setVisibility(View.GONE);

        userUID=getActivity().getIntent().getStringExtra("userUID");
//        Log.e("USERIT",userUID);
        myUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbref=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID);


        try {
            mailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + mEmail.getText().toString()));
                        intent.putExtra(Intent.EXTRA_SUBJECT, "");
                        intent.putExtra(Intent.EXTRA_TEXT, "");
                        startActivity(intent);
                    }catch(ActivityNotFoundException e){
                        //TODO smth
                    }
                }
            });

        }
        catch (Exception e){}


        try {
            callButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + mPhone.getText().toString()));
                    v.getContext().startActivity(callIntent);
                }
            });

        }
        catch (Exception e){}


try {
    whatsAppButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PackageManager packageManager = getContext().getPackageManager();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            try{
                String url = "https://api.whatsapp.com/send?phone=91"+mPhone.getText().toString();

                intent.setPackage("com.whatsapp");
                intent.setData(Uri.parse(url));
                if(intent.resolveActivity(packageManager)!=null){
                    startActivity(intent);
                }
            }catch (Exception e){e.printStackTrace();}
        }
    });

}
catch (Exception e){}

        mydb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_PING);
        pingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pingHim.getText().toString().equalsIgnoreCase("ping him") || pingHim.getText().toString().equalsIgnoreCase("Ping her") || pingHim.getText().toString().equalsIgnoreCase("Ping"))
                {
                    mydb.child(myUserUID).setValue(0);
                    pingHim.setText("PING REQUESTED");


                }
                else if(pingHim.getText().toString().equalsIgnoreCase("ping requested"))
                {
                    mydb.child(myUserUID).removeValue();
                    if(him_her.equalsIgnoreCase("male")){
                        him_her="PING HIM";

                    }
                    else if (him_her.equalsIgnoreCase("Female")){
                        him_her="PING HER";
                    }
                    else{him_her="PING";}
                    pingHim.setText(him_her);
                }

            }
        });

        try {
            crowndb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.TRENDING_DATA);

        }
        catch (Exception e){}


try {
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
        if(userUID.equalsIgnoreCase(myUserUID)){
            pingButton.setVisibility(View.GONE);

        }

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                mUserImage.setImageURI(Uri.parse(String.valueOf(dataSnapshot.child("photoURL"))));
//                mUsername.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_NAME)));
//                mTcfID.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_NAME)));
//                mCollege.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_COLLEGE)));
//                mBranch.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_BRANCH)));
//                mYear.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_YEAR)));
//                mQuote.setText(String.valueOf(dataSnapshot.child("quote")));
//                mRollNo.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_ROLLNO)));

                try {
                    mUserImage.setImageURI(Uri.parse(String.valueOf(dataSnapshot.child(StringVariable.USER_IMAGE).getValue())));
                }catch (Exception e){Log.e("OthersProfile",e.getMessage());}
                try {
                    mUsername.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_NAME).getValue()));
                    if(String.valueOf(dataSnapshot.child(StringVariable.USER_TCFID).getValue()).equals("null")){
                        mTcfID.setText("-- --");
                    }
                    else {
                        mTcfID.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_TCFID).getValue()));

                    }
                    mCollege.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_COLLEGE).getValue()));
                    mBranch.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_BRANCH).getValue()));
                    mYear.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_YEAR).getValue()));
                    mRollNo.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_ROLLNO).getValue()));
                    mPhone.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_PHONENUMBER).getValue()));
                    mEmail.setText(String.valueOf(dataSnapshot.child(StringVariable.USER_email).getValue()));
                    him_her=String.valueOf(dataSnapshot.child(StringVariable.USER_GENDER).getValue());

                    mydb.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String pinged="no";
                            for(DataSnapshot ds: dataSnapshot.getChildren()){
                                if(ds.getKey().equalsIgnoreCase(myUserUID)){
                                    if(String.valueOf(ds.getValue()).equalsIgnoreCase("0")){
                                        pingHim.setText("PING REQUESTED");
                                        fingerImage.setImageResource(R.drawable.ic_finger_color);

                                        pinged="yes";

                                        Log.e("PingStatus","requested");
                                        break;
                                    }
                                    else if(String.valueOf(ds.getValue()).equalsIgnoreCase("1")){
                                        pingHim.setText("PING ACCEPTED");
                                        fingerImage.setImageResource(R.drawable.ic_finger_reverse);
                                        Log.e("PingStatus","accepted");
                                        pinged="yes";
                                        mayKnow.setText("Now you may know each other");

                                        mstaticText1.setVisibility(View.VISIBLE);
                                        mstaticText2.setVisibility(View.VISIBLE);
                                        mPhone.setVisibility(View.VISIBLE);
                                        mEmail.setVisibility(View.VISIBLE);
                                        whatsAppButton.setVisibility(View.VISIBLE);
                                        mailButton.setVisibility(View.VISIBLE);
                                        callButton.setVisibility(View.VISIBLE);

                                        break;
                                    }
                                }
                            }
                            if(pinged.equalsIgnoreCase("no")){

                                Log.e("PingStatus","not pinged");
                                if(him_her.equalsIgnoreCase("male")){
                                    him_her="PING HIM";

                                }
                                else if (him_her.equalsIgnoreCase("Female")){
                                    him_her="PING HER";
                                }
                                else{him_her="PING";}
                                pingHim.setText(him_her);
                                fingerImage.setImageResource(R.drawable.ic_finger_color);

                            }


                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }catch (Exception e){Log.e("Othersprofile",e.getMessage());}
                try {
                    if(String.valueOf(dataSnapshot.child(StringVariable.APP).child(StringVariable.QUOTE).getValue()).equals("null")){
                        mQuote.setText("-- no quote --");
                    }else {
                        mQuote.setVisibility(View.VISIBLE);
                        mQuote.setText(String.valueOf(dataSnapshot.child(StringVariable.APP).child(StringVariable.QUOTE).getValue()));
                    }

                }catch (Exception e){
                    Log.e("OthersProfile",e.getMessage());
                }
                try{
                    String Tb = String.valueOf(dataSnapshot.child(StringVariable.APP).child(StringVariable.TRENDING_DATA).child(StringVariable.BLOGGER).child(StringVariable.USER_TREND).getValue());
                    if(Tb.equals("null")){
                        Tb = " - - ";
                    }
                trend_b.setText("#"+Tb);

                }catch ( Exception e){trend_b.setText("- -");}

                try{
                    String Tp = String.valueOf(dataSnapshot.child(StringVariable.APP).child(StringVariable.TRENDING_DATA).child(StringVariable.PERSONALITY).child(StringVariable.USER_TREND).getValue());
                    if(Tp.equals("null")){
                        Tp = " - - ";
                    }
                    trend_p.setText("#"+Tp);
                }catch (Exception e){
                    trend_p.setText("- -");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;


    }
}
