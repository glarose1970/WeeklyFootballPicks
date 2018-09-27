package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.adapters.PlayerPicksRecViewAdapter;
import com.inkedapparelonline.weeklyfootballpicks.helpers.BottomNavigationViewHelper;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player_Picks_Activity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 1;
    RecyclerView recView;
    RecyclerView.LayoutManager layoutManager;
    PlayerPicksRecViewAdapter playerPicksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_picks);

        setUpBottonNavView();

        recView = findViewById(R.id.player_picks_recView);
        layoutManager = new LinearLayoutManager(this);

        new Load_Matchup_Picks().execute("http://www.nfl.com/scores");

    }

    private void setUpBottonNavView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(Player_Picks_Activity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public class Load_Matchup_Picks extends AsyncTask<String, Void, List<MatchUp>> {

        @Override
        protected List<MatchUp> doInBackground(String... strings) {
            List<MatchUp> mList = new ArrayList<>();
            try {
                Document page = Jsoup.connect(strings[0]).get();
                Elements matchupNodes = page.getElementsByClass("new-score-box");

                for (Element team : matchupNodes) {
                    //Away Team Data
                    Elements awayTeamNode = team.select(".away-team");
                    String awayTeamImgLoc = awayTeamNode.select("img").first().attr("src");
                    String awayTeamName = awayTeamNode.select(".team-name").text();

                    //Home Team Data
                    Elements homeTeamNode = team.select(".home-team");
                    String homeTeamImgLoc = homeTeamNode.select("img").first().attr("src");
                    String homeTeamName = homeTeamNode.select(".team-name").text();

                    MatchUp matchup = new MatchUp(new Team(awayTeamName, awayTeamImgLoc), new Team(homeTeamName, homeTeamImgLoc));
                    mList.add(matchup);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return mList;
        }

        @Override
        protected void onPostExecute(List<MatchUp> matchUps) {
            super.onPostExecute(matchUps);

            playerPicksAdapter = new PlayerPicksRecViewAdapter(Player_Picks_Activity.this, matchUps, Load_Matchup_Picks.this);
            recView.setLayoutManager(layoutManager);
            recView.setAdapter(playerPicksAdapter);
        }
    }
}
