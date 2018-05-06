package com.example.ruchi.tictactoe;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ruchi.tictactoe.firebase.DataUpdateListener;
import com.example.ruchi.tictactoe.firebase.FirebaseHelper;
import com.example.ruchi.tictactoe.firebase.GameData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class GameGridFragment extends Fragment implements GameGridListener, DataUpdateListener {
    private final String TAG = GameGridFragment.class.getSimpleName();
    public static final int ANDROID = 0;
    public static final int FRIEND = 1;
    public static final int ONLINE_FRIEND = 2;

    private TextView tvMyName;
    private TextView tvFriendName;

    private CardView cardView1;
    private CardView cardViewp1;
    private CardView cardViewp2;
    private CardView cardView_btn;
    private LinearLayout linearLayout;
    private TextView text_won;
    private Button restart;
    private RelativeLayout mainContainer;
    private ProgressBar progressBar;
    private static final String GRID_SIZE = "grid_size";
    private static final String GAME_ID = "game_id";
    private static final String SECOND_PLAYER_TYPE = "second_palyer_type";
    private Boolean isSoundOn;

    private Boolean isPlayer1Turn = true;
    private int count = 0;
    private boolean hasGameFinished = false;
    private int size;
    private int ttt[][];
    private static Random random = new Random(System.currentTimeMillis());
    private GridLayoutManager mLayoutManager;
    private GameGridAdapter adapter;
    private GameData gameData;
    private int secondPlayerType = 0;
    private boolean amIPlayer1 = false;


    public GameGridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GameGridFragment.
     */
    public static GameGridFragment newInstance(int gridSize, int secondPlayerType) {
        GameGridFragment fragment = new GameGridFragment();
        Bundle args = new Bundle();
        args.putInt(GRID_SIZE, gridSize);
        args.putInt(SECOND_PLAYER_TYPE, 0);
        fragment.setArguments(args);
        return fragment;
    }

    public static GameGridFragment newInstance(int gridSize, String gameId) {
        GameGridFragment fragment = new GameGridFragment();
        Bundle args = new Bundle();
        args.putInt(GRID_SIZE, gridSize);
        args.putString(GAME_ID, gameId);
        args.putInt(SECOND_PLAYER_TYPE, GameGridFragment.ONLINE_FRIEND);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.secondPlayerType = getArguments().getInt(SECOND_PLAYER_TYPE, 0);
        if(this.secondPlayerType != ONLINE_FRIEND){
            amIPlayer1 = true;
        }
        FirebaseHelper.getInstance().setDataUpdateListener(this);
    }

    private void getTheGameFromFirebase() {
        if (getArguments().containsKey(GAME_ID)) {
            String gameId = getArguments().getString(GAME_ID);
            FirebaseHelper.getInstance().listenForDataUpdate(gameId);
            FirebaseFirestore.getInstance()
                    .collection("game")
                    .document(gameId)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(TAG, "game data: " + documentSnapshot.getData());
                            final GameData gameData = documentSnapshot.toObject(GameData.class);
                            amIPlayer1 = gameData.getPlayer1InstanceId().equals(AppUtil.getAppId());
                            updateGame(gameData, true);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "GettingGameData", e);
                        }
                    });
        }
    }

    private void updateGame(final GameData gameData, final boolean hideLoader) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ttt = AppUtil.get2DArray(gameData.getGamePlayArray(), 3, 3);
                adapter.setData(ttt);
                adapter.notifyDataSetChanged();
                if(hideLoader) {
                    if(amIPlayer1){
                        tvFriendName.setText(gameData.getPlayer2Name());
                        tvMyName.setText(gameData.getPlayer1Name());
                        setNameViewActive(tvMyName);
                    }else {
                        tvFriendName.setText(gameData.getPlayer1Name());
                        tvMyName.setText(gameData.getPlayer2Name());
                        setNameViewActive(tvFriendName);
                    }
                    progressBar.setVisibility(View.GONE);
                    mainContainer.setVisibility(View.VISIBLE);
                }
                updateGameStatus(gameData);
            }

            private void updateGameStatus(GameData gameData) {
                if(gameData.getGameStatus().equals(Constants.GameStatus.FINISHED)){
                    hasGameFinished = true;
                    switch (gameData.getWinner()){
                        case 1:
                            winningPlayer(1);
                            break;
                        case 2:
                            winningPlayer(2);
                            break;
                        case 3:
                            winningPlayer(3);
                            break;
                    }
                }else {
                    isPlayer1Turn = gameData.isFirstPlayerTurn();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_grid, container, false);
        cardView1 = view.findViewById(R.id.card_vw2);
        cardViewp1 = view.findViewById(R.id.card_vp1);
        cardViewp2 = view.findViewById(R.id.card_vp2);
        linearLayout = view.findViewById(R.id.pl_ll);
        text_won = view.findViewById(R.id.pl_won);
        restart = view.findViewById(R.id.btn_restart);
        cardView_btn = view.findViewById(R.id.card_v_btn);
        progressBar = view.findViewById(R.id.progress_bar);
        mainContainer = view.findViewById(R.id.rl_main_container);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
        String myName = sharedPreferences.getString(Constants.PLAYER1, "");
        String friendName = sharedPreferences.getString(Constants.PLAYER2, "");
        isSoundOn = sharedPreferences.getBoolean(Constants.IS_SOUNON, false);

        initNameView(view, myName, friendName);

        size = getArguments().getInt(GRID_SIZE);
        ttt = new int[size][size];
        setUpRecyclerView(view, ttt);

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRestartBtnClick();
            }
        });
        return view;
    }

    private void initNameView(View view, String myName, String friendName) {
        tvMyName = view.findViewById(R.id.pl1);
        tvFriendName = view.findViewById(R.id.pl2);

        if (secondPlayerType == ANDROID) {
            tvMyName.setText(myName);
            tvFriendName.setText("Android");
        } else if (secondPlayerType == FRIEND) {
            tvMyName.setText(myName);
            tvFriendName.setText(friendName);
        } else {
            mainContainer.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void setNameViewActive(TextView textView){
        textView.setBackgroundColor(Color.GREEN);
        textView.setTextColor(Color.BLACK);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTheGameFromFirebase();
    }

    private void setUpRecyclerView(View view, int ttt[][]) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        mLayoutManager = new GridLayoutManager(getContext(), size);
        recyclerView.setLayoutManager(mLayoutManager);
        adapter = new GameGridAdapter(getContext(), ttt, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void textOnClick(View view, int row, int col) {
        if (hasGameFinished || (secondPlayerType == ONLINE_FRIEND && !isMyTurn())) {
            return;
        }
        view.setOnClickListener(null);
        makeSound();
        setPlayerActive();
        setGridText((TextView) view, row, col);
        processWinningLogic(row, col);
        if(secondPlayerType == ANDROID) {
            androidTurnWithDelay();
        }
    }

    private boolean isMyTurn() {
        return (isPlayer1Turn && amIPlayer1) || (!isPlayer1Turn && !amIPlayer1);
    }

    private void makeSound() {
        if (isSoundOn) {
            final MediaPlayer mp = MediaPlayer.create(getContext(), R.raw.button_click1);
            mp.start();
        }
    }

    private void androidTurnWithDelay() {
        if (!hasGameFinished && !isPlayer1Turn) {
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
            if (isPlayer1Turn) {
                winningPlayer(2);
            } else {
                winningPlayer(1);
            }
        } else {
            count++;
            if (count == ttt[0].length * ttt[1].length) {
                winningPlayer(3);
                hasGameFinished = true;
            }
        }
    }

    private void setGridText(TextView view, int row, int col) {
        if (isPlayer1Turn) {
            ttt[row][col] = 1;
            turnOfPlayer1(view);
            isPlayer1Turn = false;
        } else {
            ttt[row][col] = 2;
            turnOfPlayer2(view);
            isPlayer1Turn = true;
        }
        if(secondPlayerType == ONLINE_FRIEND) {
            gameData.setGamePlayArray(AppUtil.get1DArray(ttt));
            gameData.setFirstPlayerTurn(isPlayer1Turn);
            FirebaseHelper.getInstance().updateGameData(gameData);
        }
    }

    private void setPlayerActive() {
        if(secondPlayerType == ONLINE_FRIEND){
            setNameViewActive(tvFriendName);
            return;
        }

        if (isPlayer1Turn) {
            TextView tv = amIPlayer1 ? tvFriendName : tvMyName;
            setNameViewActive(tv);
        } else {
            TextView tv = amIPlayer1 ? tvMyName : tvFriendName;
            setNameViewActive(tv);
        }
    }

    private void turnOfPlayer1(TextView textView) {
        textView.setText("O");
        TextView tv = amIPlayer1 ? tvMyName : tvFriendName;
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
    }

    private void turnOfPlayer2(TextView textView) {
        textView.setText("X");
        TextView tv = amIPlayer1 ? tvFriendName : tvMyName;
        tv.setBackgroundColor(Color.BLACK);
        tv.setTextColor(Color.WHITE);
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
//
//    private void winningPlayer(boolean tie, TextView winnerTextView) {
//        linearLayout.setVisibility(View.GONE);
//        cardView1.setVisibility(View.VISIBLE);
//        text_won.setBackgroundColor(Color.GREEN);
//        String text = "";
//        if(tie){
//            text = getResources().getString(R.string.tie);
//        }else {
//            winnerTextView.setBackgroundColor(Color.GREEN);
//            text = winnerTextView.getText() + " has won !!!";
//        }
//        text_won.setText(text);
//        cardView_btn.setVisibility(View.VISIBLE);
//        restart.setVisibility(View.VISIBLE);
//    }

    private void winningPlayer(int winner) {
        String text = "";
        TextView winnerTextView;
        if(winner == 1){
            winnerTextView = amIPlayer1 ? tvMyName : tvFriendName;
            winnerTextView.setBackgroundColor(Color.GREEN);
            text = winnerTextView.getText() + " has won !!!";
        }else if(winner == 2){
            winnerTextView = amIPlayer1 ? tvFriendName : tvMyName;
            winnerTextView.setBackgroundColor(Color.GREEN);
            text = winnerTextView.getText() + " has won !!!";
        }else if(winner == 3){
            text = getResources().getString(R.string.tie);
        }
        linearLayout.setVisibility(View.GONE);
        cardView1.setVisibility(View.VISIBLE);
        text_won.setBackgroundColor(Color.GREEN);
        text_won.setText(text);
        cardView_btn.setVisibility(View.VISIBLE);
        restart.setVisibility(View.VISIBLE);
        if(secondPlayerType == ONLINE_FRIEND){
            gameData.setWinner(winner);
            gameData.setGameStatus(Constants.GameStatus.FINISHED);
            FirebaseHelper.getInstance().updateGameData(gameData);
        }
    }

    private void onRestartBtnClick() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        GameGridFragment fragment = GameGridFragment.newInstance(size, secondPlayerType);
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public void onUpdateSuccess(String gameId) {
        Log.d(TAG, "onUpdateSuccess " + gameId);
    }

    @Override
    public void onUpdateFailed() {
        Log.d(TAG, "onUpdateFailed");
    }

    @Override
    public void onUpdateReceived(GameData data) {
        Log.d(TAG, "onUpdateReceived " + data);
        gameData = data;
        updateGame(data, false);
    }
}
