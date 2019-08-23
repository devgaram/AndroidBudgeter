package org.androidtown.mybudgeter.com.util;

import java.util.Calendar;
import java.util.Date;

public class CalendarCompare {
    private Calendar cal1;
    private Calendar cal2;
    private int CAL_DAY = (24*60*60*1000);
    public CalendarCompare() {
        cal1 = Calendar.getInstance();
        cal2 = Calendar.getInstance();
    }

    public int dayDateToDate(Date date1, Date date2) {
        cal1.setTime(date1);
        cal2.setTime(date2);
        long calTime1 = cal1.getTimeInMillis()/CAL_DAY;
        long calTime2 = cal2.getTimeInMillis()/CAL_DAY;

        if (calTime1 == calTime2) return 0;
        else if (calTime1 < calTime2) return -1;
        else return 1;
    }

    public int dayDateToCalendar(Date date1, Calendar calendar2) {
        cal1.setTime(date1);

        long calTime1 = cal1.getTimeInMillis()/CAL_DAY;
        long calTime2 = calendar2.getTimeInMillis()/CAL_DAY;

        if (calTime1 == calTime2) return 0;
        else if (calTime1 < calTime2) return -1;
        else return 1;
    }

    public int dayCalendarToCalendar(Calendar calendar1, Calendar calendar2) {
        long calTime1 = calendar1.getTimeInMillis()/CAL_DAY;
        long calTime2 = calendar2.getTimeInMillis()/CAL_DAY;

        if (calTime1 == calTime2) return 0;
        else if (calTime1 < calTime2) return -1;
        else return 1;
    }

    public Calendar getCalToday() {
        return Calendar.getInstance();
    }




}
