package com.inkedapparelonline.weeklyfootballpicks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.activity.Player_Picks_Activity;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayerPicksRecViewAdapter extends RecyclerView.Adapter<PlayerPicksRecViewAdapter.ViewHolder> {

    Context context;
    List<MatchUp> matchupList;
    Player_Picks_Activity.Load_Matchup_Picks matchupPicks;

    public PlayerPicksRecViewAdapter(Context context, List<MatchUp> matchupList, Player_Picks_Activity.Load_Matchup_Picks matchupPicks) {
        this.context = context;
        this.matchupList = matchupList;
        this.matchupPicks = matchupPicks;
    }

    @NonNull
    @Override
    public PlayerPicksRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_picks_single_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerPicksRecViewAdapter.ViewHolder holder, int position) {

        Picasso.get().load(matchupList.get(position).getAwayTeam().getImgLocation()).into(holder.iv_awayTeamLogo);
        Picasso.get().load(matchupList.get(position).getHomeTeam().getImgLocation()).into(holder.iv_homeTeamLogo);
        holder.tv_awayTeamName.setText(matchupList.get(position).getAwayTeam().getName());
        holder.tv_homeTeamName.setText(matchupList.get(position).getHomeTeam().getName());


    }

    @Override
    public int getItemCount() {
        return matchupList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox_awayTeam, checkBox_homeTeam;
        ImageView iv_awayTeamLogo, iv_homeTeamLogo;
        TextView tv_awayTeamName, tv_homeTeamName;

        public ViewHolder(View itemView) {
            super(itemView);

            checkBox_awayTeam = itemView.findViewById(R.id.player_picks_away_team_checkbox);
            checkBox_homeTeam = itemView.findViewById(R.id.player_picks_home_team_checkbox);
            iv_awayTeamLogo   = itemView.findViewById(R.id.player_picks_iv_away_team_logo);
            iv_homeTeamLogo   = itemView.findViewById(R.id.player_picks_iv_home_team_logo);
            tv_awayTeamName   = itemView.findViewById(R.id.player_picks_tv_away_team_name);
            tv_homeTeamName   = itemView.findViewById(R.id.player_picks_tv_home_team_name);

        }

    }
}
