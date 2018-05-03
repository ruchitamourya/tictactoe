package com.example.ruchi.tictactoe.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ruchi.tictactoe.AppUtil;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.List;

import javax.annotation.Nullable;

import static com.example.ruchi.tictactoe.AppUtil.get1DArray;

public class FirebaseHelper {
    private static String TAG = FirebaseHelper.class.getSimpleName();
    private DataUpdateListener dataUpdateListener;
    private FirebaseFirestore fireStore;
    private static FirebaseHelper instance;

    public static FirebaseHelper getInstance() {
        if (instance == null) {
            synchronized (FirebaseHelper.class) {
                if (instance == null) {
                    instance = new FirebaseHelper();
                }
            }
        }
        return instance;
    }

    private FirebaseHelper() {
        fireStore = FirebaseFirestore.getInstance();
    }

    public void setDataUpdateListener(DataUpdateListener dataUpdateListener) {
        this.dataUpdateListener = dataUpdateListener;
    }

    public String createNewGame(String name, int[][] gamePlayArray2D) {
        List<Integer> gamePlayArray = get1DArray(gamePlayArray2D);
        String id = generateGameId();
        GameData gameData = new GameData(id, AppUtil.getAppId(), name, gamePlayArray);
        return updateGameData(gameData);
    }

    public String updateGameData(final GameData gameData) {
        gameData.setLastUpdated(System.currentTimeMillis());
        fireStore.collection("game")
                .document(gameData.getGameId())
                .set(gameData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if(dataUpdateListener != null)
                            dataUpdateListener.onUpdateSuccess(gameData.getGameId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "updateGameData", e);
                        if(dataUpdateListener != null)
                            dataUpdateListener.onUpdateFailed();
                    }
                });
        return gameData.getGameId();
    }

    private String generateGameId() {
        String id = AppUtil.getAppId() + "_" + System.currentTimeMillis();
        Log.d(TAG, "GameId = " + id);
        return id;
    }

    public void listenForDataUpdate(String gameId) {
        fireStore.collection("game")
                .document(gameId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w(TAG, "Listen failed.", e);
                            return;
                        }

                        if (snapshot != null && snapshot.exists()) {
                            Log.d(TAG, "Current data: " + snapshot.getData());
                            GameData data = snapshot.toObject(GameData.class);
                            if(dataUpdateListener != null)
                                dataUpdateListener.onUpdateReceived(data);
                        } else {
                            Log.d(TAG, "Current data: null");
                        }
                    }
                });
    }
}
