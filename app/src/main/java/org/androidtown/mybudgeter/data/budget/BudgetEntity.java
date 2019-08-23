package org.androidtown.mybudgeter.data.budget;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import org.androidtown.mybudgeter.data.category.CategoryEntity;
import org.androidtown.mybudgeter.com.util.DateConverter;

import java.util.Date;

@Entity(tableName = "budget_table",
        foreignKeys = @ForeignKey(entity = CategoryEntity.class,
        parentColumns = "id", childColumns = "categoryId"))
@TypeConverters(DateConverter.class)
public class BudgetEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int categoryId;
    private long amount;
    private Date startDate;
    private Date endDate;
    private long period;

    @Ignore
    public BudgetEntity() {
    }

    public BudgetEntity(int id, int categoryId, long amount, Date startDate, Date endDate, long period) {
        this.id = id;
        this.categoryId = categoryId;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }
}
