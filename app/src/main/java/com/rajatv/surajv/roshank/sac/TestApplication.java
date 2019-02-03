package com.rajatv.surajv.roshank.sac;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.view.SimpleDraweeView;

public class TestApplication extends AppCompatActivity {

    SimpleDraweeView simple1 , simple2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.element_versus_intramurals);

        simple1=findViewById(R.id.team1SimpleDrawee);
        simple2=findViewById(R.id.team2SimpleDrawee);

        simple1.setImageResource(R.drawable.bg_ece);
        simple2.setImageResource(R.drawable.bg_ece);
//        simple2.setBackgroundColor(Color.parseColor("#0C769C"));
//        simple1.setBackgroundColor(Color.parseColor("#0C3456"));
//        simple2.setImageURI(Uri.parse("https://www.solidbackgrounds.com/images/950x350/950x350-teal-solid-color-background.jpg"));
//        simple1.setImageURI(Uri.parse("https://cdn3.volusion.com/jebff.evopn/v/vspfiles/photos/554-2.jpg?1515935622"));

    }
}
