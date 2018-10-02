package com.inkedapparelonline.weeklyfootballpicks.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;

public class PlayerPicksController {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private static DatabaseReference mDataRef = mDatabase.getReference("players");


    public static void Save_Player_Picks(Player player) {

        if (mAuth.getCurrentUser() != null) {
            mDataRef.child(player.getId()).child("player_picks").setValue(player.getPicks());
        }
    }
}
