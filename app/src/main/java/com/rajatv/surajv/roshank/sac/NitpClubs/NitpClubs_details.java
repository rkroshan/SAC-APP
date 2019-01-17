package com.rajatv.surajv.roshank.sac.NitpClubs;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.rajatv.surajv.roshank.sac.AnalyticsApplication;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.rajatv.surajv.roshank.sac.R;


public class NitpClubs_details extends AppCompatActivity {
    private String nitpClub, nitpClubDetails;
    Tracker mTracker;
    private static final String TAG = "NitpClubs_details";
    private TextView clubName, clubDetails;
    private ImageView back_btn, clubLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nitp_clubs_details);
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        Log.i(TAG, "Crashed in this activity : onCreate");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());

        clubName = (TextView)findViewById(R.id.nitp_club_details_club_name_textview);
        clubDetails = (TextView)findViewById(R.id.nitp_club_details_textview);
        clubLogo = (ImageView)findViewById(R.id.nitp_club_details_club_logo_imageview);
        back_btn = (ImageView)findViewById(R.id.nitp_club_details_back);
        Typeface type1 = Typeface.createFromAsset(getAssets(), "fonts/mayeka_bold_eventfirstletter.otf");
        Typeface type2 = Typeface.createFromAsset(getAssets(), "fonts/calibrilight.ttf");

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        nitpClub = getIntent().getStringExtra("clubName");
/*
        SpannableString content = new SpannableString(nitpClub);
        content.setSpan(new UnderlineSpan(), 0, nitpClub.length(), 0);
*/
        clubName.setPaintFlags(clubName.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        clubName.setText(nitpClub);
        clubName.setTypeface(type1);
        //clubName.setPaintFlags(clubName.getPaintFlags() );

        setDetails();
        clubDetails.setText(nitpClubDetails);
        clubDetails.setTypeface(type2);
    }

    private void setDetails() {
        switch (nitpClub) {
            case "Web and Coding Club":
                nitpClubDetails = "“Web and Coding” is an official club of National Institute of Technology, Patna. The main objective of the club is to promote the culture of web development and competitive programming among the students of NIT Patna. Competitive programming plays the most important role for getting a job/placement in IT sector companies. Competitive programming is that field which requires lot of practise. Most of the IITs and NITs have a club which focus solely on competitive programming but our college lacks the culture of competitive programming due to which students find it difficult to clear the coding round of reputed companies like Microsoft, DirectI, Amazon.\n" +
                        "The “Web and Coding” club of NIT Patna has a dedicated team who takes classes for the students of NIT Patna to improve the skills of competitive programming(Data Structures and Algorithms and Competitive Programming tricks) on regular basis. The club also conducts online coding contests for students to create a competitive environment, on open online platforms like HackerEarth. Students of NIT Patna will get a lot of benefits from the club. Coding skill of students of NIT Patna will improve eventually and it will directly improve the placement of students, as well as improve the ranking and reputation of our college.";

               clubLogo.setImageResource(R.mipmap.logo_web_coding_clubdetails);
                break;
            case "Literary and Art Club":
                nitpClubDetails = "The literary and Art club of NIT Patna is aimed to develop and encourage students to bring out their various skills that hardly find a platform in technical institutes. With the mission of creating events that will showcase the talents of the students on a new level, it strives to break the stereo-types and create a altogether different environment where creativity can not only survive, but thrive.\n" +
                        "The club is dedicated to conduct events that will be related to both the literary events as well as the arts and craft events. All of this, with the vision of ‘Express and Transform’, and to make these fields more interesting, appealing and scintillating to enginnering students.\n" +
                        "The club aims to conduct events on the institute level but will seek further collaboration with other organisations and institutes to bring grandeur to the events. The creations of the students will become a part of exhibitions also.\n" +
                        "A well dedicated team of students from all the existing branches of engineering and science together with the able guidance of professor in-charge will work as a team to strengthen the capacity of club so that it could bring progressive change in the field of literature, art and craft. The club will also try to inculcate the implications of literary and art activities into the minds of young students so that they could bring relative changes in their overall lifestyle.\n" +
                        "Team, Literary and Art Club, NIT Patna";

                clubLogo.setImageResource(R.mipmap.logo_literary_clubdetails);
                break;
            case "Dance Club":
                nitpClubDetails = "The dance club of NIT Patna aims to create an atmosphere of cultural diversity where students from the various technical branches get an oppurtunity to step-out from their regular academic routine and explore the vibrant world of dance.\n" +
                        "This club provides a platform for students to learn and share their knowledge of various dance forms also creating an integrated group that performs at various events.";

               clubLogo.setImageResource(R.mipmap.logo_dance_clubdetails);
                break;
            case "Music Club":
                nitpClubDetails = "The official music club of NIT Patna which provides the students a platform to explore and enhance their engagement in music and a chance to represent their college at big platforms through their talent.";

               // clubLogo.setImageResource(R.mipmap.logo_music_clubdetails);
                break;
            case "Drama and Films Club":
                nitpClubDetails = "\"All the world's a stage and most of us are desperately unrehearsed\" - Sean O'Casey\n" +
                        "The Drama and Film Club  NIT Patna is more like a family than just a club. This club stands tall upon the dedication and passion of its members. It gives you the freedom of being whoever you want to be. Discover the craziest person in you, and not only get appreciated but also encouraged . Participate in a bunch of amazing events round the year, from stage and street performances to mimes and comedy acts, leaving behind all the stage fear in you and become an amazing outspoken individual you always dreamt of.\n" ;

                clubLogo.setImageResource(R.mipmap.logo_drama_film_clubdetails);
                break;
            case "Vista":
                nitpClubDetails = "Vista; a seamless horizon\n" +
                        "..the more it tells you, the less you know……\n" +
                        "Memories are important, are they not?\n" +
                        "In this ever, evolving place, which we call home, don’t you realise how hard had it become to look into the past that we have lost to?\n" +
                        "And now we jump in, and magically freeze you!!\n" +
                        "This is to introduce you to the photographers, of our very own club, VISTA.\n" +
                        "“What makes photography a strange invention is that its primary raw materials are light and time” -the two elements which are hidden and unseen for most of the common eyes, is a blessing for us and we cherish it.\n" +
                        "Stealing the tint of Time, and holding the life still, in our snapshots, we relish, revive and relive those moments.\n" +
                        "It is very simple, yet a beautiful creation where everything is still and plausible. There is so much into it, revelation of deeper truths, lives’ richer realities, colours if innocence, wrath of helplessness, divinity of love and an everlasting essence of joy.\n" +
                        "This is us, your pioneer photographers!";

                clubLogo.setImageResource(R.mipmap.logo_photography_clubdetails);
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Action")
                .setAction("Share")
                .build());
    }
}
