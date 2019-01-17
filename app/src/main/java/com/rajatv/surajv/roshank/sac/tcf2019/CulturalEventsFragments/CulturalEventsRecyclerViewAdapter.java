package com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.FragmentEventDetailsActivity;

import java.util.List;

public class CulturalEventsRecyclerViewAdapter extends RecyclerView.Adapter<CulturalEventsRecyclerViewAdapter.ViewHolder> {

    public Context context;
    public List<CulturalEvents> eventModalClassList;
    String eventId;

    public CulturalEventsRecyclerViewAdapter(Context context, List<CulturalEvents> eventModalClassList) {
        this.context = context;
        this.eventModalClassList = eventModalClassList;
    }
    public CulturalEventsRecyclerViewAdapter(){}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_cultural_events, viewGroup, false);
        CulturalEventsRecyclerViewAdapter.ViewHolder holder = new CulturalEventsRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CulturalEvents trendingModalClass = eventModalClassList.get(i);
        viewHolder.eventname.setText(trendingModalClass.getEventname());
        String t = trendingModalClass.getAbout();
        if(!t.equals("----")){
            viewHolder.eventname2.setText(t);
            viewHolder.eventname2.setVisibility(View.VISIBLE);

        }else{
            viewHolder.eventname2.setVisibility(View.GONE);
        }
        viewHolder.nameee.setText(trendingModalClass.getId());
        eventId=trendingModalClass.getId();
    }

    @Override
    public int getItemCount() {
       return   eventModalClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView eventname,eventname2,eventid,nameee;
        Intent subeventintent;
        CardView cardView;

        private final String EVENT_NAME = "event name";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_cultural);
            eventname = itemView.findViewById(R.id.eventname_textview);
            eventname2=itemView.findViewById(R.id.eventname_about);
            nameee=itemView.findViewById(R.id.nameeeeee);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subeventintent = new Intent(context,FragmentEventDetailsActivity.class);
                    String culturaleventname = eventname.getText().toString();
                    subeventintent.putExtra("event_name",culturaleventname);
                    Log.e("goingtointent",nameee.getText().toString());
                    subeventintent.putExtra("event_id",nameee.getText().toString());
                    context.startActivity(subeventintent);
                }
            });
        }
    }
}
