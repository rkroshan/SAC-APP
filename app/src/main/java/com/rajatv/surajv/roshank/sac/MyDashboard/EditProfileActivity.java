package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageTask;
import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    Tracker mTracker;
    private static final String TAG = "EditProfileActivity.jav";
    ImageView backButton;
    SharedPreferences sharedPreferences;
    private String userUID;
    DatabaseReference userdb;
    private TextView username;
    private TextView quote_textview,college_tv,branch_tv,roll_tv,year_tv,phone_tv,email_tv;
    SimpleDraweeView profileImage;
    ImageButton imageEditButton;
    RelativeLayout saveButton;
    Switch privacySwitch;
    private StorageTask uploadTask;
    private ProgressBar progressBar;


    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private String ProfileCompleted = "";


    boolean privacy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_edit_profile);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        backButton=(ImageView)findViewById(R.id.edit_profile_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getData();
        init();
        setData();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference,MODE_PRIVATE);
                String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
                Gson gson = new Gson();
                Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
                obj = (Map<String, Object>) obj.get(StringVariable.APP);
                try {
                    ProfileCompleted = String.valueOf(obj.get(StringVariable.USER_IS_PROFILE_COMPLETED));
                }catch (Exception e){
                    ProfileCompleted = "0.0";
                }
                if(ProfileCompleted.equals("1.0")) {
                    updateData();
                    uploadImage();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"Register Yourself first",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(EditProfileActivity.this,"The image uploaded should not be more than 200kb",Toast.LENGTH_LONG).show();
                openImage();
            }
        });
    }

    private void openImage() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);

    }
    private  String getFileExtension(Uri uri){
        ContentResolver contentResolver=EditProfileActivity.this.getContentResolver();
        MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
//        Log.e("EditProfilexx",mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)));
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){

            progressBar.setVisibility(View.VISIBLE);

//        pd.setT

            if(imageUri!=null){
                final StorageReference filereference=storageReference.child(userUID+"-"+getFileExtension(imageUri));

                uploadTask=filereference.putFile(imageUri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        return filereference.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if(task.isSuccessful()){
                            Uri downloadUri=task.getResult();
                            String mUri=downloadUri.toString();

                            HashMap<String,Object> map=new HashMap<>();
                            map.put(StringVariable.USER_IMAGE,mUri);

//                            Log.e("EditProfilexx",mUri);

                            userdb.updateChildren(map);

                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            Toast.makeText(EditProfileActivity.this,"No Image Selected",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfileActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
            else{
                Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
            }
        }


//        progressBar.setVisibility(View.VISIBLE);
//
//        if(imageUri!=null){
//            final StorageReference filereference=storageReference.child(userUID+"-"+getFileExtension(imageUri));
//
//            filereference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//
//                   userdb.child(StringVariable.USER_IMAGE).setValue(taskSnapshot.getDownloadUrl());
//
//                    progressBar.setVisibility(View.GONE);
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(getApplicationContext(),"profile updation failed ",Toast.LENGTH_SHORT).show();
//                    progressBar.setVisibility(View.GONE);
//
//                }
//            });
//
//        }
//        else{
//            Toast.makeText(this,"No Image Selected",Toast.LENGTH_LONG).show();
//        }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  Log.e("EditProfilexx","REACH");


        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
        //    Log.e("EditProfilexx","REACH");
            imageUri=data.getData();
        //    Log.e("EditProfilexx",imageUri.toString());
            if(imageUri!=null){
                try{
                    profileImage.setImageURI(imageUri);
                }catch (Exception e){
//                    Log.e("image uri",e.getMessage());
                }
            }


        }

    }


    private void init()
    {
        username=findViewById(R.id.name_textview);
        quote_textview=findViewById(R.id.quoteTextView);
        college_tv=findViewById(R.id.college_name_editprofile);
        branch_tv=findViewById(R.id.Branch_name_editprofile);
        roll_tv=findViewById(R.id.rollno_editprofile);
        year_tv=findViewById(R.id.year_editprofile);
        phone_tv=findViewById(R.id.phoneno_editprofile);
        email_tv=findViewById(R.id.email_editprofile);
        privacySwitch=findViewById(R.id.privacy1_switch);
        imageEditButton=findViewById(R.id.edit_profile_button);
        profileImage=findViewById(R.id.userImage_editProfile);
        saveButton=findViewById(R.id.save_editprofile_button);
        userdb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);
        storageReference=FirebaseStorage.getInstance().getReference().child("profilePicture");
    }


    private void getData()
    {
        sharedPreferences = this.getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref,"");
        Map<String,Object> obj = gson.fromJson(data,StringVariable.RetriveClass.getClass());
        userUID = String.valueOf(obj.get(StringVariable.USER_USER_UID));

    }
    private void updateData(){


//        HashMap<String,Object> map=new HashMap<>();
//
//        Map<String,Object> childMap=new HashMap<>();
//        childMap.put(StringVariable.QUOTE,String.valueOf(quote_textview.getText()));
        userdb.child(StringVariable.USER_PHONENUMBER).setValue(String.valueOf(phone_tv.getText()));

        userdb.child(StringVariable.APP).child(StringVariable.QUOTE).setValue(String.valueOf(quote_textview.getText()));

        if(privacySwitch.isChecked())
           userdb.child(StringVariable.USER_PHONENUMBER_PRIVATE).setValue("true");
        else
            userdb.child(StringVariable.USER_PHONENUMBER_PRIVATE).setValue("false");
       // userdb.child(StringVariable.APP).setValue(childMap);

    }
    private void setData() {
        SharedPreferences sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, MODE_PRIVATE);
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Gson gson = new Gson();
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        try {
            college_tv.setText(String.valueOf(obj.get(StringVariable.USER_COLLEGE)));
            username.setText(String.valueOf(obj.get(StringVariable.USER_NAME)));
            email_tv.setText(String.valueOf(obj.get(StringVariable.USER_email)));
            branch_tv.setText(String.valueOf(obj.get(StringVariable.USER_BRANCH)));
            roll_tv.setText(String.valueOf(obj.get(StringVariable.USER_ROLLNO)));
            phone_tv.setText(String.valueOf(obj.get(StringVariable.USER_PHONENUMBER)));
            year_tv.setText(String.valueOf(obj.get(StringVariable.USER_YEAR)));
            profileImage.setImageURI(String.valueOf(obj.get(StringVariable.USER_IMAGE)));
            String quote = String.valueOf(((Map<String, Object>) obj.get(StringVariable.APP)).get(StringVariable.QUOTE));
            if(String.valueOf(obj.get(StringVariable.USER_PHONENUMBER_PRIVATE)).equalsIgnoreCase("true")){
                privacySwitch.setChecked(true);

            }
            if(quote.equals("null")){
                quote_textview.setText("");
            }else {
                quote_textview.setText(quote);
            }
        } catch (Exception e) {
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
