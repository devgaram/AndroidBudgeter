package org.androidtown.mybudgeter.view.budgetdetail;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.androidtown.mybudgeter.com.util.OnDateTimePickerListener;
import org.joda.time.DateTime;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private DateTime dateTime;
    private OnDateTimePickerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int year, month, day;
        if (dateTime == null) dateTime = DateTime.now();

        year = dateTime.yearOfEra().get();
        month = dateTime.getMonthOfYear() - 1;
        day = dateTime.dayOfMonth().get();

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        if (mListener != null) {
            mListener.onDateTimePicker(OnDateTimePickerListener.TYPE_DATE, dateTime.withDate(year, month + 1, dayOfMonth));
        }
    }

    public void setDate(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void setListener(OnDateTimePickerListener mListener) {this.mListener = mListener;}
}
