package com.ruchita.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.ruchita.tictactoe.Constants.Analytics.Params;

import static com.ruchita.tictactoe.Constants.Analytics.Events;

public class Tracker {
    private static FirebaseAnalytics mFirebaseAnalytics;

    public static void setFirebaseAnalytics(FirebaseAnalytics mFirebaseAnalytics) {
        Tracker.mFirebaseAnalytics = mFirebaseAnalytics;
    }

    public static void trackScreenView(Activity activity, Fragment screen){
        mFirebaseAnalytics.setCurrentScreen(activity, screen.getClass().getSimpleName(), null /* class override */);
    }

    public static void trackGameStart(int gameType, boolean soundOn, int secPlayerType){
        Bundle params = new Bundle();
        params.putInt(Params.GAME_TYPE, gameType);
        params.putBoolean(Params.SOUND_ON, soundOn);
        params.putInt(Params.SECOND_PLAYER_TYPE, secPlayerType);
        mFirebaseAnalytics.logEvent(Events.GAME_START, params);
    }

    public static void trackGameEnd(int gameType, boolean soundOn, int secPlayerType, int winner){
        Bundle params = new Bundle();
        params.putInt(Params.GAME_TYPE, gameType);
        params.putBoolean(Params.SOUND_ON, soundOn);
        params.putInt(Params.SECOND_PLAYER_TYPE, secPlayerType);
        params.putInt(Params.WINNER, winner);
        mFirebaseAnalytics.logEvent(Events.GAME_COMPLETED, params);
    }


}
