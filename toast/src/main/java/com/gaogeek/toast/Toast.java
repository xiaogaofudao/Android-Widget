package com.gaogeek.toast;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * Created by gaogeek on 2017/7/26.
 */

public class Toast extends Dialog {
    public static final int LENGTH_SHORT = 0;
    public static final int LENGTH_LONG = 1;
    private Long time = 2000L;
    private Context mContext;
    private TextView mMessage;
    private Window dialog;
    private static Toast mToast = null;
    public Toast(Context context, String msg, int duration) {
        super(context, R.style.Translucent_Dialog);
        mContext = context;
        if(duration == Toast.LENGTH_LONG) {
            time = 3500L;
        } else if (duration > 1000) {
            time = (long) duration;
        }
        initView(msg);
    }
    private void initView(String msg) {
        setCancelable(false);
        setContentView(R.layout.toast);
        dialog = getWindow();
        mMessage = (TextView) findViewById(R.id.mbMessage);
        WindowManager.LayoutParams lp = dialog.getAttributes();
        lp.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
        lp.y = dip2px(mContext, 72f);
        dialog.setAttributes(lp);
//        setCanceledOnTouchOutside(true);
    }

    public static Toast makeText(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = new Toast(context, msg, duration);
        }
        if (!msg.isEmpty()) {
            mToast.setText(msg);
        }
        return mToast;
    }

    public static Toast makeText(Context context, int resId, int HIDE_DELAY) {
        return makeText(context, context.getText(resId).toString(), HIDE_DELAY);
    }

    private Runnable dismissRunnable = new Runnable() {
        @Override
        public void run() {
            dismiss();
        }
    };

    private void setText(String msg) {
        mMessage.setText(msg);
    }

    public void show() {
        super.show();
        dialog.getDecorView().postDelayed(dismissRunnable, time);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}