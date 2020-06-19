package com.example.clubevents;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class DataRetrieval extends AsyncTask<Set<String>,Integer, ArrayList<DisplayContent.Entry>> {

    static ArrayList<DisplayContent.Entry> list;
    private ProgressBar mProgressBar;
    private TextView mTextView;
public DataRetrieval(ProgressBar progressBar,TextView textView)
{
    mProgressBar=progressBar;
    mTextView=textView;
}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText("Fetching Data\nPlease Wait");
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressBar.setProgress(values[0]);
        Log.d(TAG, "onProgressUpdate: "+mProgressBar.getProgress()+"  "+values[0]);

    }

    @Override
    protected ArrayList<DisplayContent.Entry> doInBackground(Set<String>... sets) {
list=new ArrayList<>();
        Set<String> userfavs = sets[0];
        int count=userfavs.size();
        int i=0;
        FirebaseFirestore db = MainActivity.db;
        CollectionReference clubs = db.collection("Clubs");
        for (String clubname : userfavs) {
             Long lastPost = 0L;
            DocumentReference docRef = clubs.document(clubname);
            Task<DocumentSnapshot> docSnap=docRef.get();
            while (true)
            {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (docSnap.isComplete())
                {break;}
            }


            lastPost=docSnap.getResult().getLong("POSTS");

            CollectionReference posts = docRef.collection("Posts");

            Log.d(TAG, "doInBackground: lastpost" + lastPost);


            Task<QuerySnapshot> snapshotTask=posts.whereGreaterThan("PostNum",lastPost-10).get();
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (snapshotTask.isComplete())
                {break;}
            }
            for (QueryDocumentSnapshot documentSnapshot: snapshotTask.getResult())
            {
                DisplayContent.Entry entry = documentSnapshot.toObject(DisplayContent.Entry.class);
                if(entry.IMG_URLS==null)
                {
                    entry.IMG_URLS=new ArrayList<>();
                }
                list.add(entry);
            }
i++;
publishProgress(i*100/count);
            Log.d(TAG, "doInBackground: progress "+(i*100/count));
        }
        Log.d(TAG, "doInBackground: LISTSSIZE" + list.size());
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<DisplayContent.Entry> entries) {
        super.onPostExecute(entries);
        DisplayContent.SetList(entries);
        mProgressBar.setProgress(0);
        mProgressBar.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.INVISIBLE);
        Log.d("LIST", "onPostExecute: " + entries.size());
        Log.d("LIST", "onPostExecutesdds: "+DisplayContent.list.size());
        DisplayContent.fetchingData=0;

    }


}

