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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignIn extends AppCompatActivity {
    Button signIn;
    ImageView back;
    ProgressBar progressBar;
    EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signIn=findViewById(R.id.signIn_signIn);
        back=findViewById(R.id.back_signIn);
        email=findViewById(R.id.email_signIn);
        password=findViewById(R.id.pass_signIn);
        progressBar=findViewById(R.id.progressBar3);
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,StartUpScreen.class));
                finish();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);


                String curEmail=email.getText().toString();
                String curPass=password.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(curEmail, curPass)
                        .addOnCompleteListener(SignIn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    MainActivity.sharedPreferences.edit().putInt(MainActivity.SignedIN,1).commit();
                                    Log.d("LOL", "onComplete: "+mAuth.getCurrentUser());


                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
//                                    DocumentReference docRef = db.collection("Users").document(mAuth.getCurrentUser().getEmail());
//                                    Task<DocumentSnapshot> docSnap = docRef.get();
//                                    while (true) {
//                                        try {
//                                            Thread.sleep(100);
//                                        } catch (InterruptedException e) {
//                                            e.printStackTrace();
//                                        }
//                                        if (docSnap.isComplete()) {
//                                            break;
//                                        }
//                                    }
//
//
//                                    if (docSnap.getResult().getLong("init").longValue() == 1)
                                    {

                                        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                                        editor.putInt(MainActivity.ExistingSETUP, 1);
//                                        ArrayList<String> arrayList=(ArrayList<String>)docSnap.getResult().get("favs_user");
//                                        for (String clubName:arrayList)
//                                        {
//                                            DisplayContent.userFavourites.add(clubName);
//                                        }
                                        progressBar.setVisibility(View.INVISIBLE);
                                        editor.commit();
                                      //  MainActivity.sharedPreferences.edit().putStringSet(MainActivity.UserFAVS,new HashSet<String>()).commit();
                                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }


                                    }
                                 else {
                                    // If sign in fails, display a message to the user.
                                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid Combination", Toast.LENGTH_LONG);
                                    toast.show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                // ...
                            }
                        });
            }

        });
    }

}
