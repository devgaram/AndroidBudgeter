package org.androidtown.mybudgeter.expenditure;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.androidtown.mybudgeter.budget.Budget;

@Entity(tableName = "expenditure_table",
        foreignKeys =
            @ForeignKey(entity = Budget.class,
                        parentColumns = "id",
                        childColumns = "budgetId")
        )
public class Expenditure {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int budgetId;
    private int typeId;
    private String memo;
    private int amount;
    private String date;

    public Expenditure(int budgetId, int typeId, String memo, int amount, String date) {
        this.budgetId = budgetId;
        this.typeId = typeId;
        this.memo = memo;
        this.amount = amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
