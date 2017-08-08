package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.text.Spannable;

public interface SpinnerTextFormatter {
    Spannable format(String text);
}
