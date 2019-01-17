package com.rajatv.surajv.roshank.sac.Splash;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.view.PagerAdapter;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.SignUp_Login.GettingStarted;
import com.rajatv.surajv.roshank.sac.SignUp_Login.Login_activity;
import com.rajatv.surajv.roshank.sac.SignUp_Login.SignUp_activity;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{

    private ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;
    Tracker mTracker;
    private int[] layouts;
    private TextView[] dots;
    private LinearLayout dotsBar;
    private ActionBar toolbar;


    private static final int RC_SIGN_IN = 101;
    private static final Map<String, Object> retriveData = new HashMap<>();

    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button google_login;
    private GoogleApiClient mGoogleApiClient;

    ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences,sharedprefsFirstRun;
    private Gson gson;

    private ConstraintLayout constraintLayout3,constraintLayout4,constraintLayout5;
    private TextView textView7;

    private TextView whatsNew;
    private Button skipButton;
    private  SharedPreferences sharedPreferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        init();
    }
    public void init()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);

        toolbar = getSupportActionBar();
        //   toolbar.hide();




        constraintLayout3 = findViewById(R.id.constraintLayout3);
        constraintLayout4 = findViewById(R.id.constraintLayout4);
        constraintLayout5=findViewById(R.id.constraintLayout5);
        textView7 = findViewById(R.id.textView7);
        skipButton=findViewById(R.id.skipButton_splashActivity);
        whatsNew=findViewById(R.id.whatsNew);
        dotsBar = (LinearLayout) findViewById(R.id.slide_dots);
        viewPager = (ViewPager)findViewById(R.id.viewpager_splash);


        constraintLayout4.setVisibility(View.GONE);
        constraintLayout3.setVisibility(View.GONE);
        constraintLayout5.setVisibility(View.GONE);
        skipButton.setVisibility(View.GONE);
        whatsNew.setVisibility(View.GONE);

        sharedprefsFirstRun = getSharedPreferences(StringVariable.CHECKFIRSTRUN_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedprefsFirstRun.edit();
        editor.putInt("first",1);
        editor.apply();

        int second = sharedprefsFirstRun.getInt("second", 0);
        Log.e("second---",second+"");

        if(second != 0)
        {
            Intent intent = new Intent(SplashActivity.this, GettingStarted.class);
            startActivity(intent);
            finish();
        }

        //next_slide.setVisibility(View.GONE);
        sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, MODE_PRIVATE);
        gson = new Gson();


        constraintLayout3.setOnClickListener(this);
        constraintLayout4.setOnClickListener(this);
        textView7.setOnClickListener(this);
        layouts = new int[]{R.layout.splash1,R.layout.splash2, R.layout.splash3, R.layout.splash4,
                R.layout.splash5, R.layout.splash6, R.layout.splash7, R.layout.splash8, R.layout.splash9,R.layout.splash10};

        addBottomDots(0);

        viewPageAdapter = new ViewPageAdapter();
        viewPager.setAdapter(viewPageAdapter);


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
                        Toast.makeText(getApplicationContext(), "Conection failed", Toast.LENGTH_SHORT).show();
                    }
                } /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //checkingUserExist();

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                addBottomDots(i);


                if(i==layouts.length-1)
                {
                    // next_slide.setVisibility(View.GONE);
                    constraintLayout3.setVisibility(View.VISIBLE);
                    constraintLayout4.setVisibility(View.VISIBLE);
                    constraintLayout5.setVisibility(View.VISIBLE);

                    SharedPreferences.Editor editor = sharedprefsFirstRun.edit();
                    editor.putInt("first", 1);
                    editor.commit();
                }
                else
                {
                    constraintLayout3.setVisibility(View.GONE);
                    constraintLayout4.setVisibility(View.GONE);
                    constraintLayout5.setVisibility(View.GONE);
                }
                if(i==layouts.length-2 || i==layouts.length-3 || i==layouts.length-4 || i==layouts.length-5)
                {
                    skipButton.setVisibility(View.VISIBLE);
                }
                else {
                    skipButton.setVisibility(View.GONE);
                }
                if(i==5 || i==1 || i==2 || i==3 || i==4)
                {
                    whatsNew.setVisibility(View.VISIBLE);
                }
                else {
                    whatsNew.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //     Toast.makeText(SplashActivity.this,"skip clicked",Toast.LENGTH_LONG).show();
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(layouts.length-1);
                }
            }
        });



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
                Toast.makeText(getApplicationContext(), " Conection failed", Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(getApplicationContext(), GettingStarted.class));
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

    private int getItem(int i) {
        Log.e("CurrentItem",String.valueOf(viewPager.getCurrentItem()+i));

        return viewPager.getCurrentItem() + i;
    }
    private void addBottomDots(int position)
    {
        dots = new TextView[layouts.length];
        int colorActive = Color.parseColor("#2bff85");
        int colorInactive = Color.parseColor("#7a8689");

        dotsBar.removeAllViews();
        for(int i=0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive);
            dotsBar.addView(dots[i]);
        }
        if(dots.length>0)
            dots[position].setTextColor(colorActive);
    }


      /*  next_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int current = getItem(+1);
                if(current < layouts.length)
                {
                    viewPager.setCurrentItem(current);
                }
            }
        });*/




    public class ViewPageAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
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
