package org.androidtown.mybudgeter.budget;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.BudgeterDatabase;

import java.util.List;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<List<Budget>> allBudgets;
    private LiveData<Budget> budget;

    public BudgetRepository(Application application) {
        BudgeterDatabase database = BudgeterDatabase.getInstance(application);
        budgetDao = database.budgetDao();
        allBudgets = budgetDao.getAllbudgets();
    }

    public void insert(Budget budget) {
        new InsertBudgetAsyncTask(budgetDao).execute(budget);
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
    }
    public LiveData<Budget> getBudgetById(int budgetId) {
        budget = budgetDao.getBudgetForId(budgetId);
        return budget;
    }

    private static class InsertBudgetAsyncTask extends AsyncTask<Budget, Void, Void> {
        private BudgetDao budgetDao;

        public InsertBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(Budget... budgets) {
            budgetDao.insert(budgets[0]);
            return null;
        }
    }
}
