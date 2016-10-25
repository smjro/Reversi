package com.example.smjro.reversi.model;

import android.graphics.Point;
import android.graphics.RectF;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by smjro on 16/09/27.
 */

public class Board {

    public static final int COLS = 8;   // 行
    public static final int ROWS = 8;   // 列

    private Cell cells[][] = new Cell[ROWS][COLS];
    private Cell.CELL_STATUS turn;
    private RectF rectF = new RectF();
    private Player player1;
    private Player player2;

    // コンストラクタ
    public Board() {
        for (int i = 0; i < ROWS; i++ ) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j] = new Cell(this, new Point(j, i));
            }
        }

        // 初期配置
        cells[ROWS/2 - 1][COLS/2 - 1].setStatus(Cell.CELL_STATUS.Black);
        cells[ROWS/2][COLS/2].setStatus(Cell.CELL_STATUS.Black);
        cells[ROWS/2 - 1][COLS/2].setStatus(Cell.CELL_STATUS.White);
        cells[ROWS/2][COLS/2 - 1].setStatus(Cell.CELL_STATUS.White);

        // 初めのターンに黒を設定
        turn = Cell.CELL_STATUS.Black;

        // 置けるセルの数を計算
        setAllReversibleCells();
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
        ArrayList<Cell> list = cell.getReversibleCells();   // 反転可能なセルリスト

        for (Cell cell2 : list) {
            cell2.setStatus(status);
        }

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

    public int setAllReversibleCells() {

        int n = 0;  // 設置可能なセルの数
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j <COLS; j++) {
                Cell cell = cells[i][j];

                // ひっくり返せるセルを探す
                if (cell.getStatus() == Cell.CELL_STATUS.None){
                    Cell.CELL_STATUS opponent = cell.getOpponentStatus(this.turn);
                    cell.setReversibleCells(this.turn, opponent);
                }

                if (cell.getReversibleCells().size() > 0) {
                    n++;
                }
            }
        }

        return n;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getCurrentPlayer() {
        Player p = null;
        if (turn == Cell.CELL_STATUS.Black) {
            p = player1;
        } else if (turn == Cell.CELL_STATUS.White) {
            p = player2;
        }
        return p;
    }

    // 石が置けるリストを返す
    public ArrayList<Cell> getSetPossibleCells() {

        ArrayList<Cell> set_possible_cells = new ArrayList<Cell>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getReversibleCells().size() > 0) {
                    set_possible_cells.add(cells[i][j]);
                }
            }
        }
        return set_possible_cells;
    }
}
