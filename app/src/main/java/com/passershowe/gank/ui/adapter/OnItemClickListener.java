package com.passershowe.gank.ui.adapter;

import android.view.View;

/**
 * Created by hao809395357 on 2016/10/13.
 */

public interface OnItemClickListener<T> {
    void OnItemClick(View convertView, int position, T t);
}
