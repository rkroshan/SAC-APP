package com.rajatv.surajv.roshank.sac.tcf2019;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

public class AttachmentRecyclerViewAdapter extends RecyclerView.Adapter<AttachmentRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> attachmentList;
    ArrayList<String> discriptionList;

    public AttachmentRecyclerViewAdapter(Context context, ArrayList<String> attachmentList, ArrayList<String> discriptionList) {
        this.context = context;
        this.attachmentList = attachmentList;
        this.discriptionList = discriptionList;
    }

    @NonNull

    @Override
    public AttachmentRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_attachment, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AttachmentRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        String download = attachmentList.get(i);
        viewHolder.name.setText(discriptionList.get(i));
        viewHolder.download_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager mManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                try {
                   Uri uri = Uri.parse(attachmentList.get(viewHolder.getAdapterPosition()));
                    Intent i = new Intent(Intent.ACTION_VIEW,uri);
                    context.startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attachmentList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        RelativeLayout download_box;

        public ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.pdfname);
            download_box = v.findViewById(R.id.download_bar_card);
        }
    }
}
