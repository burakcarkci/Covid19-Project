package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class Profile extends AppCompatActivity {

    TextView name, mail;
    Button logout;
    Button dashboard;
    //ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout = findViewById(R.id.logout);
        dashboard = findViewById(R.id.dashboard);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.mail);
        //imageView2 = findViewById(R.id.imageView2);


        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            name.setText(signInAccount.getDisplayName());
            mail.setText(signInAccount.getEmail());
            //imageView2.setImageURI(signInAccount.getPhotoUrl());
            //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView2);
        }

        //redirect user to dashboard
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(intent);
            }
        });

        //logout user
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
