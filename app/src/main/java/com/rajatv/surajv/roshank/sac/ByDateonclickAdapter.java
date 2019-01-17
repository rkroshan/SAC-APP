package com.rajatv.surajv.roshank.sac;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.tcf2019.Description;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.FunEvents;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ByDateonclickAdapter extends RecyclerView.Adapter<ByDateonclickAdapter.ViewHolder> {
        DatabaseReference databaseReference;
private Context context;
        List<FunEvents> funEventList;

public ByDateonclickAdapter(Context context, List<FunEvents> subeventDetailsList) {
        this.context = context;
        this.funEventList = subeventDetailsList;
        }

public ByDateonclickAdapter(ValueEventListener valueEventListener, List<FunEvents> subeventDetailsList) {
        }

@NonNull
@Override
public ByDateonclickAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_details_subevents, viewGroup, false);
    ByDateonclickAdapter.ViewHolder holder = new ByDateonclickAdapter.ViewHolder(view);
        return holder;

        }

@Override
public void onBindViewHolder(@NonNull ByDateonclickAdapter.ViewHolder viewHolder, int i) {
        String dateString="null";
        try {
        SimpleDateFormat formatter = new SimpleDateFormat("hh;mm;ss dd/MM/yyyy");
        dateString = formatter.format(new Date(Long.parseLong(funEventList.get(i).getCurrenttime())));
        }
        catch(NumberFormatException n){

        }
        viewHolder.subeventname.setText(funEventList.get(i).getSubeventname());

        viewHolder.currenttime.setText(dateString);
        viewHolder.location.setText(funEventList.get(i).getCurrentplace());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());
        viewHolder.currentItem = funEventList.get(i);





        }
public class ViewHolder extends RecyclerView.ViewHolder {
    RecyclerView recyclerView;
    Intent intent;
    public FunEvents currentItem;

    private final String SUB_EVENT_NAME = "event-name";


    //ADD ELEMENT VARIABLES
    private ImageView ic_time,ic_location,ic_addition,ic_heart;
    private TextView subeventname,currenttime,location,register_yourself,registration_open,added_to_wishlist;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.funevents_recyclerView);

        //ADD ELEMENTS VARIABLES HERE
        subeventname=itemView.findViewById(R.id.tv_subeventName);
        currenttime = itemView.findViewById(R.id.tv_event_time_suvevent);
        location = itemView.findViewById(R.id.tv_venue_subEvent);
        ic_time = itemView.findViewById(R.id.clocksymbol);
        ic_location = itemView.findViewById(R.id.locationsymbol);
        ic_addition = itemView.findViewById(R.id.additionsymbol);
        ic_heart = itemView.findViewById(R.id.heartsymbol);
        register_yourself = itemView.findViewById(R.id.registrationpath);
        registration_open = itemView.findViewById(R.id.tv_registrationOpen_subEvent);
        added_to_wishlist = itemView.findViewById(R.id.wishlist);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context,Description.class);
                intent.putExtra(SUB_EVENT_NAME,currentItem.getUseruid());
                context.startActivity(intent);
            }
        });

    }
}

    @Override
    public int getItemCount() {
        return funEventList.size();
    }


}
