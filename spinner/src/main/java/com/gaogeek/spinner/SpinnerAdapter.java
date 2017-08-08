package com.gaogeek.spinner;

/**
 * Created by gaogeek on 2017/8/8.
 */

import android.content.Context;

import java.util.List;

/*
 * Copyright (C) 2015 Angelo Marchesin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
