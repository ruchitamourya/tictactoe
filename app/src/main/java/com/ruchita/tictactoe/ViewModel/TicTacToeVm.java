package com.ruchita.tictactoe.ViewModel;

import android.util.Log;
import android.view.View;

public class TicTacToeVm {

    private String data;

    public TicTacToeVm() {
        this.data = "O";
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void onClickTab(View view){
        Log.d("zxcv", "Clicked ");
    }
}
