package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.content.Context;
import android.widget.ListAdapter;

public class SpinnerAdapterWrapper extends SpinnerBaseAdapter {

    private final ListAdapter baseAdapter;

    SpinnerAdapterWrapper(Context context, ListAdapter toWrap, int textColor, int backgroundSelector,
                              SpinnerTextFormatter spinnerTextFormatter) {
        super(context, textColor, backgroundSelector, spinnerTextFormatter);
        baseAdapter = toWrap;
    }

    @Override
    public int getCount() {
        int size = baseAdapter.getCount();
        return selectedIndex < 0 ? size : size - 1;
    }

    @Override
    public Object getItem(int position) {
        if (selectedIndex < 0 || position < selectedIndex) {
            return baseAdapter.getItem(position);
        } else {
            return baseAdapter.getItem(position + 1);
        }
    }

    @Override
    public Object getItemInDataset(int position) {
        return baseAdapter.getItem(position);
    }
}
