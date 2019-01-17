package com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.Highlights;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.List;

public class HighlightsAdapter extends  RecyclerView.Adapter<HighlightsAdapter.ViewHolder> {

        Context context;
public List<Highlights> highlightsList;
String uid = "";

public HighlightsAdapter() {
        }

public HighlightsAdapter(Context context, List<Highlights> highlightsList) {
        this.context = context;
        this.highlightsList = highlightsList;
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_highlights, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Highlights highlights = highlightsList.get(i);
        viewHolder.mEventName.setText(highlights.getEventName());
        viewHolder.mdateTime.setText(highlights.getDateTime());
        viewHolder.mgoing.setText(highlights.getGoing());
        viewHolder.mothersGoing.setText(highlights.getOthersGoing());
        viewHolder.cardImageView.setImageURI(Uri.parse(highlights.getImageUri()));

    Drawable buttonDrawable = viewHolder.mgoing.getBackground();
    buttonDrawable = DrawableCompat.wrap(buttonDrawable);

    if(highlights.getLiked()==1){
        DrawableCompat.setTint(buttonDrawable,Color.parseColor("#00cb7d") );
        viewHolder.mgoing.setBackground(buttonDrawable);
        viewHolder.mgoing.setText("Going..");
    }else {
        DrawableCompat.setTint(buttonDrawable,Color.parseColor("#cb0000") );
        viewHolder.mgoing.setBackground(buttonDrawable);
        viewHolder.mgoing.setText("Going?");
    }

    viewHolder.mgoing.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Drawable buttonDrawable = viewHolder.mgoing.getBackground();
            buttonDrawable = DrawableCompat.wrap(buttonDrawable);

            if(viewHolder.mgoing.getText().toString().equals("Going?")){

                viewHolder.mgoing.setText("Going..");
                DrawableCompat.setTint(buttonDrawable,Color.parseColor("#00cb7d") );
                viewHolder.mgoing.setBackground(buttonDrawable);
                Log.e("chanheGoing",1+"");
                changeGoing(1,highlights.getKey());

            }
            else{
                viewHolder.mgoing.setText("Going?");
                DrawableCompat.setTint(buttonDrawable,Color.parseColor("#cb0000") );
                viewHolder.mgoing.setBackground(buttonDrawable);
                changeGoing(-1,highlights.getKey());

            }
        }
    });

        }

    private void changeGoing(int i, String key) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.HIGHLIGHTHS).child(key);
        databaseReference.child(StringVariable.PEOPLEGOING).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if(mutableData.getValue()==null) return Transaction.success(mutableData);
                else{
                    Log.e("mutable data",mutableData.getValue().toString());
                    int count = Integer.valueOf(mutableData.getValue().toString());
                    mutableData.setValue(count+i);

                    if(i==1) {
                        //adding user
                        databaseReference.child(StringVariable.PEOPLE).child(uid).setValue(count);
                    }else {
                        //deleting user
                        databaseReference.child(StringVariable.PEOPLE).child(uid).removeValue();
                    }

                    return Transaction.success(mutableData);
                }
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    @Override
public int getItemCount() {
        return highlightsList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    RecyclerView recyclerView;

    public TextView mEventName,mdateTime,mothersGoing;
    private CardView mCardView;
    private RelativeLayout mRelative;
    private ImageView cardImageView;
    private Button mgoing;




    public ViewHolder(@NonNull View view) {
        super(view);

        mEventName= view.findViewById(R.id.tv_highlight_event);
        mdateTime= view.findViewById(R.id.tv_highlight_dateTime);
        mgoing= view.findViewById(R.id.goingButton);
        mothersGoing= view.findViewById(R.id.tv_highlight_otherpeopleGoing);
        mCardView=view.findViewById(R.id.cardView_highlights);
        mRelative=view.findViewById(R.id.rel_lay_highlights);
        cardImageView=view.findViewById(R.id.highlights_card_imageView);


    }
}
}
