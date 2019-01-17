package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.Blogssacapp.MyBlogs;
import com.rajatv.surajv.roshank.sac.MyDashboard.Registered.RegisteredEventsFragment;
import com.rajatv.surajv.roshank.sac.MyDashboard.SpeedDial.SpeedDialFragment;
import com.rajatv.surajv.roshank.sac.MyDashboard.Wishlist.WishListFragment;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentMyDashboard extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyDashboardViewPagerAdapter myDashboardViewPagerAdapter;
    private Dialog mdialog;
    private Dialog ndialog;
    private Button yesbtn;
    private Button nobtn;
    private Spinner sascategoryspinner;
    private Spinner sasdesignationspinner;
    private Spinner tcfeventcategoryspinner;
    private Spinner subeventcategoryspinner;
    private Spinner tcfdesignationspinner;
    private Spinner branch_spinner;

    List<String> sascategorylist = new ArrayList<>();
    List<String> designationlist = new ArrayList<>();
    List<String> otherlist = new ArrayList<>();
    List<String> eventcategorylist = new ArrayList<>();
    List<String> technicalsubeventslist = new ArrayList<>();
    List<String> culturalsubeventlist = new ArrayList<>();
    List<String> tcfdesignationlist = new ArrayList<>();
    List<String> branchlist = new ArrayList<>();

    private Map<String, Object> retriveData = new HashMap<>();
    private String ProfileCompleted = "0.0";
    Toolbar toolbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_dashboard_activity, container, false);
        tabLayout = v.findViewById(R.id.dashboard_tabLayout);
        viewPager = v.findViewById(R.id.dashboard_viewPager_id);
        myDashboardViewPagerAdapter = new MyDashboardViewPagerAdapter(getChildFragmentManager());
        mdialog = new Dialog(getActivity());
        ndialog = new Dialog(getActivity());
        addListToSpinner();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MemberPref",Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt("Memberbox",0);
        if(data==0) {

            firstdialogevent();
        }
        checkData();
        // toolbar = (android.support.v7.widget.Toolbar) v.findViewById(R.id.appbar_dashboard);
        //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(false);
        return v;
    }

    private void firstdialogevent() {
        mdialog.setContentView(R.layout.pop_up_sac);
        mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        mdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //mdialog.show();
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.setCancelable(false);
        yesbtn = mdialog.findViewById(R.id.yes_btn);
        nobtn = mdialog.findViewById(R.id.no_btn);

        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndialog.setContentView(R.layout.pop_up_sac_member_register);
                ndialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                ndialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                ndialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //ndialog.show();
                seconddialogevent();
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MemberPref",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Memberbox",1);
                editor.apply();

            }
        });

    }

    private void seconddialogevent() {
        Button dialogSubmitButton = ndialog.findViewById(R.id.dialog_submit);
        ImageView close_ndialogbox = ndialog.findViewById(R.id.close);
        sascategoryspinner = ndialog.findViewById(R.id.categoryspinner);
        sasdesignationspinner = ndialog.findViewById(R.id.designationspinner);
        tcfeventcategoryspinner = ndialog.findViewById(R.id.eventcategoryspinner);
        tcfdesignationspinner = ndialog.findViewById(R.id.designationtcfspinner);
        subeventcategoryspinner = ndialog.findViewById(R.id.subeventspinner);
        branch_spinner=ndialog.findViewById(R.id.branchspinner);
        tcfdesignationspinner.setVisibility(View.INVISIBLE);
        subeventcategoryspinner.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, sascategorylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sascategoryspinner.setAdapter(dataAdapter);

        sascategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (category.equals("Select")) {
                    sasdesignationspinner.setVisibility(View.INVISIBLE);
                } else {
                    sasdesignationspinner.setVisibility(View.VISIBLE);
                }
                if (category.contains("Council Leader")) {

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, otherlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sasdesignationspinner.setAdapter(dataAdapter);

                } else {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, designationlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sasdesignationspinner.setAdapter(dataAdapter);
                }
                if(category.equals("Sports")||category.equals("Cultural")||category.equals("Discipline")){
                    branch_spinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, branchlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    branch_spinner.setAdapter(dataAdapter);
                    }else{
                    branch_spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> tcfdataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, eventcategorylist);
        tcfdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tcfeventcategoryspinner.setAdapter(tcfdataAdapter);
        tcfeventcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eventcategory = parent.getItemAtPosition(position).toString();

                if (eventcategory.contains("Select")) {
                    subeventcategoryspinner.setVisibility(View.INVISIBLE);
                    tcfdesignationspinner.setVisibility(View.INVISIBLE);
                } else {
                    subeventcategoryspinner.setVisibility(View.VISIBLE);
                }

                if (eventcategory.contains("Technical Event")) {

                    subeventcategoryspinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, technicalsubeventslist);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subeventcategoryspinner.setAdapter(dataAdapter1);

                    subeventcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String subevents = parent.getItemAtPosition(position).toString();
                            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
                            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tcfdesignationspinner.setAdapter(dataAdapter2);

                            if (subeventcategoryspinner.getSelectedItem().toString() == "Select") {
                                tcfdesignationspinner.setVisibility(View.INVISIBLE);
                            } else {
                                tcfdesignationspinner.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (eventcategory.contains("Cultural Event")) {

                    subeventcategoryspinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, culturalsubeventlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subeventcategoryspinner.setAdapter(dataAdapter);

                    subeventcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String subevents = parent.getItemAtPosition(position).toString();
                            if (subevents.equals("Select")) {
                                tcfdesignationspinner.setVisibility(View.INVISIBLE);
                            } else {
                                tcfdesignationspinner.setVisibility(View.VISIBLE);
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tcfdesignationspinner.setAdapter(dataAdapter);
                        }


                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                } else if (eventcategory.contains("Fun Event")) {

                    subeventcategoryspinner.setVisibility(View.GONE);
                    tcfdesignationspinner.setVisibility(View.VISIBLE);
                    tcfdataAdapter.notifyDataSetChanged();
                    dataAdapter.notifyDataSetChanged();

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tcfdesignationspinner.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dialogSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sasCouncil = "";
                String tcfCouncil = "";
                String sasDesignation = "";
                String tcfCategory = "";
                String tcfDesignation = "";
                String branch="";
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Registering you in SAS/TCF list....");
                progressDialog.show();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
                Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
                String userUID = "";
                try {
                    userUID= String.valueOf(obj.get(StringVariable.USER_USER_UID));
                }catch (Exception e){}

                String sasCategory = sascategoryspinner.getSelectedItem().toString();
                String tcfEvent = tcfeventcategoryspinner.getSelectedItem().toString();
                try {
                    sasDesignation = sasdesignationspinner.getSelectedItem().toString();
                }catch (Exception e){}

                try {
                    tcfCategory = subeventcategoryspinner.getSelectedItem().toString();
                }catch (Exception e){}

                try {
                    tcfDesignation = tcfdesignationspinner.getSelectedItem().toString();
                }catch (Exception e){}
                try{

                    branch = branch_spinner.getSelectedItem().toString();
                }catch (Exception e){}


                if(sasCategory.equals("Cultural & Arts")||sasCategory.equals("Sports & Games")||sasCategory.equals("Discipline")) {
                    if (!(sasCategory.equals("Field") || sasDesignation.equals("Post") || sasDesignation.equals("") || branch.equals("Branch") || branch.equals(""))) {
                        sasCouncil = sasCategory + "@SAC2.0@" + sasDesignation + "@SAC2.0@" + branch;
                    }
                }else{
                    if (!(sasCategory.equals("Field") || sasDesignation.equals("Post") || sasDesignation.equals(""))) {
                        sasCouncil = sasCategory + "@SAC2.0@" + sasDesignation ;
                    }

                }
                if(sasCategory.equals("Field")){
                     sasCouncil = "" + "@SAC2.0@" + "";
                }
                else if(sasDesignation.equals("Post")||sasDesignation.equals("")){
                    sasCouncil = sasCategory + "@SAC2.0@" + "";
                }
                else if(branch.equals("Branch")||branch.equals("")){
                    sasCouncil = sasCategory + "@SAC2.0@" + sasDesignation + "@SAC2.0@" + "";
                }
                if(!(tcfEvent.equals("Event type")||tcfCategory.equals("Event")||tcfCategory.equals("")||tcfDesignation.equals("Post")||tcfDesignation.equals(""))){
                    tcfCouncil = tcfEvent + "@SAC2.0@" + tcfCategory + "@SAC2.0@" + tcfDesignation;
                }
                if(tcfEvent.equals("Event type")||tcfEvent.equals("")){
                    tcfCouncil = "" + "@SAC2.0@" + "" + "@SAC2.0@" + "";
                }
                else if(tcfCategory.equals("Event")||tcfCategory.equals("")){
                    tcfCouncil = tcfEvent + "@SAC2.0@" + "" + "@SAC2.0@" + "";
                }
                else if(tcfDesignation.equals("Post")||tcfDesignation.equals("")){
                    tcfCouncil = tcfEvent + "@SAC2.0@" + tcfCategory + "@SAC2.0@" + "";
                }
                if(tcfEvent.equals("Fun Events")){
                    if(tcfDesignation.equals("Post")){
                        tcfCouncil = tcfEvent+"@SAC2.0@"+""+"@SAC2.0@"+"";
                    }else {
                        tcfCouncil = tcfEvent+"@SAC2.0@"+""+"@SAC2.0@"+tcfDesignation;
                    }

                }

//                Log.e("sascouncilUpload",sasCouncil);
//                Log.e("tcfcouncilUpload",tcfCouncil);


                DatabaseReference maindb = FirebaseDatabase.getInstance().getReference();

                if(!(tcfCategory.equals("Select")||tcfEvent.equals("Select")||tcfCategory.equals(""))){
                    DatabaseReference eventsdb = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child(subeventcategoryspinner.getSelectedItem().toString().toLowerCase()).child("profile");
                    eventsdb.push().setValue(userUID);
                }
                if(sasCategory.equals("")||sasCategory.equals("Select")||sasDesignation.equals("")||sasDesignation.equals("Select")){
                    //not submit sascouncil
                }
                else {
                    maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_SASMEMBER).setValue(sasCouncil);
                    maindb.child("SASCOUNCILS").push().setValue(userUID);
                }

                maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_SASMEMBER).setValue(sasCouncil);
//                Log.e("SASUserID",maindb.child(StringVariable.USERS).child(userUID).toString() );
                maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_TCFMEMBER).setValue(tcfCouncil).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                            ndialog.dismiss();
                            mdialog.dismiss();
                        }
                        else {
                            ndialog.dismiss();
                            mdialog.dismiss();
                        }
                        Toast.makeText(getActivity().getApplicationContext(),"Submitted Succesfully",Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MemberPref",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("Memberbox",1);
                        editor.apply();
                    }
                });
            }
        });

        close_ndialogbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndialog.dismiss();
            }
        });

    }

    private void addListToSpinner() {
        sascategorylist.add("Field");
        sascategorylist.add("Council Leader");
        sascategorylist.add("Designing & Social Media");
        sascategorylist.add("App & Web Devlopment");
        sascategorylist.add("Editor");
        sascategorylist.add("Media & PR");
        sascategorylist.add("Cultural & Arts");
        sascategorylist.add("Sports & Games");
        sascategorylist.add("Discipline");


        designationlist.add("Post");
        designationlist.add("Secretory");
        designationlist.add("Cordinator");
        designationlist.add("Co-Cordinator");
        designationlist.add("Member");

        otherlist.add("Post");
        otherlist.add("President");
        otherlist.add("Vice-President");
        otherlist.add("Technical Secretory");

        eventcategorylist.add("Event type");
        eventcategorylist.add("Technical Event");
        eventcategorylist.add("Cultural Event");
        eventcategorylist.add("Fun Events");

        technicalsubeventslist.add("Event");
        technicalsubeventslist.add("ByteWorld");
        technicalsubeventslist.add("Aayam");
        technicalsubeventslist.add("Concrete");
        technicalsubeventslist.add("Robotrix");
        technicalsubeventslist.add("Ohm");
        technicalsubeventslist.add("Navyakalam");
        technicalsubeventslist.add("Yantriki");

        culturalsubeventlist.add("Event");
        culturalsubeventlist.add("Nrityangana");
        culturalsubeventlist.add("Pratibimb");
        culturalsubeventlist.add("Abhinay");
        culturalsubeventlist.add("Avlokan");
        culturalsubeventlist.add("Kalakriti");
        culturalsubeventlist.add("Sanhita");
        culturalsubeventlist.add("Raaga");

        tcfdesignationlist.add("Post");
        tcfdesignationlist.add("Coordinator");
        tcfdesignationlist.add("Co-Coordinator");
        tcfdesignationlist.add("Senior Member");
        tcfdesignationlist.add("Member");

        branchlist.add("Branch");
        branchlist.add("CSE");
        branchlist.add("ECE");
        branchlist.add("EE");
        branchlist.add("ME");
        branchlist.add("CE");
        branchlist.add("Arch");
        branchlist.add("IMSc");


    }


   /* @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setViewPager();

    }*/

    private void setViewPager() {
        myDashboardViewPagerAdapter.clear();

        if (ProfileCompleted.equals("0.0")) {
            Log.e("setViewPager insie 1---", ProfileCompleted);
            myDashboardViewPagerAdapter.addFragment(new ProfileUnregisteredFragment(), "Profile");
            myDashboardViewPagerAdapter.addFragment(new RegisterNowFragment(), "Blogs");
            myDashboardViewPagerAdapter.addFragment(new SpeedDialFragment(), "Speed Dial");
            myDashboardViewPagerAdapter.addFragment(new RegisterNowFragment(), "Wishlist");
            myDashboardViewPagerAdapter.addFragment(new RegisterNowFragment(), "Registered");
            myDashboardViewPagerAdapter.notifyDataSetChanged();
        } else if (ProfileCompleted.equals("1.0")) {
            Log.e("setViewPager insie 2---", ProfileCompleted);
            myDashboardViewPagerAdapter.addFragment(new ProfileRegisteredFragment(), "Profile");
            myDashboardViewPagerAdapter.addFragment(new MyBlogs(), "Blogs");
            myDashboardViewPagerAdapter.addFragment(new SpeedDialFragment(), "Speed Dial");
            myDashboardViewPagerAdapter.addFragment(new WishListFragment(), "Wishlist");
            myDashboardViewPagerAdapter.addFragment(new RegisteredEventsFragment(), "Registered");
            myDashboardViewPagerAdapter.notifyDataSetChanged();
        }

        viewPager.setAdapter(myDashboardViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void checkData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
//        Log.e("profileData",data.toString());
        try {
            Map<String, Object> obj = gson.fromJson(data, retriveData.getClass());
            ProfileCompleted = ((Map<String, Object>) obj.get(StringVariable.APP)).get(StringVariable.USER_IS_PROFILE_COMPLETED).toString();
//            Log.e("ProfileCompleted", ProfileCompleted);

        } catch (Exception e) {
//            Log.e("Exception My Dashboard", e.getMessage());
            ProfileCompleted = "0.0";
        }


    }

    @Override
    public void onResume() {
//        Log.e("FragmentMyDashboard", "OnResume Dashboard---");
        checkData();
//         Log.e("ProfileCompletedResu", ProfileCompleted);
        super.onResume();
        setViewPager();
    }

    @Override
    public void onStop() {
//        Log.e("FragmentMyDashboard", "OnStop Dashboard---");
        super.onStop();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.dashboard_settings_menu, menu);

        MenuItem dashboardSettings = menu.findItem(R.id.settings_menu);
        dashboardSettings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getContext(), "clicked", Toast.LENGTH_LONG).show();
                return false;
            }
        });

    }
}