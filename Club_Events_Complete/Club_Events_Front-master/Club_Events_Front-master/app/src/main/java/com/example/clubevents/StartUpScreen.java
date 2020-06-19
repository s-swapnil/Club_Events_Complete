package com.example.clubevents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class StartUpScreen extends AppCompatActivity {
    Button signIn,signUp,continueGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up__screen);
        signIn=findViewById(R.id.signInBtn);
        signUp=findViewById(R.id.signUpbtn);
        continueGuest=findViewById(R.id.guestBtn);

        continueGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putInt(MainActivity.GuestMode, 1);
                editor.commit();
                startActivity(new Intent(StartUpScreen.this,SetFavourites.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUpScreen.this,SignUp.class));
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartUpScreen.this,SignIn.class));
                finish();
            }
        });
    }
}
