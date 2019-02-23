package org.androidtown.mybudgeter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "budget_table")
public class Budget {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int title;
    private int amount;
    private String startDate;
    private String endDate;
    private int period;

    public Budget(int title, int amount, String startDate, String endDate, int period) {
        this.title = title;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
