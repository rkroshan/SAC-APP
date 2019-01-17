package com.rajatv.surajv.roshank.sac.MyDashboard.SpeedDial;

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
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Contacts;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class SpeedDialRecyclerViewAdapter extends RecyclerView.Adapter<SpeedDialRecyclerViewAdapter.ViewHolder> {

  //  DatabaseReference speedDialDatabase;
    private Context context;
    List<Contacts> contactsList;
    private String userUID;

    public SpeedDialRecyclerViewAdapter(Context context, List<Contacts> contactsList) {
        this.context = context;
        this.contactsList = contactsList;
    }

    public SpeedDialRecyclerViewAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_contact_events, viewGroup, false);
        SpeedDialRecyclerViewAdapter.ViewHolder holder = new SpeedDialRecyclerViewAdapter.ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {


//        Log.e("useruid",userUID);
//        Log.e("speeddialData",contactsList.get(i).getDesignation());
//        Log.e("speeddialData",contactsList.get(i).getName());
//        Log.e("speeddialData",contactsList.get(i).getPhone());
//        Log.e("speeddialdata",contactsList.get(i).getUserUID());

        //   viewHolder.profilePhoto.setImageDrawable();
     //   final DatabaseReference speedDialDatabase=FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.USER_OTHERDATA).child(StringVariable.USER_PROFILE).child(StringVariable.USER_SPEEDDIAL);

//        Log.e("profilePicture",contactsList.get(i).getProfile_pic());
    //    Log.e("profilePicture",String.valueOf(contactsList.size()));

        viewHolder.username.setText(contactsList.get(i).getName());
        viewHolder.designation.setText(contactsList.get(i).getDesignation());
        viewHolder.profilePhoto.setImageURI(Uri.parse(contactsList.get(i).getProfile_pic()));
        //viewHolder.heartImage.setImageDrawable(context.getDrawable(R.drawable.ic_heart2));
//        viewHolder.progressBar.setVisibility(View.GONE);
        String userUID=(contactsList.get(i).getUserUID());


        viewHolder.heartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Drawable.ConstantState notWishlisted = context.getDrawable(R.drawable.ic_heart).getConstantState();
//                Drawable.ConstantState wishlisted = context.getDrawable(R.drawable.ic_heart2).getConstantState();
//                if(viewHolder.heartImage.getDrawable().getConstantState() == wishlisted){
//                    viewHolder.heartImage.setImageResource(R.drawable.ic_heart);
//
//                    speedDialDatabase.addValueEventListener(new ValueEventListener() {
//
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//
//                            for(DataSnapshot postsnapshot: dataSnapshot.getChildren())
//                            {
//                                if(String.valueOf(postsnapshot.getValue()).equalsIgnoreCase(userUID))
//                                {
//
//                                    speedDialDatabase.child(postsnapshot.getKey()).removeValue();
//                                    notifyDataSetChanged();
//
//                                }
//
//                            }
//                        }
//
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//                else {
//                    viewHolder.heartImage.setImageResource(R.drawable.ic_heart2);
//                }
            }
        });

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
//                Intent intent =new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+contactsList.get(i).getWhatsapp()));
//                intent.setPackage("com.whatsapp");
//                v.getContext().startActivity(intent);

                String contact = "091"+contactsList.get(i).getWhatsapp(); // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = context.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    v.getContext().startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(v.getContext(), "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }


        });

    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        private SimpleDraweeView profilePhoto;
        private TextView username,designation;
        ImageView callButton,whatsappButton,heartImage;


        public ViewHolder(@NonNull View itemView) {


            super(itemView);
//            Log.e("viewHolderInvoked", "1");

            profilePhoto=itemView.findViewById(R.id.iv_contact_image);
            username=itemView.findViewById(R.id.tv_contact_name);
            designation=itemView.findViewById(R.id.tv_contact_designation);
            callButton=itemView.findViewById(R.id.callButton);
            whatsappButton=itemView.findViewById(R.id.whatsAppButton);
            heartImage=itemView.findViewById(R.id.iv_heart);

            //progressBar.setVisibility(View.GONE);

        }
    }


    @Override
    public int getItemCount() {
//       Log.e("sppeddialSize",String.valueOf(contactsList.size()));
        return contactsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return  position;
    }
}
