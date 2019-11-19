package org.androidtown.mybudgeter.data.expenditure;

import android.arch.persistence.room.Embedded;

public class ExpenditureWithBudget {
    @Embedded
    private ExpenditureEntity expenditureEntity;

    private long budgetAmount;
    private long remainAmount;

    public ExpenditureWithBudget(ExpenditureEntity expenditureEntity) {
        this.expenditureEntity = expenditureEntity;
    }

    public ExpenditureEntity getExpenditureEntity() {
        return expenditureEntity;
    }

    public void setExpenditureEntity(ExpenditureEntity expenditureEntity) {
        this.expenditureEntity = expenditureEntity;
    }

    public long getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(long budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }
}
