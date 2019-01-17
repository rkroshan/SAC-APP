package com.rajatv.surajv.roshank.sac.Feeds.SacFeeds;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

public class Uploadfile_Apdapter extends RecyclerView.Adapter<Uploadfile_Apdapter.uploadViewholder> {

    ArrayList<Uri_List> mlist = new ArrayList<>();
    private Context mcontext;
    private Uri_List uri_list = new Uri_List();

    public Uploadfile_Apdapter(Context context, ArrayList<Uri_List> list){
        mlist=list;
        mcontext = context;
    }

    @Override
    public uploadViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_files_list,parent,false);
        return new uploadViewholder(v);
    }

    @Override
    public void onBindViewHolder(uploadViewholder holder, int position) {
        uri_list = mlist.get(position);
        holder.upload_attachment.setText(uri_list.getUriFile_name());

        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mlist.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public class uploadViewholder extends RecyclerView.ViewHolder {
        TextView upload_attachment;
        ImageView cancel_btn;
        public uploadViewholder(View itemView) {
            super(itemView);
            upload_attachment = itemView.findViewById(R.id.upload_attachment);
            cancel_btn = itemView.findViewById(R.id.cancel_btn);
        }
    }
}
