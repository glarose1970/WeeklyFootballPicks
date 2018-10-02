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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.adapters.MatchUpRecViewAdapter;
import com.inkedapparelonline.weeklyfootballpicks.helpers.BottomNavigationViewHelper;
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

public class MatchupActivity extends AppCompatActivity {

    private static final int ACTIVITY_NUM = 2;
    Spinner weekSpinner;
    RecyclerView recView;
    MatchUpRecViewAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matchup);

        setUpBottonNavView();

        weekSpinner = findViewById(R.id.matchup_activity_weekSpinner);
        recView = findViewById(R.id.matchup_activity_recView);
        layoutManager = new LinearLayoutManager(this);

        Integer[] weekNums = new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weekNums);
        weekSpinner.setAdapter(spinnerAdapter);
        weekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                new Load_Week_Matchups().execute(String.valueOf(position + 1));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setUpBottonNavView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(MatchupActivity.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    public class Load_Week_Matchups extends AsyncTask<String, Void, List<MatchUp>> {
        @Override
        protected List<MatchUp> doInBackground(String... strings) {
            List<MatchUp> mList = new ArrayList<>();
            try {
                Document page = Jsoup.connect("http://www.nfl.com/scores/2018/REG" + strings[0]).get();
                Elements matchUpNodes = page.getElementsByClass("new-score-box");
                for (Element team : matchUpNodes) {

                    //Away Team Data
                    Elements awayTeamNode = team.select(".away-team");
                    Element awayTeamImgLocElement = awayTeamNode.select("img").first();
                    String awayTeamImgLocValue = awayTeamImgLocElement.attr("src");
                    String awayTeamName = awayTeamNode.select(".team-name").text();
                    String awayTeamScore = awayTeamNode.select(".total-score").text();
                    String awayTeamRecord = awayTeamNode.select(".team-record").text();

                    //Home Team Data
                    Elements homeTeamNode = team.select(".home-team");
                    Element homeTeamImgLocElement = homeTeamNode.select("img").first();
                    String homeTeamImgLocValue = homeTeamImgLocElement.attr("src");
                    String homeTeamName = homeTeamNode.select(".team-name").text();
                    String homeTeamScore = homeTeamNode.select(".total-score").text();
                    String homeTeamRecord = homeTeamNode.select(".team-record").text();

                    //Add matchup to matchup list
                    mList.add(new MatchUp(new Team(awayTeamName, awayTeamRecord, awayTeamImgLocValue, awayTeamScore, false, false), new Team(homeTeamName, homeTeamRecord, homeTeamImgLocValue, homeTeamScore, false, false)));
                    String test = "";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mList;
        }

        @Override
        protected void onPostExecute(List<MatchUp> matchUps) {

            adapter = new MatchUpRecViewAdapter(MatchupActivity.this, Load_Week_Matchups.this,  matchUps);
            recView.setLayoutManager(layoutManager);
            recView.setAdapter(adapter);
        }
    }
}
