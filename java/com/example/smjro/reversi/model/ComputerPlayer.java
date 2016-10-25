package com.example.smjro.reversi.model;

import android.graphics.Point;
import android.os.Handler;

/**
 * Created by smjro on 16/10/05.
 */

public abstract class ComputerPlayer extends Player implements Runnable{

    private Thread mThread;
    private Handler mHandler = new Handler();
    private IPlayerCallback mCallback;

    public ComputerPlayer(Cell.CELL_STATUS turn, String name, Board board) {
        super(turn, name, board);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public synchronized void startThinking(IPlayerCallback callback) {

        mCallback = callback;

        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void run() {

        // 思考ルーチンを実行
        final Point pos = think();

        // UIスレッドに処理を渡す
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onEndThinking(pos);
            }
        });
    }

    protected abstract Point think();
}
