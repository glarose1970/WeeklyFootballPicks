package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;

import java.util.ArrayList;
import java.util.List;

public class Single_Player_View extends AppCompatActivity {

    TextView tv_playername, tv_company, tv_wins, tv_losses;
    FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mDataRef;
    List<Team> teamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single__player__view);

        Bundle details = this.getIntent().getExtras();
        String[] extra = details.getStringArray("names");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDataRef = mDatabase.getReference("players").child(mAuth.getUid()).child("picks");

        tv_playername = findViewById(R.id.single_player_view_tv_playername);
        tv_company    = findViewById(R.id.single_player_view_tv_company);
        tv_wins       =  findViewById(R.id.single_player_view_tv_wins);
        tv_losses     = findViewById(R.id.single_player_view_tv_losses);

        tv_playername.setText("Player Name : " + extra[0].toString().replace("-", ""));
        tv_company.setText("Company : " + extra[1].toString());
    }

    private class Load_Picks extends AsyncTask<Void, Void, List<Team>> {
        @Override
        protected List<Team> doInBackground(Void... voids) {
            teamList = new ArrayList<>();

            return null;
        }

        @Override
        protected void onPostExecute(List<Team> teams) {
            super.onPostExecute(teams);
        }
    }
}
