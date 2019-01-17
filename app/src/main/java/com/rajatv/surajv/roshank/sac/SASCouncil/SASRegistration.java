package com.rajatv.surajv.roshank.sac.SASCouncil;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.List;

public class SASRegistration extends AppCompatActivity {

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
    String userUID;
    ProgressDialog progressDialog;
    TextView alreadyRegistered;

    List<String> sascategorylist = new ArrayList<>();
    List<String> designationlist = new ArrayList<>();
    List<String> otherlist = new ArrayList<>();
    List<String> eventcategorylist = new ArrayList<>();
    List<String> technicalsubeventslist = new ArrayList<>();
    List<String> culturalsubeventlist = new ArrayList<>();
    List<String> tcfdesignationlist = new ArrayList<>();
    List<String> branchlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sasregistration);

        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
       // Log.e("SASREG", userUID);
        progressDialog = new ProgressDialog(SASRegistration.this);
        progressDialog.setMessage("Registering you in SAS/TCF list....");
        alreadyRegistered = findViewById(R.id.alreadyRegistered);
        mdialog = new Dialog(this);
        ndialog = new Dialog(this);
        addListToSpinner();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
        int data = sharedPreferences.getInt("Memberbox", 0);
        if (data == 0) {
            firstdialogevent();
            alreadyRegistered.setVisibility(View.GONE);
        }
        if(data==1){
            alreadyRegistered.setVisibility(View.VISIBLE);
        }
    }

    private void firstdialogevent() {
        mdialog.setContentView(R.layout.pop_up_sac);
        try {
            mdialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
            mdialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        } catch (Exception e) {

        }
        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mdialog.show();
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.setCancelable(false);
        yesbtn = mdialog.findViewById(R.id.yes_btn);
        nobtn = mdialog.findViewById(R.id.no_btn);

        yesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndialog.setContentView(R.layout.pop_up_sac_member_register);
                try {
                    ndialog.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                    ndialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                } catch (Exception e) {

                }
                ndialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                ndialog.show();
                seconddialogevent();
            }
        });
        nobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.dismiss();
                finish();
                SharedPreferences sharedPreferences = getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("Memberbox", 1);
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
        branch_spinner = ndialog.findViewById(R.id.branchspinner);
        tcfdesignationspinner.setVisibility(View.INVISIBLE);
        subeventcategoryspinner.setVisibility(View.INVISIBLE);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, sascategorylist);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sascategoryspinner.setAdapter(dataAdapter);

        sascategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                if (category.equals("Field")) {
                    sasdesignationspinner.setVisibility(View.INVISIBLE);
                } else {
                    sasdesignationspinner.setVisibility(View.VISIBLE);
                }
                if (category.contains("Council Leader")) {

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, otherlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sasdesignationspinner.setAdapter(dataAdapter);

                } else {
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, designationlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sasdesignationspinner.setAdapter(dataAdapter);
                }
                if (category.equals("Sports & Games") || category.equals("Cultural & Arts") || category.equals("Discipline")) {
                    branch_spinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, branchlist);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    branch_spinner.setAdapter(dataAdapter);
                } else {
                    branch_spinner.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> tcfdataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, eventcategorylist);
        tcfdataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tcfeventcategoryspinner.setAdapter(tcfdataAdapter);
        tcfeventcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String eventcategory = parent.getItemAtPosition(position).toString();

                if (eventcategory.contains("Event Type")) {
                    subeventcategoryspinner.setVisibility(View.INVISIBLE);
                    tcfdesignationspinner.setVisibility(View.INVISIBLE);
                } else {
                    subeventcategoryspinner.setVisibility(View.VISIBLE);
                }

                if (eventcategory.contains("Technical Event")) {

                    subeventcategoryspinner.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, technicalsubeventslist);
                    dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subeventcategoryspinner.setAdapter(dataAdapter1);

                    subeventcategoryspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String subevents = parent.getItemAtPosition(position).toString();
                            ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
                            dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            tcfdesignationspinner.setAdapter(dataAdapter2);

                            if (subeventcategoryspinner.getSelectedItem().toString() == "Event") {
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
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, culturalsubeventlist);
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
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
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

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text_white, tcfdesignationlist);
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
                String branch = "";
                int flagTCF = 0;
                int flagSAS = 0;
//                progressDialog.show();

                String sasCategory = sascategoryspinner.getSelectedItem().toString();
                String tcfEvent = tcfeventcategoryspinner.getSelectedItem().toString();
                try {
                    sasDesignation = sasdesignationspinner.getSelectedItem().toString();
                } catch (Exception e) {
                }

                try {
                    tcfCategory = subeventcategoryspinner.getSelectedItem().toString();
                } catch (Exception e) {
                }

                try {
                    tcfDesignation = tcfdesignationspinner.getSelectedItem().toString();
                } catch (Exception e) {
                }
                try {

                    branch = branch_spinner.getSelectedItem().toString();
                } catch (Exception e) {
                }


                if (sasCategory.equals("Cultural & Arts") || sasCategory.equals("Sports & Games") || sasCategory.equals("Discipline")) {
                    if (!(sasDesignation.equals("Post") || sasDesignation.equals("") || branch.equals("Branch") || branch.equals(""))) {
                        sasCouncil = sasCategory + "@SAC2.0@" + sasDesignation + "@SAC2.0@" + branch;
                    }
                } else {
                    if (!(sasCategory.equals("Field") || sasDesignation.equals("Post") || sasDesignation.equals(""))) {
                        sasCouncil = sasCategory + "@SAC2.0@" + sasDesignation + "@SAC2.0@" + "";
                    }
                }

                if (!(tcfEvent.equals("Event Type") || tcfCategory.equals("Event") || tcfCategory.equals("") || tcfDesignation.equals("Post") || tcfDesignation.equals(""))) {
                    tcfCouncil = tcfEvent + "@SAC2.0@" + tcfCategory + "@SAC2.0@" + tcfDesignation;
                }
                if (tcfEvent.equals("Fun Events")) {
                    if (!tcfDesignation.equals("Post")) {
                        tcfCouncil = tcfEvent + "@SAC2.0@" + "" + "@SAC2.0@" + tcfDesignation;
                    }
                }
//                Log.e("sasReg : sasupload", sasCouncil);
//                Log.e("sasReg : tcfupload", tcfCouncil);

                DatabaseReference maindb = FirebaseDatabase.getInstance().getReference();

                if (!(sasCouncil.equals("") || sasCouncil.equals("@SAC2.0@") || sasCouncil.equals("@SAC2.0@@SAC2.0@"))) {
                    maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_SASMEMBER).setValue(sasCouncil);
                    maindb.child("SASCOUNCILS").push().setValue(userUID);
                    SharedPreferences sharedPreferences = getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Memberbox", 1);
                    flagSAS = 1;
                    mdialog.dismiss();
                    ndialog.dismiss();
                    editor.apply();
                } else {
                    flagSAS = -1;
                    SharedPreferences sharedPreferences = getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Memberbox", 0);
                    editor.apply();
                }

                if (!(tcfCouncil.equals("") || tcfCouncil.equals("@SAC2.0@") || tcfCouncil.equals("@SAC2.0@@SAC2.0@"))) {
                    maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_TCFMEMBER).setValue(tcfCouncil);
                    if (subeventcategoryspinner.getSelectedItem().toString().toLowerCase().equals("kalakriti")) {
                        DatabaseReference eventsdb = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child("Kalakriti").child("profile");
                    } else {
                        DatabaseReference eventsdb = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child(subeventcategoryspinner.getSelectedItem().toString().toLowerCase()).child("profile");
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Memberbox", 1);
                    flagTCF = 1;
                    mdialog.dismiss();
                    ndialog.dismiss();
                    editor.apply();
                } else {
                    flagTCF = -1;
                    SharedPreferences sharedPreferences = getSharedPreferences("MemberPref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("Memberbox", 0);
                    editor.apply();
                }

                if (flagTCF == -1 && flagSAS == -1) {
                    Toast.makeText(SASRegistration.this, "Please fill the details", Toast.LENGTH_LONG).show();
                } else if (flagSAS == 1 && flagTCF == -1) {
                    Toast.makeText(SASRegistration.this, "SAS details uploaded", Toast.LENGTH_LONG).show();
                } else if (flagTCF == 1 && flagSAS == -1) {
                    Toast.makeText(SASRegistration.this, "TCF details uploaded", Toast.LENGTH_LONG).show();
                } else if (flagSAS == 1 && flagTCF == 1) {
                    Toast.makeText(SASRegistration.this, "SAS and TCF details uploaded", Toast.LENGTH_LONG).show();
                    finish();
                }
//                if(!(tcfCategory.equals("Event")||tcfEvent.equals("Event Type")||tcfCategory.equals(""))){
//                    DatabaseReference eventsdb = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS).child(subeventcategoryspinner.getSelectedItem().toString().toLowerCase()).child("profile");
//                    eventsdb.push().setValue(userUID);
//                    Log.e("sasReg : useruid",userUID);
//                }
//                if(sasCategory.equals("")||sasCategory.equals("Feild")||sasDesignation.equals("")||sasDesignation.equals("Post")){
//                    //not submit sascouncil
//                }
//                else {
//                    maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_SASMEMBER).setValue(sasCouncil);
//                    maindb.child("SASCOUNCILS").push().setValue(userUID);
//                }
//
//                maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_SASMEMBER).setValue(sasCouncil);
//                maindb.child(StringVariable.USERS).child(userUID).child(StringVariable.USER_IS_TCFMEMBER).setValue(tcfCouncil).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(progressDialog.isShowing()){
//                            progressDialog.dismiss();
//                            ndialog.dismiss();
//                            mdialog.dismiss();
//                        }
//                        else {
//                            ndialog.dismiss();
//                            mdialog.dismiss();
//                        }
//                        Toast.makeText(getApplicationContext(),"Submitted Succesfully",Toast.LENGTH_SHORT).show();
//                        SharedPreferences sharedPreferences = getSharedPreferences("MemberPref",Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putInt("Memberbox",1);
//                        editor.apply();
//                    }
//                });
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
        sascategorylist.add("App & Web Development");
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

        eventcategorylist.add("Event Type");
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
        tcfdesignationlist.add("Cordinator");
        tcfdesignationlist.add("Co-Cordinator");
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

}
