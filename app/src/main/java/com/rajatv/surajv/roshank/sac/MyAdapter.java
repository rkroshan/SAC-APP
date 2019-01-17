package com.rajatv.surajv.roshank.sac;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.util.List;
    public class MyAdapter  extends PagerAdapter {

        List<Integer> lstImages;
        Context context;
        LayoutInflater layoutInflater;

        public MyAdapter(List<Integer> lstImages, Context context) {
            this.lstImages = lstImages;
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return lstImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = layoutInflater.inflate(R.layout.developers_content_view,container,false);
            ImageView imageView = (ImageView)view.findViewById(R.id.developer_imageview);
            imageView.setImageResource(lstImages.get(position));
            container.addView(view);
            return view;
        }
    }
