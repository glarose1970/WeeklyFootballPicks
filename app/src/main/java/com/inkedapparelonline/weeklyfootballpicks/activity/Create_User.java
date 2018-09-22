package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.inkedapparelonline.weeklyfootballpicks.R;

public class Create_User extends AppCompatActivity {

    private EditText mEtUserName, mEtCompanyName;
    private Button mBtnSave, mBtnCancel;

    private Button.OnClickListener mBtnSaveOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: create save method
        }
    };

    private Button.OnClickListener mBtnCancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO: create cancel method
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mEtUserName = findViewById(R.id.create_user_et_username);
        mEtCompanyName = findViewById(R.id.create_user_et_company_name);
        mBtnSave       = findViewById(R.id.create_user_btnSave);
        mBtnSave.setOnClickListener(mBtnSaveOnClickListener);
        mBtnCancel     = findViewById(R.id.create_user_btnCancel);
        mBtnCancel.setOnClickListener(mBtnCancelOnClickListener);
    }

    private void Handle_CancelClick() {

    }

    private void Handle_SaveClick() {

    }
}
