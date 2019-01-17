package com.rajatv.surajv.roshank.sac.NitpClubs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.rajatv.surajv.roshank.sac.R;

import java.util.ArrayList;

/**
 * Created by HP on 10-01-2018.
 */

public class NitpClubsFragment extends Fragment {

    ArrayList<NitpClubsObject> mlist = new ArrayList<>();
    RecyclerView recyclerView;

    public NitpClubsFragment() {

    }

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(view==null)
        {
            view = inflater.inflate(R.layout.fragment_nitp_club, container, false);
            recyclerView = (RecyclerView)view.findViewById(R.id.nitp_clubs_recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
            recyclerView.setHasFixedSize(true);
            setList();
            NitpClubsAdapter nitpClubsAdapter = new NitpClubsAdapter(mlist);
            recyclerView.setAdapter(nitpClubsAdapter);
        }
        return view;
    }

    private void setList() {

        mlist.add(new NitpClubsObject("Web and Coding Club",R.mipmap.logo_web_coding_club));
        mlist.add(new NitpClubsObject("Literary and Art Club",R.mipmap.logo_literary_club));
        mlist.add(new NitpClubsObject("Dance Club",R.mipmap.logo_dance_club));
        mlist.add(new NitpClubsObject("Music Club",R.mipmap.logo_music_club));
        mlist.add(new NitpClubsObject("Drama and Films Club",R.mipmap.logo_drama_film_club));
        mlist.add(new NitpClubsObject("Vista",R.mipmap.logo_photography_club));
    }

}
