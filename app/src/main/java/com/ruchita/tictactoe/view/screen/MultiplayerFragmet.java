package com.ruchita.tictactoe.view.screen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ruchita.tictactoe.R;
import com.ruchita.tictactoe.AppUtil;
import com.ruchita.tictactoe.Constants;
import com.ruchita.tictactoe.firebase.FireBaseHelper;
import com.ruchita.tictactoe.firebase.GameData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MultiplayerFragmet extends Fragment implements View.OnClickListener {
    private static String TAG = MultiplayerFragmet.class.getSimpleName();
    private Button joinBtn;
    private Button hostBtn;
    private EditText codeText;
    private Button startButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multiplayer, container, false);
        joinBtn = view.findViewById(R.id.btn_join_game);
        hostBtn = view.findViewById(R.id.btn_host_game);
        codeText = view.findViewById(R.id.et_code);
        startButton = view.findViewById(R.id.btn_start_game);
        startButton.setOnClickListener(this);
        hostBtn.setOnClickListener(this);
        joinBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_host_game) {
            String gameId = FireBaseHelper.getInstance().createNewGame("Ruchita", new int[3][3]);
            codeText.setText(gameId);
        } else if (id == R.id.btn_join_game) {
            String code = codeText.getText().toString();
            setUpTheGame(code);
        } else if (id == R.id.btn_start_game) {
            String code = codeText.getText().toString();
            openGridFragment(code);
        }
    }

    private void openGridFragment(final String gameId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                GameGridFragment fragment = GameGridFragment.newInstance(3, gameId);
                transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName());
                transaction.addToBackStack(fragment.getClass().getSimpleName());
                transaction.commit();
            }
        });
    }

    private void setUpTheGame(final String gameId) {
        FirebaseFirestore.getInstance()
                .collection("game")
                .document(gameId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Log.d(TAG, "game data: " + documentSnapshot.getData());
                        final GameData gameData = documentSnapshot.toObject(GameData.class);
                        gameData.setPlayer2Name("Chandan");
                        gameData.setPlayer2InstanceId(AppUtil.getAppId());
                        gameData.setGameStatus(Constants.GameStatus.PLAYER_2_JOINED);
                        FireBaseHelper.getInstance().updateGameDataAsync(gameData);
                        openGridFragment(gameId);
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
