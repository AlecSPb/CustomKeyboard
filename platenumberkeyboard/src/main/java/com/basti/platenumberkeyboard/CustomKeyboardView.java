package com.basti.platenumberkeyboard;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;

import java.util.List;

/**
 * Created by Boateng17 on 2017/4/13.
 */

public class CustomKeyboardView extends KeyboardView {
    Paint paint;
    SparseArray<KeyBackGround> specialKeyBackgrounds;
    KeyBackGround keyBackGround;
    Bitmap bitmap;

    public CustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    public void init() {
        paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(40);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public void setKeyBackground(KeyBackGround keyBackground) {
        this.keyBackGround = keyBackground;
        invalidateAllKeys();
    }

    public void setSpecialKeyBackgrounds(SparseArray<KeyBackGround> specialKeyBackgrounds) {
        this.specialKeyBackgrounds = specialKeyBackgrounds;
        invalidateAllKeys();
    }

    @Override
    public void onDraw(Canvas canvas) {
        // super.onDraw(canvas);
        if (keyBackGround != null) {
            List<Keyboard.Key> keys = getKeyboard().getKeys();
            for (Keyboard.Key key : keys) {
                int code = key.codes[0];
                //特殊案件
                if (specialKeyBackgrounds != null && specialKeyBackgrounds.get(code) != null) {
                    //画背景
                    KeyBackGround keyBackGround = specialKeyBackgrounds.get(code);
                    if (keyBackGround != null && keyBackGround.getKeyDrawable() != null) {
                        Drawable keyBackgroundDrawble = keyBackGround.getKeyDrawable();
                        keyBackgroundDrawble.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                        keyBackgroundDrawble.draw(canvas);
                    }
                    //写字
                    if (key.label != null && keyBackGround != null) {
                        paint.setColor(keyBackGround.getTextColor());
                        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                        int baseline = (key.y + key.height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
                                + key.y / 2;
                        canvas.drawText(key.label.toString(), key.x + (key.width / 2), baseline, paint);
                    }
                    if (keyBackGround.getResId() != -1 && keyBackGround.getResId() != 0) {
                        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_delete);
                        int bitmapWidth = bitmap.getWidth();
                        int bitmapHeight = bitmap.getHeight();

                        int x = key.x + (key.width - bitmapWidth) / 2;
                        int y = key.y + (key.height-bitmapHeight)/2;
                        canvas.drawBitmap(bitmap, x, y, null);
                    }
                } else {
                    //普通按键
                    Log.e("KEY", "Drawing key with code " + key.codes[0]);
                    Drawable normalBackground = keyBackGround.getKeyDrawable();
                    if (normalBackground != null) {
                        normalBackground.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                        normalBackground.draw(canvas);
                        if (key.label != null) {
                            paint.setColor(keyBackGround.getTextColor());
                            Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
                            int baseline = (key.y + key.height - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top
                                    + key.y / 2;
                            canvas.drawText(key.label.toString(), key.x + (key.width / 2), baseline, paint);
                        }
                    }
                }
            }
        }

    }

}
