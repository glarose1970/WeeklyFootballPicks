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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.activity.Player_Picks_Activity;
import com.inkedapparelonline.weeklyfootballpicks.controller.PlayerPicksController;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;
import com.inkedapparelonline.weeklyfootballpicks.model.MatchUp;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PlayerPicksRecViewAdapter extends RecyclerView.Adapter<PlayerPicksRecViewAdapter.ViewHolder> {

    Context context;
    List<MatchUp> matchupList;
    Player_Picks_Activity.Load_Matchup_Picks matchupPicks;
    List<Team> playerPicksList;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    DatabaseReference mDataRef = mDataBase.getReference("players");

    private int count = 0;
    private int matchupCount = MatchUpHelper.matchupCount;

    public PlayerPicksRecViewAdapter(Context context, List<MatchUp> matchupList, Player_Picks_Activity.Load_Matchup_Picks matchupPicks) {
        this.context = context;
        this.matchupList = matchupList;
        this.matchupPicks = matchupPicks;
        playerPicksList = new ArrayList<>();
    }

    @NonNull
    @Override
    public PlayerPicksRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_picks_single_row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlayerPicksRecViewAdapter.ViewHolder holder, final int position) {

        final Team awayTeam = matchupList.get(position).getAwayTeam();
        final Team homeTeam = matchupList.get(position).getHomeTeam();
        Picasso.get().load(matchupList.get(position).getAwayTeam().getImgLocation()).into(holder.iv_awayTeamLogo);
        Picasso.get().load(matchupList.get(position).getHomeTeam().getImgLocation()).into(holder.iv_homeTeamLogo);
        holder.tv_awayTeamName.setText(matchupList.get(position).getAwayTeam().getName());
        holder.tv_homeTeamName.setText(matchupList.get(position).getHomeTeam().getName());

        holder.checkBox_awayTeam.setOnCheckedChangeListener(null);
        holder.checkBox_homeTeam.setOnCheckedChangeListener(null);

        holder.checkBox_awayTeam.setChecked(awayTeam.isChecked());
        holder.checkBox_homeTeam.setChecked(homeTeam.isChecked());

        holder.checkBox_awayTeam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (count < matchupCount) {
                    awayTeam.setChecked(isChecked);
                    playerPicksList.add(new Team(matchupList.get(position).getAwayTeam().getName(), matchupList.get(position).getAwayTeam().getRecord(),
                            matchupList.get(position).getAwayTeam().getImgLocation(), matchupList.get(position).getAwayTeam().getScore(), true, true));
                    Toast.makeText(context, matchupList.get(position).getAwayTeam().getName(), Toast.LENGTH_SHORT).show();
                    count++;
                }else {
                    Toast.makeText(context, "Pick count reached!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

        holder.checkBox_homeTeam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (count < matchupCount) {
                    homeTeam.setChecked(isChecked);
                    Toast.makeText(context, matchupList.get(position).getHomeTeam().getName(), Toast.LENGTH_SHORT).show();
                    playerPicksList.add(new Team(matchupList.get(position).getHomeTeam().getName(), matchupList.get(position).getHomeTeam().getRecord(),
                            matchupList.get(position).getHomeTeam().getImgLocation(), matchupList.get(position).getHomeTeam().getScore(), true, true));
                    count++;
                }else {
                    Toast.makeText(context, "Pick count reached!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
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
