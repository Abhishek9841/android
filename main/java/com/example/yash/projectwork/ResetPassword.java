package com.example.yash.projectwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText resetemail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mAuth = FirebaseAuth.getInstance();

//        TextView info = findViewById(R.id.info);
        resetemail = findViewById(R.id.resetemail);
        Button sendmail = findViewById(R.id.sendmail);

        sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_email = resetemail.getText().toString().trim();//gets user email

                // to check is user_email field is empty
                if (TextUtils.isEmpty(user_email)) {
                    Toast.makeText(ResetPassword.this, "Please enter your valid email id first", Toast.LENGTH_LONG).show();
                } else {
                    //we will send a mail to the email provided
                    mAuth.sendPasswordResetEmail(user_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ResetPassword.this, "Please check your email", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(ResetPassword.this, LoginActivity.class));
                            } else {
                                String message = task.getException().getMessage();//gets the type of error occured
                                Toast.makeText(ResetPassword.this, "Error Occured : " + message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
