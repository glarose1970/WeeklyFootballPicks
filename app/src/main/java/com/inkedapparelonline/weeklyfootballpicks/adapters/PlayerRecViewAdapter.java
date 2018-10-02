package com.inkedapparelonline.weeklyfootballpicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.activity.Single_Player_View;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

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
    public void onBindViewHolder(@NonNull final PlayerViewHolder holder, final int position) {

        holder.name.setText(playerList.get(position).getName() + " -");
        holder.company.setText(playerList.get(position).getCompany());
        String win = String.valueOf(playerList.get(position).getWinTotal());
        holder.wins.setText("Wins : " + win);
        String loss = String.valueOf(playerList.get(position).getLossTotal());
        holder.losses.setText("Losses : " +  loss);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] names = new String[] {holder.name.getText().toString(), holder.company.getText().toString()};
                Bundle details = new Bundle();
                details.putStringArray("names", names);
                Intent intent = new Intent(holder.itemView.getContext(), Single_Player_View.class);
                intent.putExtras(details);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder{

        TextView name, company, wins, losses;

        public PlayerViewHolder(final View itemView) {
            super(itemView);
            name    = itemView.findViewById(R.id.player_card_tv_playername);
            company = itemView.findViewById(R.id.player_card_tv_companyrname);
            wins    = itemView.findViewById(R.id.player_single_row_tvWins);
            losses  = itemView.findViewById(R.id.player_single_row_tvLosses);

        }
    }
}
