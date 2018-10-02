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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.adapters.PlayerPicksRecViewAdapter;
import com.inkedapparelonline.weeklyfootballpicks.controller.PlayerPicksController;
import com.inkedapparelonline.weeklyfootballpicks.helpers.BottomNavigationViewHelper;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;
import com.inkedapparelonline.weeklyfootballpicks.helpers.PlayerPicksHelper;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player_Picks_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int ACTIVITY_NUM = 1;
    RecyclerView recView;
    RecyclerView.LayoutManager layoutManager;
    PlayerPicksRecViewAdapter playerPicksAdapter;
    TextView tv_wins;
    Button btn_save, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_picks);
        tv_wins = findViewById(R.id.player_picks_tv_wins);
        btn_save = findViewById(R.id.player_picks_btn_save);
        btn_cancel = findViewById(R.id.player_picks_btn_cancel);

        setUpBottonNavView();

        recView = findViewById(R.id.player_picks_recView);
        layoutManager = new LinearLayoutManager(this);

        new Load_Matchup_Picks().execute("http://www.nfl.com/scores");

        btn_save.setOnClickListener(this);

    }

    private void setUpBottonNavView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(Player_Picks_Activity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        List<Team> picks = new ArrayList<>();
        picks.add(new Team("bucs", "", ""));
        picks.add(new Team("lions", "", ""));
        picks.add(new Team("raiders", "", ""));
        picks.add(new Team("bills", "", ""));
        picks.add(new Team("dallas", "", ""));
        Player player = new Player("Gene", "vThuE9qg29PzrmLSQ927X9YzLFD2", picks);
        PlayerPicksController.Save_Player_Picks(player);
    }

    public class Load_Matchup_Picks extends AsyncTask<String, Void, List<MatchUp>> {

        boolean isGood = false;
        @Override
        protected List<MatchUp> doInBackground(String... strings) {
            List<MatchUp> mList = new ArrayList<>();
            try {
                Document page = Jsoup.connect(strings[0]).get();
                Elements matchupNodes = page.getElementsByClass("new-score-box");
                MatchUpHelper.matchupCount = matchupNodes.size();
                for (Element team : matchupNodes) {
                    //Away Team Data
                    Elements awayTeamNode = team.select(".away-team");
                    String awayTeamImgLoc = awayTeamNode.select("img").first().attr("src");
                    String awayTeamName = awayTeamNode.select(".team-name").text();

                    //Home Team Data
                    Elements homeTeamNode = team.select(".home-team");
                    String homeTeamImgLoc = homeTeamNode.select("img").first().attr("src");
                    String homeTeamName = homeTeamNode.select(".team-name").text();

                    MatchUp matchup = new MatchUp(new Team(awayTeamName, awayTeamImgLoc, false, true), new Team(homeTeamName, homeTeamImgLoc, false, true));
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
