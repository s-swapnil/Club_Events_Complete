package com.example.clubevents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class SetFavourites extends AppCompatActivity {


    CheckBox cadence, anR, fineArts, montage, lumiere, octaves, xpressions, litsoc, debsoc;
    CheckBox aero, equinox, coding, cnA, electronics, prakriti, fnE, robotics, quiz, eCell;
    CheckBox aquatics, athletics, badminton, basketball, cricket, football, hockey, squash, tennis, tableTennis, volleyball, weightlifting;
    TextView textView;
    Button save, selectAll;
    private ArrayList<CheckBox> boxes = new ArrayList<>();
    private int select = 0;
    private int noSelect = 0;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("noSelect", noSelect);
        outState.putInt("select", select);
        outState.putString("SelectAll", selectAll.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        noSelect = savedInstanceState.getInt("noSelect");
        select = savedInstanceState.getInt("select");
        selectAll.setText(savedInstanceState.getString("SelectAll"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_favourites);
        setTitle("Set Favourites");
        InitializeScreen();
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.commit();
        DisplayContent.fetchingData = 0;

        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select == 0) {
                    selectAll.setText("RESET");
                    select = 1;

                    for (int i = 0; i < boxes.size(); i++) {
                        boxes.get(i).setChecked(true);
                    }
                } else {
                    selectAll.setText("SELECT ALL");
                    select = 0;

                    for (int i = 0; i < boxes.size(); i++) {
                        boxes.get(i).setChecked(false);
                    }
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < boxes.size(); i++) {
                    if (boxes.get(i).isChecked()) {
                        noSelect++;
                    }
                }

                if (noSelect == 0) {
                    Toast.makeText(getApplicationContext(),
                            "Please Select At Least One Club",
                            Toast.LENGTH_LONG)
                            .show();

                } else {
                    Set<String> strings = new HashSet<>();
                    noSelect = 0;
                    for (CheckBox checkBox : boxes) {
                        if (checkBox.isChecked()) {
                            strings.add(checkBox.getText().toString());
                        }
                    }
                    SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                    editor.remove(MainActivity.UserFAVS);
                    editor.putStringSet(MainActivity.UserFAVS, strings);
                    editor.putInt(MainActivity.ExistingSETUP, 1);
                    editor.commit();

                    startActivity(new Intent(SetFavourites.this, DisplayContent.class));
                    finish();
                }
            }
        });

    }

    private void InitializeScreen() {
        cadence = findViewById(R.id.cadence_box);
        anR = findViewById(R.id.anR_box);
        fineArts = findViewById(R.id.fineArts_box);
        montage = findViewById(R.id.montage_box);
        lumiere = findViewById(R.id.lumiere_box);
        octaves = findViewById(R.id.octaves_box);
        xpressions = findViewById(R.id.xpressions_box);
        litsoc = findViewById(R.id.litSoc_box);
        debsoc = findViewById(R.id.debSoc_box);

        aero = findViewById(R.id.aeromodelling_box);
        equinox = findViewById(R.id.equinox_box);
        coding = findViewById(R.id.coding_box);
        cnA = findViewById(R.id.cnA_box);
        electronics = findViewById(R.id.electronics_box);
        prakriti = findViewById(R.id.prakriti_box);
        fnE = findViewById(R.id.fnE_box);
        robotics = findViewById(R.id.robotics_box);
        quiz = findViewById(R.id.quiz_box);
        eCell = findViewById(R.id.eCell_box);

        aquatics = findViewById(R.id.aquatics_box);
        athletics = findViewById(R.id.athletics_box);
        badminton = findViewById(R.id.badminton_box);
        basketball = findViewById(R.id.basketball_box);
        cricket = findViewById(R.id.cricket_box);
        football = findViewById(R.id.football_box);
        hockey = findViewById(R.id.hockey_box);
        squash = findViewById(R.id.squash_box);
        tennis = findViewById(R.id.tennis_box);
        tableTennis = findViewById(R.id.tableTennis_box);
        volleyball = findViewById(R.id.volleyball_box);
        weightlifting = findViewById(R.id.weightlifting_box);

        selectAll = findViewById(R.id.selectAll_btn);
        save = findViewById(R.id.save_btn);
        textView = findViewById(R.id.firstTime2);


        boxes.add(cadence);
        boxes.add(anR);
        boxes.add(fineArts);
        boxes.add(montage);
        boxes.add(lumiere);
        boxes.add(octaves);
        boxes.add(xpressions);
        boxes.add(litsoc);
        boxes.add(debsoc);

        boxes.add(aero);
        boxes.add(equinox);
        boxes.add(coding);
        boxes.add(cnA);
        boxes.add(electronics);
        boxes.add(prakriti);
        boxes.add(fnE);
        boxes.add(robotics);
        boxes.add(quiz);
        boxes.add(eCell);

        boxes.add(aquatics);
        boxes.add(athletics);
        boxes.add(badminton);
        boxes.add(basketball);
        boxes.add(cricket);
        boxes.add(football);
        boxes.add(hockey);
        boxes.add(squash);
        boxes.add(tennis);
        boxes.add(tableTennis);
        boxes.add(volleyball);
        boxes.add(weightlifting);

        if (MainActivity.sharedPreferences.getInt(MainActivity.ExistingSETUP, 0) == 1) {
            textView.setText(" ");

            for (int i = 0; i < boxes.size(); i++) {
                for (String name : DisplayContent.userFavourites) {
                    Log.d("HE", "InitializeScreen: "+name+"  "+i);
                    if (boxes.get(i).getText().equals(name)) {
                        boxes.get(i).setChecked(true);
                    }
                }
            }
        }

    }
}
