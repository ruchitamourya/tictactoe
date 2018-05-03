package com.example.ruchi.tictactoe;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ruchi.tictactoe.firebase.FirebaseHelper;

public class MultiplayerFragmet extends Fragment implements View.OnClickListener{
    private Button joinBtn;
    private Button hostBtn;
    private EditText codeText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiplayer, container, false);
        joinBtn = view.findViewById(R.id.btn_join_game);
        hostBtn = view.findViewById(R.id.btn_host_game);
        codeText = view.findViewById(R.id.et_code);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btn_host_game){
            String gameId = FirebaseHelper.getInstance().createNewGame("Ruchita", new int[3][3]);
            codeText.setText(gameId);
        }else if(id == R.id.btn_join_game){
            
        }
    }
}
