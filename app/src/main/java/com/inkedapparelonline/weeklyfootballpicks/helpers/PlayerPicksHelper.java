package com.inkedapparelonline.weeklyfootballpicks.helpers;

import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class PlayerPicksHelper {

    //TODO: add methods to help the player picks view
    public static int Get_Player_Wins(String week, List<Player> playerPicks) {
        int winCount = 0;
        for (int i = 0; i < playerPicks.size(); i++) {
            try {

                Document page = Jsoup.connect("http://www.nfl.com/scores/2018/REG" + week).get();
                Elements scoreBoxNodes = page.getElementsByClass("new-score-box");

                for (Element scoreBox : scoreBoxNodes) {
                    Elements awayTeam = scoreBox.select(".away-team");
                    Elements homeTeam = scoreBox.select(".home-team");
                    String awayTeamName = awayTeam.select(".team-name").text();
                    String homeTeamName = homeTeam.select(".team-name").text();
                    if (awayTeam.select(".total-score").text() != "--" && homeTeam.select(".total-score").text() != "--") {
                        int awayTeamScore = Integer.valueOf(awayTeam.select(".total-score").text());
                        int homeTeamScore = Integer.valueOf(homeTeam.select(".total-score").text());

                        if (awayTeamScore > homeTeamScore) {
                            String winner = awayTeamName;
                            if (playerPicks.contains(winner)) {
                                winCount++;
                            }
                        }else {
                           String winner = homeTeamName;
                            if (playerPicks.contains(winner)) {
                                winCount++;
                            }
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return winCount;
    }

    //Make sure all the games are complete
    public static boolean isMatchupsComplete(String week) {
        try {
            Document page = Jsoup.connect("http://www.nfl.com/scores/2018/REG" + week).get();
            Elements scoreBoxNodes = page.getElementsByClass("new-score-box");

            for (Element scoreBox : scoreBoxNodes) {
                Elements awayNode = scoreBox.select(".away-team");
                Elements homeNode = scoreBox.select(".home-team");
                if (awayNode.select(".total-score").text().equalsIgnoreCase("--") || homeNode.select(".total-score").text().equalsIgnoreCase("--")) {
                    return false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
