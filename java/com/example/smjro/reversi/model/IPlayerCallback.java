package com.example.smjro.reversi.model;

import android.graphics.Point;

/**
 * Created by smjro on 16/10/05.
 */

public abstract interface IPlayerCallback {

    public void onEndThinking(Point pos);
    //public void onProgress();
}
