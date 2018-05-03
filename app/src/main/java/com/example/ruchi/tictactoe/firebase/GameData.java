package com.example.ruchi.tictactoe.firebase;

import java.util.List;

import static com.example.ruchi.tictactoe.Constants.GameStatus.CREATED;

public class GameData {
    private String gameId;
    private String player1InstanceId;
    private String player2InstanceId;
    private String player1Name;
    private String player2Name;
    private List<Integer> gamePlayArray;
    private long lastUpdated;
    private long created;
    private boolean firstPlayerTurn;
    private String gameStatus;

    public GameData(){

    }

    public GameData(String gameId, String player1InstanceId, String player1Name, List<Integer> gamePlayArray){
        //Setup game data
        this.gameId = gameId;
        this.player1InstanceId = player1InstanceId;
        this.player1Name = player1Name;
        this.created = System.currentTimeMillis();
        this.lastUpdated = this.created;
        this.gamePlayArray = gamePlayArray;
        this.firstPlayerTurn = true;
        this.gameStatus = CREATED;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getPlayer1InstanceId() {
        return player1InstanceId;
    }

    public void setPlayer1InstanceId(String player1InstanceId) {
        this.player1InstanceId = player1InstanceId;
    }

    public String getPlayer2InstanceId() {
        return player2InstanceId;
    }

    public void setPlayer2InstanceId(String player2InstanceId) {
        this.player2InstanceId = player2InstanceId;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public void setPlayer2Name(String player2Name) {
        this.player2Name = player2Name;
    }

    public List<Integer> getGamePlayArray() {
        return gamePlayArray;
    }

    public void setGamePlayArray(List<Integer> gamePlayArray) {
        this.gamePlayArray = gamePlayArray;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public boolean isFirstPlayerTurn() {
        return firstPlayerTurn;
    }

    public void setFirstPlayerTurn(boolean firstPlayerTurn) {
        this.firstPlayerTurn = firstPlayerTurn;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }
}
