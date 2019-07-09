package org.androidtown.mybudgeter.budgetAdd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mybudgeter.CalendarCompare;
import org.androidtown.mybudgeter.CalendarView;
import org.androidtown.mybudgeter.OnClickPosition;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.CalendarHolder> implements OnClickPosition{
    private List<Calendar> calendars;
    private HashMap<String, Date> rangeDate;
    private CalendarCompare calendarCompare;


    public CalendarRecyclerAdapter(List<Calendar> calendars) {
        this.calendars = new ArrayList<Calendar>(calendars);
        this.rangeDate = new HashMap<>();
        this.calendarCompare = new CalendarCompare();
    }

    @NonNull
    @Override
    public CalendarRecyclerAdapter.CalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
          CalendarView itemView = new CalendarView(viewGroup.getContext(), null);
          itemView.setLayoutParams(new ViewGroup.LayoutParams(
                  ViewGroup.LayoutParams.MATCH_PARENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT));
          return new CalendarHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarRecyclerAdapter.CalendarHolder viewHolder, int i) {
        ((CalendarHolder)viewHolder).getCalendarView().updateCalendar(null, calendars.get(i), viewHolder.mListener, rangeDate);
        ((CalendarHolder)viewHolder).getCalendarView().setDisplayDate(calendars.get(i));
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    @Override
    public void getPosition(View v, Date date) {
        Date startDate = rangeDate.get("startDate");
        Date endDate = rangeDate.get("endDate");

        if (startDate == null && endDate == null) {
            rangeDate.put("startDate", date);
        } else if (startDate != null && endDate == null && calendarCompare.dayDateToDate(startDate, date) < 0) {
            rangeDate.put("endDate", date);
        } else if (startDate != null && endDate == null && calendarCompare.dayDateToDate(startDate, date) > 0) {
            rangeDate.put("startDate", date);
        } else if (startDate != null && endDate != null) {
            rangeDate.clear();
            rangeDate.put("startDate", date);
        }

        this.notifyDataSetChanged();
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
        this.notifyDataSetChanged();
    }

    public static class CalendarHolder extends RecyclerView.ViewHolder {
        private CalendarView calendarView;
        final private OnClickPosition mListener;

        public CalendarHolder(@NonNull View itemView, final OnClickPosition mListener) {
            super(itemView);
            calendarView = (CalendarView) itemView;
            this.mListener = mListener;
        }
        public CalendarView getCalendarView() {
            return calendarView;
        }
    }

}
