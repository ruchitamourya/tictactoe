package com.example.ruchi.tictactoe;

import android.view.View;
import android.widget.TextView;

public interface GameGridListener {

    public void textOnClick(View view,int row,int col);
}
