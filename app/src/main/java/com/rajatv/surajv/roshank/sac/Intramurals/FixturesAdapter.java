package com.rajatv.surajv.roshank.sac.Intramurals;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;
import java.util.List;

public class FixturesAdapter extends RecyclerView.Adapter<FixturesAdapter.ViewHolder> {

    private Context context;
    private List<FixturesModal> fixturesList;
    public FixturesAdapter(){}

    public FixturesAdapter(Context context, List<FixturesModal> fixturesList){
        this.context=context;
        this.fixturesList=fixturesList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.element_versus_intramurals,viewGroup,false);

        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Log.e("amanData",fixturesList.get(i).toString());
        viewHolder.mTeam1.setText(fixturesList.get(i).getTeam1());
        viewHolder.mTeam2.setText(fixturesList.get(i).getTeam2());
        if(fixturesList.get(i).getMan_of_match().equalsIgnoreCase("1"))
        viewHolder.mMatchType.setText("League Match");
        viewHolder.mdateTime.setText(fixturesList.get(i).getTime_from());
        viewHolder.mVenue.setText(fixturesList.get(i).getVenue());
        viewHolder.mResult.setText(fixturesList.get(i).getResult());
        viewHolder.mMOM.setText(fixturesList.get(i).getMan_of_match());

        if(fixturesList.get(i).getResult().equalsIgnoreCase("")){
            viewHolder.mResult.setVisibility(View.GONE);
        }
        if(fixturesList.get(i).getMan_of_match().equalsIgnoreCase("")){
            viewHolder.mMOM.setVisibility(View.GONE);

        }


    }

    @Override
    public int getItemCount() {
        return fixturesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mMatchType,mdateTime,mVenue,mTeam1,mTeam2,mResult,mMOM;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mMatchType=itemView.findViewById(R.id.element_versus_intramurals_tv_matchtype);
            mdateTime=itemView.findViewById(R.id.element_versus_intramurals_tv_dateTime);
            mVenue=itemView.findViewById(R.id.element_versus_intramurals_tv_venue);
            mTeam1=itemView.findViewById(R.id.element_versus_intramurals_team1_tv);
            mTeam2=itemView.findViewById(R.id.element_versus_intramurals_team2_tv);
            mResult=itemView.findViewById(R.id.element_versus_intramurals_tv_result);
            mMOM=itemView.findViewById(R.id.element_versus_intramurals_manofmatch);

        }
    }
}
