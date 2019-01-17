package com.rajatv.surajv.roshank.sac.Feeds.Result;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

public class DownloadRecyclerViewAdapter extends RecyclerView.Adapter<DownloadRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<String> downloadArrayList;

    public DownloadRecyclerViewAdapter(Context context, ArrayList<String> downloadArrayList) {
        this.context = context;
        this.downloadArrayList = downloadArrayList;
    }
    public DownloadRecyclerViewAdapter(){}

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        RelativeLayout download_box;

        public ViewHolder(@NonNull View v) {
            super(v);
            name = v.findViewById(R.id.pdfname);
            download_box = v.findViewById(R.id.download_bar_card);
        }
    }

    @NonNull
    @Override
    public DownloadRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_download, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DownloadRecyclerViewAdapter.ViewHolder viewHolder, int i) {

        String download = downloadArrayList.get(i);
        try {
            Uri uri = Uri.parse(download);
            viewHolder.name.setText(uri.getLastPathSegment());
        }catch (Exception e){
            viewHolder.name.setText(download);
        }
        viewHolder.download_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Downloading File", Toast.LENGTH_SHORT).show();
                DownloadManager mManager = (DownloadManager) v.getContext().getSystemService(Context.DOWNLOAD_SERVICE);
                try {
                    Uri uri = Uri.parse(download);
                    DownloadManager.Request mrequest = new DownloadManager.Request(uri);
                    mrequest.setTitle(uri.getLastPathSegment());
                    mrequest.setDescription("File Downloading...");
                    mrequest.allowScanningByMediaScanner();
                    mrequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    mrequest.setDestinationInExternalFilesDir(v.getContext(), Environment.DIRECTORY_DOWNLOADS + "/SAC/DOWNLOADS", uri.getLastPathSegment());
                    mManager.enqueue(mrequest);
                } catch (Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Log.e("Download File : ", e.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return downloadArrayList.size();
    }
}
