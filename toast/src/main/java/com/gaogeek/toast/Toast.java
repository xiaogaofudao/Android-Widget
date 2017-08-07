package com.gaogeek.toast;

/**
 * Created by gaogeek on 2017/8/2.
 */

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Toast {

    private Handler handler;

    public static int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;
    public static int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;

    private Context mContext;

    private Object mObj;
    private Method showMethod, hideMethod;
    private android.widget.Toast mToast;

    private String text = "Toast";
    private int time = 2500;

    private boolean hasReflectException = false;

    private static Toast instance;

    public static Toast makeText(Context context, String text, int delay) {
        if (instance == null) {
            instance = new Toast(context);
        }
        instance.setDuration(delay);
        instance.setText(text);
        return instance;
    }

    private Toast(Context context) {
        if (context == null || context.getApplicationContext() == null) {
            throw new NullPointerException("context can't be null");
        }
        this.mContext = context;
        initTN();
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Toast.this.hideToast();
                }
            };
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setText(int resId) {
        setText(mContext.getText(resId).toString());
    }

    public void setDuration(int t) {
        if (t == android.widget.Toast.LENGTH_SHORT) {
            this.time = 2500;
        } else if (t == android.widget.Toast.LENGTH_LONG) {
            this.time = 3500;
        } else if (t > 1000) {
            this.time = t;
        }
    }

    public final void show() {
        if (!hasReflectException) {
            mToast.setText(text);
            showToast();
            handler.removeMessages(1);
            handler.sendEmptyMessageDelayed(1, time);
        }
    }

    private void initTN() {
        Field mTN;
        if (mToast == null) {
            mToast = android.widget.Toast.makeText(mContext, text, android.widget.Toast.LENGTH_SHORT);
        }
        Class<android.widget.Toast> clazz = android.widget.Toast.class;
        try {
            mTN = clazz.getDeclaredField("mTN");
            mTN.setAccessible(true);
            mObj = mTN.get(mToast);
            showMethod = mObj.getClass().getDeclaredMethod("show", new Class<?>[0]);
            hideMethod = mObj.getClass().getDeclaredMethod("hide", new Class<?>[0]);
            Field mY = mObj.getClass().getDeclaredField("mY");
            mY.setAccessible(true);
            mY.set(mObj, dip2px(mContext, 68F));
            hasReflectException = false;
          } catch (Exception e) {
            hasReflectException = true;
        }
    }

    private void showToast() {
        try {
            Field mNextView = mObj.getClass().getDeclaredField("mNextView");
            mNextView.setAccessible(true);
            mNextView.set(mObj, mToast.getView());
            showMethod.invoke(mObj, new Object[0]);
            hasReflectException = false;
        } catch (Exception e) {
            hasReflectException = true;
        }
    }

    private void hideToast() {
        try {
            hideMethod.invoke(mObj, new Object[0]);
            hasReflectException = false;
        } catch (Exception e) {
            hasReflectException = true;
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}