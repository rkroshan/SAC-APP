package com.rajatv.surajv.roshank.sac.TrendingFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.OthersProfile.OthersProfile;
import com.facebook.drawee.view.SimpleDraweeView;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;
import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    private List<TrendingModalClass> trendingModalClassList;
    private List<TrendingModalClass> trendingModalClassListfull;


    public TrendingAdapter(Context context, List<TrendingModalClass> trendingModalClassList) {
        this.context = context;
        this.trendingModalClassList = trendingModalClassList;
        this.trendingModalClassListfull=trendingModalClassList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        View v;

        switch (viewType) {
            case R.layout.element_card_trending :
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_card_trending, parent,false);
                holder = new ViewHolder1(v);
                break;
            case R.layout.element_list_trending :
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_trending, parent, false);
                holder =  new ViewHolder2(v);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TrendingModalClass trendingModalClass = trendingModalClassList.get(position);


        switch (holder.getItemViewType()) {

            case R.layout.element_card_trending:

                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                viewHolder1.rankview.setText("#"+trendingModalClass.getNewRank());
                viewHolder1.username.setText(trendingModalClass.getUsername());
                viewHolder1.totallikecount.setText(trendingModalClass.getTotallikecount().toString());
                viewHolder1.todaylikescount.setText(trendingModalClass.getTodaylikecount().toString());
                viewHolder1.userprofileimage.setImageURI(trendingModalClass.getProfile_pic());
                if(position>0){viewHolder1.crownimgview.setVisibility(View.INVISIBLE);}
                else{viewHolder1.crownimgview.setVisibility(View.VISIBLE);}

                viewHolder1.userprofileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",trendingModalClass.getUserUID());
                        context.startActivity(intent);
//                        AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
                    }
                });

                viewHolder1.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",trendingModalClass.getUserUID());
                        context.startActivity(intent);
                       // AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
                    }
                });
                break;

            case R.layout.element_list_trending:

                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.rankview.setText("#"+trendingModalClass.getNewRank());
                viewHolder2.username.setText(trendingModalClass.getUsername());
                viewHolder2.totallikecount.setText(trendingModalClass.getTodaylikecount().toString());
                viewHolder2.userprofileimage.setImageURI(trendingModalClass.getProfile_pic());
                Log.e("RANKS",String.valueOf(trendingModalClassList.get(position).getNewRank())+"++"+trendingModalClassList.get(position).getOldRank());

                if(trendingModalClassList.get(position).getOldRank()==0 && trendingModalClassList.get(position).getNewRank()>0){
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_uparrow);

                }
                else if(trendingModalClassList.get(position).getNewRank()<trendingModalClassList.get(position).getOldRank()){
                    Log.e("RANKS",String.valueOf(trendingModalClassList.get(position).getNewRank())+"++"+trendingModalClassList.get(position).getOldRank());
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_uparrow);
                }
                else if(trendingModalClassList.get(position).getNewRank()>trendingModalClassList.get(position).getOldRank()){
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_downarrow);
                }
                else {
                    viewHolder2.arrowImageView.setVisibility(View.GONE);

                }

                viewHolder2.userprofileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",trendingModalClass.getUserUID());
                        context.startActivity(intent);
                       // AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
                    }
                });

                viewHolder2.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",trendingModalClass.getUserUID());
                        context.startActivity(intent);
//                        AllFeedsRecyclerViewAdapter.goToDashBoard(trendingModalClass.getUserUID());
                    }
                });

                break;
        }

    }

    @Override
    public int getItemCount() {
        int size = trendingModalClassList.size();
        if(size>100) return 100;
        else return size;
    }

    public int getItemViewType(int position) {
        if(position<3 && position>=0)
        {return R.layout.element_card_trending;}
        else
        {return R.layout.element_list_trending;}
    }

    @Override
    public Filter getFilter() {
        return personalityfilter;
    }
    private Filter personalityfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TrendingModalClass> filterlist = new ArrayList<>();

            if(constraint == null || constraint.length()==0)
            {filterlist = trendingModalClassListfull;
                Log.e("FilterList", filterlist.size()+"");}
            else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (TrendingModalClass item : trendingModalClassListfull){
                    if (item.getUsername().toLowerCase().contains(filterpattern)){
                        filterlist.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            trendingModalClassList=(List<TrendingModalClass>)results.values;
            notifyDataSetChanged();
        }
    };

   public static class ViewHolder1 extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView rankview;
        public TextView todaylikescount;
        public TextView totallikecount;
        public SimpleDraweeView userprofileimage;
        public ImageView crownimgview;

       public ViewHolder1(View itemView) {
           super(itemView);
           rankview = itemView.findViewById(R.id.rank_textview);
           crownimgview=itemView.findViewById(R.id.crown_img_view);
                username = itemView.findViewById(R.id.username_textview);
                todaylikescount = itemView.findViewById(R.id.today_point_number);
                totallikecount = itemView.findViewById(R.id.total_point_number);
                userprofileimage = itemView.findViewById(R.id.user_profile_image);
       }
   }

    public class ViewHolder2 extends RecyclerView.ViewHolder{

        public TextView username;
        public TextView rankview;
        public TextView totallikecount;
        public SimpleDraweeView userprofileimage;
        public ImageView arrowImageView;


        public ViewHolder2(View itemView) {
            super(itemView);
           username = itemView.findViewById(R.id.username_view);
            rankview = itemView.findViewById(R.id.rank_view);
            totallikecount = itemView.findViewById(R.id.likes_count);
            userprofileimage = itemView.findViewById(R.id.user_img_view);
            arrowImageView= itemView.findViewById(R.id.uparrow_imgview);

        }

    }
}

