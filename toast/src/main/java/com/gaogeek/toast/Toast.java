package com.gaogeek.toast;

/**
 * Created by gaogeek on 2017/8/2.
 */
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import android.os.Message;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

public class Toast {

    private static int currentapiVersion = Build.VERSION.SDK_INT;

    private Handler handler;
    private static Timer timer;

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
        if (instance != null)
            throw new NullPointerException("error");
        if (handler == null) {
            handler = new Handler(){
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
        if(t == android.widget.Toast.LENGTH_SHORT) {
            this.time = 2000;
        } else if(t == android.widget.Toast.LENGTH_LONG) {
            this.time = 3500;
        } else if (t > 1000) {
            this.time = t;
        }
    }

    public final void show() {
        if (hasReflectException) {
            android.widget.Toast t = new android.widget.Toast(mContext);
            t.setText(text);
            t.setDuration(android.widget.Toast.LENGTH_SHORT);
            t.show();
            initTN();
            return;
        }
        mToast.setText(text);
        showToast();
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, time);
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
            showMethod = mObj.getClass().getDeclaredMethod("show",
                    new Class<?>[0]);
            hideMethod = mObj.getClass().getDeclaredMethod("hide",
                    new Class<?>[0]);
            Field mY = mObj.getClass().getDeclaredField("mY");
            mY.setAccessible(true);
            mY.set(mObj, dip2px(mContext, 68F));
            hasReflectException = false;
        } catch (NoSuchFieldException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (IllegalAccessException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        } catch (NoSuchMethodException e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        }
    }

    private void showToast() {
        try {
            if (currentapiVersion > 10) {
                Field mNextView = mObj.getClass().getDeclaredField("mNextView");
                mNextView.setAccessible(true);
                mNextView.set(mObj, mToast.getView());
            }
            showMethod.invoke(mObj, new Object[0]);
            hasReflectException = false;
        } catch (Exception e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        }
    }

    private void hideToast() {
        timer.cancel();
        timer = null;
        try {
            hideMethod.invoke(mObj, new Object[0]);
            hasReflectException = false;
        } catch (Exception e) {
            hasReflectException = true;
            System.out.println(e.getMessage());
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}