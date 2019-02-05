package com.rajatv.surajv.roshank.sac.Intramurals;

import android.content.Context;
import android.net.Uri;
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

public class IntramuralRegistration extends Fragment {
    private RecyclerView recyclerView;
    private List<IntramuralsGamesModal> gamesList;
    DatabaseReference reference;
    IntraRegistrationAdapter recyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intramural_registration, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.intra_registration_recycler);
        recyclerViewAdapter=new IntraRegistrationAdapter(getContext(),gamesList);
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
        reference= FirebaseDatabase.getInstance().getReference().child("Intra-Registration-list");

        try {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gamesList.clear();
                    for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        String game = String.valueOf(postSnapshot.getKey());
                        gamesList.add(new IntramuralsGamesModal(game,""));
                    }
                    recyclerViewAdapter.notifyDataSetChanged();
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
