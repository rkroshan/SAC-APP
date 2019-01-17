package com.rajatv.surajv.roshank.sac.Pings;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.OthersProfile.OthersProfile;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;

public class PingAdapter extends RecyclerView.Adapter<PingAdapter.ViewHolder> {
   private Context context;
    ArrayList<Ping> pinglist;
    private DatabaseReference dbref;
    private String userUID;


    public PingAdapter(Context context, ArrayList<Ping> pinglist) {
        this.context = context;
        this.pinglist = pinglist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.element_ping_req,viewGroup,false);
        PingAdapter.ViewHolder holder= new PingAdapter.ViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.pinguserimage.setImageURI(pinglist.get(i).getProfilePicUrl());
        viewHolder.pingusername.setText(pinglist.get(i).getName());
        userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
try {
    dbref = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_PING);
}catch (Exception e){

}
        try {
            viewHolder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
try {
    dbref.child(pinglist.get(i).getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0")){
               try {
                   dbref.child(pinglist.get(i).getUserUid()).setValue(1);
               }
               catch (Exception e){}
//                    viewHolder.itemView.setVisibility(View.GONE);
             //   Log.e("PingAdapater:",String.valueOf(pinglist.size()));
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

}
catch (Exception e){}
                    if(pinglist.size()==1){
                     //  pinglist.clear();
                    }
                    notifyDataSetChanged();
                }
            });
        } catch (Exception e) {

        }

        try {
            viewHolder.gotoProfileRelative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        dbref.child(pinglist.get(i).getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0")){
                                    Intent intent = new Intent(context, OthersProfile.class);
                                    intent.putExtra("userUID", pinglist.get(i).getUserUid());
                                    context.startActivity(intent);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    catch (Exception e){}
                }
            });
        } catch (Exception e) {

        }

        try {
            viewHolder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        dbref.child(pinglist.get(i).getUserUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(String.valueOf(dataSnapshot.getValue()).equalsIgnoreCase("0")){

                                    try {
                                        dbref.child(pinglist.get(i).getUserUid()).removeValue();

                                    }
                                    catch (Exception e){}

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    catch (Exception e){}
                    if(pinglist.size()==1){
                      //  pinglist.clear();
                    }
                    notifyDataSetChanged();
                }
            });

        }catch (Exception e){

        }
    }
    @Override
    public int getItemCount() {
        return pinglist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      SimpleDraweeView pinguserimage;
      RelativeLayout accept,reject,gotoProfileRelative,fullCard;
      TextView pingusername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pinguserimage=(SimpleDraweeView)itemView.findViewById(R.id.pinguserimage);
            pingusername=(TextView)itemView.findViewById(R.id.pingusername);
            accept=(RelativeLayout)itemView.findViewById(R.id.acceptPingButton);
            reject=itemView.findViewById(R.id.notInterestedPingButton);
            gotoProfileRelative=itemView.findViewById(R.id.gotoProfileRelative);
            fullCard=itemView.findViewById(R.id.fullCard);
        }
    }
}
