package org.androidtown.mybudgeter.com.util;

import org.joda.time.DateTime;

public interface OnDateTimePickerListener {
    public static final int TYPE_DATE = 1;
    public static final int TYPE_TIME = 2;
    void onDateTimePicker(int type, DateTime dt);
}
