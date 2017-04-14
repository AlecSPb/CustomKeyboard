package com.basti.platenumberkeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 键盘基类
 * <p>
 * Created by Boateng17 on 2017/4/12.
 */

public abstract class BaseKeyboard extends LinearLayout {

    //View
    protected RecyclerView headViewRv;
    protected CustomKeyboardView keyboardView;
    protected TextView contentTv;

    protected Keyboard keyboard;

    //header数据
    protected List<PlateContent> headerList;

    protected OnHeaderViewClick onHeaderViewClick;

    int keyboardBackgroundColor;
    int keyBackgroundColor;

    public BaseKeyboard(Context context) {
        super(context);
        init(null);
    }

    public BaseKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BaseKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setOnHeaderViewClick(OnHeaderViewClick onHeaderViewClick) {
        this.onHeaderViewClick = onHeaderViewClick;
    }

    //初始化
    private void init(AttributeSet attrs) {
        setOrientation(VERTICAL);

        headerList = new ArrayList<>();
        headViewRv = new RecyclerView(getContext());
        headViewRv.setLayoutManager(new GridLayoutManager(getContext(), getHeadViewCount()));
        headViewRv.setAdapter(getAdapter());

        keyboard = new Keyboard(getContext(), getKeyBoardConfig());
        keyboardView = new CustomKeyboardView(getContext(), null);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(getKeyboardActionListener());
        keyboardView.setKeyBackground(new KeyBackGround(getContext().getResources().getDrawable(R.drawable.normal), getResources().getColor(R.color.normal_textcolor)));
        if (headViewRv != null) {
            addView(headViewRv);
        }
        if (keyboardView != null) {
            addView(keyboardView);
        }

        //读取自定义属性
        initAttr(attrs);
        setAttr();
    }

    //设置自定义属性
    private void setAttr() {
        keyboardView.setBackgroundColor(keyboardBackgroundColor);
        setBackgroundColor(keyboardBackgroundColor);
        // keyboardView.setKey
    }

    //读取自定义属性
    private void initAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BaseKeyboard);
        keyboardBackgroundColor = typedArray.getColor(R.styleable.BaseKeyboard_keyboardBackground, getResources().getColor(R.color.default_keyboard_background));
        keyBackgroundColor = typedArray.getColor(R.styleable.BaseKeyboard_keyBackground, getResources().getColor(R.color.white));
        typedArray.recycle();
    }

    protected void setSpecialBackground(SparseArray backgroundArray) {
        keyboardView.setSpecialKeyBackgrounds(backgroundArray);
    }

    protected abstract KeyboardView.OnKeyboardActionListener getKeyboardActionListener();

    //获得头部HeadView的适配器
    protected abstract RecyclerView.Adapter getAdapter();

    //获得头部View每行的个数
    protected abstract int getHeadViewCount();

    //获得keyboard的配置文件
    protected abstract int getKeyBoardConfig();

    //弹出自定义键盘
    protected abstract void showKeyboard(boolean show);

    public void setContentTv(TextView contentTv) {
        this.contentTv = contentTv;
        initContentEvent();
    }

    //设置输入框的触摸事件
    private void initContentEvent() {
        contentTv.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //屏蔽系统键盘
                hideSoftInputMethod();
                //弹出自定义键盘
                showKeyboard(true);
                return false;
            }
        });
    /*    ((ViewGroup) getParent()).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showKeyboard(false);
                return true;
            }
        });*/
    }

    //隐藏键盘
    private void hideSoftInputMethod() {
        ((Activity) getContext()).getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            contentTv.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName,
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(contentTv, false);
            } catch (NoSuchMethodException e) {
                contentTv.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
