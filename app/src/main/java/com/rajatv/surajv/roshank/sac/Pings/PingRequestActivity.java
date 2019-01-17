package com.rajatv.surajv.roshank.sac.Pings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;

public class PingRequestActivity extends AppCompatActivity  {

    RecyclerView recyclerView;
    ArrayList<Ping> pingList;
    DatabaseReference pingDb,otherDb;
    private String userUID,pinguid;
    private PingAdapter recyclerViewAdapter;
    private LinearLayoutManager linearLayout;
    private ImageView backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ping_request);



        pingList=new ArrayList<>();
//        recyclerView= findViewById(R.id.ping_recyclerView);
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();


        recyclerViewAdapter=new PingAdapter(this,pingList);
        recyclerView=findViewById(R.id.ping_request_recyclerview);
        final LinearLayoutManager linearLayout = new LinearLayoutManager(PingRequestActivity.this);
        linearLayout.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(recyclerViewAdapter);

        backButton=findViewById(R.id.ping_request_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        pingDb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_PING);
        otherDb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS);


        pingDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pingList.clear();


                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    pinguid=ds.getKey();

                    if(String.valueOf(ds.getValue()).equalsIgnoreCase("0"))
                    otherDb.child(pinguid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot1) {
                            try{
                                pingList.add(new Ping(
                                   String.valueOf(dataSnapshot1.child(StringVariable.USER_IMAGE).getValue()),
                                   String.valueOf(dataSnapshot1.child(StringVariable.USER_NAME).getValue()),
                                   String.valueOf(dataSnapshot1.getKey())

                                ));
                                recyclerViewAdapter.notifyDataSetChanged();
                            }
                            catch (Exception e){}
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                recyclerViewAdapter.notifyDataSetChanged();

            }
        });



    }
}
