package com.example.smjro.reversi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.v4.widget.SlidingPaneLayout;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.smjro.reversi.model.Board;
import com.example.smjro.reversi.model.Cell;


/**
 * Created by smjro on 16/09/27.
 */

public class ReversiView extends View {

    private static final float CELL_RESIZE_FACTOR = 0.85f;
    private static final float HINTS_SIZE_FACTOR = 0.1f;

    private Board mBoard;

    private Bitmap mBitmapBoard;
    private Bitmap mBitmapScreen;
    private Bitmap mBitmapBlack;
    private Bitmap mBitmapWhite;
    private Paint paint = new Paint();
    private Paint mPaintBoarder = new Paint();
    private Paint mPaintHintsBlack = new Paint();
    private Paint mPaintHintsWhite = new Paint();

    private int mWidth;     // viewサイズの横幅
    private int mHeight;    // viewサイズの縦幅

    public ReversiView(Context context) {
        super(context);

        mBoard = new Board();

        mPaintBoarder.setColor(Color.rgb(0,0,0));
        mPaintHintsBlack.setColor(Color.BLACK);
        mPaintHintsWhite.setColor(Color.WHITE);

        // アンチエイリアス。縁を滑らかにする
        mPaintHintsBlack.setAntiAlias(true);
        mPaintHintsWhite.setAntiAlias(true);

        // 透明度
        mPaintHintsBlack.setAlpha(50);
        mPaintHintsWhite.setAlpha(50);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // リソースからbitmapを作成
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

        // view範囲の幅、高さを調べる
        this.mWidth = dm.widthPixels;
        this.mHeight = dm.heightPixels;

        // ボードのサイズを設定
        mBoard.setSize(mWidth, mHeight);

        // 初めだけbitmapを読み込む
        if (mBitmapBoard == null) {
            loadBitbap();
        }
        // ボードの描画
        drawBoard(canvas);
    }

    // bitmapの読み込み
    private void loadBitbap() {

        float cell_width = mBoard.getCellWidth();
        float cell_height = mBoard.getCellHeight();

        try {
            // screen
            Bitmap screen = BitmapFactory.decodeResource(getResources(), R.drawable.screen); // 画像の読み込み
            mBitmapScreen = Bitmap.createScaledBitmap(screen, mWidth, mHeight, true); // 幅の指定

            // board
            Bitmap board = BitmapFactory.decodeResource(getResources(), R.drawable.board); // 画像の読み込み
            mBitmapBoard = Bitmap.createScaledBitmap(board, (int)mBoard.getRectF().width(), (int)mBoard.getRectF().height(), true); // 幅の指定
        } catch (Exception ex) {
            Utils.d(ex.getMessage());
        }

        try {
            // 黒
            Bitmap black = BitmapFactory.decodeResource(getResources(), R.drawable.black);
            mBitmapBlack = Bitmap.createScaledBitmap(black, (int)(cell_width*CELL_RESIZE_FACTOR), (int)(cell_height*CELL_RESIZE_FACTOR), true);

            // 白
            Bitmap white = BitmapFactory.decodeResource(getResources(), R.drawable.white);
            mBitmapWhite = Bitmap.createScaledBitmap(white, (int)(cell_width*CELL_RESIZE_FACTOR), (int)(cell_height*CELL_RESIZE_FACTOR), true);
        } catch (Exception ex) {
            Utils.d(ex.getMessage());
        }
    }

    // ボードの描画
    private void drawBoard(Canvas canvas) {

        float board_left = mBoard.getRectF().left;
        float board_top = mBoard.getRectF().top;
        float board_height = mBoard.getRectF().height();
        float board_width = mBoard.getRectF().width();
        float cell_width = mBoard.getCellWidth();
        float cell_height = mBoard.getCellHeight();

        // 背景の描画
        canvas.drawBitmap(mBitmapScreen, 0, 0, null);
        // ボードの描画
        canvas.drawBitmap(mBitmapBoard, board_left, board_top, null);

        // 縦線
        for (int i = 0; i <= Board.COLS; i++) {
            canvas.drawLine(cell_width * i + board_left, board_top, cell_width * i + board_left, board_top + board_height, mPaintBoarder);
        }

        // 横線
        for (int i = 0; i <= Board.ROWS; i++) {
            canvas.drawLine(board_left, board_top + i * cell_height, board_left + board_width, board_top + i * cell_height, mPaintBoarder);
        }

        // ４つの点
        paint.setColor(Color.BLACK);
        canvas.drawCircle(board_left + 2*cell_width, board_top + 2*cell_height, 10f, paint);
        canvas.drawCircle(board_left + 6*cell_width, board_top + 2*cell_height, 10f, paint);
        canvas.drawCircle(board_left + 2*cell_width, board_top + 6*cell_height, 10f, paint);
        canvas.drawCircle(board_left + 6*cell_width, board_top + 6*cell_height, 10f, paint);

        // セルの状態を描画
        drawCells(canvas, cell_width);
    }

    // セル毎に石を描画
    private void drawCells(Canvas canvas, float cell_width) {

        Cell[][] cells = mBoard.getCells();
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                Cell cell = cells[i][j];
                Cell.CELL_STATUS status = cell.getStatus();

                if (status == Cell.CELL_STATUS.None) {
                    drawHints(canvas, cell, cell_width);
                } else {
                    // 石の描画
                    drawStone(cell, canvas, cell_width, status);
                }

            }
        }
    }

    // 石の描画
    private void drawStone(Cell cell, Canvas canvas, float cell_width, Cell.CELL_STATUS status) {

        final float INSET = (cell_width * (1 - CELL_RESIZE_FACTOR))/2;

        Bitmap stone = (status == Cell.CELL_STATUS.Black) ? mBitmapBlack : mBitmapWhite;
        canvas.drawBitmap(stone, cell.getLeft() + INSET, cell.getTop() + INSET, null);
    }

    private void drawHints(Canvas canvas, Cell cell, float cell_width) {

        if (cell.getReversibleCells().size() == 0) {
            return;
        }

        // 小さな丸をヒントとして表示
        float hints_size = cell_width * HINTS_SIZE_FACTOR;
        Paint hints = mBoard.getTurn() == Cell.CELL_STATUS.Black ? mPaintHintsBlack : mPaintHintsWhite;
        canvas.drawCircle(cell.getCenterX(), cell.getCenterY(), hints_size, hints);
    }

    // タップ時の動作を設定
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int row = (int)(y / mBoard.getCellHeight());    // 座標を対応するセルの行に変換
                int col = (int)(x / mBoard.getCellWidth());     // 座標を対応するセルの列に変換
                // ボードの範囲外は無視
                if (row < Board.ROWS && col < Board.COLS && row >= 0 && col >= 0) {
                    move(new Point(col, row));
                }
                break;
            default:
        }
        return true;
    }

    private void move(final Point point) {

        Cell touchedCell = mBoard.getCell(point);

        // ひっくり返せるセル以外は処理しない
        if (touchedCell.getReversibleCells().size() == 0) {
            return;
        }

        if (touchedCell.getStatus() == Cell.CELL_STATUS.None) {
            // タップしたセル及び挟んだセルをひっくり返す
            mBoard.changeCell(point, mBoard.getTurn());
            mBoard.changeTurn(mBoard.getTurn());
            mBoard.setAllReversibleCells();
        }

        invalidate();
    }
}
