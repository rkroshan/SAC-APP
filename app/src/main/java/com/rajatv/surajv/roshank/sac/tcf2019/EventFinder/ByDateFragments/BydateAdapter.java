package com.rajatv.surajv.roshank.sac.tcf2019.EventFinder.ByDateFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.ByDateOnclick;
import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;
import java.util.List;

public class BydateAdapter extends RecyclerView.Adapter<BydateAdapter.ViewHolder> implements Filterable {

    public Context context;
    public List<String> eventModalClassList;
    public List<String> eventModalClassEvent;

    public List<String> eventModalClassListfull;

    public BydateAdapter(Context context, List<String> meventModalClassList, List<String> meventModalClassEvent) {
        this.context = context;
        this.eventModalClassList = meventModalClassList;
        this.eventModalClassListfull = meventModalClassList;
        this.eventModalClassEvent = meventModalClassEvent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_event_finder_by_date, viewGroup, false);
        BydateAdapter.ViewHolder holder = new BydateAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        /*Random rand = new Random();

        float r = rand.nextFloat();
        float g = rand.nextFloat();
        float b = rand.nextFloat();

       /* public int getIntFromColor(float Red, float Green, float Blue){
            int R = Math.round(255 * Red);
            int G = Math.round(255 * Green);
            int B = Math.round(255 * Blue);

            R = (R << 16) & 0x00FF0000;
            G = (G << 8) & 0x0000FF00;
            B = B & 0x000000FF;

            return 0xFF000000;
        }*/
       /* viewHolder.mCardview.setCardBackgroundColor(getRandomColorCode());
        public int getRandomColorCode(){

            Random random = new Random();

            return Color.argb(255, random.nextInt(256), random.nextInt(256),random.nextInt(256));

        }*/
        String formattedDate = eventModalClassList.get(i);

        String day = formattedDate.split(" ")[0].split(",")[0];
        formattedDate = formattedDate.split(",")[1];
        Log.e("day--",day);
        String date = formattedDate.split(" ")[2];
        String month = formattedDate.split(" ")[1];
        month = getMonth(month);

        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Bahnhof Ultra Ultra.otf");

        viewHolder.date.setText(date + " " + month);
        viewHolder.imagedate.setText(date);
        viewHolder.day.setText(get(day));

    }

    @Override
    public int getItemCount() {
        Log.e("getItemCount--",eventModalClassList.size()+"");
        return eventModalClassList.size();
    }

    @Override
    public Filter getFilter() {
        return personalityfilter;
    }

    private Filter personalityfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<String> filterlist = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterlist = eventModalClassListfull;
                Log.e("FilterList", filterlist.size() + "");
            } else {
                String filterpattern = constraint.toString().toLowerCase().trim();
                for (String item : eventModalClassListfull) {
                    if (item.toLowerCase().contains(filterpattern)) {
                        filterlist.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            eventModalClassList = (List<String>) results.values;
            notifyDataSetChanged();
        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date;
        private TextView day;
        public TextView imagedate;
        public CardView mCardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_textview);
            day = itemView.findViewById(R.id.day_textview);
            imagedate=itemView.findViewById(R.id.day_text_rightview);
            mCardview = itemView.findViewById(R.id.datecardview);

            mCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO get the neccesary data from eventModalClassEvent do what to do next after getting data
                    //keep in mind that data is sepearted as ,@sac,
                    TextView date_clicked = view.findViewById(R.id.date_textview);
                    TextView day_clicked = view.findViewById(R.id.day_textview);
                    String subEvent = "";
                    String dayClicked = day_clicked.getText().subSequence(0,3).toString();
                    String dateClicked = date_clicked.getText().toString().split(" ")[0];
                    String monthClicked = date_clicked.getText().toString().split(" ")[1].substring(0,3);

                    String eventdate = dayClicked+", "+monthClicked+" "+dateClicked+", '19";
                    Log.e("byDateEvent-Date",eventdate);
                    if(eventModalClassList.contains(eventdate)){
                        int index = eventModalClassList.indexOf(eventdate);
                        Log.e("byDateEvent-index",String.valueOf(index));
                        subEvent = eventModalClassEvent.get(index);
                        Intent subeventintent = new Intent(context,ByDateOnclick.class);
                        subeventintent.putExtra("event_name",subEvent);
                        Log.e("byDateEvent-string",subEvent);
                        context.startActivity(subeventintent);
                    }
                    }
            });
        }
    }

    public String getMonth(String month) {
        switch (month.toLowerCase()) {
            case "jan":
                return "January";
            case "feb":
                return "February";
            case "mar":
                return "March";
            case "apr":
                return "April";
            case "may":
                return "May";
            case "jun":
                return "June";
            case "jul":
                return "July";
            case "aug":
                return "August";
            case "sep":
            case "sept":
                return "September";
            case "oct":
                return "October";
            case "nov":
                return "November";
            case "dec":
                return "December";
            default:
                return "";
        }
    }

    private String get(String day){
        switch(day.toLowerCase()){
            case "mon":return "Monday";
            case "tue":return "Tuesday";
            case "wed":return "Wednesday";
            case "thu":return "Thursday";
            case "fri":return "Friday";
            case "sat":return "Saturday";
            case "sun":return "Sunday";
            default: return "";
        }
    }
}
