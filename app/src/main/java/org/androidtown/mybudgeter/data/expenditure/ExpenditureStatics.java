package org.androidtown.mybudgeter.data.expenditure;

import android.arch.persistence.room.TypeConverters;

import org.androidtown.mybudgeter.com.util.DateConverter;

@TypeConverters(DateConverter.class)
public class ExpenditureStatics {
    private String edate;
    private long daysum;

    public String getEdate() {
        return edate;
    }

    public void setEdate(String edate) {
        this.edate = edate;
    }

    public long getDaysum() {
        return daysum;
    }

    public void setDaysum(long daysum) {
        this.daysum = daysum;
    }
}
