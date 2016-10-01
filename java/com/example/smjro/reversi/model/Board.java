package com.example.smjro.reversi.model;

import android.graphics.Point;
import android.graphics.RectF;

import com.example.smjro.reversi.Utils;

/**
 * Created by smjro on 16/09/27.
 */

public class Board {

    public static final int COLS = 8;
    public static final int ROWS = 8;

    private Cell cells[][] = new Cell[ROWS][COLS];
    private Cell.CELL_STATUS turn;
    private RectF rectF = new RectF();

    // コンストラクタ
    public Board() {
        for (int i = 0; i < ROWS; i++ ) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j] = new Cell();
            }
        }

        // 初期配置
        cells[ROWS/2 - 1][COLS/2 - 1].setStatus(Cell.CELL_STATUS.Black);
        cells[ROWS/2][COLS/2].setStatus(Cell.CELL_STATUS.Black);
        cells[ROWS/2 - 1][COLS/2].setStatus(Cell.CELL_STATUS.White);
        cells[ROWS/2][COLS/2 - 1].setStatus(Cell.CELL_STATUS.White);

        // 初めのターンに黒を設定
        turn = Cell.CELL_STATUS.Black;
    }

    // ボードのサイズを設定
    public void setSize(int w, int h) {

        int ss = w < h ? w : h; // 正方形を作るために小さい方に合わせる
        ss = (int)(ss * 0.98);  // 余白
        ss = (ss / Board.COLS) * Board.COLS;   // 整数にする
        int border = (w < h ? (w - ss) : (h - ss)) / 2;
        this.rectF.left = border;
        this.rectF.top =  border;
        this.rectF.right = border +ss;
        this.rectF.bottom = border + ss;

        float cell_width = getCellWidth();
        float cell_height = getCellHeight();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j].setPosition(this.rectF.left + j*cell_width
                        , this.rectF.top + i*cell_height
                        , cell_width
                        , cell_height);
            }
        }

    }

    // セルのサイズ取得
    public float getCellWidth() {
        return this.rectF.width() / (float)COLS;
    }
    public float getCellHeight() {
        return this.rectF.height() / (float)ROWS;
    }

    // ボードのサイズ取得
    public RectF getRectF() {
        return this.rectF;
    }

    // 全セルの情報を取得
    public Cell[][] getCells() {
        return cells;
    }

    // セルの情報を取得
    public Cell getCell(Point point) {
        return cells[point.y][point.x];
    }

    // 現在のターンを取得
    public Cell.CELL_STATUS getTurn() {
        return turn;
    }

    // セルの状態を変更
    public void changeCell(Point point, Cell.CELL_STATUS status) {

        Cell cell = cells[point.y][point.x];
        cell.changeStatus(status);
    }

    // ターンの変更
    public void changeTurn(Cell.CELL_STATUS turn) {
        if (this.turn == Cell.CELL_STATUS.Black) {
            this.turn = Cell.CELL_STATUS.White;
        } else {
            this.turn = Cell.CELL_STATUS.Black;
        }
    }
}
