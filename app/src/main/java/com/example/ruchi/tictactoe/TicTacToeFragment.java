package com.example.ruchi.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        v.setOnClickListener(null);
        if (isPlayer1Active) {
            player2.setBackgroundColor(Color.GREEN);
            player2.setTextColor(Color.BLACK);
        } else {
            player1.setBackgroundColor(Color.GREEN);
            player1.setTextColor(Color.BLACK);
        }
        int id = v.getId();

        switch (id) {
            case R.id.text1:
                if (isPlayer1Active) {
                    turnOfPlayer1(text1);
                    ttt[0][0] = 1;
                    boolean hasWon = hasThisPlayerWon(0,0);
//                    String t1 = text1.getText().toString();
//                    String t2 = text2.getText().toString();
//                    String t3 = text3.getText().toString();
//                    String t4 = text4.getText().toString();
//                    String t5 = text5.getText().toString();
//                    String t7 = text7.getText().toString();
//                    String t9 = text9.getText().toString();
//
//                    if ((t1.equals(t2) && t1.equals(t3))) {
//                        text1.setBackgroundColor(Color.GREEN);
//                        text2.setBackgroundColor(Color.GREEN);
//                        text3.setBackgroundColor(Color.GREEN);
//                        winningPlayer(player1);
//
//                    } else if ((t1.equals(t4) && t1.equals(t7))) {
//                        text1.setBackgroundColor(Color.GREEN);
//                        text4.setBackgroundColor(Color.GREEN);
//                        text7.setBackgroundColor(Color.GREEN);
//                        winningPlayer(player1);
//                    } else if ((t1.equals(t5) && t1.equals(t9))) {
//                        text1.setBackgroundColor(Color.GREEN);
//                        text5.setBackgroundColor(Color.GREEN);
//                        text9.setBackgroundColor(Color.GREEN);
//                        winningPlayer(player1);
//                    }

                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text1);
                    ttt[0][0] = 2;
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t1.equals(t2) && t1.equals(t3))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text2.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t1.equals(t4) && t1.equals(t7))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text4.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t1.equals(t5) && t1.equals(t9))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }

                    isPlayer1Active = true;
                }
                break;
            case R.id.text2:

                if (isPlayer1Active) {
                    turnOfPlayer1(text2);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t5 = text5.getText().toString();
                    String t8 = text8.getText().toString();
                    if ((t2.equals(t1) && t2.equals(t3))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text2.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t2.equals(t5) && t2.equals(t8))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }

                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text2);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t5 = text5.getText().toString();
                    String t8 = text8.getText().toString();
                    if ((t2.equals(t1) && t2.equals(t3))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text2.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t2.equals(t5) && t2.equals(t8))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }

                    isPlayer1Active = true;
                }
                break;
            case R.id.text3:
                if (isPlayer1Active) {
                    turnOfPlayer1(text3);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t6 = text6.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t3.equals(t2) && t3.equals(t1))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text2.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t3.equals(t6) && t3.equals(t9))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t3.equals(t5) && t3.equals(t7))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }

                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text3);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t6 = text6.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t3.equals(t2) && t3.equals(t1))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text2.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t3.equals(t6) && t3.equals(t9))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t3.equals(t5) && t3.equals(t7))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text4:

                if (isPlayer1Active) {
                    turnOfPlayer1(text4);
                    String t1 = text1.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t6 = text6.getText().toString();

                    if ((t4.equals(t1) && t4.equals(t7))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text4.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t4.equals(t5) && t4.equals(t6))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text4);
                    String t1 = text1.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t6 = text6.getText().toString();

                    if ((t4.equals(t1) && t4.equals(t7))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text4.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t4.equals(t5) && t4.equals(t6))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text5:
                if (isPlayer1Active) {
                    turnOfPlayer1(text5);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t6 = text6.getText().toString();
                    String t7 = text7.getText().toString();
                    String t8 = text8.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t5.equals(t2) && t5.equals(t8))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t5.equals(t4) && t5.equals(t6))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t5.equals(t1) && t5.equals(t9))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t5.equals(t3) && t5.equals(t7))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text5);
                    String t1 = text1.getText().toString();
                    String t2 = text2.getText().toString();
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t6 = text6.getText().toString();
                    String t7 = text7.getText().toString();
                    String t8 = text8.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t5.equals(t2) && t5.equals(t8))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t5.equals(t4) && t5.equals(t6))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t5.equals(t1) && t5.equals(t9))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t5.equals(t3) && t5.equals(t7))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text6:
                if (isPlayer1Active) {
                    turnOfPlayer1(text6);
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t6 = text6.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t6.equals(t3) && t6.equals(t9))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t6.equals(t5) && t6.equals(t4))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text6);
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t6 = text6.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t6.equals(t3) && t6.equals(t9))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t6.equals(t5) && t6.equals(t4))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text7:
                if (isPlayer1Active) {
                    turnOfPlayer1(text7);
                    String t1 = text1.getText().toString();
                    String t8 = text8.getText().toString();
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t7.equals(t1) && t7.equals(t4))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text1.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t7.equals(t8) && t7.equals(t9))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t7.equals(t5) && t7.equals(t3))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text7);
                    String t1 = text1.getText().toString();
                    String t8 = text8.getText().toString();
                    String t3 = text3.getText().toString();
                    String t4 = text4.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t7.equals(t1) && t7.equals(t4))) {
                        text4.setBackgroundColor(Color.GREEN);
                        text1.setBackgroundColor(Color.GREEN);
                        text7.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t7.equals(t8) && t7.equals(t9))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t7.equals(t5) && t7.equals(t3))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text3.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text8:
                if (isPlayer1Active) {
                    turnOfPlayer1(text8);
                    String t2 = text2.getText().toString();
                    String t8 = text8.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t8.equals(t2) && t8.equals(t5))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t8.equals(t9) && t8.equals(t7))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text8);
                    String t2 = text2.getText().toString();
                    String t8 = text8.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t8.equals(t2) && t8.equals(t5))) {
                        text2.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t8.equals(t9) && t8.equals(t7))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;
            case R.id.text9:
                if (isPlayer1Active) {
                    turnOfPlayer1(text9);
                    String t1 = text1.getText().toString();
                    String t6 = text6.getText().toString();
                    String t3 = text3.getText().toString();
                    String t8 = text8.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t9.equals(t6) && t9.equals(t3))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t9.equals(t8) && t9.equals(t7))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    } else if ((t9.equals(t5) && t9.equals(t1))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player1);
                    }
                    isPlayer1Active = false;
                } else {
                    turnOfPlayer2(text9);
                    String t1 = text1.getText().toString();
                    String t6 = text6.getText().toString();
                    String t3 = text3.getText().toString();
                    String t8 = text8.getText().toString();
                    String t5 = text5.getText().toString();
                    String t7 = text7.getText().toString();
                    String t9 = text9.getText().toString();

                    if ((t9.equals(t6) && t9.equals(t3))) {
                        text3.setBackgroundColor(Color.GREEN);
                        text6.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t9.equals(t8) && t9.equals(t7))) {
                        text7.setBackgroundColor(Color.GREEN);
                        text8.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    } else if ((t9.equals(t5) && t9.equals(t1))) {
                        text1.setBackgroundColor(Color.GREEN);
                        text5.setBackgroundColor(Color.GREEN);
                        text9.setBackgroundColor(Color.GREEN);
                        winningPlayer(player2);
                    }
                    isPlayer1Active = true;
                }
                break;

        }

        count++;
        if (count == 9) {
            linearLayout.setVisibility(View.GONE);
            cardView1.setVisibility(View.VISIBLE);
            text_won.setBackgroundColor(Color.GREEN);
            text_won.setText("it's a tie match !!!");
            restart.setVisibility(View.VISIBLE);


        }

    }

    private boolean hasThisPlayerWon(int row, int col) {
        boolean won = checkRow(row, col);
        won = won || checkColumn(row, col);
        won = won || checkDiagonal(row, col);
        return won;
    }

    private boolean checkDiagonal(int row, int col) {
        return false;
    }

    private boolean checkColumn(int row, int col) {
        return false;
    }

    private boolean checkRow(int row, int col) {


        return false;
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

}
