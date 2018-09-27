package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    private static final int ACTIVITY_NUM = 3;
    private EditText mEtUserName, mEtCompanyName;
    private Button mBtnSave, mBtnCancel;

    private DatabaseReference mDataRef;
    private FirebaseDatabase mData;

    private Button.OnClickListener mBtnSaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Handle_Save_Click();
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

    private void Handle_Save_Click() {

        if (!TextUtils.isEmpty(mEtUserName.getText()) && !TextUtils.isEmpty(mEtCompanyName.getText())) {
            String username = mEtUserName.getText().toString();
            String company = mEtCompanyName.getText().toString();
            final String id = PlayerHelper.Generate_Player_Id(8);
            final Player player = new Player(username, company, id);
            mDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    mDataRef.child(id).setValue(player);
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
}
