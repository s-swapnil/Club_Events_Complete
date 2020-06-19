package com.example.clubevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity implements OnAppStartInterface {

    public static final String MyPREFERENCES = "MyPrefs";
    public static final String ExistingSETUP = "EXISTING";
    public static final String UserFAVS = "FAVS";
    public static final String SignedIN = "SignedIn";
    public static final String GuestMode="Guest";
    public static SharedPreferences sharedPreferences;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressbar9);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        DisplayContent.userFavourites=new HashSet<>();
        if (MainActivity.sharedPreferences.getInt(MainActivity.GuestMode,0)==1) {
            DisplayContent.userFavourites=null;
            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
            editor.putInt(MainActivity.GuestMode,0);
            editor.putInt(MainActivity.ExistingSETUP, 0);
            editor.commit();
        }


        if (sharedPreferences.getInt(SignedIN, 0) == 0) {
            startActivity(new Intent(this, StartUpScreen.class));
            finish();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            OnAppStartAsyncTask onAppStartAsyncTask=new OnAppStartAsyncTask(this);
            onAppStartAsyncTask.execute();
        }

    }


    @Override
    public void OnGetResults(int i) {
        progressBar.setVisibility(View.INVISIBLE);
        if (i==0) {
            startActivity(new Intent(this, SetFavourites.class));
            finish();
        } else if (i==1) {
            startActivity(new Intent(this, DisplayContent.class));
            finish();
        }
    }
}
