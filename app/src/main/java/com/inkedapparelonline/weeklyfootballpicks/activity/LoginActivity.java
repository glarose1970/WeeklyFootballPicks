package com.inkedapparelonline.weeklyfootballpicks.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.flags.impl.DataUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inkedapparelonline.weeklyfootballpicks.R;
import com.inkedapparelonline.weeklyfootballpicks.helpers.MatchUpHelper;

public class LoginActivity extends Activity {


    Button btn_SignIn;
    Button btn_createAcct;
    EditText et_Email;
    EditText et_Password;

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        et_Email       = findViewById(R.id.signin_et_email);
        et_Password    = findViewById(R.id.signin_et_password);
        btn_SignIn     = findViewById(R.id.signin_btn_signin);
        btn_createAcct = findViewById(R.id.signin_btn_createAcct);

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_Email.getText().toString().trim();
                String password = et_Password.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    signIn(email, password);
                }else {
                    Toast.makeText(LoginActivity.this, "All fields required", Toast.LENGTH_LONG).show();
                    et_Email.requestFocus();
                }
              // startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        btn_createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_Email.getText().length() > 0 && et_Password.getText().length() > 0) {
                    Bundle details = new Bundle();
                    details.putStringArray("details", new String[] {et_Email.getText().toString(), et_Password.getText().toString()});
                    Intent intent = new Intent(LoginActivity.this, NewAcctActivity.class);
                    intent.putExtras(details);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(LoginActivity.this, NewAcctActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
           Intent intent = new Intent(LoginActivity.this, MainActivity.class);
           startActivity(intent);
            // mAuth.signOut();
        }

    }

    private void signIn(String email, String password) {

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                                et_Email.requestFocus();
                            }
                        }
                    });

    }
}
