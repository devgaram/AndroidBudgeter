package org.androidtown.mybudgeter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;


public class CalendarViewAdapter extends RecyclerView.Adapter<CalendarViewAdapter.CalendarHolder> implements OnItemClickedListener {
    private ArrayList<Date> days;
    private Calendar currentDate;
    private Calendar actualCalendar = Calendar.getInstance();
    private RecyclerView mRecyclerView;
    private OnClickPosition mPositionListener;
    private HashMap<String, Date> rangeDate;
    private CalendarCompare calenderCompare;

    public CalendarViewAdapter(ArrayList<Date> days, HashSet<Date> eventDay, Calendar currentDate, OnClickPosition mPositionListener, HashMap<String, Date> rangeDate) {
        this.days = days;
        this.currentDate = currentDate;
        this.mPositionListener = mPositionListener;
        this.rangeDate = rangeDate;
        this.calenderCompare = new CalendarCompare();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.calendar_day, viewGroup, false);
        return new CalendarHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder calendarHolder, int i) {
        Date date = days.get(i);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        ((TextView) calendarHolder.dayView).setTypeface(null, Typeface.NORMAL);
        ((TextView) calendarHolder.dayView).setTextColor(Color.BLACK);
        ((TextView) calendarHolder.dayView).setText(String.valueOf(calendar.get(Calendar.DATE)));

        // 범위 설정
        Date startDate = rangeDate.get("startDate");
        Date endDate = rangeDate.get("endDate");

        // 오늘 날짜 표시
        if (calenderCompare.dayDateToCalendar(date, actualCalendar) == 0) {
            ((TextView) calendarHolder.dayView).setTextColor(Color.BLACK);
            calendarHolder.dayView.setBackground(calendarHolder.itemView.getContext().getResources().getDrawable(R.drawable.calendar_today));
        }
        // 선택한 날짜 범위 표시
        if (startDate != null && endDate == null && calenderCompare.dayDateToDate(startDate, date) == 0) {
            calendarHolder.dayView.setBackground(calendarHolder.itemView.getContext().getResources().getDrawable(R.drawable.calendar_pick));
            ((TextView) calendarHolder.dayView).setTextColor(Color.WHITE);
        } else if (startDate != null && endDate != null) {
            if (calenderCompare.dayDateToDate(date, startDate) > 0 && calenderCompare.dayDateToDate(date, endDate) < 0) {
                calendarHolder.dayView.setBackground(calendarHolder.itemView.getContext().getResources().getDrawable(R.drawable.calendar_range));
                ((TextView) calendarHolder.dayView).setTextColor(Color.WHITE);
            } else if (calenderCompare.dayDateToDate(date, startDate) == 0) {
                calendarHolder.dayView.setBackground(calendarHolder.itemView.getContext().getResources().getDrawable(R.drawable.calendar_range_start));
                ((TextView) calendarHolder.dayView).setTextColor(Color.WHITE);
            } else if (calenderCompare.dayDateToDate(date, endDate) == 0) {
                calendarHolder.dayView.setBackground(calendarHolder.itemView.getContext().getResources().getDrawable(R.drawable.calendar_range_end));
                ((TextView) calendarHolder.dayView).setTextColor(Color.WHITE);
            }
        }
        // 오늘 날짜 이전, 해당 달의 일이 아닐 경우
        if ((month != currentDate.get(Calendar.MONTH) || year != currentDate.get(Calendar.YEAR)) || calenderCompare.dayDateToCalendar(date, actualCalendar) < 0) {
            ((TextView) calendarHolder.dayView).setTextColor(Color.parseColor(("#E0E0E0")));
            ((TextView) calendarHolder.dayView).setBackgroundColor(Color.WHITE);
        }


    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    @Override
    public void onIteClicked(View view, int pos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(days.get(pos));
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        if (calenderCompare.dayDateToCalendar(days.get(pos), actualCalendar) >= 0) {
            if ((month == currentDate.get(Calendar.MONTH) && year == currentDate.get(Calendar.YEAR))) {
                if (mPositionListener != null)
                    mPositionListener.getPosition(view, days.get(pos));
            }
        }
    }

    public static class CalendarHolder extends RecyclerView.ViewHolder {
        public View itemView;
        public TextView dayView;
        public Date date;

        public CalendarHolder(@NonNull View itemView, final OnItemClickedListener mListener) {
            super(itemView);
            this.itemView = (View) itemView;
            this.dayView = (TextView) itemView.findViewById(R.id.calendar_day);
            this.dayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onIteClicked(v, getAdapterPosition());
                }
            });
        }

    }



}


