package com.example.ruchi.tictactoe;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openTicTacToeFragment();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.showOverflowMenu();
        getSupportActionBar().setTitle("Tic Tac Toe ");
        // toolbar.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private void openTicTacToeFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TicTacToeFragment fragment = TicTacToeFragment.newInstance();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    private void openAddPlayerFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        AddPlayerFragment fragment = AddPlayerFragment.newInstance();
        fragment.show(transaction, null);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         //Handle item selection
        switch (item.getItemId()) {
            case R.id.add_player:
                openAddPlayerFragment();
                return true;
            case R.id.change_player:
               openAddPlayerFragment();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
