package org.androidtown.mybudgeter.data.budget;

import android.arch.persistence.room.Embedded;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.data.category.CategoryEntity;

public class BudgetWithCategory {
    @Embedded
    private BudgetEntity budget;

    @NonNull
    @Embedded(prefix = "category_")
    private CategoryEntity category;

    private long remainAmount;

    public BudgetWithCategory(BudgetEntity budget) {
        this.budget = budget;
    }

    public BudgetEntity getBudget() {
        return budget;
    }

    public void setBudget(BudgetEntity budget) {
        this.budget = budget;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public long getRemainAmount() {
        return remainAmount;
    }

    public void setRemainAmount(long remainAmount) {
        this.remainAmount = remainAmount;
    }
}
