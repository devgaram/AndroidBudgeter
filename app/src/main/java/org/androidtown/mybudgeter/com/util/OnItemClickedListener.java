package org.androidtown.mybudgeter.com.util;

import android.view.View;

public interface OnItemClickedListener {
    void onItemClicked(View view, int pos, int viewType);
    void onItemLongClicked(View view, int pos, int viewType);
}
