package com.rajatv.surajv.roshank.sac.Intramurals;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {

    private Context context;
    private List<FixturesModal> fixturesList;
    private Dialog publishResult;
    String currentUser = "";

    public FixturesAdapter() {
    }

    public FixturesAdapter(Context context, List<FixturesModal> fixturesList) {
        this.context = context;
        this.fixturesList = fixturesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_versus_intramurals, viewGroup, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Log.e("amanData", fixturesList.get(i).toString());
        viewHolder.mTeam1.setText(fixturesList.get(i).getTeam1());
        viewHolder.mTeam2.setText(fixturesList.get(i).getTeam2());
        try {
            currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }

        if (fixturesList.get(i).getTeam1().equalsIgnoreCase("") || fixturesList.get(i).getTeam1().equalsIgnoreCase(null)) {
            viewHolder.mTeam1.setText("?");
        }
        else{

        }
        if (fixturesList.get(i).getTeam2().equalsIgnoreCase("") || fixturesList.get(i).getTeam2().equalsIgnoreCase(null)) {
            viewHolder.mTeam2.setText("?");
        }
        else {

        }
       switch (fixturesList.get(i).getType()){
           case "1":viewHolder.mMatchType.setText("League Match");
           break;
           case "2":viewHolder.mMatchType.setText("Semi Final");
               break;
           case "3":viewHolder.mMatchType.setText("Final");
               break;

       }

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM | hh:mm a");
        Calendar calendar = Calendar.getInstance();
        long timestamp = Long.parseLong(fixturesList.get(i).getTime_from());
        calendar.setTimeInMillis(timestamp);
        String formattedDate = sdf.format(calendar.getTime());
        viewHolder.mdateTime.setText(formattedDate);
        viewHolder.mVenue.setText(fixturesList.get(i).getVenue());
        viewHolder.mResult.setText(fixturesList.get(i).getResult());
        viewHolder.mMOM.setText(fixturesList.get(i).getMan_of_match());

        switch (fixturesList.get(i).getTeam1().toLowerCase()) {
            case "cse":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_cse);
                break;
            case "ece":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_ece);
                break;
            case "imsc":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.imsc);
                break;
            case "arch":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_archi);
                break;
            case "ee":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_ee);
                break;
            case "me":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_mech);
                break;
            case "2k15":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_2k15);
                break;
            case "2k16":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_2k16);
                break;
            case "2k17":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_2k17);
                break;
            case "2k18":
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_2k18);
                break;
            default:
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_civil);
                break;

        }
        switch (fixturesList.get(i).getTeam2().toLowerCase()) {
            case "cse":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_cse);
                break;
            case "ece":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_ece);
                break;
            case "imsc":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.imsc);
                break;
            case "arch":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_archi);
                break;
            case "ee":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_ee);
                break;
            case "me":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_cse);
                break;
            case "2k15":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_2k15);
                break;
            case "2k16":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_2k16);
                break;
            case "2k17":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_2k17);
                break;
            case "2k18":
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_2k18);
                break;
            default:
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_civil);
                break;

        }

        if (fixturesList.get(i).getResult().equalsIgnoreCase("") || fixturesList.get(i).getResult().equalsIgnoreCase("null")) {
            viewHolder.mResult.setVisibility(View.GONE);
        }else {
            viewHolder.mResult.setVisibility(View.VISIBLE);

        }

        if (fixturesList.get(i).getMan_of_match().equalsIgnoreCase("") || fixturesList.get(i).getMan_of_match().equalsIgnoreCase("null")) {
            viewHolder.mMOM.setVisibility(View.GONE);
            viewHolder.trophy1.setVisibility(View.GONE);
            viewHolder.trophy2.setVisibility(View.GONE);
        }else{
            viewHolder.mMOM.setVisibility(View.VISIBLE);
            viewHolder.trophy1.setVisibility(View.VISIBLE);
            viewHolder.trophy2.setVisibility(View.VISIBLE);
        }

        Long currDateTime=System.currentTimeMillis();
        if (currDateTime>=Long.parseLong(fixturesList.get(i).getTime_from()) &&
                currDateTime<=Long.parseLong(fixturesList.get(i).getTime_to())){
            viewHolder.mLive.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.mLive.setVisibility(View.GONE);
        }
        viewHolder.mdateTime.setText(formattedDate);
        viewHolder.mVenue.setText(fixturesList.get(i).getVenue());
        viewHolder.mResult.setText(fixturesList.get(i).getResult());
        viewHolder.mMOM.setText(fixturesList.get(i).getMan_of_match());


        viewHolder.mCardViewFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference adminref= FirebaseDatabase.getInstance().getReference().child("ResultAdmins");
                adminref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try{
                            if(dataSnapshot.toString().contains(currentUser)){
                                publishResult=new Dialog(context);
                                try {
                                    publishResult.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
                                }catch (Exception e){

                                }
                                publishResult.setContentView(R.layout.popup_intramurals_result);
                                publishResult.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                publishResult.show();
                                EditText mResult_tv,mManOfTheMatch_tv;
                                ImageView close;
                                mResult_tv=publishResult.findViewById(R.id.popup_intramurals_result_result_tv);
                                mManOfTheMatch_tv=publishResult.findViewById(R.id.popup_intramurals_result_mom_tv);
                                close = publishResult.findViewById(R.id.close_intra);
                                TextView submitButton = publishResult.findViewById(R.id.dialog_submit_intra);
                                close.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        publishResult.dismiss();
                                    }
                                });
                                submitButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            if(!(mManOfTheMatch_tv.getText().toString().isEmpty()&&mResult_tv.getText().toString().isEmpty())) {
                                                DatabaseReference uploadResultref = FirebaseDatabase.getInstance().getReference().child(StringVariable.INTRAMURALS).child(fixturesList.get(i).getGender()).child(fixturesList.get(i).getGameName()).child(fixturesList.get(i).getFixturesKey());
                                                uploadResultref.child(StringVariable.MATCHRESULT).setValue(mResult_tv.getText().toString());
                                                uploadResultref.child(StringVariable.MANOFTHEMATCH).setValue(mManOfTheMatch_tv.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        publishResult.dismiss();
                                                        notifyDataSetChanged();
                                                    }
                                                });
                                            }
                                            else {
                                                Toast.makeText(context,"Please Write Something",Toast.LENGTH_SHORT).show();
                                            }
                                        }catch (Exception e){
                                            Toast.makeText(context,"Some error occurred",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else{

                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mSimpleDrawee1, mSimpleDrawee2;
        TextView mMatchType, mdateTime, mVenue, mTeam1, mTeam2, mResult, mMOM;
        CardView mCardViewFixtures;
        ImageView trophy1,trophy2,mLive;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMatchType = itemView.findViewById(R.id.element_versus_intramurals_tv_matchtype);
            mdateTime = itemView.findViewById(R.id.element_versus_intramurals_tv_dateTime);
            mVenue = itemView.findViewById(R.id.element_versus_intramurals_tv_venue);
            mTeam1 = itemView.findViewById(R.id.element_versus_intramurals_team1_tv);
            mTeam2 = itemView.findViewById(R.id.element_versus_intramurals_team2_tv);
            mResult = itemView.findViewById(R.id.element_versus_intramurals_tv_result);
            mMOM = itemView.findViewById(R.id.element_versus_intramurals_manofmatch);
            mCardViewFixtures = itemView.findViewById(R.id.cardview_fixtures);
            mSimpleDrawee1 = itemView.findViewById(R.id.team1SimpleDrawee);
            mSimpleDrawee2 = itemView.findViewById(R.id.team2SimpleDrawee);
            trophy1=itemView.findViewById(R.id.trophy1);
            trophy2=itemView.findViewById(R.id.trophy2);
            mLive=itemView.findViewById(R.id.live);
        }
    }

    private void openDialog() {
        publishResult = new Dialog(context);
        try {
            publishResult.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        }catch (Exception e){

        }

}}
