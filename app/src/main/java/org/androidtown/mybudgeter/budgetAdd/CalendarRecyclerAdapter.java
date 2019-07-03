package org.androidtown.mybudgeter.budgetAdd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mybudgeter.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarRecyclerAdapter extends RecyclerView.Adapter<CalendarRecyclerAdapter.CalendarHolder> {
    private List<Calendar> calendars;

    public CalendarRecyclerAdapter(List<Calendar> calendars) {
        this.calendars = new ArrayList<Calendar>(calendars);
    }

    @NonNull
    @Override
    public CalendarRecyclerAdapter.CalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CalendarView itemView = new CalendarView(viewGroup.getContext(), null);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new CalendarHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder viewHolder, int i) {
        viewHolder.getCalendarView().updateCalendar(null, calendars.get(i));
        viewHolder.getCalendarView().setDisplayDate(calendars.get(i));

    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    public static class CalendarHolder extends RecyclerView.ViewHolder {
        private CalendarView calendarView;

        public CalendarHolder(@NonNull View itemView) {
            super(itemView);
            calendarView = (CalendarView) itemView;
        }

        public CalendarView getCalendarView() {
            return calendarView;
        }
    }

}
