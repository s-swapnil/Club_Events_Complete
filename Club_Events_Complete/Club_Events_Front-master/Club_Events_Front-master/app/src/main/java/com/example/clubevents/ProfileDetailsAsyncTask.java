package com.example.clubevents;

import android.os.AsyncTask;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileDetailsAsyncTask extends AsyncTask<Void,Void,SignUp.User> {
    private ProfileDetailListener profileDetailListener;
    public ProfileDetailsAsyncTask(ProfileDetailListener profileDetailListener)
    {
        this.profileDetailListener=profileDetailListener;
    }
    @Override
    protected SignUp.User doInBackground(Void... voids) {
        FirebaseFirestore db = MainActivity.db;
            DocumentReference docRef = db.collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail());
            Task<DocumentSnapshot> docSnap=docRef.get();
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
                    SignUp.User user = docSnap.getResult().toObject(SignUp.User.class);
        return user;
    }

    @Override
    protected void onPostExecute(SignUp.User user) {
        super.onPostExecute(user);
        profileDetailListener.OnGetProfileDetails(user);

    }
}
