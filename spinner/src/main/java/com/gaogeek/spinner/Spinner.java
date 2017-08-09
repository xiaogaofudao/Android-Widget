package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;

public class Spinner extends TextView {

    private static final int MAX_LEVEL = 10000;
    private static final int DEFAULT_ELEVATION = 16;

    private int selectedIndex;
    private Drawable arrowDrawable;
    private PopupWindow popupWindow;
    private ListView listView;
    private SpinnerBaseAdapter adapter;
    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;
    private int textColor;
    private int backgroundSelector;

    private SpinnerTextFormatter spinnerTextFormatter = new SimpleSpinnerTextFormatter();

    public Spinner(Context context) {
        super(context);
        init(context, null);
    }

    public Spinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public Spinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (isInEditMode()){
            return;
        }
        Resources resources = getResources();
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Spinner);

        setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
        setClickable(true);
        setFocusable(true);

        backgroundSelector = typedArray.getResourceId(R.styleable.Spinner_backgroundSelector, R.drawable.selector);
        textColor = typedArray.getColor(R.styleable.Spinner_textTint, getDefaultTextColor(context));

        listView = new ListView(context);
        listView.setId(getId());
        listView.setDivider(new ColorDrawable(Color.LTGRAY));
        listView.setDividerHeight(1);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= selectedIndex && position < adapter.getCount() && selectedIndex > -1) {
                    position++;
                }
                selectedIndex = position;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }

                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }

                adapter.setSelectedIndex(position);
                setText(adapter.getItemInDataset(position).toString());
                dismissDropDown();
            }
        });
        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        popupWindow.setHeight(Math.min(resources.getDisplayMetrics().heightPixels / 2, popupWindow.getMaxAvailableHeight(this)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(DEFAULT_ELEVATION);
            popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.spinner_background));
        } else {
            popupWindow.setBackgroundDrawable(resources.getDrawable(R.drawable.drop_down_shadow));
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                animateArrow(false);
            }
        });

        arrowDrawable = resources.getDrawable(R.drawable.arrow);
        int size = dp2px(context, 18);
        arrowDrawable.setBounds(0, 0, size, size);
        setArrowDrawable(arrowDrawable);
        typedArray.recycle();
    }

    public static int dp2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private int getDefaultTextColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme()
                .resolveAttribute(android.R.attr.textColorPrimary, typedValue, true);
        TypedArray typedArray = context.obtainStyledAttributes(typedValue.data,
                new int[]{android.R.attr.textColorPrimary});
        int defaultTextColor = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();
        return defaultTextColor;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setArrowDrawable(Drawable drawable) {
        arrowDrawable = drawable;
        setCompoundDrawables(null, null, arrowDrawable, null);
    }

    public void setSelectedIndex(int position) {
        if (adapter != null) {
            if (position >= 0 && position <= adapter.getCount()) {
                adapter.setSelectedIndex(position);
                selectedIndex = position;
                setText(adapter.getItemInDataset(position).toString());
            } else {
                throw new IllegalArgumentException("Position must be lower than adapter count!");
            }
        }
    }

    public void addOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public <T> void attachDataSource(List<T> list) {
        adapter = new SpinnerAdapter<>(getContext(), list, textColor, backgroundSelector,
                spinnerTextFormatter);
        setAdapterInternal(adapter);
    }

    public void setAdapter(ListAdapter adapter) {
        this.adapter = new SpinnerAdapterWrapper(getContext(), adapter, textColor, backgroundSelector,
                spinnerTextFormatter);
        setAdapterInternal(this.adapter);
    }

    private void setAdapterInternal(SpinnerBaseAdapter adapter) {
        selectedIndex = -1;
        listView.setAdapter(adapter);
//        setSelectedIndex(14);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        listView.measure(widthMeasureSpec, heightMeasureSpec);
        popupWindow.setWidth(View.MeasureSpec.getSize(widthMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled() && event.getAction() == MotionEvent.ACTION_UP) {
            if (!popupWindow.isShowing()) {
                showDropDown();
            } else {
                dismissDropDown();
            }
        }
        return super.onTouchEvent(event);
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(arrowDrawable, "level", start, end);
        animator.start();
    }

    public void dismissDropDown() {
        animateArrow(false);
        popupWindow.dismiss();
    }

    public void showDropDown() {
        animateArrow(true);
        popupWindow.showAsDropDown(this);
    }

    public void setSpinnerTextFormatter(SpinnerTextFormatter spinnerTextFormatter) {
        this.spinnerTextFormatter = spinnerTextFormatter;
    }
}