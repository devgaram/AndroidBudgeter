package org.androidtown.mybudgeter.com.util;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.DecimalFormat;

public class StaticsValueFormatter extends ValueFormatter {

    private final DecimalFormat format;
    private String suffix;

    public StaticsValueFormatter(String suffix) {
        format = new DecimalFormat("###,###,###,##0");
        this.suffix = suffix;
    }

    @Override
    public String getFormattedValue(float value) {
        return format.format(value) + suffix;
    }

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        if (axis instanceof XAxis) {
            int month = (int) value / 100;
            int day = (int) value % 100;
            return month + "/" + day;
        } else if (value > 0) {
            return format.format(value) + suffix;
        } else {
            return format.format(value);
        }
    }
}
