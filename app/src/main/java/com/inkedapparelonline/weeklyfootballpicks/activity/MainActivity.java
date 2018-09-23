package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.adapters.PlayerRecViewAdapter;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FloatingActionButton fab;

    FirebaseDatabase mData;
    DatabaseReference mDataRef;
    RecyclerView mainRecView;
    PlayerRecViewAdapter playerAdapter;
    List<Player> playerList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    new Load_Players().execute();
                    return true;
                case R.id.navigation_matchups:

                    return true;
                case R.id.navigation_players:

                    return true;
            }
            return false;
        }
    };

    private FloatingActionButton.OnClickListener mOnClickListener = (new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(new Intent(MainActivity.this, Create_User.class));

        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        mDataRef = mData.getReference("players");

        playerList = new ArrayList<>();
        mainRecView = findViewById(R.id.mainActivity_recView);



        fab = findViewById(R.id.main_activity_fab);
        fab.setOnClickListener(mOnClickListener);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public  class Load_Players extends AsyncTask<Void, Void, Void> {

        List<Player> list = new ArrayList<>();
        @Override
        protected Void doInBackground(Void... voids) {

            mDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot players : dataSnapshot.getChildren()) {
                        String playerName = players.child("name").getValue().toString();
                        String company = players.child("company").getValue().toString();
                        String id = players.getKey();
                        int wins = Integer.parseInt(players.child("winTotal").getValue().toString());
                        int losses = Integer.parseInt(players.child("lossTotal").getValue().toString());
                        list.add(new Player(playerName, company, id, wins, losses));

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mainRecView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            playerAdapter = new PlayerRecViewAdapter(MainActivity.this, list);
            mainRecView.setAdapter(playerAdapter);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
