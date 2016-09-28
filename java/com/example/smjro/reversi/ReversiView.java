package com.example.smjro.reversi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by smjro on 16/09/27.
 */

public class ReversiView extends View {

    private Bitmap mBitmapBoard;
    private Paint paint = new Paint();

    private int mwidth;
    private int mheight;

    public ReversiView(Context context) {
        super(context);

        // リソースからbitmapを作成
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();

        mwidth = dm.widthPixels;
        mheight = dm.heightPixels;

        Bitmap board = BitmapFactory.decodeResource(getResources(), R.drawable.screen);

        mBitmapBoard = Bitmap.createScaledBitmap(board, mwidth, mheight, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmapBoard, 0, 0, paint);
    }
}
