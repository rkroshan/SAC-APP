package com.rajatv.surajv.roshank.sac.MyDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rajatv.surajv.roshank.sac.R;


public class RegisterNowFragment extends Fragment {
    Button mRegisterButton;
    public RegisterNowFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_register_now, container, false);


        mRegisterButton=(Button) view.findViewById(R.id.button_register_registerNow);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        goToActivity(view);
    }
});
        return view;

    }
    public void goToActivity(View v)
    {
        Intent intent = new Intent(getActivity(), RegisterActivity.class);
        startActivity(intent);
    }
}