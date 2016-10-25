package com.example.smjro.reversi.model;

import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by smjro on 16/09/27.
 */

public class Cell {

    public enum CELL_STATUS {
        None,
        Black,
        White
    }

    private Board mBoard;
    private CELL_STATUS status = CELL_STATUS.None;
    private RectF rectF = new RectF();
    private Point point = new Point();

    // 裏返されるセルリスト
    private ArrayList<Cell> mReversibleCells = new ArrayList<Cell>();

    public Cell(Board board, Point point){
        this.mBoard = board;
        this.point = point;
    }

    public void setStatus(CELL_STATUS status) {
        this.status = status;
    }

    public float getLeft() {
        return rectF.left;
    }

    public float getTop() {
        return rectF.top;
    }

    public float getCenterX() {
        return this.rectF.centerX();
    }

    public float getCenterY() {
        return this.rectF.centerY();
    }

    public Point getPoint() {
        return this.point;
    }

    // セルの位置
    public void setPosition(float left, float top, float width, float height) {
        this.rectF.left = left;
        this.rectF.top = top;
        this.rectF.right = this.rectF.left + width;
        this.rectF.bottom = this.rectF.top + height;
    }

    public CELL_STATUS getStatus() {
        return status;
    }

    // セルの状態を変更
    public void changeStatus(CELL_STATUS status) {
        this.status = status;
    }

    // 相手の状態（白or黒）を取得
    public CELL_STATUS getOpponentStatus(CELL_STATUS turn) {
        if (turn == CELL_STATUS.Black) {
            return CELL_STATUS.White;
        } else {
            return CELL_STATUS.Black;
        }
    }

    // 設置可能なセルを探索
    public void setReversibleCells(CELL_STATUS now_turn, CELL_STATUS opponent) {

        mReversibleCells.clear();

        // 対象セルの周囲８方向のセルを調べる
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0){
                    // ひっくり返せるセルのリスト
                    ArrayList<Cell> list = new ArrayList<Cell>();
                    int n = getCellLine(j, i, opponent, list);
                    if (n > 0) {
                        Cell cell = getNextCell(j * (n+1), i * (n+1));
                        // board外を考慮
                        if (cell != null) {
                            if (cell.getStatus() == now_turn) {
                                mReversibleCells.addAll(list);
                            }
                        }
                    }
                }
            }
        }
    }

    // ひっくり返せるセルのラインを見つける
    private int getCellLine(int x, int y, CELL_STATUS opponent, ArrayList<Cell> list) {

        Cell cell = getNextCell(x, y);
        if (cell != null) {
            // 相手側の石であれば処理を実行
            if (cell.getStatus() == opponent) {
                list.add(cell);
                cell.getCellLine(x, y, opponent, list);
            }
        }
        return list.size();
    }

    // 隣のセル情報を取得
    @Nullable
    private Cell getNextCell(int x, int y) {
        int next_x = this.point.x + x;
        int next_y = this.point.y + y;

        if (next_x < 0 || next_x >= Board.COLS || next_y < 0 || next_y >= Board.ROWS) {
            return null;
        }

        Cell[][] cells = mBoard.getCells();

        return cells[next_y][next_x];
    }

    public ArrayList<Cell> getReversibleCells() {
        return mReversibleCells;
    }
}
