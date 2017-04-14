package com.basti.platenumberkeyboard;

import android.content.Context;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.basti.platenumberkeyboard.adapter.HeaderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 车牌键盘里的字母键盘
 * <p>
 * Created by Boateng17 on 2017/4/13.
 */

public class PlateContentKeyboard extends BaseKeyboard {

    static String[] DEFAULT_HEAD_LIST = new String[]{"警", "学", "领", "试", "挂", "拖"};

    HeaderAdapter headerAdapter;

    public PlateContentKeyboard(Context context) {
        super(context);
        initBackground();
    }

    public PlateContentKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public PlateContentKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }

    private void initBackground() {
        SparseArray<KeyBackGround> keyBackgroundArray = new SparseArray<>();
        int textColor = getResources().getColor(R.color.white);
        //确认按钮
        keyBackgroundArray.put(1, new KeyBackGround(getContext().getResources().getDrawable(R.drawable.confirm_keybackground), textColor));
        //删除
        keyBackgroundArray.put(-1, new KeyBackGround(getContext().getResources().getDrawable(R.drawable.normal), textColor,R.mipmap.icon_delete));
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
                        //删除
                        if (contentTv != null) {
                            if (contentTv instanceof EditText) {
                                Editable editable = ((EditText) contentTv).getText();
                                int start = contentTv.getSelectionStart();
                                if (start != 0) {
                                    editable.delete(start - 1, start);
                                }
                            }
                        }
                        break;
                    case 1:
                        //确认
                        showKeyboard(false);
                        break;
                    default:
                        if (contentTv != null) {
                            if (contentTv instanceof EditText) {
                                Editable editable = ((EditText) contentTv).getText();
                                int start = contentTv.getSelectionStart();
                                editable.insert(start, Character.toString((char) primaryCode));
                            }
                        }
                        break;
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
        PlateContent plateContent;
        for (String headString : DEFAULT_HEAD_LIST) {
            plateContent = new PlateContent(headString, headString);
            headerList.add(plateContent);
        }

        headerAdapter = new HeaderAdapter(headerList);
        headerAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position < headerList.size()) {
                    if (onHeaderViewClick != null) {
                        PlateContent clickContent = headerList.get(position);
                        onHeaderViewClick.onClick(position, clickContent);
                    }
                }
            }
        });

        return headerAdapter;
    }

    @Override
    protected int getHeadViewCount() {
        return 6;
    }

    @Override
    protected int getKeyBoardConfig() {
        return R.xml.content;
    }

    @Override
    public void showKeyboard(boolean show) {
        setVisibility(show ? VISIBLE : GONE);
    }
}
