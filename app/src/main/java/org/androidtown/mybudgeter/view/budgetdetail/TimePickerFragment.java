package org.androidtown.mybudgeter.view.budgetdetail;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import org.androidtown.mybudgeter.com.util.OnDateTimePickerListener;
import org.joda.time.DateTime;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private DateTime dateTime;
    private OnDateTimePickerListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        int hour, minute;
        if (dateTime == null) dateTime = DateTime.now();

        hour = dateTime.hourOfDay().get();
        minute = dateTime.minuteOfHour().get();

        return new TimePickerDialog(getActivity(), this, hour, minute, false);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (mListener != null)
            mListener.onDateTimePicker(OnDateTimePickerListener.TYPE_TIME, dateTime.withTime(hourOfDay, minute, 0, 0));
    }



    public void setTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void setListener(OnDateTimePickerListener mListener) {this.mListener = mListener;}
}
