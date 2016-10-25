package com.example.smjro.reversi.model;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by smjro on 16/10/05.
 */

public class ComputerPlayerLevel0 extends ComputerPlayer{

    private Random mRand;
    private static int WAIT_MSEC = 10;

    public ComputerPlayerLevel0(Cell.CELL_STATUS turn, String name, Board board) {
        super(turn, name, board);

        mRand = new Random();
    }

    @Override
    protected Point think() {

        Point pos = null;

        try {
            Thread.sleep(WAIT_MSEC);
        } catch (InterruptedException e) {

        }

        ArrayList<Cell> set_possible_cells = mBoard.getSetPossibleCells();
        if (set_possible_cells.size() == 0) {
            return pos;
        }

        int n = mRand.nextInt(set_possible_cells.size());
        Cell chosenCell = set_possible_cells.get(n);
        pos = chosenCell.getPoint();

        return pos;
    }
}
