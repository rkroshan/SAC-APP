package com.rajatv.surajv.roshank.sac.Feeds.SacFeeds;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.rajatv.surajv.roshank.sac.Feeds.FeedsAllFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Notice.FeedsNoticeFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Result.FeedsResultFrag;
import com.rajatv.surajv.roshank.sac.Feeds.Feeds_Home_ViewPagerAdapter;
import com.rajatv.surajv.roshank.sac.MyDashboard.RegisterNowFragment;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class SacFeedsFragment extends Fragment implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Feeds_Home_ViewPagerAdapter feeds_home_viewPagerAdapter;
    private Toolbar toolbar;
    private FloatingActionButton fb;
    private Dialog mdialog;
    String btnlabel = null;
    SimpleDraweeView imgload;
    ImageButton pdfload;
    RelativeLayout upload;
    private static final int IMAGE_PERMISSION_REQUEST = 0;
    private static final int PDF_PERMISSION_REQUEST = 1;
    private static final int RESULT_LOAD_PDF = 3;
    private static final int REQUEST_LOAD_IMAGE = 2;
    List<String> categories = new ArrayList<String>();
    List<String> eventDetailsList = new ArrayList<String>();
    List<String> subeventDetailsList = new ArrayList<String>();
    private DatabaseReference dbProfile;
    private String currentUserUID;

    private DatabaseReference dbref;
    private ArrayList<String> Urilist = new ArrayList<>();

    int check = 0;

    //for create post
    //-----------------------------------------------------------
    ArrayAdapter<String> dataAdapter, dataAdapter1, dataAdapter2;
    TextView username, writepost;
    EditText post;
    SimpleDraweeView blog_profile_pic;
    RelativeLayout close;
    Button publish;
    Button timelinebtn;
    Button noticebtn;
    Button resultbtn;
    RelativeLayout timelineRL;
    RelativeLayout noticeresultRV;
    Spinner categoryevent;
    Spinner event;
    Spinner subevent;
    String TECHNICAL_EVENT;
    String CULTURAL_EVENT;
    String event_data = " ", sub_event_data = " ";
    RecyclerView uploaded_files;
    LinearLayoutManager layoutManager;
    Uploadfile_Apdapter fileapdapter;
    ArrayList<Uri_List> mlist = new ArrayList<>();
    int type = 0;
    Uri image_data = null;
    ProgressDialog progressDialog;

    private String UserUID = "";
    private String UserImageUrl = "";
    private String Name = "";

    private SwipeRefreshLayout refresh_layout;

    int i = 0;


    //--------------------------------------------------------------------------------


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sac_feeds, container, false);
        tabLayout = v.findViewById(R.id.tabLayout);
        viewPager = v.findViewById(R.id.viewPager_id);
        fb = v.findViewById(R.id.write_post_fab);

        dbref = FirebaseDatabase.getInstance().getReference().child("events");


        init(v);

        /*toolbar = (Toolbar) v.findViewById(R.id.appbar_sacfeeds);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);*/
        return v;
    }

    private void init(View v) {

        feeds_home_viewPagerAdapter = new Feeds_Home_ViewPagerAdapter(getChildFragmentManager());

        currentUserUID="";
        try {
            currentUserUID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        catch (Exception e){}
        try {
            dbProfile=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(currentUserUID).child(StringVariable.APP).child(StringVariable.USER_IS_PROFILE_COMPLETED);
            dbProfile.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Log.e("complete",dataSnapshot.toString());
                    Log.e("complete",String.valueOf(dataSnapshot.child(StringVariable.USER_IS_PROFILE_COMPLETED).getValue()));
                    if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0") ||
                            String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("null")){
                        feeds_home_viewPagerAdapter.addFragment(new RegisterNowFragment(), "All");
                        feeds_home_viewPagerAdapter.addFragment(new RegisterNowFragment(), "Notice");
                        feeds_home_viewPagerAdapter.addFragment(new RegisterNowFragment(), "Results");
                        viewPager.setAdapter(feeds_home_viewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);


                    }
                    else {
                        feeds_home_viewPagerAdapter.addFragment(new FeedsAllFrag(), "All");
                        feeds_home_viewPagerAdapter.addFragment(new FeedsNoticeFrag(), "Notice");
                        feeds_home_viewPagerAdapter.addFragment(new FeedsResultFrag(), "Results");
                        viewPager.setAdapter(feeds_home_viewPagerAdapter);

                        tabLayout.setupWithViewPager(viewPager);


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){}
//        feeds_home_viewPagerAdapter.addFragment(new FeedsAllFrag(), "All");
//        feeds_home_viewPagerAdapter.addFragment(new FeedsNoticeFrag(), "Notice");
//        feeds_home_viewPagerAdapter.addFragment(new FeedsResultFrag(), "Results");

//        viewPager.setAdapter(feeds_home_viewPagerAdapter);
//
//        tabLayout.setupWithViewPager(viewPager);

        /*refresh_layout = v.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeResources(R.color.colorAccent);

*/
        init_dialog();
        getDataFromSharedPref();

       /* refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("rereshing pref","refreshing");
                setSharedPref();
                admin_and_resgistered();
                refresh_layout.setRefreshing(false);
            }
        });
*/
    }

    private void getDataFromSharedPref() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Gson gson = new Gson();
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        try {
            UserUID = obj.get(StringVariable.USER_USER_UID).toString();
        } catch (Exception e) {
            Log.e("UserUID", e.getMessage());
        }
        try {
            UserImageUrl = obj.get(StringVariable.USER_IMAGE).toString();
             Log.e("UserImageUrl",UserImageUrl);
            blog_profile_pic.setImageURI(UserImageUrl);
        } catch (Exception e) {
            Log.e("UserImageUrl", e.getMessage());
        }
        try {
            Name = obj.get(StringVariable.USER_NAME).toString();
            username.setText(Name);
        } catch (Exception e) {
            Log.e("Name", e.getMessage());
        }
    }


/*
    private void setSharedPref() {
        DatabaseReference db =FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(UserUID);
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Log.i("Login activity", "USER EXIST");
                    Map<String, Object> dt = (HashMap<String, Object>) dataSnapshot.getValue();
                    Gson gson = new Gson();
                    String data = gson.toJson(dt);
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference,Context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                    prefsEditor.putString(StringVariable.UserData_Object_SharedPref, data);
                    prefsEditor.apply();

                    Log.e("Data user---",dt.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/

    private void init_dialog() {

        mdialog = new Dialog(getActivity());
        mdialog.setContentView(R.layout.pop_up_feeds);
        mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        imgload = mdialog.findViewById(R.id.image_loader);
        upload = mdialog.findViewById(R.id.add_attachment);
        pdfload = mdialog.findViewById(R.id.upload_btn);
        blog_profile_pic = mdialog.findViewById(R.id.blog_profile_pic);

        mlist = new ArrayList<>();
        uploaded_files = mdialog.findViewById(R.id.uploaded_files);
        layoutManager = new LinearLayoutManager(getActivity());
        uploaded_files.setLayoutManager(layoutManager);
        fileapdapter = new Uploadfile_Apdapter(getActivity(), mlist);
        uploaded_files.setAdapter(fileapdapter);

        username = mdialog.findViewById(R.id.pop_up_blog_username);
        writepost = mdialog.findViewById(R.id.write_post);
        post = mdialog.findViewById(R.id.edit_post);
        post.setMovementMethod(null);
        close = mdialog.findViewById(R.id.close_relative_view);
        publish = mdialog.findViewById(R.id.publish_btn);
        timelinebtn = mdialog.findViewById(R.id.timeline_btn);
        noticebtn = mdialog.findViewById(R.id.notice_btn);
        resultbtn = mdialog.findViewById(R.id.result_btn);
        timelineRL = mdialog.findViewById(R.id.timeline_relative_layout);
        noticeresultRV = mdialog.findViewById(R.id.notice_result_attachment);
        categoryevent = mdialog.findViewById(R.id.eventcategoryspinner);
        event = mdialog.findViewById(R.id.eventspinner);
        subevent = mdialog.findViewById(R.id.subeventspinner);
        TECHNICAL_EVENT = "technical-event";
        CULTURAL_EVENT = "cultural-event";

        admin_and_resgistered();
        fb.setOnClickListener(this);
        imgload.setOnClickListener(this);
        upload.setOnClickListener(this);
        close.setOnClickListener(this);
        publish.setOnClickListener(this);

        timelinebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                btnlabel = timelinebtn.getText().toString();
                writepost.setText("Write Captions");
                if (v == timelinebtn) {
                    timelinebtn.setBackgroundResource(R.drawable.round_post_feed_btn_onclick);
                    resultbtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                    noticebtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                }
                if (noticeresultRV.getVisibility() == View.VISIBLE) {
                    noticeresultRV.setVisibility(View.GONE);
                }
                if (timelineRL.getVisibility() == View.INVISIBLE || timelineRL.getVisibility() == View.GONE) {
                    timelineRL.setVisibility(View.VISIBLE);
                }

            }
        });

        noticebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                writepost.setText("Write Notice");
                btnlabel = noticebtn.getText().toString();
                if (v == noticebtn) {
                    noticebtn.setBackgroundResource(R.drawable.round_post_feed_btn_onclick);
                    timelinebtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                    resultbtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                }
                if (timelineRL.getVisibility() == View.VISIBLE) {
                    timelineRL.setVisibility(View.GONE);

                }
                if (noticeresultRV.getVisibility() == View.INVISIBLE || noticeresultRV.getVisibility() == View.GONE) {
                    noticeresultRV.setVisibility(View.VISIBLE);

                }

            }
        });

        resultbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                writepost.setText("Write Result");
                btnlabel = resultbtn.getText().toString();
                if (v == resultbtn) {
                    noticebtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                    timelinebtn.setBackgroundResource(R.drawable.round_pop_post_feed_btn);
                    resultbtn.setBackgroundResource(R.drawable.round_post_feed_btn_onclick);
                }
                if (timelineRL.getVisibility() == View.VISIBLE) {
                    timelineRL.setVisibility(View.GONE);

                }
                if (noticeresultRV.getVisibility() == View.INVISIBLE || noticeresultRV.getVisibility() == View.GONE) {
                    noticeresultRV.setVisibility(View.VISIBLE);

                }
            }

        });


    }


    private void beforeLoadingDialog() {

        categories.clear();
        categories.add("Technical Event");
        categories.add("Cultural Event");
        categories.add("Miscellaneous");

        dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryevent.setAdapter(dataAdapter);

        dataAdapter1 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text, eventDetailsList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        event.setAdapter(dataAdapter1);

        dataAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text, subeventDetailsList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subevent.setAdapter(dataAdapter2);

        categoryevent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                event_data = item;
                Log.e("event_data--", item);
                if (item == "Technical Event") {
                    set_event_spinner(dbref.child("technical-events").child("children"));
                    event.setVisibility(View.VISIBLE);
                    subevent.setVisibility(View.VISIBLE);
                } else if (item == "Cultural Event") {
                    set_event_spinner(dbref.child("cultural-events").child("children"));
                    event.setVisibility(View.VISIBLE);
                    subevent.setVisibility(View.VISIBLE);
                } else {
                    event.setVisibility(View.INVISIBLE);
                    subevent.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                Log.e("Sub event", item + "");

                set_subevent_spinner(dbref.child(item).child("children"));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void set_event_spinner(DatabaseReference db) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                eventDetailsList.clear();
                if (dataSnapshot.exists()) {

                    event.setVisibility(View.VISIBLE);


                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        String name = String.valueOf(postSnapshot.child("id").getValue());
                        eventDetailsList.add(name);
                        Log.e("eventDetailsList", eventDetailsList.toString());
                    }
                } else {
                    event.setVisibility(View.INVISIBLE);
                    event_data = "";
                }
                dataAdapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }


    private void set_subevent_spinner(DatabaseReference db) {

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                subeventDetailsList.clear();
                if (dataSnapshot.exists()) {

                    subevent.setVisibility(View.VISIBLE);

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.e("subeventId", postSnapshot.child("id").getValue().toString());

                        String name = String.valueOf(postSnapshot.child("id").getValue());
                        subeventDetailsList.add(name);
                    }

                } else {
                    subevent.setVisibility(View.INVISIBLE);
                    sub_event_data = "";
                }

                dataAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void checkpermission(int i) {
        switch (i) {
            case 0:
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED  &&
                        ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    Log.e("permission",ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE )+ ":"+
                            ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));

                    loadimage();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, IMAGE_PERMISSION_REQUEST);
                    }
                }
                break;

            case 1:
                if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED  &&
                        ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Log.e("permission",ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE )+ ":"+
                            ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE));

                    uploadfile();
                } else {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, PDF_PERMISSION_REQUEST);
                    }
                }

                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case IMAGE_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadimage();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission_Denied", Toast.LENGTH_LONG).show();
                }
                break;

            case PDF_PERMISSION_REQUEST:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    uploadfile();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Permission_Denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_post_fab:
                beforeLoadingDialog();
                    mdialog.show();
                break;

            case R.id.image_loader:
                checkpermission(0);
                break;

            case R.id.add_attachment:
                checkpermission(1);

                break;

            case R.id.close_relative_view:
                mdialog.dismiss();
                break;

            case R.id.publish_btn:
                publish_the_post();
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    private boolean admin_and_resgistered() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(uid).child(StringVariable.USER_ADMIN);

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    Log.e("admin", String.valueOf(dataSnapshot.getValue()));
                    if (String.valueOf(dataSnapshot.getValue()).equals("0")) {
                        fb.setVisibility(View.GONE);
                    } else {
                        fb.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    fb.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return true;
    }


    private void loadimage() {
        Toast.makeText(getContext(),"The image uploaded should not be more than 2MB",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_LOAD_IMAGE);

    }

    private void uploadfile() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("*/*");
        startActivityForResult(intent, RESULT_LOAD_PDF);
    }

    private void publish_the_post() {
        sub_event_data = subevent.getSelectedItem().toString();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Publishing...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        if (post.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please write the caption", Toast.LENGTH_SHORT).show();
        } else {
            switch (type) {
                case 0:
                    Toast.makeText(getActivity(),"Please write the caption",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    progressDialog.show();
                    upload_in_posts();
                    break;
                case 2:
                    progressDialog.show();
                    upload_in_notice(0);
                    break;
                case 3:
                    progressDialog.show();
                    upload_in_notice(1);
                    break;
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_LOAD_IMAGE) {
            Uri selectedImage = data.getData();
            Log.e("image uri", "Uri getted ");
            imgload.setImageURI(selectedImage);
            image_data = selectedImage;
        }

        if (resultCode == RESULT_OK && requestCode == RESULT_LOAD_PDF) {
            Uri selectedpdf = data.getData();
            Uri_List uri_list = new Uri_List();
            uri_list.setUri(data.getData());
            uri_list.setUriFile_name(data.getData().getLastPathSegment());
            mlist.add(uri_list);
            fileapdapter.notifyDataSetChanged();
        }

    }


    private void upload_in_posts() {
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(StringVariable.POSTS).push();
        DatabaseReference db_timeline = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(StringVariable.POSTS);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put(StringVariable.POSTS_PUBLISHER_NAME, Name);
        userMap.put(StringVariable.POSTS_PUBLISHER_USERUID, UserUID);
        userMap.put(StringVariable.POSTS_PUBLISHER_USERIMAGEURL, UserImageUrl);

        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put(StringVariable.POSTS_CONTENT, post.getText().toString());
        mainMap.put(StringVariable.POSTS_LIKES, 0);
        mainMap.put(StringVariable.POSTS_LIKES_BY, 0);
        mainMap.put(StringVariable.POSTS_EVENTNAME, event_data);
        mainMap.put(StringVariable.POSTS_SUBEVENTNAME, sub_event_data);
        mainMap.put(StringVariable.NOTICE_POSTED_BY, userMap);
        mainMap.put(StringVariable.POSTS_TIMESTAMP, System.currentTimeMillis());
        mainMap.put(StringVariable.POST_TYPE,"0");

       /* File compressedImage = new File(image_data.getPath());

        try {
            Log.e("compressed Image",compressedImage.toString());

            compressedImage = new Compressor(getActivity())
                    .setQuality(30)
                    .setCompressFormat(Bitmap.CompressFormat.PNG)
                    .setDestinationDirectoryPath(Environment.getExternalStorageDirectory().getAbsolutePath())
                    .compressToFile(new File(image_data.getPath()));

            Log.e("compressed Image new",compressedImage.toString());

        } catch (IOException e) {
            Log.e("compressed Image",e.getMessage());
            e.printStackTrace();
        }
*/
                /*
                * compressedImage = new Compressor(this)
            .setMaxWidth(640)
            .setMaxHeight(480)
            .setQuality(75)
            .setCompressFormat(Bitmap.CompressFormat.WEBP)
            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
              Environment.DIRECTORY_PICTURES).getAbsolutePath())
            .compressToFile(actualImage);*/
       /* db.setValue(mainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {*/
        if (image_data != null) {
            storageReference.child(db.getKey()).putFile(image_data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            mainMap.put(StringVariable.POSTS_PHOTOURL, taskSnapshot.getDownloadUrl().toString());
                            db.setValue(mainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    db_timeline.child(db.getKey()).setValue(mainMap);
                                    progressDialog.dismiss();
                                    mdialog.dismiss();
                                    Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "File Upload Failed", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "File Upload Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });
        } else {
            mainMap.put(StringVariable.POSTS_PHOTOURL, "");
            db.setValue(mainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    db_timeline.child(db.getKey()).setValue(mainMap);
                    progressDialog.dismiss();
                    mdialog.dismiss();
                    Toast.makeText(getActivity(), "posted", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }


    }
        /*.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Uploading failed", Toast.LENGTH_SHORT).show();
            }
        });*/


    private void upload_in_notice(int j) {
        String ref = "";
        if (j == 0) {
            ref = StringVariable.NOTICE;
        } else if (j == 1) {
            ref = StringVariable.RESULT;
        }
        ArrayList<Uri_List> list = fileapdapter.mlist;

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(ref).push();
        DatabaseReference db_timeline = FirebaseDatabase.getInstance().getReference().child(StringVariable.TIMELINE);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(ref);
        String key = "";

        Map<String, Object> userMap = new HashMap<>();
        userMap.put(StringVariable.NOTICE_NAME, Name);
        userMap.put(StringVariable.NOTICE_USER_UID, UserUID);
        userMap.put(StringVariable.NOTICE_USERIMAGEURL, UserImageUrl);

        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put(StringVariable.NOTICE_LIKES, 0);
        mainMap.put(StringVariable.NOTICE_LIKES_BY, 0);
        mainMap.put(StringVariable.NOTICE_EVENT_NAME, event_data);
        mainMap.put(StringVariable.NOTICE_SUB_EVENT_NAME, sub_event_data);
        mainMap.put(StringVariable.NOTICE_POSTED_BY, userMap);
        mainMap.put(StringVariable.NOTICE_TIMESTAMP, System.currentTimeMillis());

        if (j == 0) {
            mainMap.put(StringVariable.NOTICE_CONTENT, post.getText().toString());
            mainMap.put(StringVariable.POST_TYPE,"1");

        } else if (j == 1) {
            mainMap.put(StringVariable.RESULT_DATA, post.getText().toString());
            mainMap.put(StringVariable.POST_TYPE,"2");

        }

        Urilist.clear();
        if (list.size() != 0) {
            check = 0;
            for (i = 0; i < list.size(); i++) {
                Log.e("list.get(i).getUri()", list.get(i).getUri() + "");
                storageReference.child(db.getKey() + i + list.get(i).getUriFile_name()).putFile(list.get(i).getUri()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //db.child(StringVariable.NOTICE_LINKS).push().setValue(taskSnapshot.getDownloadUrl().toString());
                        check++;
                        Urilist.add(String.valueOf(taskSnapshot.getDownloadUrl()));
                        if (check == list.size()) {
                            mainMap.put(StringVariable.NOTICE_LINKS, Urilist);
                            db.setValue(mainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                        db_timeline.child(db.getKey()).setValue(mainMap);
                                    Toast.makeText(getActivity(), "File Uploaded", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    mdialog.dismiss();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "File Upload Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        } else {
            mainMap.put(StringVariable.NOTICE_LINKS, "");

            db.setValue(mainMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    db_timeline.child(db.getKey()).setValue(mainMap);
                    Toast.makeText(getActivity(), "published", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    mdialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    mdialog.dismiss();
                    Toast.makeText(getActivity(), "File Upload Failed", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }


}
