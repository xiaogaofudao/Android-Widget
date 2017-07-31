package com.gaogeek.toast;

import android.content.Context;

public class Toast {
    public static final int LENGTH_SHORT = eToast.LENGTH_SHORT;
    public static final int LENGTH_LONG = eToast.LENGTH_LONG;
    private Object mToast;

    private Toast(Context context, String message, int duration) {
        mToast = eToast.makeText(context, message, duration);
    }

    private Toast(Context context, int resId, int duration) {
        mToast = eToast.makeText(context, resId, duration);
    }

    public static Toast makeText(Context context, String message, int duration) {
        return new Toast(context, message, duration);
    }

    public static Toast makeText(Context context, int resId, int duration) {
        return new Toast(context, resId, duration);
    }

    public void show() {
        if (mToast instanceof eToast) {
            ((eToast) mToast).show();
        } else if (mToast instanceof android.widget.Toast) {
            ((android.widget.Toast) mToast).show();
        }
    }

    public void cancel() {
        if (mToast instanceof eToast) {
            ((eToast) mToast).cancel();
        } else if (mToast instanceof android.widget.Toast) {
            ((android.widget.Toast) mToast).cancel();
        }
    }

    public void setText(CharSequence s) {
        if (mToast instanceof eToast) {
            ((eToast) mToast).setText(s);
        } else if (mToast instanceof android.widget.Toast) {
            ((android.widget.Toast) mToast).setText(s);
        }
    }

    /**
     * 用来判断是否开启通知权限
     */
//    private static boolean isNotificationEnabled(Context context) {
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//            ApplicationInfo appInfo = context.getApplicationInfo();
//
//            String pkg = context.getApplicationContext().getPackageName();
//
//            int uid = appInfo.uid;
//
//            Class appOpsClass = null; /* Context.APP_OPS_MANAGER */
//
//            try {
//                appOpsClass = Class.forName(AppOpsManager.class.getName());
//                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);
//                Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
//                int value = (int) opPostNotificationValue.get(Integer.class);
//                return ((int) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return false;
//            }
//        } else {
//            return false;
//        }
//    }
}