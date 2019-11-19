package org.androidtown.mybudgeter.com.util;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.text.DecimalFormat;

public class MoneyEditView extends AppCompatEditText {
    private long originalAmount;
    private DecimalFormat decimalFormat;

    public MoneyEditView(Context context) {
        super(context);
        init();
    }

    public MoneyEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoneyEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
       addTextChangedListener(new TextWatcher() {
           private final int MAX_COUNT = 10;
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               removeTextChangedListener(this);
               if (decimalFormat == null) decimalFormat = new DecimalFormat("#,###");
               String result = s.toString().replaceAll(",", "").replace("원", "");
               if (result.length() > MAX_COUNT) {
                   result = result.substring(0, result.length()-1);
               }

               if (result.length() > 0) {
                   originalAmount = Long.parseLong(result);
                   result = decimalFormat.format(Long.parseLong(result)) + "원";
               } else {
                   originalAmount = 0;
                   result = "0원";
               }
               setText(result);
               setSelection(result.length()-1);
               addTextChangedListener(this);
           }
       });
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);

    }

    public long getOriginalAmount() {
        return this.originalAmount;
    }
}
