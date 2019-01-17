package com.rajatv.surajv.roshank.sac.NitpClubs;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by HP on 17-12-2017.
 */

public class NitpClubsAdapter extends RecyclerView.Adapter<NitpClubsAdapter.ViewHolder> {

    NitpClubsObject nitpClubsObject = new NitpClubsObject();
    private ArrayList<NitpClubsObject> mlist = new ArrayList<>();

    Typeface type;

    public NitpClubsAdapter(ArrayList<NitpClubsObject> list)
    {
        mlist = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nitpclubs_elementview,parent,false);
        type = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/century_gothic.TTF");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        nitpClubsObject = mlist.get(position);
        //holder.nitp_clubs_text_content.setText(nitpClubsObject.getClubName());
        holder.nitp_clubs_icon.setImageResource(nitpClubsObject.getImageId());
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView nitp_clubs_text_content;
        ImageView nitp_clubs_icon;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            //nitp_clubs_text_content = itemView.findViewById(R.id.corona_melange_text_content);
            nitp_clubs_icon = itemView.findViewById(R.id.nitpclub_element_imageview);
            //nitp_clubs_text_content.setTypeface(type);

        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            Intent intent = new Intent(view.getContext(), NitpClubs_details.class);
            intent.putExtra("clubName", mlist.get(getAdapterPosition()).getClubName());
            //intent.putExtra("clubLogoId",mlist.get(getAdapterPosition()).getImageId());
            view.getContext().startActivity(intent);
        }
    }

}
