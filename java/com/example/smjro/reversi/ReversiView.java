package com.example.smjro.reversi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.smjro.reversi.model.Board;


/**
 * Created by smjro on 16/09/27.
 */

public class ReversiView extends View {

    private Bitmap mBitmapBoard;
    private Bitmap mBitmapScreen;
    private Paint paint = new Paint();
    private Board mBoard;

    private Paint mPaintBoarder = new Paint();

    private int mwidth;
    private int mheight;

    public ReversiView(Context context) {
        super(context);

        mBoard = new Board();

        mPaintBoarder.setColor(Color.rgb(0,0,0));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // リソースからbitmapを作成
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

        // view範囲の幅、高さを調べる
        this.mwidth = dm.widthPixels;
        this.mheight = dm.heightPixels;

        // ボードのサイズを設定
        mBoard.setSize(mwidth, mheight);

        // 初めだけbitmapを読み込む
        if (mBitmapBoard == null) {
            loadBitbap();
        }
        // ボードの描画
        drawBoard(canvas);
    }

    private void loadBitbap() {

        try {
            // screen
            Bitmap screen = BitmapFactory.decodeResource(getResources(), R.drawable.screen); // 画像の読み込み
            mBitmapScreen = Bitmap.createScaledBitmap(screen, mwidth, mheight, true); // 幅の指定

            // board
            Bitmap board = BitmapFactory.decodeResource(getResources(), R.drawable.board); // 画像の読み込み
            mBitmapBoard = Bitmap.createScaledBitmap(board, (int)mBoard.getRectF().width(), (int)mBoard.getRectF().height(), true); // 幅の指定
        } catch (Exception ex) {
            Utils.d(ex.getMessage());
        }
    }

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
        canvas.drawBitmap(mBitmapBoard, board_left, board_top, paint);

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

    }
}
