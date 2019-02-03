package com.rajatv.surajv.roshank.sac.Intramurals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.List;

public class FixturesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView back;
    TextView gameToolbarText;
    DatabaseReference dbref;
    FixturesAdapter recyclerViewAdapter;
    String gender;
    private List<FixturesModal> fixturesList;
    String gameName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);

        fixturesList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_fixtures);
        back = findViewById(R.id.back_btn);
        gameToolbarText = findViewById(R.id.gameName);
        gameName = getIntent().getExtras().getString("gamename");
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewAdapter=new FixturesAdapter(FixturesActivity.this,fixturesList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);

        gender = "";
        gender = getIntent().getExtras().getString("menorwomen");
        gameToolbarText.setText(getIntent().getExtras().getString("gamename"));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dbref = FirebaseDatabase.getInstance().getReference().child(StringVariable.INTRAMURALS).child(gender).child(getIntent().getExtras().getString("gamename"));
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(gender.equalsIgnoreCase("men")){
                    fixturesList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.e("datasnapshot",ds.toString());
                        fixturesList.add(new FixturesModal(
                                String.valueOf(ds.child("mom").getValue()),
                                String.valueOf(ds.child(StringVariable.MATCHRESULT).getValue()),
                                String.valueOf(ds.child(StringVariable.TEAM1).getValue()),
                                String.valueOf(ds.child(StringVariable.TEAM2).getValue()),
                                String.valueOf(ds.child(StringVariable.STARTTIME).getValue()),
                                String.valueOf(ds.child(StringVariable.ENDTIME).getValue()),
                                String.valueOf(ds.child(StringVariable.TYPE).getValue()),
                                String.valueOf(ds.child(StringVariable.MATCHVENUE).getValue()),
                                gender,
                                ds.getKey(),
                                gameName
                        ));
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }else if(gender.equalsIgnoreCase("women")){
                    fixturesList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        Log.e("datasnapshot",ds.toString());
                        fixturesList.add(new FixturesModal(
                                String.valueOf(ds.child("mom").getValue()),
                                String.valueOf(ds.child(StringVariable.MATCHRESULT).getValue()),
                                String.valueOf(ds.child(StringVariable.TEAM1).getValue()),
                                String.valueOf(ds.child(StringVariable.TEAM2).getValue()),
                                String.valueOf(ds.child(StringVariable.STARTTIME).getValue()),
                                String.valueOf(ds.child(StringVariable.ENDTIME).getValue()),
                                String.valueOf(ds.child(StringVariable.TYPE).getValue()),
                                String.valueOf(ds.child(StringVariable.MATCHVENUE).getValue()),
                                gender,
                                ds.getKey(),
                                gameName
                        ));
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }
                else{

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });

    }
}
