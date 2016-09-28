package com.example.smjro.reversi;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by smjro on 16/09/27.
 */

public class PrefFrag extends PreferenceFragment {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        addPreferencesFromResource(R.xml.pref);
    }
}
