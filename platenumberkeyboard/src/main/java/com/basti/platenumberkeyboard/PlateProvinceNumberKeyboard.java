package com.basti.platenumberkeyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;

/**
 * Created by Boateng17 on 2017/4/12.
 */

public class PlateProvinceNumberKeyboard extends BaseKeyboard {
    public PlateProvinceNumberKeyboard(Context context) {
        super(context);
    }

    public PlateProvinceNumberKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public PlateProvinceNumberKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    //初始化按键背景颜色
    private void initBackground() {
        SparseArray<KeyBackGround> keyBackgroundArray = new SparseArray<>();
        int textColor = getResources().getColor(R.color.white);
        keyBackgroundArray.put(-1, new KeyBackGround(getContext().getResources().getDrawable(R.drawable.back_keybackground), textColor));
        //   keyboardView.setKeyBackground(new KeyBackGround(getContext().getResources().getDrawable(R.drawable.normal), getResources().getColor(R.color.normal_textcolor)));

        setSpecialBackground(keyBackgroundArray);
    }

    @Override
    protected KeyboardView.OnKeyboardActionListener getKeyboardActionListener() {
        return new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {

            }

            @Override
            public void onRelease(int primaryCode) {

            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                switch (primaryCode) {
                    case -1:
                        //取消
                        showKeyboard(false);
                        break;
                    case 1:
                        //下一步
                        break;
                    default:
                        //选择省份
                        if (contentTv != null) {
                            contentTv.setText(Character.toString((char) primaryCode));
                            showKeyboard(false);
                        }
                }

            }

            @Override
            public void onText(CharSequence text) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };
    }

    @Override
    protected RecyclerView.Adapter getAdapter() {
        return null;
    }

    @Override
    protected int getHeadViewCount() {
        return 1;
    }

    @Override
    protected int getKeyBoardConfig() {
        return R.xml.province;
    }

    @Override
    public void showKeyboard(boolean show) {
        setVisibility(show ? VISIBLE : GONE);
    }
}
