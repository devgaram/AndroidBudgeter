package org.androidtown.mybudgeter.com.util;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import java.text.DecimalFormat;

public class MoneyFormatView extends AppCompatTextView {

    public MoneyFormatView(Context context) {
        super(context);
    }

    public MoneyFormatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoneyFormatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        super.setText(decimalFormat.format(Integer.parseInt(text.toString())) + "원", type);
    }
}
