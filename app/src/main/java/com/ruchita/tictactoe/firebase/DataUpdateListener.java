package com.ruchita.tictactoe.firebase;

public interface DataUpdateListener {
    void onUpdateSuccess(String gameId);
    void onUpdateFailed();
    void onUpdateReceived(GameData data);
}
