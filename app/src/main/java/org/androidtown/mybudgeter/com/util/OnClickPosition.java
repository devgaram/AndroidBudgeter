package org.androidtown.mybudgeter.com.util;

import android.view.View;

import org.androidtown.mybudgeter.data.category.CategoryEntity;

import java.util.Date;

public interface OnClickPosition {
    void getPosition(View v, Date date);
    void getEntity(CategoryEntity categoryEntity);
}
