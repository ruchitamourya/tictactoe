package com.example.ruchi.tictactoe;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddPlayerFragment extends DialogFragment {

    public static AddPlayerFragment newInstance(){
        Bundle bundle = new Bundle();
        AddPlayerFragment fragment = new AddPlayerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedStateInstate){
        super.onCreate(savedStateInstate);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedStateInstate){
        super.onCreateView(inflater,container,savedStateInstate);
        View view = inflater.inflate(R.layout.fragment_add_player,container,false);
        return view;
    }
}
