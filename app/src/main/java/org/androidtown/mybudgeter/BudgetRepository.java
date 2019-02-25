package org.androidtown.mybudgeter;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<List<Budget>> allBudgets;

    public BudgetRepository(Application application) {
        BudgetDatabase database = BudgetDatabase.getInstance(application);
        budgetDao = database.budgetDao();
        allBudgets = budgetDao.getAllbudgets();
    }

    public void insert(Budget budget) {
        new InsertBudgetAsyncTask(budgetDao).execute(budget);
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
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
