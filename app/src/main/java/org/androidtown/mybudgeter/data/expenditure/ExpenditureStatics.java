package org.androidtown.mybudgeter.data.expenditure;

import android.arch.persistence.room.TypeConverters;

import org.androidtown.mybudgeter.com.util.DateConverter;

import java.util.Date;

@TypeConverters(DateConverter.class)
public class ExpenditureStatics {
    private Date edate;
    private long daysum;

    public Date getEdate() {
        return edate;
    }

    public void setEdate(Date edate) {
        this.edate = edate;
    }

    public long getDaysum() {
        return daysum;
    }

    public void setDaysum(long daysum) {
        this.daysum = daysum;
    }
}
