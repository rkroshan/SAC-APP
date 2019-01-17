package com.rajatv.surajv.roshank.sac.Feeds.Result;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class FeedsResultFrag extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Results> listResult;
    private DatabaseReference databaseReference;
    private DatabaseReference linkReference;
    ResultsRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView downloadRecyclerView;
    String uid;

    public FeedsResultFrag() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_results, container, false);
        View view2 = inflater.inflate(R.layout.element_result_feeds, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.results_recycler_view);
        recyclerViewAdapter = new ResultsRecyclerViewAdapter(getContext(), listResult);

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

        listResult = new ArrayList<>();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(StringVariable.RESULT);
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    listResult.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        int liked = 0;
                        for (DataSnapshot ds : postSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildren()) {
                            if (ds.getKey().equals(uid)) {
                                liked = 1;
                                break;
                            }
                        }

                        int likes = 0;

                        try {
                            likes = (int) postSnapshot.child(StringVariable.POSTS_LIKES_BY).getChildrenCount();
                        } catch (Exception e) {
                            Log.e("Posts likes by", e.getMessage());
                        }

                        //Log.e("Posts likes by", likes+"");


                        long downloadItem = postSnapshot.child("Links").getChildrenCount();

                        ArrayList<String> downloadList = new ArrayList<>();
                        for (int i = 0; i < downloadItem; i++) {
                            downloadList.add(String.valueOf(postSnapshot.child("Links").child(Integer.toString(i)).getValue()));
                        }
                        listResult.add(0, new Results(
                                liked,
                                String.valueOf(postSnapshot.getKey()),
                                String.valueOf(postSnapshot.child(StringVariable.RESULT_POSTED_BY).child(StringVariable.RESULT_USERIMAGEURL).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.RESULT_POSTED_BY).child(StringVariable.RESULT_NAME).getValue()),
                                getTimestamp(Long.parseLong(String.valueOf(postSnapshot.child(StringVariable.RESULT_TIMESTAMP).getValue()))),
                                String.valueOf(postSnapshot.child(StringVariable.RESULT_DATA).getValue()),
                                likes + "",
                                String.valueOf(postSnapshot.child(StringVariable.RESULT_SUB_EVENT_NAME).getValue()),
                                String.valueOf(postSnapshot.child(StringVariable.RESULT_EVENT_NAME).getValue()),
                                (int) downloadItem,
                                downloadList, String.valueOf(postSnapshot.child(StringVariable.RESULT_POSTED_BY).child(StringVariable.RESULT_USERUID).getValue())));
                    }

                    recyclerViewAdapter.notifyDataSetChanged();
                }catch (Exception e){
                    Log.e("result frag",e.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public static String getTimestamp(long timestamp) {

        String time = "";

        long difference = System.currentTimeMillis()-timestamp;

        double days = Math.floor(difference / 1000 / 60 / 60 / 24);
        difference -= days * 1000 * 60 * 60 * 24;

        double hours = Math.floor(difference / 1000 / 60 / 60);
        difference -= hours * 1000 * 60 * 60;

        double minutes = Math.floor(difference / 1000 / 60);
        difference -= minutes * 1000 * 60;

        double seconds = Math.floor(difference / 1000);

        if ((int) days > 1) {
            time += String.valueOf((int)days) + "days ";
        }
        else if((int) days>0){
            time += String.valueOf((int)days) + "day ";
        }
        else if ((int) hours > 1) {
            time += String.valueOf((int)hours) + "hrs ";
        }
        else if ((int) hours > 0) {
            time += String.valueOf((int)hours) + "hr ";
        }
        else if ((int) minutes > 1) {
            time += String.valueOf((int)minutes) + "mins ";
        }
        else if ((int) minutes > 0) {
            time += String.valueOf((int)minutes) + "min ";
        }
        else if ((int) seconds >= 0) {
            time += String.valueOf((int)seconds) + "secs ";
        }
        time += "ago";

        return time;
    }
}