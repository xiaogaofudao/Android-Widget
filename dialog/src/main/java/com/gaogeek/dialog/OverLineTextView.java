package com.gaogeek.dialog;

/**
 * Created by gaogeek on 2017/7/28.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


public class OverLineTextView extends TextView {

    public OverLineTextView(Context context) {
        this(context, null);
    }

    public OverLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OverLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setOverLine(getLineCount() > 1);
    }

    private void setOverLine(boolean overLine) {
        if (overLine) {
            setGravity(Gravity.START | Gravity.TOP);
        } else {
            setGravity(Gravity.CENTER);
        }
    }
}
