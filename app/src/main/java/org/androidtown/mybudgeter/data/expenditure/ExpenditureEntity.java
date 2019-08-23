package org.androidtown.mybudgeter.data.expenditure;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import org.androidtown.mybudgeter.com.util.DateConverter;

import java.util.Date;

@Entity(tableName = "expenditure_table")
@TypeConverters(DateConverter.class)
public class ExpenditureEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private long amount;
    private Date date;
    private String memo;
    private int budetId;

    @Ignore
    public ExpenditureEntity() {
    }

    public ExpenditureEntity(int id, long amount, Date date, String memo, int budetId) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.memo = memo;
        this.budetId = budetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getBudetId() {
        return budetId;
    }

    public void setBudetId(int budetId) {
        this.budetId = budetId;
    }
}
