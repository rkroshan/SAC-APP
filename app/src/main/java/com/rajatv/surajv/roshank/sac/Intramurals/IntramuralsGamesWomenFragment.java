package com.rajatv.surajv.roshank.sac.Intramurals;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.ArrayList;
import java.util.List;

public class IntramuralsGamesWomenFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<IntramuralsGamesModal> gamesList;
    DatabaseReference gameDB,gameDB2;
    IntramuralsGamesRecyclerViewAdapter recyclerViewAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.freg_cultural_events,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.cultural_event_recyclerview);
        recyclerViewAdapter=new IntramuralsGamesRecyclerViewAdapter(getContext(),gamesList);
        final FragmentActivity c = getActivity();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recyclerViewAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gamesList=new ArrayList<>();
        gameDB=FirebaseDatabase.getInstance().getReference().child(StringVariable.INTRAMURALS).child(StringVariable.WOMEN);

        try {
            gameDB.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gamesList.clear();
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        //ADD THE DATA FROM DATABASE AS PER THE FIELD
                        String game = String.valueOf(postSnapshot.getKey());
                        Log.e("gamename",game);
                        gamesList.add(new IntramuralsGamesModal(game,"women"));
                        recyclerViewAdapter.notifyDataSetChanged();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }
        catch (Exception e){}
    }
}
