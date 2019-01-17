package com.rajatv.surajv.roshank.sac.tcf2019;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Descriptions;

import java.util.ArrayList;

public class DescriptionRecyclerViewAdapter extends RecyclerView.Adapter<DescriptionRecyclerViewAdapter.ViewHolder> {

    ArrayList<Descriptions> eventList;
    Context context;

    public DescriptionRecyclerViewAdapter(Context context,ArrayList<Descriptions> eventList) {
        this.eventList = eventList;
        this.context = context;
    }

    @NonNull
    @Override
    public DescriptionRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_description, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DescriptionRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        viewHolder.titleView.setText(eventList.get(i).getEventTitle());
        viewHolder.eventDescription.setText(eventList.get(i).getEventDescription());
        viewHolder.eventRules.setText(eventList.get(i).getEventRules());
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView eventDescription;
        TextView eventRules;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleView = itemView.findViewById(R.id.roundtitle);
            eventDescription = itemView.findViewById(R.id.event_description);
            eventRules = itemView.findViewById(R.id.event_rules);
        }
    }
}
