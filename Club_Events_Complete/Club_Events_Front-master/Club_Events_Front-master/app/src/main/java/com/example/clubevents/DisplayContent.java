package com.example.clubevents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

public class DisplayContent extends AppCompatActivity implements ViewListener, FragmentListener, NavigationView.OnNavigationItemSelectedListener,ProfileDetailListener {

    public static Set<String> userFavourites;
    public static ArrayList<Entry> list;
    public static int fetchingData = 0;
    public static String userName, userEmail;
    private static CustomAdapter adapter;
    private static RecyclerView recyclerView;
    public int returnFromFb = 0;
    Button changFavs;
    ProgressBar progressBar;
    TextView progressText;
    FirebaseAuth mAuth;
    private RecyclerView.LayoutManager layoutManager;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    public static void SetList(ArrayList<DisplayContent.Entry> entries) {
        if (MainActivity.sharedPreferences.getInt(MainActivity.GuestMode,0) == 0) {
            UpdateProfile updateProfile = new UpdateProfile();
            updateProfile.execute();
        }
        list = entries;
        Collections.sort(list);
        Log.d("LIST", "SetList: " + list.size());
        adapter.UpdateData(list);
    }

    @Override
    public void onBackPressed() {
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
            Log.d("CLOSE", "onBackPressed: closedrawer");
        } else {
            Log.d("CLOSE", "onBackPressed: NOTOPEN");
            if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                SetVisibiiltyViews(true);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
        editor.commit();
        fetchingData = 0;
        Log.d("LOL", "onStop: HERE");
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth=FirebaseAuth.getInstance();

        list = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_content);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);
        changFavs = findViewById(R.id.changFavs);
        progressBar.setVisibility(View.INVISIBLE);
        progressText.setVisibility(View.INVISIBLE);
        userFavourites = MainActivity.sharedPreferences.getStringSet(MainActivity.UserFAVS, null);
        Log.d("haha", "onCreate: user="+MainActivity.sharedPreferences.getStringSet(MainActivity.UserFAVS, null));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dl = findViewById(R.id.drawer);
        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.OPEN, R.string.CLOSE);
        dl.addDrawerListener(t);
        t.syncState();
        Log.d("LOL", "onCreate: ");
        nv = findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);
        Log.d("HII", "onCreate: USERFAVS+after"+userFavourites.toString());
        if (MainActivity.sharedPreferences.getInt(MainActivity.GuestMode,0) == 0) {
            ProfileDetailsAsyncTask profileDetailsAsyncTask=new ProfileDetailsAsyncTask(this);
            profileDetailsAsyncTask.execute();
            Log.d("LOL", "onCreate: HEREEEEEEEEEEE");
                }

         else
             {
            View headerView = nv.getHeaderView(0);
            TextView name = (TextView) headerView.findViewById(R.id.person_name);
            name.setText("GUEST");
            TextView email = (TextView) headerView.findViewById(R.id.person_email);
            email.setText(" ");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        DataRetrieval dataRetrieval = new DataRetrieval(progressBar, progressText);
        if (returnFromFb == 0) {
            Log.d("HAHA", "onStart: "+userFavourites);
            SetContent();
            fetchingData = 1;
            dataRetrieval.execute(userFavourites);


        }
        changFavs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fetchingData == 0) {
                    startActivity(new Intent(DisplayContent.this, SetFavourites.class));
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Can't Change Favourites While Fetching Data.\nPlease Wait",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    public void SetContent() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new CustomAdapter(list, this);
        recyclerView.setAdapter(adapter);

        Log.d("LIST", "SetContent: " + list.size());
    }

    @Override
    public void OnClick(int position) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com" + list.get(position).URL)));
        returnFromFb = 1;
    }

    @Override
    public void onClickText(int i) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Links.getURL(i))));
        returnFromFb = 1;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.developer: {
                SetVisibiiltyViews(false);
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new DevProfile(DisplayContent.this)).addToBackStack("DEV").commit();
                break;
            }
            case R.id.contact: {
                SetVisibiiltyViews(false);
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frag_container, new ContactUs(DisplayContent.this)).addToBackStack("CONTACT").commit();
                break;
            }
            case R.id.fav: {
                Log.d("THIS", "onNavigationItemSelected: BACKSRACK+ " + getSupportFragmentManager().getBackStackEntryCount());
                if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
                    Log.d("THIS", "onNavigationItemSelected: HERE ");
                    SetVisibiiltyViews(true);
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                }
                break;

            }
            case R.id.logOut: {

                SharedPreferences.Editor editor = MainActivity.sharedPreferences.edit();
                editor.putInt(MainActivity.ExistingSETUP, 0);
                editor.putInt(MainActivity.SignedIN, 0);
                if (MainActivity.sharedPreferences.getInt(MainActivity.GuestMode,0)==1)
                {
                    editor.remove(MainActivity.UserFAVS);
                }
                editor.remove(MainActivity.UserFAVS);
                editor.commit();
                mAuth.signOut();
                fetchingData = 0;
                startActivity(new Intent(DisplayContent.this, MainActivity.class));
                finish();
                break;


            }
        }
        dl.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SetVisibiiltyViews(boolean bool) {
        if (bool) {
            recyclerView.setVisibility(View.VISIBLE);
            changFavs.setVisibility(View.VISIBLE);
            if (fetchingData == 1) {
                progressText.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
            }
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            changFavs.setVisibility(View.INVISIBLE);
            progressText.setVisibility(View.INVISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void OnGetProfileDetails(SignUp.User user) {
        View headerView = nv.getHeaderView(0);
                    TextView name = (TextView) headerView.findViewById(R.id.person_name);
                    name.setText(user.name);
                    userName = user.name;
                    TextView email = (TextView) headerView.findViewById(R.id.person_email);
                    email.setText(user.email);
                    userEmail = user.email;
                    Log.d("LOL", "onSuccess: USER   " + user.toString());

    }

    static class Entry implements Comparable<Entry> {
        public String clubName;
        public String board;
        public String text;
        public Date timestamp;
        public String URL;
        public String VID_IMG_URL;
        public ArrayList<String> IMG_URLS;
        public String MoreImgs;

        public Entry() {
        }

        public Entry(String clubName, String board, String text, Date timestamp, String URL, String VID_IMG_URL, ArrayList<String> IMG_URLS, String MoreImgs) {
            this.clubName = clubName;
            this.board = board;
            this.text = text;
            this.timestamp = timestamp;
            this.URL = URL;
            this.VID_IMG_URL = VID_IMG_URL;
            this.IMG_URLS = IMG_URLS;
            this.MoreImgs = MoreImgs;

        }

        @Override
        public int compareTo(Entry o) {
            return o.timestamp.compareTo(timestamp);
        }
    }
}
