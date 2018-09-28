package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.helpers.BottomNavigationViewHelper;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;
import com.inkedapparelonline.weeklyfootballpicks.helpers.PlayerHelper;
import com.inkedapparelonline.weeklyfootballpicks.model.Player;
import com.inkedapparelonline.weeklyfootballpicks.model.Team;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Create_User extends AppCompatActivity {

    private static final String TAG = "Create_User";

    private static final int ACTIVITY_NUM = 3;
    private EditText mEtUserName, mEtCompanyName;
    private Button mBtnSave, mBtnCancel;

    private DatabaseReference mDataRef;
    private FirebaseDatabase mData;

    private Button.OnClickListener mBtnSaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Handle_Save_Click(mEtUserName.getText().toString(), mEtCompanyName.getText().toString());
        }
    };

    private Button.OnClickListener mBtnCancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Handle_Cancel_Click();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        setUpBottonNavView();
        mData = FirebaseDatabase.getInstance();
        mDataRef = mData.getReference("players");

        mEtUserName = findViewById(R.id.create_new_user_et_username);
        mEtCompanyName = findViewById(R.id.create_new_user_et_company_name);
        mBtnSave       = findViewById(R.id.create_new_user_btn_create);
        mBtnSave.setOnClickListener(mBtnSaveOnClickListener);
        mBtnCancel     = findViewById(R.id.create_new_user_btn_cancel);
        mBtnCancel.setOnClickListener(mBtnCancelOnClickListener);

    }

    private void setUpBottonNavView() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavView);
        BottomNavigationViewHelper.setUpBottomNavView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(Create_User.this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    private void Handle_Cancel_Click() {
        startActivity(new Intent(Create_User.this, MainActivity.class));
    }

    private void Handle_Save_Click(final String str_user_name, final String str_company) {

        if (!TextUtils.isEmpty(mEtUserName.getText()) && !TextUtils.isEmpty(mEtCompanyName.getText())) {

            mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (UserExist(dataSnapshot, str_user_name, str_company)) {
                        Toast.makeText(Create_User.this, "Username Already Exists\r\nChange Player Name or Company!", Toast.LENGTH_SHORT).show();
                        mEtUserName.requestFocus();
                    }else {
                        String id = PlayerHelper.Generate_Player_Id(8);
                        Player player = new Player(str_user_name, str_company, id);
                        mDataRef.child(id).setValue(player);
                        mEtUserName.setText("");
                        mEtCompanyName.setText("");
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {
            Toast.makeText(Create_User.this, "All fields required!", Toast.LENGTH_LONG).show();
            mEtUserName.requestFocus();
        }
    }

    private boolean UserExist(DataSnapshot data, String username, String company) {

        Player player = new Player();

        for (DataSnapshot user : data.getChildren()) {
            Log.d(TAG, "UserExist: " + data.getValue(Player.class).getName());

            player.setName(user.getValue(Player.class).getName().toLowerCase());
            player.setCompany(user.getValue(Player.class).getCompany());

            if (player.getName().equalsIgnoreCase(username) && player.getCompany().equalsIgnoreCase(company)) {
                return true;
            }
        }
        return false;
    }
}
