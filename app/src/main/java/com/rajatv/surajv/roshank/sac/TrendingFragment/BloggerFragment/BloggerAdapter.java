package com.rajatv.surajv.roshank.sac.TrendingFragment.BloggerFragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.TrendingFragment.TrendingModalClass;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class BloggerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private Context context;
    private List<TrendingModalClass> bloggerModalClassList;
    private List<TrendingModalClass> bloggerModalClassListfull;

    public BloggerAdapter() {}

    public BloggerAdapter(Context context, List<TrendingModalClass> bloggerModalClassList) {
        this.context = context;
        this.bloggerModalClassList = bloggerModalClassList;
        this.bloggerModalClassListfull = bloggerModalClassList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        View v;

        switch (viewType) {
            case R.layout.element_card_blogger:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_card_blogger, parent, false);
                holder = new ViewHolder1(v);
                break;
            case R.layout.element_list_blogger:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.element_list_blogger, parent, false);
                holder = new ViewHolder2(v);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final TrendingModalClass bloggerModalClass = bloggerModalClassList.get(position);

        switch (holder.getItemViewType()) {

            case R.layout.element_card_blogger:
                ViewHolder1 viewHolder1 = (ViewHolder1)holder;
                viewHolder1.rankview.setText("#"+bloggerModalClass.getNewRank());
                viewHolder1.username.setText(bloggerModalClass.getUsername());
                viewHolder1.totallikecount.setText(bloggerModalClass.getTotallikecount().toString());
                viewHolder1.todaylikescount.setText(bloggerModalClass.getTodaylikecount().toString());
                viewHolder1.userprofileimage.setImageURI(bloggerModalClass.getProfile_pic());
                if(position>0){viewHolder1.Blogger_crownImage.setVisibility(View.INVISIBLE);}
                else{viewHolder1.Blogger_crownImage.setVisibility(View.VISIBLE);}

                viewHolder1.userprofileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",bloggerModalClass.getUserUID());
                        context.startActivity(intent);
                        //AllFeedsRecyclerViewAdapter.goToDashBoard(bloggerModalClass.getUserUID());
                    }
                });

                viewHolder1.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",bloggerModalClass.getUserUID());
                        context.startActivity(intent);
                        //AllFeedsRecyclerViewAdapter.goToDashBoard(bloggerModalClass.getUserUID());
                    }
                });
                break;

            case R.layout.element_list_blogger:
                ViewHolder2 viewHolder2 = (ViewHolder2) holder;
                viewHolder2.rankview.setText("#"+bloggerModalClass.getNewRank());
                viewHolder2.username.setText(bloggerModalClass.getUsername());
                viewHolder2.totallikescount.setText(bloggerModalClass.getTodaylikecount().toString());
                viewHolder2.userprofileimage.setImageURI(bloggerModalClass.getProfile_pic());

                if(bloggerModalClassList.get(position).getOldRank()==0 && bloggerModalClassList.get(position).getNewRank()>0){
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_uparrow);

                }

                else if(bloggerModalClassList.get(position).getNewRank()<bloggerModalClassList.get(position).getOldRank()){
                    Log.e("RANKS",String.valueOf(bloggerModalClassList.get(position).getNewRank())+"++"+bloggerModalClassList.get(position).getOldRank());
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_uparrow);
                }
                else if(bloggerModalClassList.get(position).getNewRank()>bloggerModalClassList.get(position).getOldRank()){
                    viewHolder2.arrowImageView.setBackgroundResource(R.drawable.ic_downarrow);
                }
                else {
                    viewHolder2.arrowImageView.setVisibility(View.GONE);

                }
                viewHolder2.userprofileimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",bloggerModalClass.getUserUID());
                        context.startActivity(intent);                    }
                });

                viewHolder2.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(context,OthersProfile.class);
                        intent.putExtra("userUID",bloggerModalClass.getUserUID());
                        context.startActivity(intent);                             }
                });

                break;
        }
    }


        @Override
        public int getItemCount () {
            int size = bloggerModalClassList.size();
            if(size>100) return 100;
            else return bloggerModalClassList.size();
        }

        @Override
        public int getItemViewType ( int position){
            if (position<3 && position>=0) {
                return R.layout.element_card_blogger;
            } else
                return R.layout.element_list_blogger;
        }

    @Override
    public Filter getFilter() {
        return blogFilter;
    }

    public Filter blogFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<TrendingModalClass> filterList = new ArrayList<>();

            if(constraint == null|| constraint.length()==0){
                filterList = bloggerModalClassListfull;
            }
            else{
                String filterpattern = constraint.toString().toLowerCase().trim();
                for(TrendingModalClass item : bloggerModalClassListfull){
                    if (item.getUsername().toLowerCase().contains(filterpattern)){
                        filterList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=filterList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            bloggerModalClassList= (List<TrendingModalClass>) results.values;
            notifyDataSetChanged();
        }
    };

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

            public TextView username;
            public TextView rankview;
            public TextView todaylikescount;
            public TextView totallikecount;
            public SimpleDraweeView userprofileimage;
            public ImageView Blogger_crownImage;

            public ViewHolder1(View itemView) {
                super(itemView);
                Blogger_crownImage = itemView.findViewById(R.id.Blogger_crownImage);
                username = itemView.findViewById(R.id.username_textview);
                rankview = itemView.findViewById(R.id.rank_textview);
                todaylikescount = itemView.findViewById(R.id.today_point_number);
                totallikecount = itemView.findViewById(R.id.total_point_number);
                userprofileimage = itemView.findViewById(R.id.user_profile_image);
            }
        }

        public class ViewHolder2 extends RecyclerView.ViewHolder {

            public TextView username;
            public TextView rankview;
            public TextView totallikescount;
            public SimpleDraweeView userprofileimage;
            public ImageView arrowImageView;

            public ViewHolder2(View itemView) {
                super(itemView);
                username = itemView.findViewById(R.id.username_view);
                rankview = itemView.findViewById(R.id.rank_view);
                totallikescount = itemView.findViewById(R.id.likes_count);
                userprofileimage = itemView.findViewById(R.id.user_img_view);
                arrowImageView= itemView.findViewById(R.id.uparrow_imgview);

            }
        }
    }


