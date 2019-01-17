package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.rajatv.surajv.roshank.sac.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileUnregisteredFragment extends Fragment {

    private Button mRegisterButton;
    private Map<String,Object> retriveData = new HashMap<>();
    RelativeLayout mInfoButton;
    private Dialog mdialog;
private Button getmRegisterButton;
    public ProfileUnregisteredFragment(){}

    private String WholeData;


    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.frag_profile_unregistered, container, false);
        mRegisterButton = (Button) view.findViewById(R.id.button_register_unregistered);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(view);
            }
        });
        final View view= inflater.inflate(R.layout.frag_profile_unregistered, container, false);
        mRegisterButton=(Button)view.findViewById(R.id.button_register_unregistered);
        mInfoButton=(RelativeLayout)view.findViewById(R.id.info_relative_clickable);
        mdialog=new Dialog(getActivity());
        mdialog.setContentView(R.layout.pop_up_info);
        mdialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return view;

    }

    public void goToActivity(View v) {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        Log.e("ProfileUneredFragment","OnResume---");

        super.onResume();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(v);
            }
        });
        mInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView close = mdialog.findViewById(R.id.close);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mdialog.dismiss();
                    }
                });

                mdialog.show();
                mdialog.getWindow().setLayout(346*3,520*3);


            }
        });
    }
}
