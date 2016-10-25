package com.example.smjro.reversi.model;

/**
 * Created by smjro on 16/10/04.
 */

public class HumanPlayer extends Player {

    public HumanPlayer(Cell.CELL_STATUS turn, String name, Board board) {
        super(turn, name, board);
    }

    // 人がプレイヤーを示すフラグ
    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public void startThinking (IPlayerCallback callback) {
        callback.onEndThinking(null);
    }
}
