package com.rajatv.surajv.roshank.sac.Intramurals;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {

    private Context context;
    private List<FixturesModal> fixturesList;
    private Dialog publishResult;

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

        if (fixturesList.get(i).getTeam1().equalsIgnoreCase("") || fixturesList.get(i).getTeam1().equalsIgnoreCase(null)) {
            viewHolder.mTeam1.setText("?");
        }
        if (fixturesList.get(i).getTeam2().equalsIgnoreCase("") || fixturesList.get(i).getTeam2().equalsIgnoreCase(null)) {
            viewHolder.mTeam2.setText("?");
        }
        if (fixturesList.get(i).getMan_of_match().equalsIgnoreCase("1"))
            viewHolder.mMatchType.setText("League Match");
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
                viewHolder.mSimpleDrawee1.setBackgroundResource(R.drawable.bg_cse);
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
            default:
                viewHolder.mSimpleDrawee2.setBackgroundResource(R.drawable.bg_civil);
                break;

        }

        if (fixturesList.get(i).getResult().equalsIgnoreCase("")) {
            viewHolder.mResult.setVisibility(View.GONE);
        }
        if (fixturesList.get(i).getMan_of_match().equalsIgnoreCase("")) {
            viewHolder.mMOM.setVisibility(View.GONE);

        }

        viewHolder.mCardViewFixtures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference adminref = FirebaseDatabase.getInstance().getReference().child("ResultAdmins");
                adminref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {
                            if (dataSnapshot.toString().contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                openDialog();

                            }
                        } catch (Exception e) {
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                openDialog();
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

        }
    }

    private void openDialog() {
        publishResult = new Dialog(context);
        publishResult.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);

        publishResult.setContentView(R.layout.popup_intramurals_result);
        publishResult.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        publishResult.show();
        TextView submitButton = publishResult.findViewById(R.id.dialog_submit_intra);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
