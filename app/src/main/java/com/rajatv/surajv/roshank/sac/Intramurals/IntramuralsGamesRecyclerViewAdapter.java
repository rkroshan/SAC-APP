package com.rajatv.surajv.roshank.sac.Intramurals;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.tcf2019.CulturalEventsFragments.CulturalEvents;
import com.rajatv.surajv.roshank.sac.tcf2019.FragmentEventDetailsActivity;

import java.util.List;

public class IntramuralsGamesRecyclerViewAdapter extends RecyclerView.Adapter<IntramuralsGamesRecyclerViewAdapter.ViewHolder> {

    public Context context;
    public List<IntramuralsGamesModal> gamesList;
    String eventId;

    public IntramuralsGamesRecyclerViewAdapter(Context context, List<IntramuralsGamesModal> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    public IntramuralsGamesRecyclerViewAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.element_intramurals_games, viewGroup, false);
        IntramuralsGamesRecyclerViewAdapter.ViewHolder holder = new IntramuralsGamesRecyclerViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mGamename.setText(gamesList.get(i).getGame().substring(1));

        viewHolder.mGamechar.setText(gamesList.get(i).getGame().substring(0, 1));

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,FixturesActivity.class);
                intent.putExtra("gamename",gamesList.get(i).getGame());
                intent.putExtra("menorwomen",gamesList.get(i).getGender());
                context.startActivity(intent);
            }
        });

//        switch (gamesList.get(i).getGame()) {
//            case "Cricket":
//                viewHolder.mGameImage.setImageResource(R.mipmap.cricketmen);
//                break;
//            case "Volleyball":
//                viewHolder.mGameImage.setImageResource(R.mipmap.volleymen);
//                break;
//            case "Football":
//                viewHolder.mGameImage.setImageResource(R.mipmap.footballmen);
//                break;
//            case "Badminton":
//                viewHolder.mGameImage.setImageResource(R.mipmap.badmintonmen);
//                break;
//            case "Kabaddi":
//                viewHolder.mGameImage.setImageResource(R.mipmap.kabaddimen);
//                break;
//            case "Table_Tennis":
//                viewHolder.mGameImage.setImageResource(R.mipmap.ttmen);
//                break;
//            case "Squash":
//                viewHolder.mGameImage.setImageResource(R.mipmap.squashmen);
//                break;
//
//
//            default:
//        }
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mGamename, mGamechar;
        public ImageView mGameImage;
        Intent subeventintent;
        public CardView cardView;


        private final String EVENT_NAME = "event name";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGamechar = itemView.findViewById(R.id.element_intramurals_games_gamechar);
            mGamename = itemView.findViewById(R.id.element_intramurals_games_gamename);
            mGameImage = itemView.findViewById(R.id.element_intramurals_games_image);
            cardView=itemView.findViewById(R.id.card_games);


        }
    }
}
