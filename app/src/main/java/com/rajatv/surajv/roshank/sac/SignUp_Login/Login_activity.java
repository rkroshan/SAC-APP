package com.rajatv.surajv.roshank.sac.SignUp_Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.rajatv.surajv.roshank.sac.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.HashMap;
import java.util.Map;

public class Login_activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText enter_email, enter_password;
    private Button login_getin;
    private TextView enter_signup_page;
    private ProgressDialog progressDialog;
    private FirebaseAuth.AuthStateListener authStateListener;
    Tracker mTracker;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private String Email = "";
    private String Password = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        init();

    }

    private void init() {

        sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, MODE_PRIVATE);
        gson = new Gson();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        enter_password = findViewById(R.id.enter_password);
        enter_email = findViewById(R.id.enter_email);
        enter_signup_page = findViewById(R.id.enter_signup_page);
        login_getin = findViewById(R.id.login_getin);

        enter_signup_page.setOnClickListener(this);
        login_getin.setOnClickListener(this);

//        Log.e("USERUID---",mAuth.getCurrentUser().getUid());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.enter_signup_page:
                startActivity(new Intent(this, SignUp_activity.class));
                break;
            case R.id.login_getin:
                check_Fields();
                break;
        }

    }

    private void check_Fields() {
        try {
            Email = enter_email.getText().toString();

            if (Email.length() == 0 || !Email.contains("@")) {
                Toast.makeText(this, "Please Enter Email-Id Correctly", Toast.LENGTH_SHORT).show();
            }
            else{

                try {
                    Password = enter_password.getText().toString();

                    if (Password.length() == 0) {
                        Toast.makeText(this, "Please Enter Password Correctly", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        progressDialog.setMessage("Signing In...");
                        progressDialog.show();
                        try_signin();
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "OPPS You Hadn't Entered the Password", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            Toast.makeText(this, "OPPS You Hadn't Entered the email", Toast.LENGTH_LONG).show();
        }



        // try sign in
    }

    private void try_signin() {
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressDialog.setMessage("Checking User...");
                            checkingUserExist(mAuth.getCurrentUser().getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Log.e("Login Activity", "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(),"Invalid User Or Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    private void checkingUserExist(String UID) {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);

        //String userUid = auth.getCurrentUser().getUid();
        //"000AAAuserIdFake8587984980"
        db.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.i("Login activity", "USER EXIST");
                    Map<String, Object> dt = (HashMap<String, Object>) dataSnapshot.getValue();
                    String data = gson.toJson(dt);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    prefsEditor.putString(StringVariable.UserData_Object_SharedPref, data);
                    prefsEditor.apply();

                    Log.e("Data user---",dt.toString());
                    progressDialog.dismiss();
                    nextActivity();

                } else {
                    String mailid = "";
                    String name = "";
                    String photo = "";
                    try {
                        mailid = mAuth.getCurrentUser().getEmail();
                        name = mAuth.getCurrentUser().getDisplayName();
                        photo = mAuth.getCurrentUser().getPhotoUrl().toString();
                    } catch (Exception e) {
                        Log.e("Getting Started", e.getMessage());
                    }
                    progressDialog.setMessage("Creating New User...");
                    createNewUser(mailid, name, photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),databaseError.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nextActivity() {
       /* Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,MODE_PRIVATE);
        String json = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Map<String,Object> obj = gson.fromJson(json, retriveData.getClass());
        String data = ((Map<String,Object>)obj.get(StringVariable.USER_OTHERDATA)).get(StringVariable.USER_IS_TCFID_GENERATED).toString();
        Log.e("Login activity obj--",data);*/
        startActivity(new Intent(getApplicationContext(), GettingStarted.class));
        finish();
    }


    public void createNewUser(String mailid, String name, String photo) {
        String key = mAuth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);

        db.child(StringVariable.USER_ADMIN).setValue(0);
        db.child(StringVariable.USER_email).setValue(mailid);
        db.child(StringVariable.USER_name).setValue(name);
        db.child(StringVariable.USER_USER_UID).setValue(key);


        db.child(StringVariable.USER_EMAIL_VERIFIED).setValue(1);
        db.child(StringVariable.USER_IMAGE).setValue(photo);

        db.child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED).setValue(0);


        //TODO send verification email

        //Storing Data in sharedPreference
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.i("Login activity", "USER EXIST");
                    Map<String, Object> dt = (HashMap<String, Object>) dataSnapshot.getValue();
                    String data = gson.toJson(dt);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    prefsEditor.putString(StringVariable.UserData_Object_SharedPref, data);
                    prefsEditor.apply();

                    Log.e("Data user---",dt.toString());
                    progressDialog.dismiss();
                    nextActivity();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                nextActivity();
            }
        });

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

