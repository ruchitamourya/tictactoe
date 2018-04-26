package com.example.ruchi.tictactoe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private Button start;

    public static HomeFragment newInstance() {
        Bundle bundle = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        start = view.findViewById(R.id.btn_start);
        start.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        openTicTacToeFragment();
    }

    private void openTicTacToeFragment() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TicTacToeFragment fragment = TicTacToeFragment.newInstance();
        transaction.add(R.id.fragment_container, fragment, "ticTagToeFragment");
       // transaction.addToBackStack("ticTacToeFragment");
        transaction.commit();
    }
}
