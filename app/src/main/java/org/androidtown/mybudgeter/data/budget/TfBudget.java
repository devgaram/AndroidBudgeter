package org.androidtown.mybudgeter.data.budget;

import java.util.Date;

public class TfBudget {
    private int id;
    private int categoryId;
    private String categoryName;
    private long amount;
    private Date startDate;
    private Date endDate;
    private long period;
    private long remainAmount;
    private long remainDate;
    private String fmtStartDate;
    private String fmtEndDate;
    private String fmtAmount;
    private String fmtRemainAmount;

    public TfBudget() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }

    public long getRemainDate() {
        return remainDate;
    }

    public void setRemainDate(long remainDate) {
        this.remainDate = remainDate;
    }

    public String getFmtStartDate() {
        return fmtStartDate;
    }

    public void setFmtStartDate(String fmtStartDate) {
        this.fmtStartDate = fmtStartDate;
    }

    public String getFmtEndDate() {
        return fmtEndDate;
    }

    public void setFmtEndDate(String fmtEndDate) {
        this.fmtEndDate = fmtEndDate;
    }

    public String getFmtAmount() {
        return fmtAmount;
    }

    public void setFmtAmount(String fmtAmount) {
        this.fmtAmount = fmtAmount;
    }

    public String getFmtRemainAmount() {
        return fmtRemainAmount;
    }

    public void setFmtRemainAmount(String fmtRemainAmount) {
        this.fmtRemainAmount = fmtRemainAmount;
    }
}
