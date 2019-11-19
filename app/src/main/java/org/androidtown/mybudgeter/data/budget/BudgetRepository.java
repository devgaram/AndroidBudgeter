package org.androidtown.mybudgeter.data.budget;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.Database;

import java.util.List;

public class BudgetRepository {
    private BudgetDao budgetDao;
    private LiveData<List<BudgetWithCategory>> allBudgetWithCategory;

    public BudgetRepository(Application application) {
        Database database = Database.getInstance(application);
        budgetDao = database.budgetDao();
        allBudgetWithCategory = budgetDao.getAllBudgetWithCategory();
    }

    public void insert(BudgetEntity budgetEntity) {
        new InsertBudgetAsyncTask(budgetDao).execute(budgetEntity);
    }

    public void delete(TfBudget tfBudget) {
        new DeleteBudgetAsyncTask(budgetDao).execute(toEntity(tfBudget));
    }

    public LiveData<List<BudgetWithCategory>> getAllBudgetWithCategory(){ return allBudgetWithCategory; }


    public LiveData<BudgetWithCategory> getBudgetWithCategoryForId(int id) { return budgetDao.getBudgetWithCategoryForId(id); }

    public BudgetEntity toEntity(TfBudget tfBudget) {
        BudgetEntity budgetEntity = new BudgetEntity();
        budgetEntity.setId(tfBudget.getId());
        budgetEntity.setAmount(tfBudget.getAmount());
        budgetEntity.setStartDate(tfBudget.getStartDate());
        budgetEntity.setEndDate(tfBudget.getEndDate());
        budgetEntity.setCategoryId(tfBudget.getCategoryId());
        budgetEntity.setPeriod(tfBudget.getPeriod());
        return budgetEntity;
    }


    private static class DeleteBudgetAsyncTask extends AsyncTask<BudgetEntity, Void, Void> {
        private BudgetDao budgetDao;

        public DeleteBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(BudgetEntity... budgetEntities) {
            budgetDao.delete(budgetEntities[0]);
            return null;
        }
    }

    private static class InsertBudgetAsyncTask extends AsyncTask<BudgetEntity, Void, Void> {
        private BudgetDao budgetDao;

        public InsertBudgetAsyncTask(BudgetDao budgetDao) {
            this.budgetDao = budgetDao;
        }

        @Override
        protected Void doInBackground(BudgetEntity... budgetEntities) {
            budgetDao.insertAll(budgetEntities);
            return null;
        }
    }
}
