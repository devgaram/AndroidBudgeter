package org.androidtown.mybudgeter.view.addbudget.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.androidtown.mybudgeter.data.budget.BudgetEntity;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<BudgetEntity> addedBudget = new MutableLiveData<BudgetEntity>();

    public void addBudget(BudgetEntity budgetEntity) {
        addedBudget.setValue(budgetEntity);
    }
    public LiveData<BudgetEntity> getAddedBudget() {
        return addedBudget;
    }

}
