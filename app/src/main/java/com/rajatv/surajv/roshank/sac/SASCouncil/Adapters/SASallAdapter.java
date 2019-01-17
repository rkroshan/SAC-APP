package com.rajatv.surajv.roshank.sac.SASCouncil.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Contacts;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class SASallAdapter extends RecyclerView.Adapter<SASallAdapter.ViewHolder> {

    DatabaseReference contactsDatabase;
    private Context context;
    List<Contacts> contactsList;

    public SASallAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    public SASallAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_contact_events, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        //   viewHolder.profilePhoto.setImageDrawable();


        viewHolder.username.setText(contactsList.get(i).getName());
        viewHolder.designation.setText(contactsList.get(i).getDesignation());

        //  viewHolder.username.setText(contactsList.get(i).getName());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());




    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private ImageView profilePhoto;
        private TextView username,designation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.contacts_events_recycler_view);
            profilePhoto=itemView.findViewById(R.id.iv_contact_image);
            username=itemView.findViewById(R.id.tv_contact_name);
            designation=itemView.findViewById(R.id.tv_contact_designation);




        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}