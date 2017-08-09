package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.content.Context;

import java.util.List;

public class SpinnerAdapter<T> extends SpinnerBaseAdapter {

    private final List<T> items;

    SpinnerAdapter(Context context, List<T> items, int textColor, int backgroundSelector,
                       SpinnerTextFormatter spinnerTextFormatter) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter);
        this.items = items;
    }

    @Override
    public int getCount() {
        int size = items.size();
        return selectedIndex < 0 ? size : size - 1;
    }

    @Override
    public T getItem(int position) {
        if (selectedIndex < 0 || position < selectedIndex) {
            return items.get(position);
        } else {
            return items.get(position + 1);
        }
    }

    @Override
    public T getItemInDataset(int position) {
        return items.get(position);
    }
}
