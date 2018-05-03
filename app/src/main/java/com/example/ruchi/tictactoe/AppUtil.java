package com.example.ruchi.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.UUID;

public class AppUtil {
    private static String mAppId;
    private static Context mContext;
    public static String getAppId() {
        if(mAppId == null) {
            SharedPreferences preferences = mContext.getSharedPreferences(Constants.SHARE_PRE_KEY, Context.MODE_PRIVATE);
            mAppId = preferences.getString(Constants.APP_ID_KEY, "");
            if(TextUtils.isEmpty(mAppId)){
                mAppId = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString(Constants.APP_ID_KEY, mAppId);
                editor.apply();
            }
        }
        return mAppId;
    }

    public static int[] get1DArray(int[][] gamePlayArray2D) {
        int[] array = new int[gamePlayArray2D.length*2];
        int i = 0;
        for(int r = 0; r < gamePlayArray2D.length; r++){
            for(int c = 0; c < gamePlayArray2D[r].length; c++){
                array[i] = gamePlayArray2D[r][c];
            }
        }
        return array;
    }

    public static int[][] get2DArray(int[] array1D, int row, int col){
        int[][] array2D = new int[row][col];
        int r = 0;
        int c = 0;
        for(int num : array1D){
            array2D[r][c] = num;
            c++;
            if(c == col){
                c = 0;
                r++;
            }
        }
        return array2D;
    }
}
