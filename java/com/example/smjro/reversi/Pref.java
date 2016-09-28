package com.example.smjro.reversi;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by smjro on 16/09/26.
 */

public class Pref extends PreferenceActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFrag())
                .commit();
    }
}
