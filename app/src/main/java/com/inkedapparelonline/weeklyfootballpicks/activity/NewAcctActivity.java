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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inkedapparelonline.weeklyfootballpicks.R;

public class NewAcctActivity extends Activity {

    EditText et_email, et_company, et_password;
    Button btn_create, btn_cancel;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_acct);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mDataRef = mDatabase.getReference("players");

        et_email    = findViewById(R.id.newAcct_et_email);
        et_password = findViewById(R.id.newAcct_et_password);
        et_company  = findViewById(R.id.newAcct_et_company);
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
                    createUserAccount(et_email.getText().toString(), et_company.getText().toString(), et_password.getText().toString());
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

    private void createUserAccount(final String email, final String company, String password) {
        if (currentUser != null) {

        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String userID = mAuth.getCurrentUser().getUid().toString();
                                mDataRef.child(userID).child("name").setValue(email.toString());
                                mDataRef.child(userID).child("id").setValue(userID);
                                mDataRef.child(userID).child("company").setValue(company);
                                mDataRef.child(userID).child("winTotal").setValue("0");
                                mDataRef.child(userID).child("lossTotal").setValue("0");
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
