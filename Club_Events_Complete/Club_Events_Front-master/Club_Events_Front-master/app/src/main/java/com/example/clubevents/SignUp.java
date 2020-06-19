package com.example.clubevents;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    Button signUp;
    EditText pass, newPass, email, user_name;
    ImageView back;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp = findViewById(R.id.NewSignUp);
        back = findViewById(R.id.back3);
        pass = findViewById(R.id.newPass);
        newPass = findViewById(R.id.NewRePass);
        email = findViewById(R.id.newEmail);
        user_name = findViewById(R.id.newName);
        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,StartUpScreen.class));
                finish();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                final String email, pass, repass, nameUser;
                email = SignUp.this.email.getText().toString();
                pass = SignUp.this.pass.getText().toString();
                repass = SignUp.this.newPass.getText().toString();
                nameUser = SignUp.this.user_name.getText().toString();
                if (!repass.equals(pass)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "The Passwords do not match", Toast.LENGTH_LONG);
                    toast.show();
                } else if (pass.length() < 8) {
                    Toast toast = Toast.makeText(getApplicationContext(), "The Password must have at least 8 characters", Toast.LENGTH_LONG);
                    toast.show();
                } else if (email != null && nameUser != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Users").document(email);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("LOL", "DocumentSnapshot data: " + document.getData());
                                    Toast toast = Toast.makeText(getApplicationContext(), "An Account is Already Registered With This E Mail", Toast.LENGTH_LONG);
                                    toast.show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                } else {
                                    Log.d("LOL", "No such document");

                                    mAuth.createUserWithEmailAndPassword(email, pass)
                                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Log.d("LOL", "onComplete: popopppp");
                                                        FirebaseFirestore db = MainActivity.db;
                                                        User user = new User(nameUser, email, new ArrayList<String>(),0L);
                                                        db.collection("Users").document(email).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Log.d("LOL", "onSuccess: ");
                                                                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                                                                editor.putInt(MainActivity.SignedIN, 1);
                                                                editor.commit();
                                                                progressBar.setVisibility(View.INVISIBLE);
                                                                Intent intent = new Intent(SignUp.this, SetFavourites.class);
                                                        startActivity(intent);
                                                        finish();
                                                            }
                                                        });

//                                                        Intent intent = new Intent(SignUp.this, MainActivity.class);
//                                                        startActivity(intent);
//                                                        finish();

                                                    } else {
                                                        Log.d("LOL", "onComplete: BADEMIAL");
                                                        Toast toast = Toast.makeText(getApplicationContext(), "Please Enter a Valid E Mail", Toast.LENGTH_LONG);
                                                        toast.show();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                    }

                                                }


                                            });
                                }
                            } else {
                                Log.d("LOL", "get failed with ", task.getException());
                            }
                        }
                    });

                }
            }

        });


    }

    static class User {
        public String name, email;
        public ArrayList<String> favs_user;
        public Long init;

        public User() {
        }

        public User(String name, String email, ArrayList<String> favs_user,Long init) {
            this.name = name;
            this.email = email;
            this.favs_user = favs_user;
            this.init=init;
        }
    }
}
