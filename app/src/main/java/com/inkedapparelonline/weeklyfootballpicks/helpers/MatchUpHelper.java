package com.inkedapparelonline.weeklyfootballpicks.helpers;

import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchUpHelper {

    public static final List<MatchUp> Load_Matchups(String week) {
        List<MatchUp> matchupList = new ArrayList<>();
        try {
            Document pageDoc = Jsoup.connect("http://www.nfl.com/scores/2018/REG" + week).get();
            Elements awayTeamNode = pageDoc.getElementsByClass("away-team");
            Elements homeTeamNode = pageDoc.getElementsByClass("home-team");

            //Loop the Team Nodes / Load the matchUpList.
            for (int i = 0; i < awayTeamNode.size(); i++) {
                //Away Team Data
                String awayTeamRecord = awayTeamNode.get(i).getElementsByClass("team-record").text();
                String awayTeamName = awayTeamNode.get(i).getElementsByClass("team-name").text();
                int awayTeamScore = Integer.parseInt(awayTeamNode.get(i).getElementsByClass("total-score").text());
                String awayTeamLogoUrl = awayTeamNode.get(i).getElementsByClass("team-logo").select("img").attr("src");
                String awayTeamAbrDetails = awayTeamNode.get(i).select("a").attr("href");
                int awayTeamAbrIndex = awayTeamAbrDetails.lastIndexOf('=');
                String awayTeamAbr = awayTeamAbrDetails.substring(awayTeamAbrIndex + 1);

                //Home Team Data
                String homeTeamRecord = homeTeamNode.get(i).getElementsByClass("team-record").text();
                String homeTeamName = homeTeamNode.get(i).getElementsByClass("team-name").text();
                int homeTeamScore = Integer.parseInt(homeTeamNode.get(i).getElementsByClass("total-score").text());
                String homeTeamLogoUrl = homeTeamNode.get(i).getElementsByClass("team-logo").select("img").attr("src");
                String homeTeamAbrDetails = homeTeamNode.get(i).select("a").attr("href");
                int homeTeamAbrIndex = homeTeamAbrDetails.lastIndexOf('=');
                String homeTeamAbr = homeTeamAbrDetails.substring(homeTeamAbrIndex + 1);

                matchupList.add(new MatchUp(new Team(awayTeamName, awayTeamAbr, awayTeamRecord, awayTeamLogoUrl, awayTeamScore), new Team(homeTeamName, homeTeamAbr, homeTeamRecord, homeTeamLogoUrl, homeTeamScore)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchupList;
    }

    public static final String Get_Week() {
        String week = "";
        try {
            Document page = Jsoup.connect("http://www.nfl.com/scores").get();
            week = page.getElementsByClass("week-title").text();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return week;
    }
}
