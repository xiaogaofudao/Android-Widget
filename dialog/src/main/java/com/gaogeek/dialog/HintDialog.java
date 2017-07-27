package com.gaogeek.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by gaogeek on 2017/7/27.
 */

public class HintDialog extends Dialog {
    private static HintDialog mDialog = null;
    private HintDialog(Context context) {
        super(context, R.style.Translucent_Dialog);
    }

    public static HintDialog makeText(Context context, String msg) {
        if (mDialog == null) {
            mDialog = new HintDialog(context);
        }
        if (!msg.isEmpty()) {
//            mDialog.set(msg);
        }
        return mDialog;
    }
}
