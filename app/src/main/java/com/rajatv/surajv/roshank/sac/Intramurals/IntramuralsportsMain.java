package com.rajatv.surajv.roshank.sac.Intramurals;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.rajatv.surajv.roshank.sac.MyDashboard.RegisterActivity;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by CREATOR on 2/5/2018.
 */

public class IntramuralsportsMain extends AppCompatActivity {

    private static final String TAG = "INTRAMURALS_MAIN";
    private RadioGroup rb_gender_group, rb_group_players, rb_group_degree;
    private Spinner intramuralsports_main_game_type_spinner;//intramuralsports_main_game_spinner
    private LinearLayout player1_detail_layout, player2_detail_layout;
    private EditText intramuralsports_main_name1, intramuralsports_main_roll1, intramuralsports_main_emailId1, intramuralsports_main_branch1,
            intramuralsports_main_mobile1,
            intramuralsports_main_name2, intramuralsports_main_roll2, intramuralsports_main_emailId2, intramuralsports_main_branch2,
            intramuralsports_main_mobile2;
    private Button intramuralsports_main_register;
    private ArrayList<String> gamelist = new ArrayList<>();
    private ArrayAdapter<String> gameListArrayAdapter, gametypeListArrayAdapter;
    private ArrayList<String> game_type_list = new ArrayList<>();
    private View focusview;
    private int game_type, gamecode;
    private String name, roll, emailid, branch, mobileno;
    private SharedPreferences sharedPreferences, sharedpref_intramurals, student_pref;
    private String user_name, user_roll, user_branch, user_mobile, user_emailid, isProfileComplete;
    private ProgressDialog progressDialog;
    private String GAME_GENDER, gameNameIntent,user_gender;
    private ImageView intramuralsports_back;
    private TextView game_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.intramuralsports_main);
        init();

    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        sharedpref_intramurals = getSharedPreferences(StringVariable.SHAREDPREFERENCES_INTRAMURALS, 0);
        student_pref = getSharedPreferences(StringVariable.SHAREDPREFERENCE_STUDENTDATA, 0);
        intramuralsports_back = (ImageView) findViewById(R.id.intramuralsports_back);
        intramuralsports_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        game_name = (TextView) findViewById(R.id.intramuralsports_main_game_spinner);
        gameListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gamelist);
        gameListArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        intramuralsports_main_game_spinner.setAdapter(gameListArrayAdapter);

        intramuralsports_main_game_type_spinner = (Spinner) findViewById(R.id.intramuralsports_main_game_type_spinner);
        gametypeListArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, game_type_list);
        gametypeListArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        intramuralsports_main_game_type_spinner.setAdapter(gametypeListArrayAdapter);

        player1_detail_layout = (LinearLayout) findViewById(R.id.player1_detail_layout);
        player2_detail_layout = (LinearLayout) findViewById(R.id.player2_detail_layout);
        intramuralsports_main_register = (Button) findViewById(R.id.intramuralsports_main_register);
        intramuralsports_main_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedpref_intramurals.getString(game_name.getText().toString()
                        + intramuralsports_main_game_type_spinner.getSelectedItem().toString(), "").equals("1")) {
                    intramuralsports_main_register.setEnabled(false);
                    Toast.makeText(getApplicationContext(), "YOU ARE ALREADY REGISTERED", Toast.LENGTH_LONG).show();
                } else {
                     register();      //TODO here
                }
            }
        });
        rb_group_players = (RadioGroup) findViewById(R.id.rb_group_players);
        intramuralsports_main_name1 = (EditText) findViewById(R.id.intramuralsports_main_name1);
        intramuralsports_main_roll1 = (EditText) findViewById(R.id.intramuralsports_main_roll1);
        intramuralsports_main_emailId1 = (EditText) findViewById(R.id.intramuralsports_main_emailId1);
        intramuralsports_main_branch1 = (EditText) findViewById(R.id.intramuralsports_main_branch1);
        intramuralsports_main_mobile1 = (EditText) findViewById(R.id.intramuralsports_main_mobile1);

        intramuralsports_main_name2 = (EditText) findViewById(R.id.intramuralsports_main_name2);
        intramuralsports_main_roll2 = (EditText) findViewById(R.id.intramuralsports_main_roll2);
        intramuralsports_main_emailId2 = (EditText) findViewById(R.id.intramuralsports_main_emailId2);
        intramuralsports_main_branch2 = (EditText) findViewById(R.id.intramuralsports_main_branch2);
        intramuralsports_main_mobile2 = (EditText) findViewById(R.id.intramuralsports_main_mobile2);
        getUserData(); // todo here
        setUserData();

        rb_gender_group = (RadioGroup) findViewById(R.id.rb_gender_group);
        rb_group_degree = (RadioGroup) findViewById(R.id.rb_group_degree);
        final String gender = user_gender;
        if (!isProfileComplete.equalsIgnoreCase("1.0")) {
            Toast.makeText(this, "Register Your Self first", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        } else {
            rb_gender_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    Log.e(TAG, String.valueOf(R.id.rb_men) + " CHECKED-----");
                    switch (i) {
                        case R.id.rb_men:
                            if (gender.equalsIgnoreCase("female")) {
                                Toast.makeText(getApplicationContext(), "Your Gender is Female" +
                                        "", Toast.LENGTH_SHORT).show();
                                rb_gender_group.check(R.id.rb_mixDoubles);

                                rb_gender_group.check(R.id.rb_women);
                            } else {
                                Log.e(TAG, "MEN_INIT");
                                GAME_GENDER = "MEN";
                                Men_init(); //todo
                            }
                            break;
                        case R.id.rb_women:
                            if (gender.equalsIgnoreCase("male")) {
                                Toast.makeText(getApplicationContext(), "Your Gender is Male", Toast.LENGTH_SHORT).show();
                                rb_gender_group.check(R.id.rb_mixDoubles);

                                rb_gender_group.check(R.id.rb_men);

                            } else {
                                Log.e(TAG, "Women_init");
                                GAME_GENDER = "WOMEN";
                                Women_init(); //todo
                            }
                            break;
                        case R.id.rb_mixDoubles:
                            Log.e(TAG, "MixDoubles_init");
                            GAME_GENDER = "MIX DOUBLES";
                            MixDoubles_init();// todo
                    }
                }
            });
        }

    }

    private void setUserData() {
        intramuralsports_main_name1.setText(user_name);
        intramuralsports_main_roll1.setText(user_roll);
        intramuralsports_main_branch1.setText(user_branch);
        intramuralsports_main_emailId1.setText(user_emailid);
        intramuralsports_main_mobile1.setText(user_mobile);

    }

    private void getUserData() {
        sharedPreferences = getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "0");
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        Map<String, Object> app = (Map<String, Object>) obj.get("app");

        Log.e("sharedpref", obj.toString());
        Log.e("shared", "share");
        user_name = String.valueOf(obj.get(StringVariable.USER_NAME));
        user_roll = String.valueOf(obj.get(StringVariable.USER_ROLLNO));
        user_emailid = String.valueOf(obj.get(StringVariable.USER_email));
        user_branch = String.valueOf(obj.get(StringVariable.USER_BRANCH));
        user_mobile = String.valueOf(obj.get(StringVariable.USER_PHONENUMBER));
        isProfileComplete = String.valueOf(app.get(StringVariable.USER_IS_PROFILE_COMPLETED));
        user_gender=String.valueOf(obj.get(StringVariable.USER_GENDER));
    }

    private void MixDoubles_init() {
        init_spinners(2);
        game_type_list.clear();
        gametypeListArrayAdapter.notifyDataSetChanged();
        init_player1layout(false);
        init_player2layout(false);

        try {
              gameNameIntent=getIntent().getExtras().getString("gamename","cricket");//todo
        } catch (Exception e) {
        }

        game_name.setText(gameNameIntent.toUpperCase());
        gamecode = getCode(2, gameNameIntent);

        switch (gamecode) {
            case 0:
                game_type = 0;
                game_type_list.clear();
                gametypeListArrayAdapter.notifyDataSetChanged();
                init_player_radiogroup(false);
                init_player1layout(false);
                init_degree_radiobtns(false);
                break;
            case 1:
                init_degree_radiobtns(false);
                game_type = 2;
                game_type_list.clear();
                game_type_list.add(StringVariable.MIX_DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;

            case 2:
                init_degree_radiobtns(false);
                game_type = 2;
                game_type_list.clear();
                game_type_list.add(StringVariable.MIX_DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;
        }

//        intramuralsports_main_game_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//                    case 0:
//                        game_type = 0;
//                        game_type_list.clear();
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        init_player_radiogroup(false);
//                        init_player1layout(false);
//                        init_degree_radiobtns(false);
//                        break;
//                    case 1:
//                        init_degree_radiobtns(false);
//                        game_type = 2;
//                        game_type_list.clear();
//                        game_type_list.add(StringVariable.MIX_DOUBLES);
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

    private void Women_init() {
        init_spinners(1);
        game_type_list.clear();
        gametypeListArrayAdapter.notifyDataSetChanged();
        init_player1layout(false);
        init_player2layout(false);

        try {
            gameNameIntent=getIntent().getExtras().getString("gamename","cricket");//todo
        } catch (Exception e) {
        }
        game_name.setText(gameNameIntent.toUpperCase());
        gamecode = getCode(1, gameNameIntent);

        switch (gamecode) {
            case 0:
                init_degree_radiobtns(false);
                game_type = 0;
                game_type_list.clear();
                gametypeListArrayAdapter.notifyDataSetChanged();
                init_player_radiogroup(false);
                init_player1layout(false);
                init_degree_radiobtns(false);
                break;
            case 1:
            case 4:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add(StringVariable.SINGLES);
                game_type_list.add(StringVariable.DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;

            case 3:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add(StringVariable.SINGLES);
                game_type_list.add(StringVariable.DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                init_degree_radiobtns(true);
                break;
            case 2:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add(StringVariable.SINGLES);
                game_type_list.add(StringVariable.DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;
            case 5:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add("47KG");
                game_type_list.add("52KG");
                game_type_list.add("57KG");
                game_type_list.add("72KG");
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;


        }

//        intramuralsports_main_game_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//                    case 0:
//                        init_degree_radiobtns(false);
//                        game_type = 0;
//                        game_type_list.clear();
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        init_player_radiogroup(false);
//                        init_player1layout(false);
//                        init_degree_radiobtns(false);
//                        break;
//                    case 1:
//                    case 4:
//                        init_degree_radiobtns(false);
//                        game_type_list.clear();
//                        game_type_list.add(StringVariable.SINGLES);
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        break;
//
//                    case 3:
//                        init_degree_radiobtns(false);
//                        game_type_list.clear();
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        init_degree_radiobtns(true);
//                        break;
//                    case 2:
//                        init_degree_radiobtns(false);
//                        game_type_list.clear();
//                        game_type_list.add(StringVariable.SINGLES);
//                        game_type_list.add(StringVariable.DOUBLES);
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }

    private void Men_init() {
        init_spinners(0);
        game_type_list.clear();
        gametypeListArrayAdapter.notifyDataSetChanged();
        init_player1layout(false);
        init_player2layout(false);


        try {
               gameNameIntent=getIntent().getExtras().getString("gamename","cricket");//todo
        } catch (Exception e) {
        }

        game_name.setText(gameNameIntent.toUpperCase());
        gamecode = getCode(0, gameNameIntent);

        Log.e("gamecode", String.valueOf(gamecode));
        switch (gamecode) {

            case 0:
                game_type = 0;
                game_type_list.clear();
                gametypeListArrayAdapter.notifyDataSetChanged();
                init_player_radiogroup(false);
                init_player1layout(false);
                init_degree_radiobtns(false);
                break;
            case 1:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add(StringVariable.SINGLES);
                gametypeListArrayAdapter.notifyDataSetChanged();
                break;

            case 2:
                init_degree_radiobtns(false);
                game_type_list.clear();
                game_type_list.add(StringVariable.SINGLES);
                game_type_list.add(StringVariable.DOUBLES);
                gametypeListArrayAdapter.notifyDataSetChanged();

        }
//        intramuralsports_main_game_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                switch (i) {
//                    case 0:
//                        game_type = 0;
//                        game_type_list.clear();
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        init_player_radiogroup(false);
//                        init_player1layout(false);
//                        init_degree_radiobtns(false);
//                        break;
//                    case 1:
//                        init_degree_radiobtns(false);
//                        game_type_list.clear();
//                        game_type_list.add(StringVariable.SINGLES);
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                        break;
//
//                    case 2:
//                        init_degree_radiobtns(false);
//                        game_type_list.clear();
//                        game_type_list.add(StringVariable.SINGLES);
//                        game_type_list.add(StringVariable.DOUBLES);
//                        gametypeListArrayAdapter.notifyDataSetChanged();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }


    private void init_degree_radiobtns(boolean status) {
        if (status) {
            rb_group_degree.setVisibility(View.VISIBLE);
        } else {
            rb_group_degree.setVisibility(View.INVISIBLE);
        }
        rb_group_degree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_pg:
                        game_type_list.clear();
                        game_type_list.add(StringVariable.SINGLES);
                        game_type_list.add(StringVariable.DOUBLES);
                        gametypeListArrayAdapter.notifyDataSetChanged();
                        break;

                    case R.id.rb_ug:
                        game_type_list.clear();
                        game_type_list.add("SELECT TYPE");
                        game_type_list.add(StringVariable.DOUBLES);
                        gametypeListArrayAdapter.notifyDataSetChanged();
                        intramuralsports_main_game_type_spinner.setSelection(0);
                        break;
                }
            }
        });
    }


    private void init_spinners(final int j) {
//        intramuralsports_main_game_spinner.setVisibility(View.VISIBLE);
//        setgamelist(j);
        gameListArrayAdapter.notifyDataSetChanged();
//        intramuralsports_main_game_spinner.setSelection(0);

        intramuralsports_main_game_type_spinner.setVisibility(View.VISIBLE);
        intramuralsports_main_game_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (j != 2) {
                    Log.e(TAG, "value of J---" + String.valueOf(j));
                    switch (i) {
                        case 0:
                            init_player2layout(false);
                            game_type = 1;
                            init_player1layout(true);
                            init_player_radiogroup(false);
                            break;
                        case 1:
                            init_player2layout(false);
                            game_type = 2;
                            init_player_radiogroup(true);
                            init_player1layout(true);
                            break;
                    }
                } else {
                    init_player2layout(false);
                    game_type = 2;
                    init_player_radiogroup(true);
                    init_player1layout(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void init_player_radiogroup(boolean status) {
        if (status) {
            rb_group_players.setVisibility(View.VISIBLE);
        } else {
            rb_group_players.setVisibility(View.INVISIBLE);
        }
        rb_group_players.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_player1:
                        init_player1layout(true);
                        player2_detail_layout.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.rb_player2:
                        init_player2layout(true);
                        player1_detail_layout.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        });
    }

    private void init_player1layout(boolean status) {
        if (status) {
            player1_detail_layout.setVisibility(View.VISIBLE);
            setUserData();
            init_regiserBtn(true);
        } else {
            player1_detail_layout.setVisibility(View.INVISIBLE);
            init_regiserBtn(false);

        }
    }

    private void init_player2layout(boolean status) {
        if (status) {
            player2_detail_layout.setVisibility(View.VISIBLE);
            init_regiserBtn(true);
        } else {
            player2_detail_layout.setVisibility(View.INVISIBLE);
            init_regiserBtn(false);
        }
    }

    private void init_regiserBtn(boolean status) {
        if (status) {
            intramuralsports_main_register.setVisibility(View.VISIBLE);
            /*if (sharedpref_intramurals.getString(intramuralsports_main_game_spinner.getSelectedItem().toString()
                    + intramuralsports_main_game_type_spinner.getSelectedItem().toString(), "").equals("1")) {
                intramuralsports_main_register.setEnabled(false);
                intramuralsports_main_register.setText("REGISTERED");
            }else {
                intramuralsports_main_register.setEnabled(true);
                intramuralsports_main_register.setText("REGISTER");
            }*/
        } else {
            intramuralsports_main_register.setVisibility(View.INVISIBLE);
        }
    }

    //    private void setgamelist(int i) {
//        gamelist.clear();
//        switch (i) {
//            case 0:
//                gamelist.add("SELECT GAME");
//                gamelist.add(StringVariable.CHESS);
//                gamelist.add(StringVariable.CARROM);
//                break;
//            case 1:
//                gamelist.add("SELECT GAME");
//                gamelist.add(StringVariable.CHESS);
//                gamelist.add(StringVariable.CARROM);
//                gamelist.add(StringVariable.BADMINTON);
//                gamelist.add(StringVariable.TABLE_TENNIS);
//                break;
//            case 2:
//                gamelist.add("SELECT GAME");
//                gamelist.add(StringVariable.CARROM);
//                break;
//
//        }
//    }


    private void register() {
        Boolean check = true;
        Log.e(TAG, "game_type - " + String.valueOf(game_type));
        if (game_type == 2) {
            name = intramuralsports_main_name2.getText().toString();
            roll = intramuralsports_main_roll2.getText().toString();
            emailid = intramuralsports_main_emailId2.getText().toString();
            branch = intramuralsports_main_branch2.getText().toString();
            mobileno = intramuralsports_main_mobile2.getText().toString();
            Log.e(TAG, "name - " + name);
            Log.e(TAG, "roll - " + roll);
            Log.e(TAG, "emailid - " + emailid);
            Log.e(TAG, "branch - " + branch);
            Log.e(TAG, "mobileno - " + mobileno);
            if (name.isEmpty()) {
                check = false;
                intramuralsports_main_name2.setError("Enter player2 name");
                focusview = intramuralsports_main_name2;
                focusview.requestFocus();
            } else if (roll.isEmpty()) {
                check = false;
                intramuralsports_main_roll2.setError("Enter player2 roll");
                focusview = intramuralsports_main_roll2;
                focusview.requestFocus();
            } else if (emailid.isEmpty()) {
                check = false;
                intramuralsports_main_emailId2.setError("Enter player2 emailid");
                focusview = intramuralsports_main_emailId2;
                focusview.requestFocus();
            } else if (branch.isEmpty()) {
                check = false;
                intramuralsports_main_branch2.setError("Enter player2 branch");
                focusview = intramuralsports_main_branch2;
                focusview.requestFocus();
            } else if (mobileno.isEmpty() | mobileno.length() < 10) {
                check = false;
                intramuralsports_main_mobile2.setError("Enter Valid Mobile No");
                focusview = intramuralsports_main_mobile2;
                focusview.requestFocus();
            }
        } else if (game_type == 0) {
            check = false;
        }
        if (check) {
            registerForIntramurals();
        } else {
            Toast.makeText(getApplicationContext(), "Please Enter Valid Details ", Toast.LENGTH_LONG).show();
        }
    }

    private void registerForIntramurals() {
        progressDialog.setMessage("Registering Please Wait---");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("INTRAMURALS").child(StringVariable.INTRAMURALS_REGISTRATION);

        final String gamename = game_name.getText().toString();
        final String gametype = intramuralsports_main_game_type_spinner.getSelectedItem().toString();
        DatabaseReference dataref = firebaseDatabase.child(gamename).child(GAME_GENDER)
                .child(gametype).child(sharedPreferences.getString(StringVariable.STUDENTDATA_ROLLNO, ""));

        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "0");
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
        Map<String, Object> app = (Map<String, Object>) obj.get("app");

        if (game_type == 1) {
//            String key=dataref.push().getKey();
            DatabaseReference dataref1=dataref.child(String.valueOf(obj.get(StringVariable.USER_ROLLNO)));
            dataref1.child(StringVariable.USER_NAME).setValue(String.valueOf(obj.get(StringVariable.USER_NAME)));
            dataref1.child(StringVariable.USER_ROLLNO).setValue(String.valueOf(obj.get(StringVariable.USER_ROLLNO)));
            dataref1.child(StringVariable.USER_email).setValue(String.valueOf(obj.get(StringVariable.USER_email)));
            dataref1.child(StringVariable.USER_PHONENUMBER).setValue(String.valueOf(obj.get(StringVariable.USER_PHONENUMBER)));
            dataref1.child(StringVariable.USER_BRANCH).setValue(String.valueOf(obj.get(StringVariable.USER_BRANCH)))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            setSharedPreferences(gamename, gametype);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registered, BEST OF LUCK", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
//            String key=dataref.push().getKey();
            DatabaseReference dataref1=dataref.child(String.valueOf(obj.get(StringVariable.USER_ROLLNO)));
            dataref1.child(StringVariable.PLAYER1_NAME).setValue(String.valueOf(obj.get(StringVariable.USER_NAME)));
            dataref1.child(StringVariable.PLAYER1_ROLL).setValue(String.valueOf(obj.get(StringVariable.USER_ROLLNO)));
            dataref1.child(StringVariable.PLAYER1_EMAILID).setValue(String.valueOf(obj.get(StringVariable.USER_email)));
            dataref1.child(StringVariable.PLAYER1_MOBILENO).setValue(String.valueOf(obj.get(StringVariable.USER_PHONENUMBER)));
            dataref1.child(StringVariable.PLAYER1_BRANCH).setValue(String.valueOf(obj.get(StringVariable.USER_BRANCH)));

            dataref1.child(StringVariable.PLAYER2_NAME).setValue(name);
            dataref1.child(StringVariable.PLAYER2_ROLL).setValue(roll);
            dataref1.child(StringVariable.PLAYER2_EMAILID).setValue(emailid);
            dataref1.child(StringVariable.PLAYER2_MOBILENO).setValue(mobileno);
            dataref1.child(StringVariable.PLAYER2_BRANCH).setValue(branch)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            setSharedPreferences(gamename,gametype);
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Registered, BEST OF LUCK", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getCause().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private int getCode(int men_women, String gameNameIntent) {

        gameNameIntent = gameNameIntent.toLowerCase();
        if (men_women == 0) {
            switch (gameNameIntent) {
                case "chess":
                    return 1;
                case "carrom":
                    return 2;
                default:
                    return 0;
            }
        } else if (men_women == 1) {
            switch (gameNameIntent) {
                case "chess":
                    return 1;
                case "carrom":
                    return 2;
                case "badminton":
                    return 3;
                case "table tennis":
                    return 4;
                case "dead lift":
                    return 5;
                default:
                    return 0;
            }

        } else if (men_women == 2) {
            switch (gameNameIntent) {
                case "carrom":
                    return 1;
                case "badminton":
                    return 2;
                case "table tennis":
                    return 3;
                default:
                    return 0;
            }
        } else {
            return 0;
        }
    }
    private void setSharedPreferences(String gametype, String gamename) {
        SharedPreferences.Editor editor = sharedpref_intramurals.edit();
        editor.putString(gamename + gametype, "1");
        editor.apply();
    }


}
