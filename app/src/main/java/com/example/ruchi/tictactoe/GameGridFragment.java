package com.example.ruchi.tictactoe;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameGridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameGridFragment extends Fragment implements GameGridListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView player1;
    private TextView player2;


    private CardView cardView1;
    private CardView cardViewp1;
    private CardView cardViewp2;
    private CardView cardView_btn;
    private LinearLayout linearLayout;
    private TextView text_won;
    private Button restart;

    private String p1;
    private String p2;

    private Boolean isPlayer1Active = true;
    private int count = 0;
    private boolean hasGameFinished = false;
    int ttt[][] = new int[5][5];
    //int ttt[][] = new int[4][4];
    private static Random random = new Random(System.currentTimeMillis());


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private GridLayoutManager mLayoutManager;


    public GameGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameGridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameGridFragment newInstance() {
        GameGridFragment fragment = new GameGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, "");
        args.putString(ARG_PARAM2, "");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_grid, container, false);

        player1 = view.findViewById(R.id.pl1);
        player2 = view.findViewById(R.id.pl2);

        cardView1 = view.findViewById(R.id.card_vw2);
        cardViewp1 = view.findViewById(R.id.card_vp1);
        cardViewp2 = view.findViewById(R.id.card_vp2);
        linearLayout = view.findViewById(R.id.pl_ll);
        text_won = view.findViewById(R.id.pl_won);
        restart = view.findViewById(R.id.btn_restart);
        cardView_btn = view.findViewById(R.id.card_v_btn);

        setUpRecyclerView(view);

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

    private void setUpRecyclerView(View view) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
        p1 = sharedPreferences.getString(Constants.PLAYER1, "");
        p2 = sharedPreferences.getString(Constants.PLAYER2, "");

        player1.setText(p1);
        player2.setText(p2);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        mLayoutManager = new GridLayoutManager(getContext(), ttt.length);
        recyclerView.setLayoutManager(mLayoutManager);
        GameGridAdapter adapter = new GameGridAdapter(getContext(), ttt, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void textOnClick(View view, int row, int col) {
        if (hasGameFinished) {
            return;
        }
        view.setOnClickListener(null);
        makeSound();
        setPlayerColor();
        setGridText((TextView) view, row, col);
        processWinningLogic(row, col);
        androidTurnWithDelay();
    }

    private void makeSound() {
        final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.button_click1);
        mp.start();
    }

    private void androidTurnWithDelay() {
        if (!hasGameFinished && !isPlayer1Active) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        int delay = random.nextInt(5);
                        Log.d("DELAY", delay + "");
                        Thread.sleep((delay + 1) * 200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    GameGridFragment.this.getActivity().runOnUiThread(new Runnable() {
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
        //ttt[anMove[0]][anMove[1]] = 2;
        int index = ttt.length * anMove[0] + anMove[1];
        View view = mLayoutManager.findViewByPosition(index);
        View requiredTextView = view.findViewById(R.id.text_view);
        textOnClick(requiredTextView, anMove[0], anMove[1]);
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
        int cellToUse = 0;
        if (len > 1) {
            cellToUse = random.nextInt(len - 1);
        }
        return emptyCells.get(cellToUse);
    }

    private void processWinningLogic(int row, int col) {
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
            if (count == ttt[0].length * ttt[1].length) {
                linearLayout.setVisibility(View.GONE);
                cardView1.setVisibility(View.VISIBLE);
                text_won.setBackgroundColor(Color.GREEN);
                text_won.setText(R.string.tie);
                cardView_btn.setVisibility(View.VISIBLE);
                restart.setVisibility(View.VISIBLE);
                hasGameFinished = true;
            }
        }
    }

    private void setGridText(TextView view, int row, int col) {
        if (isPlayer1Active) {
            ttt[row][col] = 1;
            turnOfPlayer1(view);
            isPlayer1Active = false;
        } else {
            ttt[row][col] = 2;
            turnOfPlayer2(view);
            isPlayer1Active = true;
        }
    }

    private void setPlayerColor() {
        if (isPlayer1Active) {
            player2.setBackgroundColor(Color.GREEN);
            player2.setTextColor(Color.BLACK);
        } else {
            player1.setBackgroundColor(Color.GREEN);
            player1.setTextColor(Color.BLACK);
        }
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

    private boolean hasThisPlayerWon(int row, int col) {
        boolean won = checkRow(row, col);
        won = won || checkColumn(row, col);
        won = won || checkAllDiagonal(row, col);
        return won;
    }

    private boolean checkAllDiagonal(int row, int col) {
        boolean won = true;
        if ((row == col)) {
            for (int i = 0; i < ttt[0].length; i++) {
                won = won && ttt[row][col] == ttt[i][i];
            }
            return won;
        } else if (row + col == (ttt.length - 1)) {
            for (int i = 0; i < ttt[0].length; i++) {
                won = won && ttt[row][col] == ttt[i][ttt.length - 1 - i];
            }
            return won;
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
        GameGridFragment fragment = GameGridFragment.newInstance();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
