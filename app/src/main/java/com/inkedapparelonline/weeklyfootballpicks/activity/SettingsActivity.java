package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inkedapparelonline.weeklyfootballpicks.R;

public class SettingsActivity extends AppCompatActivity {

    Button btn_signOut;
    FirebaseAuth mAuth;
    FirebaseUser curUser;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                    return true;
                case R.id.navigation_picks:
                    startActivity(new Intent(SettingsActivity.this, Player_Picks_Activity.class));

                    return true;
                case R.id.navigation_matchups:
                    startActivity(new Intent(SettingsActivity.this, MatchupActivity.class));
                    return true;
                case R.id.navigation_players:
                    startActivity(new Intent(SettingsActivity.this, Create_User.class));
                    return true;
                case R.id.navigation_settings:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        curUser = mAuth.getCurrentUser();

        btn_signOut = findViewById(R.id.settings_btn_signout);
        btn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (curUser != null) {
                    mAuth.signOut();
                    Toast.makeText(SettingsActivity.this, "User Signed Out Successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SettingsActivity.this, LoginActivity.class));
                }
            }
        });

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
