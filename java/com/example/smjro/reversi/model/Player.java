package com.example.smjro.reversi.model;

import android.content.Context;

import com.example.smjro.reversi.Pref;

/**
 * Created by smjro on 16/10/02.
 */

public abstract class Player {

    protected Cell.CELL_STATUS mTurn;
    protected String mName;
    protected Board mBoard;

    public Player(Cell.CELL_STATUS turn, String name, Board board) {
        setTurn(turn);
        setName(name);
        mBoard = board;
    }

    public void setTurn(Cell.CELL_STATUS turn) {
        this.mTurn = turn;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public abstract boolean isHuman();

    public abstract void startThinking(IPlayerCallback callback);

    public static final Player getPlayer1(Context con, Board board, Cell.CELL_STATUS turn) {
        String name = Pref.getPlayer1Name(con);
        String value = Pref.getPlayer1(con);
        return getPlayer(name, board, turn, value);
    }

    public static final Player getPlayer2(Context con, Board board, Cell.CELL_STATUS turn) {
        String name = Pref.getPlayer2Name(con);
        String value = Pref.getPlayer2(con);
        return getPlayer(name, board, turn, value);
    }

    private static final Player getPlayer(String name, Board board, Cell.CELL_STATUS turn, String value) {

        int int_value = Integer.valueOf(value);     // 文字列をint型に変換
        Player player;

        switch (int_value) {
            case 0:
                player = new ComputerPlayerLevel0(turn, name, board);
                break;
            default:
                player = new HumanPlayer(turn, name, board);
        }
        return player;
    }
}
