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

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
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
import java.util.HashMap;
import java.util.Map;

public class SignUp_activity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    Tracker mTracker;
    private EditText enter_email_signup,enter_password_signup,retype_password;
    private Button Signup_getin;
    private TextView enter_login_page;

    private SharedPreferences sharedPreferences;
    private Gson gson;

    private ProgressDialog progressDialog;

    private String Email = "",Password = "",RePassword = "";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_up);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        auth = FirebaseAuth.getInstance();
        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, MODE_PRIVATE);
        gson = new Gson();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        enter_email_signup = findViewById(R.id.enter_email_signup);
        enter_password_signup = findViewById(R.id.enter_password_signup);
        retype_password = findViewById(R.id.retype_password);

        Signup_getin = findViewById(R.id.Signup_getin);
        enter_login_page = findViewById(R.id.enter_login_page);

        Signup_getin.setOnClickListener(this);
        enter_login_page.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Signup_getin:
                check_Fields();
                break;
            case R.id.enter_login_page:
                startActivity(new Intent(this,Login_activity.class));
                break;
        }
    }

    private void check_Fields() {
        try {
            Email = enter_email_signup.getText().toString();

            if (Email.length() == 0 || !Email.contains("@")) {
                Toast.makeText(this, "Please Enter EmailId Correctly", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    Password = enter_password_signup.getText().toString();

                    if (Password.length() == 0) {
                        Toast.makeText(this, "Please Enter Password Correctly", Toast.LENGTH_SHORT).show();
                    }else{

                        try{
                            RePassword = retype_password.getText().toString();

                            if(!Password.equals(RePassword)){
                                Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_LONG).show();
                            }
                            else if(Password.equals(RePassword)){
                                progressDialog.setMessage("Creating User...");
                                progressDialog.show();
                                checking_Exsiting_user();
                            }
                        }catch (Exception e){
                            Toast.makeText(this, "Please Confirm Your Password", Toast.LENGTH_LONG).show();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(this, "OPPS You Hadn't Entered the Password", Toast.LENGTH_LONG).show();
                }


            }
        } catch (Exception e) {
            Toast.makeText(this, "OPPS You Hadn't Entered the email", Toast.LENGTH_LONG).show();
        }


    }

    private void checking_Exsiting_user() {

        //
        auth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(),"Email Id Already In Our Database",Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"Please Sign In",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            progressDialog.setMessage("Please Wait...");
                            try_signin();
                        }

                        // ...
                    }
                });
    }

    private void try_signin() {
        auth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            String mailid = "";
                            String name = "";
                            String photo = "";
                            try {
                                mailid = auth.getCurrentUser().getEmail();
                                name = auth.getCurrentUser().getDisplayName();
                                photo = auth.getCurrentUser().getPhotoUrl().toString();
                            } catch (Exception e) {
                                Log.e("Getting Started", e.getMessage());
                            }
                            progressDialog.setMessage("Creating New User In Database ...");
                            createNewUser(mailid, name, photo);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        // ...
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
        String key = auth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);

        db.child(StringVariable.USER_ADMIN).setValue(0);
        db.child(StringVariable.USER_email).setValue(mailid);
        db.child(StringVariable.USER_name).setValue(name);
        db.child(StringVariable.USER_USER_UID).setValue(key);


        db.child(StringVariable.USER_EMAIL_VERIFIED).setValue(1);
        db.child(StringVariable.USER_IMAGE).setValue(photo);

        db.child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED).setValue(0);


        //TODO send verification email
        auth.getCurrentUser().sendEmailVerification();


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

