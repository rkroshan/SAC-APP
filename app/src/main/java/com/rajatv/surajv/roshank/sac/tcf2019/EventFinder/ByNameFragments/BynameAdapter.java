package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByNameFragments;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Description;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BynameAdapter extends RecyclerView.Adapter<BynameAdapter.ViewHolder> implements Filterable {

    DatabaseReference databaseReference;
    private Context context;
    List<BynameModalClass> subEventList;
    List<BynameModalClass> subEventListfull;

    public BynameAdapter(Context context, List<BynameModalClass> subeventDetailsList) {
        this.context = context;
        this.subEventList = subeventDetailsList;
        subEventListfull = subeventDetailsList;
    }

    public BynameAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_event_finder_by_name, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dateString = "null";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("hh;mm;ss dd/MM/yyyy");
            dateString = formatter.format(new Date(Long.parseLong(subEventList.get(i).getCurrenttime())));
        } catch (NumberFormatException n) {

        }
        viewHolder.subeventname.setText(subEventList.get(i).getSubeventname());

        viewHolder.currenttime.setText(dateString);
        viewHolder.location.setText(subEventList.get(i).getCurrentplace());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());

        viewHolder.currentItem = subEventList.get(i);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        public View view;
        public BynameModalClass currentItem;
        Intent intent;

        private final String SUB_EVENT_NAME = "event-name";



        //ADD ELEMENT VARIABLES
        private ImageView ic_time, ic_location, ic_addition, ic_heart;
        private TextView subeventname, currenttime, location, register_yourself, registration_open, added_to_wishlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            recyclerView = (RecyclerView) itemView.findViewById(R.id.subevent_details_recyclerView);

            //ADD ELEMENTS VARIABLES HERE
            subeventname = itemView.findViewById(R.id.tv_subeventName);
            currenttime = itemView.findViewById(R.id.tv_event_time_suvevent);
            location = itemView.findViewById(R.id.tv_venue_subEvent);
            ic_time = itemView.findViewById(R.id.clocksymbol);
            ic_location = itemView.findViewById(R.id.locationsymbol);
            ic_addition = itemView.findViewById(R.id.additionsymbol);
            ic_heart = itemView.findViewById(R.id.heartsymbol);
            register_yourself = itemView.findViewById(R.id.registrationpath);
            registration_open = itemView.findViewById(R.id.tv_registrationOpen_subEvent);
            added_to_wishlist = itemView.findViewById(R.id.wishlist);

            //click event.

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    intent = new Intent(context, Description.class);
                    intent.putExtra(SUB_EVENT_NAME, currentItem.getUseruid());

                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return subEventList.size();
    }

    @Override
    public Filter getFilter() {
        return personalityfilter;
    }

    private Filter personalityfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<BynameModalClass> filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist = subEventListfull;
                Log.e("FilterList", filterlist.size() + "");
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (BynameModalClass item : subEventListfull) {
                    if (item.getSubeventname().toLowerCase().contains(filterpattern)) {
                        filterlist.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            subEventList = (List<BynameModalClass>) results.values;
            notifyDataSetChanged();
        }
    };

}