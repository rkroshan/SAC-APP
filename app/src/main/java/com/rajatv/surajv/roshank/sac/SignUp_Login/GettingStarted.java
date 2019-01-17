package com.rajatv.surajv.roshank.sac.SignUp_Login;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.rajatv.surajv.roshank.sac.R;
//import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.rajatv.surajv.roshank.sac.BottomNavigationActivity;
import com.rajatv.surajv.roshank.sac.StringVariable;
import java.util.HashMap;
import java.util.Map;

public class GettingStarted extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 101;
    private static final Map<String, Object> retriveData = new HashMap<>();
    Tracker mTracker;
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button google_login;
    private GoogleApiClient mGoogleApiClient;

    ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;
    private Gson gson;


    private ConstraintLayout constraintLayout3,constraintLayout4;
    private TextView textView7;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.getting_started);


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.CHECKFIRSTRUN_PREF,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("second",1);
        editor.apply();
        Log.e("getting started","stareted");
        init();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        constraintLayout3 = findViewById(R.id.constraintLayout3);
        constraintLayout4 = findViewById(R.id.constraintLayout4);
        textView7 = findViewById(R.id.textView7);

        constraintLayout3.setOnClickListener(this);
        constraintLayout4.setOnClickListener(this);
        textView7.setOnClickListener(this);

        //
        sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, MODE_PRIVATE);
        gson = new Gson();


        //gmail related
        auth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        progressDialog.dismiss();
                        Log.e("connection failed",connectionResult.getErrorMessage());
                        Toast.makeText(getApplicationContext(), "Connection failed", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //checkingUserExist();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.constraintLayout3:
                startActivity(new Intent(this,SignUp_activity.class));
                break;
            case R.id.constraintLayout4:
                progressDialog.setMessage("Getting accounts...");
                progressDialog.show();
                signIn();
                break;
            case R.id.textView7:
                startActivity(new Intent(this,Login_activity.class));
                break;
        }

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            progressDialog.setMessage("Signing you in...");
            //progressDialog.show(EntryPage1.this, "", "Signing you in...");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                mGoogleApiClient.clearDefaultAccountAndReconnect();

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), " Connection failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Login Page", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Login Page", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.

                        if (!task.isSuccessful()) {
                            Log.w("Login Page", "signInWithCredential", task.getException());

                            Toast.makeText(getApplicationContext(), "Something went wrong\n" + task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(getApplicationContext(), "Successful signin",
                                    Toast.LENGTH_SHORT).show();

                            progressDialog.setMessage("Checking Email...");
                            checkingUserExist(auth.getCurrentUser().getUid());
                            /*Log.e("Email Address",auth.getCurrentUser().getEmail() );
                            Log.e("getDisplayName",auth.getCurrentUser().getDisplayName() );
                            Log.e("getImageUrl",auth.getCurrentUser().getPhotoUrl().toString());*/
                            progressDialog.dismiss();
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

                    progressDialog.dismiss();
                    nextActivity();

                }else{
                    String mailid = "";
                    String name = "";
                    String photo = "";
                    try{
                        mailid = auth.getCurrentUser().getEmail();
                        name = auth.getCurrentUser().getDisplayName();
                        photo = auth.getCurrentUser().getPhotoUrl().toString();
                    }
                    catch (Exception e){
                        Log.e("Getting Started",e.getMessage());
                    }
                    progressDialog.setMessage("Creating New User...");
                    createNewUser(mailid,name,photo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
        startActivity(new Intent(getApplicationContext(), BottomNavigationActivity.class));
        finish();
    }


    public void createNewUser( String mailid, String name, String photo) {
        String key = auth.getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(key);

        db.child(StringVariable.USER_ADMIN).setValue(0);
        db.child(StringVariable.USER_email).setValue(mailid);
        db.child(StringVariable.USER_name).setValue(name);
        db.child(StringVariable.USER_USER_UID).setValue(key);


        db.child(StringVariable.USER_EMAIL_VERIFIED).setValue(1);
        db.child(StringVariable.USER_IMAGE).setValue(photo);

        db.child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED).setValue(0);

        //sending email verification
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
    public void onStart() {


        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
        Log.e("LoginActivity--", "sted");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.e("USERUID----",auth.getCurrentUser().getUid());
                    if(user.isEmailVerified()) {
                        Log.e("Verify dialog true",user.isEmailVerified()+"" );

                        nextActivity();
                        finish();
                    }else {
                        //TODO have a snack bar here
                        Log.e("Verify Image dialog",user.isEmailVerified()+"" );
                        AlertDialog.Builder alertDialog;
                        alertDialog = new AlertDialog.Builder(GettingStarted.this);

                        // Setting Dialog Title
                        alertDialog.setTitle("SAC 2.0");

                        // Setting Dialog Message
                        alertDialog.setMessage("Please Verify Your Email..");

                        // Setting Icon to Dialog
                        alertDialog.setIcon(R.drawable.ic_info);

                        // Setting Positive "Yes" Button
                        alertDialog.setPositiveButton("Resend Email", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {

                                auth.getCurrentUser().sendEmailVerification();

                            }
                        });
                        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        // Showing Alert Message
                        AlertDialog alertDialog1 = alertDialog.create();
                        alertDialog1.show();
                    }
                }
            }
        };
        auth.addAuthStateListener(authStateListener);
        super.onStart();
    }

    @Override
    public void onResume() {

        Log.e("LoginActivity--", "sted");

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.e("USERUID----",auth.getCurrentUser().getUid());
                    if(user.isEmailVerified()) {
                        Log.e("Verify dialog true",user.isEmailVerified()+"" );
                        Intent intent = new Intent(getApplicationContext(),BottomNavigationActivity.class);
                        startActivity(intent);
                        finish();
                        /*nextActivity();
                        finish();*/
                    }else {
                        //TODO have a snack bar here
                        Log.e("Verify Image dialog",user.isEmailVerified()+"" );
                        Dialog mdialog = new Dialog(getApplicationContext());
                        mdialog.setContentView(R.layout.pop_up_sac);
                        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView message = mdialog.findViewById(R.id.message_edittext);
                        message.setText("Please Verify Your Email First");
                        Button resend = mdialog.findViewById(R.id.yes_btn);
                        resend.setText("Resend");

                        resend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                auth.getCurrentUser().sendEmailVerification();
                            }
                        });

                    }
                }
            }
        };
        auth.addAuthStateListener(authStateListener);
        super.onResume();
    }

    @Override
    public void onStop() {

        Log.e("LoginActivity--", "stoped");
        if (authStateListener != null) {
            auth.removeAuthStateListener(authStateListener);
        }
        super.onStop();
    }



}
