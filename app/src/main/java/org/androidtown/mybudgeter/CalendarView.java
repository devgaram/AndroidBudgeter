package org.androidtown.mybudgeter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarView extends LinearLayout {
    private LinearLayout header;
    private GridView gridView;
    private TextView dateYear;
    private TextView dateMonth;
    private static final int DAYS_COUNT = 42; // 캘린더에 표시될 최대 가질 수 있는 날짜 수
    private CalendarAdapter calendarAdapter;
    private Calendar currentDate = Calendar.getInstance();

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);

    }



    private void assignUiElements() {
        header = findViewById(R.id.calendar_header);
        gridView = findViewById(R.id.calendar_grid);
        dateYear = (TextView) findViewById(R.id.date_display_year);
        dateMonth = (TextView) findViewById(R.id.date_display_date);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_layout, this);
        assignUiElements();
        updateCalendar(null, Calendar.getInstance());
    }

    public void setDisplayDate(Calendar currentDate) {
        dateYear.setText(Integer.toString(currentDate.get(Calendar.YEAR)));
        dateMonth.setText(Integer.toString(currentDate.get(Calendar.MONTH)+1));
    }

    public void updateCalendar(HashSet<Date> events, Calendar currentDate) {

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();

        // static int DAY_OF_MONTH : 이번 달 중 날짜가 몇번째 날인지(DATE와 같음)
        // static int DAY_OF_WEEK : 이번 주에서 날짜가 몇번째 날인지, 현재 날짜의 요일(일요일 1, 토요일 7)
        // static int DAY_OF_YEAR : 1년 중 현재 날짜가 몇번째 날인지

        // 날짜를 현재 달의 1일로 설정한다.
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        // 현재 날짜의 요일을 얻는다. 즉 현재 달 1일의 요일을 얻는다
        // -1를 함으로써 일요일이 0이 되도록 한다.
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        // 캘린더 셀을 시작부터 채우기 위해서 설정하는 것이다.
        // 예) 1일이 일요일일 경우 monthBeginningCell은 0으로,
        // DAY_OF_MONTH 즉, 현재 날짜는 변함없다.
        // 1일이 월요일인 경우 monthBeginningCell은 -1이므로,
        // 현재 날짜는 이전 달 말일이 된다. 달력의 시작점이 이전 달 말일이 월요일이란 뜻이다.
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // 시작부터 셀 채우기
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendarAdapter = new CalendarAdapter(getContext(), cells, events, currentDate);
        gridView.setAdapter(calendarAdapter);

        //SimpleDateFormat sdf = new SimpleDateFormat("EEEE,d MMM,yyyy");
        //String[] dateToday = sdf.format(currentDate.getTime()).split(",");
    }


    private class CalendarAdapter extends ArrayAdapter<Date> {
        private LayoutInflater inflater;
        private HashSet<Date> eventDays;
        private Calendar currentDate;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDay, Calendar currentDate) {
            super(context, R.layout.calendar_layout, days);
            this.eventDays = eventDays;
            this.currentDate = currentDate;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            Calendar calendar = Calendar.getInstance();

            Date date = getItem(position);
            calendar.setTime(date);
            int day = calendar.get(Calendar.DATE);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            Calendar actualCalendar = Calendar.getInstance();


            if (view == null)
                view = inflater.inflate(R.layout.calendar_day, parent, false);
            ((TextView) view).setTypeface(null, Typeface.NORMAL);
            ((TextView) view).setTextColor(Color.BLACK);

            if (month != currentDate.get(Calendar.MONTH) || year != currentDate.get(Calendar.YEAR)) {
                ((TextView) view).setTextColor(Color.parseColor(("#E0E0E0")));
            } else if (day == actualCalendar.get(Calendar.DATE) && month == actualCalendar.get(Calendar.MONTH) && year == actualCalendar.get(Calendar.YEAR)) {
                ((TextView) view).setTextColor(Color.WHITE);
                ((TextView) view).setGravity(Gravity.CENTER);
                view.setBackground(getResources().getDrawable(R.drawable.calendar_pick));
            }

            ((TextView) view).setText(String.valueOf(calendar.get(Calendar.DATE)));

            return view;
        }
    }




}
