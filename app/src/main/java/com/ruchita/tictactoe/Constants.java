package com.ruchita.tictactoe;

public class Constants {

    public static final String SHARE_PRE_KEY = "share_pre_key";
    public static final String PLAYER1 = "player1";
    public static final String PLAYER2 = "player2";
    public static final String IS_SOUNON = "true";

    public static final String APP_ID_KEY = "app_id_key";

    public static class GameStatus{
        public static final String CREATED = "created";
        public static final String RUNNING = "running";
        public static final String FINISHED = "finished";
        public static final String PLAYER_2_JOINED = "player_2_joined";

    }

    public static class Analytics{
        public static class Events{
            public static final String GAME_START = "game_start";
            public static final String GAME_COMPLETED ="game_finished";



        }
        public static class Params{
            public static final String GAME_TYPE = "game_type";
            public static final String SECOND_PLAYER_TYPE = "second_player_type";
            public static final String SOUND_ON = "sound_on";
            public static final String WINNER = "winner";
        }

    }
}
