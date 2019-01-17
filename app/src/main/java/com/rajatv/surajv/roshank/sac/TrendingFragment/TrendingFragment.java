package com.rajatv.surajv.roshank.sac.TrendingFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TrendingFragment extends Fragment {

    private RecyclerView mTrendingRecyclerView;
    private TrendingAdapter trendindAdapter;
    private DatabaseReference mDatabaseRef;
    private List<TrendingModalClass> modalClassList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        modalClassList = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child(StringVariable.TRENDING).child(StringVariable.PERSONALITY);
        mDatabaseRef.keepSynced(true);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                modalClassList.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(!String.valueOf(ds.child(StringVariable.BLOGGER_NEW_RANK).getValue()).equalsIgnoreCase("null")){
                        modalClassList.add(new TrendingModalClass(

                                ds.child(StringVariable.BLOGGER_NAME).getValue().toString(),
                                ds.child(StringVariable.BLOGGER_PROFILE_PIC).getValue().toString(),
                                Integer.valueOf(ds.child(StringVariable.BLOGGER_CURRENTDAYLIKES).getValue(Integer.class)),
                                Integer.valueOf(ds.child(StringVariable.BLOGGER_OVERALLIKES).getValue(Integer.class)),
                                ds.getKey(),
                                Integer.valueOf(ds.child(StringVariable.BLOGGER_OLD_RANK).getValue(Integer.class)),
                                Integer.valueOf(ds.child(StringVariable.BLOGGER_NEW_RANK).getValue(Integer.class)))

                        );
                    }

                }
                trendindAdapter.notifyDataSetChanged();

                Collections.sort(modalClassList, (obj1, obj2) -> {
                    return obj1.getNewRank().compareTo(obj2.getNewRank());
                });

//
//                Collections.sort(modalClassList, Collections.reverseOrder((a,b) ->{
//                    return a.getTotallikecount().compareTo(b.getTotallikecount());
//                }));
//
//
//                Collections.sort(modalClassList, Collections.reverseOrder((a,b) ->{
//                    return a.getTodaylikecount().compareTo(b.getTodaylikecount());
//                }));


                int j=1;

//                for (TrendingModalClass modal : modalClassList) {
//                    modal.setRank(j);
//                    j++;
//                }


                trendindAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_trending,container,false);


        mTrendingRecyclerView =  v.findViewById(R.id.trending_recycler_view);
        mTrendingRecyclerView.setHasFixedSize(true);
        mTrendingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        trendindAdapter = new TrendingAdapter(getActivity(),modalClassList);
        mTrendingRecyclerView.setAdapter(trendindAdapter);

        return v;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.searchmenu);
        SearchView searchView= (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                trendindAdapter.getFilter().filter(s);
                return false;
            }
        });
    }
}
