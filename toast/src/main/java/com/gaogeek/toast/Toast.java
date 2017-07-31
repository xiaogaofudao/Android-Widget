package com.gaogeek.toast;

import android.content.Context;

import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class Toast {
    private WindowManager manger;
    private Long time = 2000L;
    private static View contentView;
    private WindowManager.LayoutParams params;
    private static Timer timer;
    private android.widget.Toast toast;
    private static android.widget.Toast oldToast;
    public static final int LENGTH_SHORT = android.widget.Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = android.widget.Toast.LENGTH_LONG;
    private static Handler handler;
    private CharSequence text;
    private Context mContext;

    private Toast(Context context, CharSequence text, int HIDE_DELAY){
        manger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        this.text = text;
        mContext = context;

        setDuration(HIDE_DELAY);

        if(oldToast == null){
            toast = android.widget.Toast.makeText(context, text, Toast.LENGTH_SHORT);
            contentView = toast.getView();

            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            params.y = dip2px(context, 72f);
        }
        if(handler == null){
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    Toast.this.cancel();
                }
            };
        }
    }

    public static Toast makeText(Context context, String text, int HIDE_DELAY){
        return new Toast(context, text, HIDE_DELAY);
    }

    public static Toast makeText(Context context, int resId, int HIDE_DELAY) {
        return makeText(context,context.getText(resId).toString(),HIDE_DELAY);
    }

    public void show(){
        if(oldToast == null){
            oldToast = toast;
            manger.addView(contentView, params);
            timer = new Timer();
        } else {
            setText(text);
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

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void cancel(){
        manger.removeView(contentView);
        timer.cancel();
        oldToast.cancel();
        timer = null;
        toast = null;
        oldToast = null;
        contentView = null;
        handler = null;
    }
    public void setText(CharSequence s){
        this.text = s;
        if (oldToast == null) {
            toast.setText(s);
        } else {
            oldToast.setText(s);
        }
    }

    public void setText(int resId) {
        setText(mContext.getText(resId).toString());
    }

    public void setDuration(int t) {
        if(t == Toast.LENGTH_SHORT) {
            this.time = 2000L;
        } else if(t == Toast.LENGTH_LONG) {
            this.time = 3500L;
        } else if (t > 1000) {
            this.time = (long) t;
        }
    }
}