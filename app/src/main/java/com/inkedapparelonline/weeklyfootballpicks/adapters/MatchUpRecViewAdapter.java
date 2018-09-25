package com.inkedapparelonline.weeklyfootballpicks.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.activity.MatchupActivity;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MatchUpRecViewAdapter  extends RecyclerView.Adapter<MatchUpRecViewAdapter.MatchUp_ViewHolder>{

    List<MatchUp> matchupList;
    Context context;
    MatchupActivity.Load_Week_Matchups matchUps;
    public MatchUpRecViewAdapter(Context context, MatchupActivity.Load_Week_Matchups matchup, List<MatchUp> list) {
        this.context = context;
        this.matchupList = list;
        this.matchUps = matchup;
    }

    @NonNull
    @Override
    public MatchUp_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matchup_single_row_item, parent, false);
        return new MatchUp_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchUp_ViewHolder holder, int position) {

        int awayScore;
        int homeScore;
        if (matchupList.get(position).getAwayTeam().getScore().length() > 2 && matchupList.get(position).getHomeTeam().getScore().length() > 2
                || matchupList.get(position).getAwayTeam().getScore().equalsIgnoreCase("--") || matchupList.get(position).getHomeTeam().getScore().equalsIgnoreCase("--")) {
            awayScore = 0;
            homeScore = 0;
        }else {
            awayScore = Integer.parseInt(matchupList.get(position).getAwayTeam().getScore());
            homeScore = Integer.parseInt(matchupList.get(position).getHomeTeam().getScore());
        }

        if (awayScore > homeScore) {
           // holder.tv_awayTeamName.setTextColor(Color.parseColor("#026014"));
           // holder.tv_awayTeamScore.setTextColor(Color.parseColor("#026014"));
            holder.awayTeamContainer.setBackgroundColor(Color.parseColor("#73af72"));
            holder.tv_awayTeamScore.setTextColor(Color.WHITE);
            holder.tv_awayTeamName.setTextColor(Color.WHITE);
            holder.tv_awayTeamRecord.setTextColor(Color.WHITE);
        }else if (homeScore > awayScore){
           // holder.tv_homeTeamName.setTextColor(Color.parseColor("#026014"));
           // holder.tv_homeTeamScore.setTextColor(Color.parseColor("#026014"));
            holder.homeTeamContainer.setBackgroundColor(Color.parseColor("#73af72"));
            holder.tv_homeTeamScore.setTextColor(Color.WHITE);
            holder.tv_homeTeamName.setTextColor(Color.WHITE);
            holder.tv_homeTeamRecord.setTextColor(Color.WHITE);
        }else {

        }

        //set away team data
        Picasso.get().load(matchupList.get(position).getAwayTeam().getImgLocation()).into(holder.iv_awayTeamLogo);
        holder.tv_awayTeamName.setText(matchupList.get(position).getAwayTeam().getName());
        holder.tv_awayTeamRecord.setText(matchupList.get(position).getAwayTeam().getRecord());
        holder.tv_awayTeamScore.setText(matchupList.get(position).getAwayTeam().getScore());

        //set home team data
        Picasso.get().load(matchupList.get(position).getHomeTeam().getImgLocation()).into(holder.iv_homeTeamLogo);
        holder.tv_homeTeamName.setText(matchupList.get(position).getHomeTeam().getName());
        holder.tv_homeTeamRecord.setText(matchupList.get(position).getHomeTeam().getRecord());
        holder.tv_homeTeamScore.setText(matchupList.get(position).getHomeTeam().getScore());
    }

    @Override
    public int getItemCount() {
        return matchupList.size();
    }

    public class MatchUp_ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout awayTeamContainer, homeTeamContainer;
        ImageView iv_awayTeamLogo, iv_homeTeamLogo;
        TextView tv_awayTeamName, tv_awayTeamRecord, tv_awayTeamScore, tv_homeTeamName, tv_homeTeamRecord, tv_homeTeamScore;

        public MatchUp_ViewHolder(View itemView) {
            super(itemView);

            awayTeamContainer = itemView.findViewById(R.id.single_matchup_awayTeam_container);
            homeTeamContainer = itemView.findViewById(R.id.single_matchup_homeTeam_container);
            iv_awayTeamLogo = itemView.findViewById(R.id.matchup_single_row_item_iv_awayTeamLogo);
            iv_homeTeamLogo = itemView.findViewById(R.id.matchup_single_row_item_iv_homeTeamLogo);
            tv_awayTeamName  = itemView.findViewById(R.id.matchup_single_row_item_tvAwayTeamName);
            tv_awayTeamRecord = itemView.findViewById(R.id.matchup_single_row_item_tvAwayTeamRecord);
            tv_awayTeamScore  = itemView.findViewById(R.id.matchup_single_row_item_tvAwayTeamScore);
            tv_homeTeamName   = itemView.findViewById(R.id.matchup_single_row_item_tvHomeTeamName);
            tv_homeTeamRecord = itemView.findViewById(R.id.matchup_single_row_item_tvHomeTeamRecord);
            tv_homeTeamScore  = itemView.findViewById(R.id.matchup_single_row_item_tvHomeScore);
        }
    }
}
