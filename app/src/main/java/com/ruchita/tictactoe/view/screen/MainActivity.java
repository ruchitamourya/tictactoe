package com.ruchita.tictactoe.view.screen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ruchita.tictactoe.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.ruchita.tictactoe.AppUtil;
import com.ruchita.tictactoe.Tracker;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tracker.setFirebaseAnalytics(FirebaseAnalytics.getInstance(this.getApplicationContext()));
        setContentView(R.layout.activity_main);
        openHomeFragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      //  toolbar.showOverflowMenu();
        getSupportActionBar().setTitle(R.string.title);
        FirebaseApp.initializeApp(this);
        AppUtil.setmContext(this);
        // toolbar.setOnMenuItemClickListener(this);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
//        return true;
//    }

    private void openHomeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeFragment fragment = HomeFragment.newInstance();
        transaction.replace(R.id.fragment_container, fragment,getString(R.string.homeFragment));
       // transaction.addToBackStack("homeFragment");
        transaction.commit();
    }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//         //Handle item selection
//        switch (item.getItemId()) {
//            case R.id.add_player:
//                return true;
//            case R.id.change_player:
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
