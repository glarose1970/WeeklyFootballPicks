package com.inkedapparelonline.weeklyfootballpicks.helpers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.activity.Create_User;
import com.inkedapparelonline.weeklyfootballpicks.activity.MainActivity;
import com.inkedapparelonline.weeklyfootballpicks.activity.MatchupActivity;
import com.inkedapparelonline.weeklyfootballpicks.activity.Player_Picks_Activity;
import com.inkedapparelonline.weeklyfootballpicks.activity.SettingsActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static android.support.v4.content.ContextCompat.startActivity;

public class BottomNavigationViewHelper {

    public static void setUpBottomNavView(BottomNavigationViewEx bottomNavigationViewEx) {
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        context.startActivity(new Intent(context, MainActivity.class));
                        return true;
                    case R.id.navigation_picks:
                        context.startActivity(new Intent(context, Player_Picks_Activity.class));
                        return true;
                    case R.id.navigation_matchups:
                        context.startActivity(new Intent(context, MatchupActivity.class));
                        return true;
                    case R.id.navigation_players:
                        context.startActivity(new Intent(context, Create_User.class));
                        return true;
                    case R.id.navigation_settings:
                        context.startActivity(new Intent(context, SettingsActivity.class));
                        return true;
                }
                return false;
            }
        });
    }
}
