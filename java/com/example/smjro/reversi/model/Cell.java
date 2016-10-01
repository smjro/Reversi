package com.example.smjro.reversi.model;

import android.graphics.RectF;

/**
 * Created by smjro on 16/09/27.
 */

public class Cell {

    public enum CELL_STATUS {
        None,
        Black,
        White
    }

    private CELL_STATUS status = CELL_STATUS.None;
    private RectF rectF = new RectF();

    public Cell(){

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
}
