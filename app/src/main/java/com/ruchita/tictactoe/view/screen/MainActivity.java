package com.ruchita.tictactoe.view.screen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ruchita.tictactoe.AppUtil;
import com.ruchita.tictactoe.R;
import com.ruchita.tictactoe.Tracker;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tracker.setFirebaseAnalytics(FirebaseAnalytics.getInstance(this.getApplicationContext()));
        setContentView(R.layout.activity_main);
        openHomeFragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.title);
        FirebaseApp.initializeApp(this);
        AppUtil.setmContext(this);
    }

    private void openHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment fragment = HomeFragment.newInstance();
        transaction.replace(R.id.fragment_container, fragment, getString(R.string.homeFragment));
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            manager.popBackStack(GameGridFragment.class.getSimpleName(), POP_BACK_STACK_INCLUSIVE);
        } else {
            super.onBackPressed();
        }
    }
}
