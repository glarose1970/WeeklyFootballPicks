package com.inkedapparelonline.weeklyfootballpicks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;

import java.util.List;

public class PlayerRecViewAdapter  extends RecyclerView.Adapter<PlayerRecViewAdapter.PlayerViewHolder>{

    Context context;
    List<Player> playerList;

    public PlayerRecViewAdapter(Context context, List<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_single_row_item, parent, false);
        return new PlayerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {

        holder.name.setText(playerList.get(position).getName());
        holder.company.setText(playerList.get(position).getCompany());
        String win = String.valueOf(playerList.get(position).getWinTotal());
        holder.wins.setText("Wins : " + win);
        String loss = String.valueOf(playerList.get(position).getLossTotal());
        holder.losses.setText("Losses : " +  loss);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        TextView name, company, wins, losses;

        public PlayerViewHolder(View itemView) {
            super(itemView);
            name    = itemView.findViewById(R.id.player_card_tv_playername);
            company = itemView.findViewById(R.id.player_card_tv_companyrname);
            wins    = itemView.findViewById(R.id.player_single_row_tvWins);
            losses  = itemView.findViewById(R.id.player_single_row_tvLosses);
        }
    }
}
