package com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Contacts;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder> {

    DatabaseReference contactsDatabase;
    private Context context;
    List<Contacts> contactsList;

    public ContactsRecyclerViewAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    public ContactsRecyclerViewAdapter() {
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

Log.e("profilePicture",contactsList.get(i).getProfile_pic());
        viewHolder.username.setText(contactsList.get(i).getName());
        viewHolder.designation.setText(contactsList.get(i).getDesignation());
        viewHolder.profilePhoto.setImageURI(Uri.parse(contactsList.get(i).getProfile_pic()));
        //  viewHolder.username.setText(contactsList.get(i).getName());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());


        viewHolder.callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + contactsList.get(i).getPhone()));
                v.getContext().startActivity(callIntent);
            }
        });
        viewHolder.whatsappButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+contactsList.get(i).getWhatsapp()));
                intent.setPackage("com.whatsapp");
                v.getContext().startActivity(intent);
            }


        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private SimpleDraweeView profilePhoto;
        private TextView username,designation;
        ImageView callButton,whatsappButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.contacts_events_recycler_view);
            profilePhoto=itemView.findViewById(R.id.iv_contact_image);
            username=itemView.findViewById(R.id.tv_contact_name);
            designation=itemView.findViewById(R.id.tv_contact_designation);
            callButton=itemView.findViewById(R.id.callButton);
            whatsappButton=itemView.findViewById(R.id.whatsAppButton);



        }
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

}