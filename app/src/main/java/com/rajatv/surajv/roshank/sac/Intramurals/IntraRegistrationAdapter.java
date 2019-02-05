package com.rajatv.surajv.roshank.sac.Intramurals;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.rajatv.surajv.roshank.sac.R;
import com.rajatv.surajv.roshank.sac.StringVariable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntraRegistrationAdapter extends RecyclerView.Adapter<IntraRegistrationAdapter.ViewHolder> {

    public Context context;
    public List<IntramuralsGamesModal> gamesList;
    Dialog chessDialog;

    public IntraRegistrationAdapter(Context context, List<IntramuralsGamesModal> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    public IntraRegistrationAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.intra_reg_card, viewGroup, false);
        IntraRegistrationAdapter.ViewHolder holder = new IntraRegistrationAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mGamename.setText(gamesList.get(i).getGame().substring(1));

        if (gamesList.get(i).getGame().equalsIgnoreCase("table_tennis")) {
            viewHolder.mGamename.setText("able Tennis");

        }

        viewHolder.mGamechar.setText(gamesList.get(i).getGame().substring(0, 1));

        viewHolder.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gamesList.get(i).getGame().equalsIgnoreCase("Chess")) {
                    String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    chessDialog = new Dialog(context);
                    chessDialog.setContentView(R.layout.pop_up_permission_register_intramurals);
                    chessDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    chessDialog.show();
                    chessDialog.setCanceledOnTouchOutside(false);
                    chessDialog.setCancelable(false);
                    Button yesbtn = chessDialog.findViewById(R.id.yes_btn);
                    Button noButton = chessDialog.findViewById(R.id.no_btn);

                    yesbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences chessPref1 = context.getSharedPreferences("Chess", Context.MODE_PRIVATE);
                            int valid = chessPref1.getInt("applied", 0);
                            if (valid != 1) {
                                ProgressDialog dialog = new ProgressDialog(context);
                                dialog.setMessage("Processing");
                                dialog.show();
                                String contactNO = "", name = "", rollNo = "", isProfileComplete, branch = "", gender = "genderNotFound", email = "";
                                SharedPreferences sharedPreferences = context.getSharedPreferences(StringVariable.UserData_SharedPreference, Context.MODE_PRIVATE);
                                Gson gson = new Gson();
                                String data = sharedPreferences.getString(StringVariable.UserData_Object_SharedPref, "");
                                Map<String, Object> obj = gson.fromJson(data, StringVariable.RetriveClass.getClass());
                                Map<String, Object> app = (Map<String, Object>) obj.get("app");
                                //   Log.e("xyz",app.toString());
                                //for future use
                                try {
                                    contactNO = String.valueOf(obj.get(StringVariable.USER_PHONENUMBER));
                                } catch (Exception e) {
                                }
                                try {
                                    name = obj.get(StringVariable.USER_NAME).toString();
                                } catch (Exception e) {
                                }
                                try {
                                    rollNo = obj.get(StringVariable.USER_ROLLNO).toString();
                                } catch (Exception e) {
                                }
                                try {
                                    gender = obj.get(StringVariable.USER_GENDER).toString();
                                } catch (Exception e) {
                                }
                                try {
                                    branch = obj.get(StringVariable.USER_BRANCH).toString();
                                } catch (Exception e) {
                                }
                                try {
                                    email = obj.get(StringVariable.USER_email).toString();
                                } catch (Exception e) {
                                }
                                try {
                                    isProfileComplete = app.get(StringVariable.USER_IS_PROFILE_COMPLETED).toString();
                                } catch (Exception e) {
                                }
                                HashMap<String, String> map = new HashMap<>();
                                map.put("Branch", branch);
                                map.put("Email id", email);
                                map.put("Mobile No", contactNO);
                                map.put("Name", name);
                                map.put("Roll no", rollNo);
                                FirebaseDatabase.getInstance().getReference().child("INTRAMURALS").child("INTRAMURALS_REGISTRATION").child("CHESS").child(gender).child("SINGLES").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                                        SharedPreferences chessPref = context.getSharedPreferences("Chess", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = chessPref.edit();
                                        editor.putInt("applied", 1);
                                        editor.apply();
                                        if (dialog.isShowing()) {
                                            dialog.dismiss();
                                        }
                                        chessDialog.dismiss();
                                    }
                                });
                            } else {
                                Toast.makeText(context, "Already Applied", Toast.LENGTH_LONG).show();
                                chessDialog.dismiss();
                            }
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            chessDialog.dismiss();
                        }
                    });
                } else if (gamesList.get(i).getGame().equalsIgnoreCase("Badminton")) {
                    Intent i = new Intent(context, IntramuralsportsMain.class);
                    i.putExtra("gamename","Badminton");
                    context.startActivity(i);
                } else if (gamesList.get(i).getGame().equalsIgnoreCase("Carrom")) {
                    Intent i = new Intent(context, IntramuralsportsMain.class);
                    i.putExtra("gamename","Carrom");
                    context.startActivity(i);
                } else if (gamesList.get(i).getGame().equalsIgnoreCase("Dead Lift")) {
                    Intent i = new Intent(context, IntramuralsportsMain.class);
                    i.putExtra("gamename","Dead Lift");
                    context.startActivity(i);
                } else if (gamesList.get(i).getGame().equalsIgnoreCase("Table Tennis")) {
                    Intent i = new Intent(context, IntramuralsportsMain.class);
                    i.putExtra("gamename","Table Tennis");
                    context.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mGamename, mGamechar, registerButton;


        private final String EVENT_NAME = "event name";

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGamechar = itemView.findViewById(R.id.element_intramurals_games_gamechar);
            mGamename = itemView.findViewById(R.id.element_intramurals_games_gamename);
            registerButton = itemView.findViewById(R.id.intra_button);
        }
    }
}
