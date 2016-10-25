package com.example.smjro.reversi;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

/**
 * Created by smjro on 16/09/26.
 */

public class Pref extends PreferenceActivity {

    private static final String KEY_PLAYER1_NAME = "player1_name";
    private static final String KEY_PLAYER1 = "player1_flag";
    private static final String KEY_PLAYER2_NAME = "player2_name";
    private static final String KEY_PLAYER2 = "player2_flag";

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFrag())
                .commit();
    }

    // 黒側のプレイヤーネームを取得
    public static String getPlayer1Name(Context con) {
        String name = PreferenceManager.getDefaultSharedPreferences(con)
                .getString(KEY_PLAYER1_NAME, con.getString(R.string.pref_player1_defaultName));
        return name;
    }

    public static String getPlayer1(Context con) {
        String value = PreferenceManager.getDefaultSharedPreferences(con)
                .getString(KEY_PLAYER1, con.getString(R.string.pref_player1_defaultValue));
        return value;
    }

//    public static String getPlayer1(Context con) {
//        String value = PreferenceManager.getDefaultSharedPreferences(con)
//                .getString(KEY_PLAYER1, con.getString(R.string.pref_player1_defaultValue));
//        return value;
//    }

    // 白側のプレイヤーネームを取得
    public static String getPlayer2Name(Context con) {
        String name = PreferenceManager.getDefaultSharedPreferences(con)
                .getString(KEY_PLAYER2_NAME, con.getString(R.string.pref_player2_defaultName));
        return name;
    }

    public static String getPlayer2(Context con) {
        String value = PreferenceManager.getDefaultSharedPreferences(con)
                .getString(KEY_PLAYER2, con.getString(R.string.pref_player2_defaultValue));
        return value;
    }
}
