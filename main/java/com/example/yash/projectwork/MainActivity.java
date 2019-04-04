package com.example.yash.projectwork;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    private EditText email;
    private EditText pass;
    private TextView signin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = findViewById(R.id.buttonRegister);
        email = findViewById(R.id.editTextEmail);
        pass = findViewById(R.id.editTextPassword);
        signin = findViewById(R.id.signin);

        firebaseAuth = FirebaseAuth.getInstance();//intializes the firebase auth object

        //to check if user is already logged in or not
        if (firebaseAuth.getCurrentUser() != null) {  //-->this means user has already logged in
            //profile activity here
            finish();//before starting new activity we must finish current activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        progressDialog = new ProgressDialog(this);//initialize the progress bar object

        register.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    private void registeruser() {
        String emailid = email.getText().toString().trim();
        final String password = pass.getText().toString().trim();

        if (TextUtils.isEmpty(emailid)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;//stopping function exec further
            //email is empty
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;//stopping function exec further
            //password is empty
        }
        //if validations are ok we will show a progress bar
        progressDialog.setMessage("Registering User.....");
        progressDialog.show();

        //creates email with password in the database
        firebaseAuth.createUserWithEmailAndPassword(emailid, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //user is successfully registered
                    Toast.makeText(MainActivity.this, "User is successfully registered", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();//hides the progress dialog after succesful registration
                    finish();//before starting new activity we must finish current activity
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                } else {
                    Toast.makeText(MainActivity.this, "SORRY!! User could not be successfully registered", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == register) {
            registeruser();//registers user
        }
        if (v == signin) {
            //opens the login activity here
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
