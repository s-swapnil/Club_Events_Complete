package com.example.clubevents;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OnAppStartAsyncTask extends AsyncTask<Void, Void, Integer> {
    private OnAppStartInterface onAppStartInterface;

    public OnAppStartAsyncTask(OnAppStartInterface onAppStartInterface) {
        this.onAppStartInterface = onAppStartInterface;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Task<DocumentSnapshot> docSnap = docRef.get();
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (docSnap.isComplete()) {
                break;
            }
        }


        if (docSnap.getResult().getLong("init").longValue() == 1) {

            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
            editor.putInt(MainActivity.ExistingSETUP, 1);
            Set<String> set=new HashSet<>();

            ArrayList<String> arrayList = (ArrayList<String>) docSnap.getResult().get("favs_user");
            SignUp.User userss=docSnap.getResult().toObject(SignUp.User.class);
            Log.d("ADD", "doInBackgrounds: USER "+ userss.init);

            Log.d("ADD", "doInBackgrounds: int "+docSnap.getResult().getString("name"));
            Log.d("ADD", "doInBackgrounds: ADDSS "+arrayList.toString());
            for (String clubName : arrayList) {
                set.add(clubName);
                Log.d("ADD", "doInBackgrounds: ADD "+clubName);
            }
            Log.d("ADD", "doInBackgrounds: result "+set);
            editor.putStringSet(MainActivity.UserFAVS,set);
            editor.commit();
            return 1;
        } else {
            SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
            editor.putInt(MainActivity.ExistingSETUP, 0);
            editor.commit();
            return 0;
        }
    }


    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        onAppStartInterface.OnGetResults(integer);
    }

}
