package com.example.clubevents;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UpdateProfile extends AsyncTask<Void,Void,Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Users").document(DisplayContent.userEmail);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                ArrayList<String> arrayList=new ArrayList<>();
                for (String name:DisplayContent.userFavourites)
                {
                    arrayList.add(name);
                }
                docRef.update("favs_user",arrayList);
                docRef.update("init",1);
                Log.d("LOL", "onSuccess: EddXIST");


            }
        });
        return null;
    }
}
