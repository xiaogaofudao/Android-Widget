package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.text.Spannable;
import android.text.SpannableString;

public class SimpleSpinnerTextFormatter implements SpinnerTextFormatter {

    @Override
    public Spannable format(String text) {
        return new SpannableString(text);
    }
}
