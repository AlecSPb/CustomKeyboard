package com.basti.platenumberkeyboard;

import android.graphics.drawable.Drawable;

/**
 * Created by Boateng17 on 2017/4/14.
 */

public class KeyBackGround {
    Drawable keyDrawable;
    int textColor;
    int resId;

    public Drawable getKeyDrawable() {
        return keyDrawable;
    }

    public void setKeyDrawable(Drawable keyDrawable) {
        this.keyDrawable = keyDrawable;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public KeyBackGround(Drawable keyDrawable, int textColor) {

        this.keyDrawable = keyDrawable;
        this.textColor = textColor;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public KeyBackGround(Drawable keyDrawable, int textColor, int resId) {
        this.keyDrawable = keyDrawable;
        this.textColor = textColor;
        this.resId = resId;
    }
}
