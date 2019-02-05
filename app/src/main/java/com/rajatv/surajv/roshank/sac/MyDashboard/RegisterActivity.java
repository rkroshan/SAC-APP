package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    Tracker mTracker;
    private static final String TAG = "RegisterActivity.java";
    private ActionBar toolbar;
    private EditText register_name, register_branch, register_roll, register_year, register_phone, register_mail, register_college,Degree_registrations;
    private Button register_male, register_female, register_trans, register_college_nitp, register_college_others, register_register;
    private String name, college, rollno, branch, mobileno, gender, year, email,degree;
    private RelativeLayout collegeBar;
    private Button[] btn = new Button[3];  //for button clicks one out of many
    private Button[] btnclg = new Button[2];
    private Button btn_unfocus, btn_unfocus2;
    private int[] btn_id = {R.id.Male_button_registration_page, R.id.Female_button_registration_page, R.id.trans_button_registration_page};
    private int[] btn_id2 = {R.id.NITCollege_button_registration_page, R.id.OthersCollege_button_registration_page};
    private RelativeLayout relTop;
    private Map<String,Object> Retrive = new HashMap<>();
    private String speedDial="";
    private ImageView backButton;
    private String CollegeUid = "";
    private int flag = 0;
    private String key = "";
    private String imageUrl = "";
    Dialog mdialog;

    private ProgressDialog progressDialog;



    String curr_mail = "";


    int collegeSelected;


    View focusView;


    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrations);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Image~" + name);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
 //       toolbar = getSupportActionBar();
   //     toolbar.setTitle("Registration");

        init();
        for (int i = 0; i < btn.length; i++) {
            btn[i] = findViewById(btn_id[i]);
            btn[i].setOnClickListener(this);
        }

        for (int i = 0; i < btnclg.length; i++) {
            btnclg[i] = findViewById(btn_id2[i]);
            btnclg[i].setOnClickListener(this);
        }

        btn_unfocus = btn[2];
        btn[0].performClick();
    //    btn[1].performClick();

     //   gender = "";
   //     btn[1].setBackgroundResource(R.drawable.round_button_registrations);

        btn_unfocus2 = btnclg[1];
        btnclg[0].performClick();
 //       btnclg[1].performClick();
//        college = "";
 //       btnclg[1].setBackgroundResource(R.drawable.round_button_registrations);


        register_register.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //confirmDialog();
                //    Toast.makeText(getApplicationContext()," Register clicked",Toast.LENGTH_LONG).show();
               checkdata();

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.Male_button_registration_page || v.getId() == R.id.Female_button_registration_page || v.getId() == R.id.trans_button_registration_page) {
            setFocus(btn_unfocus, findViewById(v.getId()));
 //           Toast.makeText(RegisterActivity.this,gender,Toast.LENGTH_LONG).show();
            switch (v.getId()) {
                case R.id.Male_button_registration_page:
                    gender = "male";
                    break;

                case R.id.Female_button_registration_page:
                    gender = "female";
                    break;

                case R.id.trans_button_registration_page:
                    gender = "trans";
                    break;
            }
        } else {
            setFocus2(btn_unfocus2, findViewById(v.getId()));
            switch (v.getId()) {
                case R.id.NITCollege_button_registration_page:
                 //   register_college.setEnabled(false);
                    //relTop.setBackgroundResource(0);
                    collegeBar.setVisibility(View.GONE);
                    collegeSelected = 1;
                    college = "National Institute Of Technology Patna";
            //       Toast.makeText(RegisterActivity.this,college,Toast.LENGTH_LONG).show();

                    break;
                case R.id.OthersCollege_button_registration_page:
                   // register_college.setEnabled(true);
                    collegeBar.setVisibility(View.VISIBLE);
                    collegeSelected = 0;
                    //relTop.setBackgroundResource(R.drawable.round_text_field_registrations);
                    college = "";

                    break;
            }
        }


    }


    private void setFocus(Button btn_unfocus, Button btn_focus) {
        if (btn_unfocus == btn_focus) {
            return;
        }
        btn_focus.setBackgroundResource(R.drawable.round_button_registrations_clicked);
        btn_unfocus.setBackgroundResource(R.drawable.round_button_registrations);
        this.btn_unfocus = btn_focus;
    }

    private void setFocus2(Button btn_unfocus2, Button btn_focus) {
        if (btn_unfocus2 == btn_focus) {
            return;
        }
        btn_focus.setBackgroundResource(R.drawable.round_button_registrations_clicked);
        btn_unfocus2.setBackgroundResource(R.drawable.round_button_registrations);
        this.btn_unfocus2 = btn_focus;
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


    private void init() {
        mAuth = FirebaseAuth.getInstance();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);

        register_name = findViewById(R.id.name_registration);
        register_roll = findViewById(R.id.roll_registrations);
        register_branch = findViewById(R.id.branch_registrations);
        register_phone = findViewById(R.id.phone_registrations);
        register_year = findViewById(R.id.year_registrations);

        register_male = findViewById(R.id.Male_button_registration_page);
        register_female = findViewById(R.id.Female_button_registration_page);
        register_trans = findViewById(R.id.trans_button_registration_page);
        register_mail = findViewById(R.id.email_registrations);
        register_college = findViewById(R.id.othersCollegeTV_registrations);
        Degree_registrations = findViewById(R.id.Degree_registrations);
        register_mail.setEnabled(false);
        register_register = findViewById(R.id.registerButton_register);
        relTop = findViewById(R.id.relmiddle3);
        backButton= findViewById(R.id.registrationsBack);

        collegeSelected = 0;

        collegeBar= findViewById(R.id.relmiddle);

        setData();

    }

    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,MODE_PRIVATE);
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        speedDial = sharedPreferences.getString(StringVariable.SPEEDDIAL_DATA,"");
        Gson gson = new Gson();
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
//        Log.e("setData()---",obj.toString());

        try{
            register_name.setText(obj.get(StringVariable.USER_NAME).toString());
//            Log.e("register_name",obj.get(StringVariable.USER_NAME).toString());
        }catch (Exception e){}
        try{
            register_mail.setText(obj.get(StringVariable.USER_email).toString());
        }catch(Exception e){}
        try{
            imageUrl = obj.get(StringVariable.USER_IMAGE).toString();
        }catch (Exception e){}


    }


    private void checkdata() {
        name = register_name.getText().toString();
        rollno = register_roll.getText().toString();
        branch = register_branch.getText().toString();
        mobileno = register_phone.getText().toString();
        year = register_year.getText().toString();
        email = register_mail.getText().toString();
        degree = Degree_registrations.getText().toString();
        if (collegeSelected == 0) {
            college = register_college.getText().toString();
      //      Toast.makeText(RegisterActivity.this,college,Toast.LENGTH_LONG).show();

        }

        boolean cancel = false;
        //rollExist();
        if (mobileno.length() != 10) {
            cancel = true;
            register_phone.setError("Enter 10 digit Mobile Number");
            focusView = register_phone;
            focusView.requestFocus();
        } else if (gender.isEmpty()) {
            cancel = true;
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_LONG).show();
        } else if (college.isEmpty()) {
            cancel = true;
            Toast.makeText(this, "Please select your college", Toast.LENGTH_LONG).show();
        }/* else if (year.isEmpty() || year.length() > 1) {
            cancel = true;
            register_year.setError("Enter values from 1 to 5");
            focusView = register_year;
            focusView.requestFocus();
        }*/
        else if(degree==null || degree.equals("")){
            cancel = true;
            Toast.makeText(getApplicationContext(),"Enter your degree please",Toast.LENGTH_SHORT).show();
        }
        else if (branch.isEmpty()) {
            cancel = true;
            register_branch.setError("Enter your branch");
            focusView = register_branch;
            focusView.requestFocus();
        } else if (rollno.isEmpty()) {
            cancel = true;
            register_roll.setError("Enter your roll number");
            focusView = register_roll;
            focusView.requestFocus();
        } else if (name.isEmpty()) {
            cancel = true;
            register_name.setError("Enter name");
            focusView = register_name;
            focusView.requestFocus();
        }


        if (cancel != true)
            confirmDialog();
           // BeginRegistration();
    }


    private void BeginRegistration() {
        settingUpDetails();


    }

    private void confirmDialog() {
         mdialog = new Dialog(this);
        mdialog.setContentView(R.layout.pop_up_sac);
        mdialog.getWindow().getDecorView()
        .setBackgroundResource(android.R.color.transparent);
        mdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView confirmtext = mdialog.findViewById(R.id.message_edittext);
        confirmtext.setText("Do you Want to confirm your Registration ?");
        Button confirmbtn = mdialog.findViewById(R.id.yes_btn);
        confirmbtn.setText("Confirm");
        Button nobtn = mdialog.findViewById(R.id.no_btn);
        nobtn.setText("Deny");

        mdialog.show();


        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checkdata();
                BeginRegistration();



            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
            }
        });
    }


    private void settingUpDetails() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Registering...");
        progressDialog.show();

              curr_mail = mAuth.getCurrentUser().getEmail();
//        Log.e("SignUp1", "SettingUpDeatils the details");
        key = mAuth.getCurrentUser().getUid();

        Map<String,Object> otherDataMap = new HashMap<>();
        otherDataMap.put(StringVariable.USER_IS_PROFILE_COMPLETED, 1);
        otherDataMap.put(StringVariable.USER_PHONENUMBER_PRIVATE,0);
        otherDataMap.put(StringVariable.PUBLISH,0);




        if(college.contains("National Institute of Technology Patna")){
            databaseReference.child(key).child(StringVariable.COLLEGE_ID).setValue("nitp");
        }else{
            databaseReference.child(key).child(StringVariable.COLLEGE_ID).setValue("other");
        }
       // databaseReference.child(key).child(StringVariable.APP). setValue(otherDataMap);
        databaseReference.child(key).child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED).setValue(1);
        databaseReference.child(key).child(StringVariable.APP).child(StringVariable.USER_PHONENUMBER_PRIVATE).setValue(0);
        databaseReference.child(key).child(StringVariable.APP).child(StringVariable.PUBLISH).setValue(0);
        databaseReference.child(key).child(StringVariable.USER_PHONENUMBER).setValue(mobileno);
        databaseReference.child(key).child(StringVariable.USER_email).setValue(email);
        databaseReference.child(key).child(StringVariable.USER_name).setValue(name);
        databaseReference.child(key).child(StringVariable.USER_ADMIN).setValue(0);
        databaseReference.child(key).child(StringVariable.USER_USER_UID).setValue(key);
        databaseReference.child(key).child(StringVariable.USER_BRANCH).setValue(branch);
        databaseReference.child(key).child(StringVariable.USER_COLLEGE).setValue(college);
        databaseReference.child(key).child(StringVariable.USER_IMAGE).setValue(imageUrl);
        databaseReference.child(key).child(StringVariable.USER_ROLLNO).setValue(rollno);
        databaseReference.child(key).child(StringVariable.USER_YEAR).setValue(year);
        databaseReference.child(key).child(StringVariable.USER_DEGREE).setValue(degree);
        databaseReference.child(key).child(StringVariable.USER_GENDER).setValue(gender);
        databaseReference.child(key).child(StringVariable.USER_EMAIL_VERIFIED).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                nextActivity(key);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
            }
        });        // startActivity(new Intent(getApplicationContext(), Dashboard.class));

    }

    private void nextActivity(String key) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
//                    Log.i("Login activity", "USER EXIST");
                    Map<String, Object> dt = (HashMap<String, Object>) dataSnapshot.getValue();
                    SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,MODE_PRIVATE);
                    Gson gson = new Gson();
                    String data = gson.toJson(dt);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    prefsEditor.putString(StringVariable.UserData_Object_SharedPref, data);
                    prefsEditor.apply();
                    Toast.makeText(RegisterActivity.this,"Registrations Successful",Toast.LENGTH_LONG).show();

//                    Log.e("Data user---",dt.toString());
                    progressDialog.dismiss();
                    finish();
                }
                else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
}
