package org.androidtown.mybudgeter;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "expenditure_table",
        foreignKeys =
        @ForeignKey(entity = Budget.class,
                    parentColumns = "id",
                    childColumns = "budgetId"
        ))
public class Expenditure {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int budgetId;
    private String gubun;
    private String title;
    private int amount;
    private String date;

    public Expenditure(int budgetId, String gubun, String title, int amount, String date) {
        this.budgetId = budgetId;
        this.gubun = gubun;
        this.title = title;
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

    public String getGubun() {
        return gubun;
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
