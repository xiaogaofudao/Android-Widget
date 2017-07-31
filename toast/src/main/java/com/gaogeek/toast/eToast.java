package com.gaogeek.toast;

/**
 * Created by gaogeek on 2017/7/31.
 */

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class eToast {

    private WindowManager manger;
    private Long time = 2000L;
    private static View contentView;
    private WindowManager.LayoutParams params;
    private static Timer timer;
    private Toast toast;
    private static Toast oldToast;
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private static Handler handler;
    private CharSequence text;

    private eToast(Context context, CharSequence text, int HIDE_DELAY){
        manger = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        this.text = text;

        if(HIDE_DELAY == eToast.LENGTH_SHORT)
            this.time = 2000L;
        else if(HIDE_DELAY == eToast.LENGTH_LONG)
            this.time = 3500L;

        if(oldToast == null){
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            contentView = toast.getView();

            params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.windowAnimations = toast.getView().getAnimation().INFINITE;
//            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.setTitle("EToast2");
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
                    eToast.this.cancel();
                }
            };
        }
    }

    public static eToast makeText(Context context, String text, int HIDE_DELAY){
        eToast toast = new eToast(context, text, HIDE_DELAY);
        return toast;
    }

    public static eToast makeText(Context context, int resId, int HIDE_DELAY) {
        return makeText(context,context.getText(resId).toString(),HIDE_DELAY);
    }

    public void show(){
        if(oldToast == null){
            oldToast = toast;
            manger.addView(contentView, params);
            timer = new Timer();
        }else{
            timer.cancel();
            oldToast.setText(text);
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

    public void cancel(){
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
        toast.setText(s);
    }
}