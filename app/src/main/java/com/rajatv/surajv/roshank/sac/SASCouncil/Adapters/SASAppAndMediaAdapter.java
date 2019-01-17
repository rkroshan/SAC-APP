package com.rajatv.surajv.roshank.sac.SASCouncil.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.OthersProfile.OthersProfile;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Contacts;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class SASAppAndMediaAdapter extends RecyclerView.Adapter<SASAppAndMediaAdapter.ViewHolder> {

    DatabaseReference userDb;
    private Context context;
    ArrayList<Contacts> contactsList,newContactsList;
    String speeddialed,userUID,speeddialstring;

    public SASAppAndMediaAdapter(Context context, ArrayList<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;

//        for(Contacts c:contactsList){
//            if(c.getDesignation().equalsIgnoreCase("coordinator")){
//                newContactsList.add(c);
//            }
//        }
//        for(Contacts c:contactsList){
//            if(c.getDesignation().equalsIgnoreCase("member")){
//                newContactsList.add(c);
//            }
//        }
//        contactsList=newContactsList;
    }

    public SASAppAndMediaAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_sas_council, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        speeddialstring="";
        //   viewHolder.profilePhoto.setImageDrawable();

        //Log.e("PhoneNo",contactsList.get(i).getPhone()+contactsList.get(i).getUserUID());
        try {
            userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }catch (Exception e){

        }
        speeddialed="false";
        try {
            if(!contactsList.get(i).getName().equals("null")){
                viewHolder.username.setText(contactsList.get(i).getName());
            }else {
                viewHolder.username.setText("");
            }
            viewHolder.designation.setText(contactsList.get(i).getDesignation());
        }
        catch (Exception e){}
        try {
            viewHolder.profilePhoto.setImageURI(Uri.parse(String.valueOf(contactsList.get(i).getProfile_pic())));
        }catch (Exception e){Log.e("OthersProfile",e.getMessage());}
        //  viewHolder.username.setText(contactsList.get(i).getName());
        //viewHolder.noOfLikes.setText(blogsList.get(i).());

        userDb=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_SPEEDDIAL);
        userDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              //  Log.e("reached sasadap","vhjh");

                speeddialstring=dataSnapshot.toString();
               try {
                   if(speeddialstring.contains(contactsList.get(i).getUserUID())){
                       viewHolder.speedDialStatus.setText("Added to Speed Dial");
                       viewHolder.speed_dial.setImageResource(R.drawable.ic_heart2);
                       userDb.child(userUID).setValue(0);
                   }
                   else {
                       viewHolder.speedDialStatus.setText("Add to Speed Dial");
                       viewHolder.speed_dial.setImageResource(R.drawable.ic_heart);
                   }
                //   Log.e("speeddialstring",speeddialstring);
               }
               catch (Exception e){}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // Log.e("speeddialstring2",speeddialstring);

        viewHolder.speeddialrelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewHolder.speedDialStatus.getText().toString().equalsIgnoreCase("Add to speed dial")) {
                    viewHolder.speedDialStatus.setText("Added to Speed Dial");

                    viewHolder.speed_dial.setImageResource(R.drawable.ic_heart2);
                 //   changingLike(databaseReference.child(s), 1, holder, StringVariable.noticeView);

                        userDb.child(contactsList.get(i).getUserUID()).setValue(0);




                } else {
                    viewHolder.speedDialStatus.setText("Add to Speed Dial");
try {

    viewHolder.speed_dial.setImageResource(R.drawable.ic_heart);
    userDb.child(contactsList.get(i).getUserUID()).removeValue();
}
catch (Exception e){}

                    //     changingLike(databaseReference.child(s), -1, holder, StringVariable.noticeView);
                }
            }
        });
        viewHolder.profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OthersProfile.class);
                intent.putExtra("userUID",contactsList.get(i).getUserUID());
                context.startActivity(intent);
//                        AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
            }
        });viewHolder.gotoProfileRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,OthersProfile.class);
                intent.putExtra("userUID",contactsList.get(i).getUserUID());
                context.startActivity(intent);
//                        AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
            }
        });

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
                PackageManager packageManager = context.getPackageManager();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                try{
                    String url = "https://api.whatsapp.com/send?phone=91"+contactsList.get(i).getWhatsapp();

                    intent.setPackage("com.whatsapp");
                    intent.setData(Uri.parse(url));
                    if(intent.resolveActivity(packageManager)!=null){
                        context.startActivity(intent);
                    }
                }catch (Exception e){e.printStackTrace();}
            }


        });





    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private ImageView profilePhoto,speed_dial;
        private TextView username,designation,speedDialStatus;
        ImageView callButton,whatsappButton;
        RelativeLayout speeddialrelative,gotoProfileRelative;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.contacts_events_recycler_view);
            profilePhoto=itemView.findViewById(R.id.iv_contact_image);
            username=itemView.findViewById(R.id.tv_contact_name);
            designation=itemView.findViewById(R.id.tv_contact_designation);
            callButton=itemView.findViewById(R.id.callButton);
            whatsappButton=itemView.findViewById(R.id.whatsAppButton);
           speeddialrelative=itemView.findViewById(R.id.speeddial_relative);
            speed_dial=itemView.findViewById(R.id.iv_heart);
            speedDialStatus=itemView.findViewById(R.id.tv_add_to_speed_dial);
            gotoProfileRelative=itemView.findViewById(R.id.gotoProfileRelative);



        }
    }

    public void removeItem(int position){
        contactsList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Contacts item,int position){
        contactsList.add(position,item);
        notifyItemInserted(position);
    }
    public ArrayList<Contacts> getData(){
        return contactsList;
    }
    @Override
    public int getItemCount() {

      //  Log.e("sppeddialSize",String.valueOf(contactsList.size()));

        return contactsList.size();
    }

}