package com.rajatv.surajv.roshank.sac.MyDashboard.Wishlist;

import android.content.Context;
import android.content.SharedPreferences;
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

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.rajatv.surajv.roshank.sac.tcf2019.Modal_Classes.SubeventDetails;
import com.rajatv.surajv.roshank.sac.tcf2019.RecyclerViewAdapters.SubeventsDetailsRecyclerViewAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WishListFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<SubeventDetails> wishlist;
    DatabaseReference wishlistDatabase, eventsDatabase;
    SubeventsDetailsRecyclerViewAdapter recyclerViewAdapter;
    private String userUID, wishlistString = "", wishlistArray[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_fun_event, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.funevents_recyclerView);
        recyclerViewAdapter = new SubeventsDetailsRecyclerViewAdapter(getContext(), wishlist);
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
        getUserData();
        wishlist = new ArrayList<>();

        wishlistDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.USERS).child(userUID).child(StringVariable.APP).child(StringVariable.USER_WISHLIST);
        eventsDatabase = FirebaseDatabase.getInstance().getReference().child(StringVariable.EVENTS);

        eventsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    wishlist.clear();
                    //     Log.e("Wishlist:datasnapshot",dataSnapshot.getValue().toString());

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        //          Log.e("Wishlist:datasnapshot",ds.getValue().toString());
                        try {
                            if (wishlistString.contains(ds.getKey()) && !ds.getKey().equals("raaga")) {
//                                Log.e("Wishlist:datasnapshot", String.valueOf(ds.getValue()));

                                wishlist.add(new SubeventDetails(
                                        String.valueOf(ds.child("name").getValue()),
                                        String.valueOf(ds.child("Venue").getValue()),
                                        String.valueOf(ds.child("startsAt").getValue()),
                                        ds.getKey(),
                                        String.valueOf(ds.child("endsAt").getValue()),
                                        String.valueOf(ds.child("registrationDetail").child("startsAt").getValue()),
                                        String.valueOf(ds.child("registrationDetail").child("endsAt").getValue()),
                                        String.valueOf(ds.child("registrationDetail").child("type").getValue()),
                                        String.valueOf(ds.child("registrationDetail").child("packages").getValue()),
                                        String.valueOf(ds.child("registrationDetail").child("restricted").getValue())

                                ));
                            }
                        } catch (Exception e) {
                        }
                    }

                    recyclerViewAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void getUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
        Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());

        userUID = obj.get(StringVariable.USER_USER_UID).toString();
//        Log.e("WishlisFr:useruid", userUID);
        Map<String, Object> mainMap = gson.fromJson(data, StringVariable.RetriveClass.getClass());

        userUID = mainMap.get(StringVariable.USER_USER_UID).toString();

        try {
            Map<String, Object> appData = (Map<String, Object>) mainMap.get(StringVariable.APP);

//            Log.e("testing", appData.toString());
            wishlistString = (String) appData.get(StringVariable.USER_WISHLIST).toString();

//            Log.e("WishlistFragment:shared", wishlistString);
        } catch (Exception e) {
//            Log.e("wishlist", e.getMessage());
        }

    }
}
