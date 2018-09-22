package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inkedapparelonline.weeklyfootballpicks.R;

public class NewAcctActivity extends Activity {

    EditText et_email;
    EditText et_password;
    Button btn_create;
    Button btn_cancel;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acct);

        mAuth = FirebaseAuth.getInstance();

        et_email    = findViewById(R.id.newAcct_et_email);
        et_password = findViewById(R.id.newAcct_et_password);
        btn_cancel  = findViewById(R.id.newAcct_btn_cancel);
        btn_create  = findViewById(R.id.newAcct_btn_createAcct);

        Bundle userDetail = this.getIntent().getExtras();
        if (userDetail != null) {
            String[] details = userDetail.getStringArray("details");
            Load_User_Details(details);
        }


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValid(et_email, et_password)) {
                    createUserAccount(et_email.getText().toString(), et_password.getText().toString());
                }else {
                    Toast.makeText(NewAcctActivity.this, "All fields required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Load_User_Details(String[] details) {
        if (details != null) {
            et_email.setText(details[0]);
            et_password.setText(details[1]);
        }

    }

    private void createUserAccount(String email, String password) {
        if (currentUser != null) {

        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(NewAcctActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(NewAcctActivity.this, "Account not created, please try again!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

    private boolean isValid(EditText et_email, EditText et_password) {
        if (et_email.getText().toString().length() > 0 && et_password.getText().toString().length() > 0) {
            return true;
        }else
            return false;
    }
}
