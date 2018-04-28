package com.example.ruchi.tictactoe.ViewModel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ruchi.tictactoe.R;

public class OptionsFragment extends Fragment{

    public static OptionsFragment newInstance(){
        Bundle bundle = new Bundle();
        OptionsFragment fragment = new OptionsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_options,container,false);
        return view;
    }
}
