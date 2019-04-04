package com.example.yash.projectwork;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;

    private Button logout;

    //database reference object
//    private DatabaseReference databaseReference;//to store data in DB

//    private FirebaseDatabase firebaseDatabase;

    private EditText name, address;
    private Button save;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //databaseReference= firebaseDatabase.getReference();//setting a path

        name = findViewById(R.id.edittextname);
        address = findViewById(R.id.edittextaddress);
        save = findViewById(R.id.save);

        firebaseAuth = FirebaseAuth.getInstance();

        //this means user has not logged in
        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            //start login activity
            startActivity(new Intent(this, LoginActivity.class));
        }
        //if the user has not logged in

        FirebaseUser user = firebaseAuth.getCurrentUser();


        TextView user_email = findViewById(R.id.textViewUserEmail);
        if (user != null) {
            user_email.setText("Welcome " + user.getEmail());
        }
        logout = findViewById(R.id.buttonlogout);

        logout.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void saveuserinformation() {
        String Name = name.getText().toString().trim();
        String Address = address.getText().toString().trim();

//        UserInformation userInformation = new UserInformation(Name, Address);//passing values to class via an object
        Map<String, String> map = new HashMap<>();
        map.put("Name", Name);
        map.put("Address", Address);

        FirebaseUser user = firebaseAuth.getCurrentUser();//get unique id of the user
        if (user != null) {
            FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).setValue(map);//saves data as per the unique userid
        }

        Toast.makeText(this, "Information saved...", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {
            //signout
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if (v == save) {
            //call saveuserinfo method
            saveuserinformation();
        }
    }
}
