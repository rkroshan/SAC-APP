package com.rajatv.surajv.roshank.sac.tcf2019.TechnicalEvents;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.FragmentEventDetailsActivity;

import java.util.List;

public class TechnicalEventsRecyclerViewAdapter extends RecyclerView.Adapter<TechnicalEventsRecyclerViewAdapter.ViewHolder> {
    public Context context;
    public List<TechnicalEvents> eventModalClassList;

    public TechnicalEventsRecyclerViewAdapter(Context context, List<TechnicalEvents> eventModalClassList) {
        this.context = context;
        this.eventModalClassList = eventModalClassList;
    }
    public TechnicalEventsRecyclerViewAdapter(){}
    @NonNull
    @Override
    public TechnicalEventsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_technical_events, viewGroup, false);
        TechnicalEventsRecyclerViewAdapter.ViewHolder holder = new TechnicalEventsRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicalEventsRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        TechnicalEvents trendingModalClass = eventModalClassList.get(i);
        viewHolder.eventname.setText(trendingModalClass.getEventname());
        String t = trendingModalClass.getEventnameabout();
        if(!t.equals("----")){
            viewHolder.eventname2.setText(t);
            viewHolder.eventname2.setVisibility(View.VISIBLE);
        }else{
            viewHolder.eventname2.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return   eventModalClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView eventname,eventname2;
        public CardView eventcard;
        TechnicalEvents currentItem;
        Intent subeventintent;

        private final String EVENT_NAME = "event name";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventname = itemView.findViewById(R.id.eventname_textview_technical);
            eventname2=itemView.findViewById(R.id.eventname_about_technical);
            eventcard=itemView.findViewById(R.id.eventtechnical_card);

            eventcard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subeventintent = new Intent(context,FragmentEventDetailsActivity.class);
                    String culturaleventname = eventname.getText().toString();
                    subeventintent.putExtra("event_name",culturaleventname);
                    context.startActivity(subeventintent);
                }
            });
        }
    }
}
