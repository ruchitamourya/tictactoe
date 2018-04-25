package com.example.ruchi.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToeFragment extends Fragment implements View.OnClickListener {

    private TextView text1;
    private TextView text2;
    private TextView text3;
    private TextView text4;
    private TextView text5;
    private TextView text6;
    private TextView text7;
    private TextView text8;
    private TextView text9;
    private TextView player1;
    private TextView player2;

    private CardView cardView1;
    private CardView cardView_btn;
    private LinearLayout linearLayout;
    private TextView text_won;
    private Button restart;
    private Toolbar toolbar;

    private Boolean isPlayer1Active = true;
    private int count = 0;
    private boolean hasGameFinished = false;
    int ttt[][] = new int[3][3];
    private static Random random = new Random(System.currentTimeMillis());


    public static TicTacToeFragment newInstance() {
        Bundle bundle = new Bundle();
        TicTacToeFragment fragment = new TicTacToeFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        text3 = view.findViewById(R.id.text3);
        text4 = view.findViewById(R.id.text4);
        text5 = view.findViewById(R.id.text5);
        text6 = view.findViewById(R.id.text6);
        text7 = view.findViewById(R.id.text7);
        text8 = view.findViewById(R.id.text8);
        text9 = view.findViewById(R.id.text9);
        player1 = view.findViewById(R.id.pl1);
        player2 = view.findViewById(R.id.pl2);

        cardView1 = view.findViewById(R.id.card_vw2);
        linearLayout = view.findViewById(R.id.pl_ll);
        text_won = view.findViewById(R.id.pl_won);
        restart = view.findViewById(R.id.btn_restart);
        cardView_btn = view.findViewById(R.id.card_v_btn);

        text1.setOnClickListener(this);
        text2.setOnClickListener(this);
        text3.setOnClickListener(this);
        text4.setOnClickListener(this);
        text5.setOnClickListener(this);
        text6.setOnClickListener(this);
        text7.setOnClickListener(this);
        text8.setOnClickListener(this);
        text9.setOnClickListener(this);


        player1.setBackgroundColor(Color.GREEN);
        player1.setTextColor(Color.BLACK);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestartBtnClick();
            }
        });
        return view;
    }

    @Override
    public void onClick(View v) {

        if (hasGameFinished) {
            return;
        }
        final MediaPlayer mp = MediaPlayer.create(getContext(),R.raw.sound1);
        mp.start();
        v.setOnClickListener(null);
        if (isPlayer1Active) {
            player2.setBackgroundColor(Color.GREEN);
            player2.setTextColor(Color.BLACK);
        } else {
            player1.setBackgroundColor(Color.GREEN);
            player1.setTextColor(Color.BLACK);
        }
        int id = v.getId();

        int row = -1;
        int col = -1;
        switch (id) {
            case R.id.text1:
                row = 0;
                col = 0;
                break;
            case R.id.text2:
                row = 0;
                col = 1;
                break;
            case R.id.text3:
                row = 0;
                col = 2;
                break;
            case R.id.text4:
                row = 1;
                col = 0;
                break;
            case R.id.text5:
                row = 1;
                col = 1;
                break;
            case R.id.text6:
                row = 1;
                col = 2;
                break;
            case R.id.text7:
                row = 2;
                col = 0;
                break;
            case R.id.text8:
                row = 2;
                col = 1;
                break;
            case R.id.text9:
                row = 2;
                col = 2;
                break;

        }
        if (isPlayer1Active) {
            ttt[row][col] = 1;
            turnOfPlayer1((TextView) v);
            isPlayer1Active = false;
        } else {
            ttt[row][col] = 2;
            turnOfPlayer2((TextView) v);
            isPlayer1Active = true;
        }
        boolean hasWon = hasThisPlayerWon(row, col);
        if (hasWon) {
            hasGameFinished = true;
            if (isPlayer1Active) {
                winningPlayer(player2);
            } else {
                winningPlayer(player1);
            }
        } else {
            count++;
            if (count == 9) {
                linearLayout.setVisibility(View.GONE);
                cardView1.setVisibility(View.VISIBLE);
                text_won.setBackgroundColor(Color.GREEN);
                text_won.setText("it's a tie match !!!");
                cardView_btn.setVisibility(View.VISIBLE);
                restart.setVisibility(View.VISIBLE);
                hasGameFinished = true;
            }
        }

        if (!hasGameFinished && !isPlayer1Active) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int delay = random.nextInt(5);
                        Log.d("DELAY", delay + "");
                        Thread.sleep((delay+1) * 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    TicTacToeFragment.this.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            playAndroid();
                        }
                    });
                }
            });
            t.start();
        }

    }

    private void playAndroid() {
        int[] anMove = getAndroidMove();
        int index = 3 * anMove[0] + anMove[1] + 1;
        TextView textView = text1;
        switch (index) {
            case 1:
                textView = text1;
                break;
            case 2:
                textView = text2;
                break;
            case 3:
                textView = text3;
                break;
            case 4:
                textView = text4;
                break;
            case 5:
                textView = text5;
                break;
            case 6:
                textView = text6;
                break;
            case 7:
                textView = text7;
                break;
            case 8:
                textView = text8;
                break;
            case 9:
                textView = text9;
                break;
        }
        this.onClick(textView);
    }

    private boolean hasThisPlayerWon(int row, int col) {
        boolean won = checkRow(row, col);
        won = won || checkColumn(row, col);
        won = won || checkDiagonal(row, col);
        return won;
    }

    private boolean checkDiagonal(int row, int col) {
        boolean won = true;
        if ((row == 0 && col == 0)) {
            for (int i = 0; i<ttt[0].length; i++){
                won = won && ttt[row][col] == ttt[i][i];
            }
            return won;
           // return (ttt[row][col] == ttt[row + 1][col + 1] && ttt[row][col] == ttt[row + 2][col + 2]);
        } else if ((row == 0 && col == 2)) {
            return (ttt[row][col] == ttt[row + 1][col - 1] && ttt[row][col] == ttt[row + 2][col - 2]);
        } else if (row == 1 && col == 1) {
            return ((ttt[row][col] == ttt[row - 1][col - 1] && ttt[row][col] == ttt[row + 1][col + 1]) ||
                    (ttt[row][col] == ttt[row - 1][col + 1] && ttt[row][col] == ttt[row + 1][col - 1]));
        } else if ((row == 2 && col == 0)) {
            return (ttt[row][col] == ttt[row - 1][col + 1] && ttt[row][col] == ttt[row - 2][col + 2]);
        } else if ((row == 2 && col == 2)) {
            return (ttt[row][col] == ttt[row - 1][col - 1] && ttt[row][col] == ttt[row - 2][col - 2]);
        }
        return false;
    }

    private boolean checkColumn(int row, int col) {

        boolean won = true;
        for (int i = 0; i < ttt[col].length; i++) {
            won = won && ttt[i][col] == ttt[row][col];
        }
        return won;
    }

    private boolean checkRow(int row, int col) {
        boolean won = true;
        for (int i = 0; i < ttt[row].length; i++) {
            won = won && ttt[row][i] == ttt[row][col];
        }
        return won;
    }

    private void turnOfPlayer1(TextView textView) {
        textView.setText("O");
        player1.setBackgroundColor(Color.BLACK);
        player1.setTextColor(Color.WHITE);
    }

    private void turnOfPlayer2(TextView textView) {
        textView.setText("X");
        player2.setBackgroundColor(Color.BLACK);
        player2.setTextColor(Color.WHITE);
    }

    private void winningPlayer(TextView textView) {
        linearLayout.setVisibility(View.GONE);
        cardView1.setVisibility(View.VISIBLE);
        text_won.setBackgroundColor(Color.GREEN);
        text_won.setText(textView.getText() + " has won !!!");
        textView.setBackgroundColor(Color.GREEN);
        cardView_btn.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);
    }

    private void onRestartBtnClick() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        TicTacToeFragment fragment = TicTacToeFragment.newInstance();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    private int[] getAndroidMove() {
        List<int[]> emptyCells = new ArrayList<>();
        int[] p1Winning = new int[]{-1, -1};
        for (int i = 0; i < ttt.length; i++) {
            for (int j = 0; j < ttt[0].length; j++) {
                if (ttt[i][j] == 0) {
                    emptyCells.add(new int[]{i, j});
                    ttt[i][j] = 1;
                    boolean won = hasThisPlayerWon(i, j);
                    if (won) {
                        p1Winning[0] = i;
                        p1Winning[1] = j;
                    }
                    ttt[i][j] = 2;
                    won = hasThisPlayerWon(i, j);
                    if (won) {
                        return new int[]{i, j};
                    }
                    ttt[i][j] = 0;
                }
            }
        }
        if (p1Winning[0] != -1) {
            return p1Winning;
        }

        int len = emptyCells.size();
        int cellToUse = random.nextInt(len - 1);
        return emptyCells.get(cellToUse);
    }

}
