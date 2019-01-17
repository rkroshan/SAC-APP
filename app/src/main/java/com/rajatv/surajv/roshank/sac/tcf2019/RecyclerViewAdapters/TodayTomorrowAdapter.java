package com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.TodayTomorrowModalClass;

import java.util.List;

public class TodayTomorrowAdapter extends  RecyclerView.Adapter<TodayTomorrowAdapter.ViewHolder> {

    Context context;
    public List<TodayTomorrowModalClass> todayEventList,tomorrowEventList;

    public TodayTomorrowAdapter() {
    }

    public TodayTomorrowAdapter(Context context, List<TodayTomorrowModalClass> todayEventList) {
        this.context = context;
        this.todayEventList = todayEventList;
        this.tomorrowEventList=tomorrowEventList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_today_tomorrow_card, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder todayTomorrowViewHolder, int i) {

        TodayTomorrowModalClass todayTomorrowModalClass = todayEventList.get(i);
        todayTomorrowViewHolder.subevent.setText(todayTomorrowModalClass.getSubeventname());
        todayTomorrowViewHolder.eventname.setText(todayTomorrowModalClass.getEventname());
        todayTomorrowViewHolder.eventlocation.setText(todayTomorrowModalClass.getLocation());
        todayTomorrowViewHolder.eventtime.setText(todayTomorrowModalClass.getTime());


    }

    @Override
    public int getItemCount() {
        return todayEventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;

        public TextView eventname;
        public TextView subevent;
        public TextView eventtime;
        public TextView eventlocation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            eventlocation = itemView.findViewById(R.id.event_location_textview);
            eventname = itemView.findViewById(R.id.event_name_textview);
            subevent = itemView.findViewById(R.id.subevent_name_textview);
            eventtime = itemView.findViewById(R.id.event_time_textview);

        }
    }
}