package org.androidtown.mybudgeter.data.expenditure;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.Database;

import java.util.List;

public class ExpenditureRepository {
    private ExpenditureDao expenditureDao;
    private LiveData<List<ExpenditureEntity>> allExpenditures;

    public ExpenditureRepository(Application application) {
        Database database = Database.getInstance(application);
        expenditureDao = database.expenditureDao();
    }

    public LiveData<List<ExpenditureEntity>> getExpenditureForBudget(int BudgetId){
        allExpenditures = expenditureDao.getExpenditureForBudget(BudgetId);
        return allExpenditures;
    }

    public void insert(ExpenditureEntity expenditureEntity) {
        new InsertExpenditureAsyncTask(expenditureDao).execute(expenditureEntity);
    }

    public long getExpenditureSumForBudget(int budgetId) {
        return expenditureDao.getExpenditureSumForBudget(budgetId);
    }

    public LiveData<List<ExpenditureStatics>> getExpenditureStaticsForBudget(int budgetId) {
        return expenditureDao.getExpenditureStaticsForBudget(budgetId);
    }

    private static class InsertExpenditureAsyncTask extends AsyncTask<ExpenditureEntity, Void, Void> {
        private ExpenditureDao expenditureDao;

        public InsertExpenditureAsyncTask(ExpenditureDao expenditureDao) {
            this.expenditureDao = expenditureDao;
        }

        @Override
        protected Void doInBackground(ExpenditureEntity... expenditureEntities) {
            expenditureDao.insertAll(expenditureEntities);
            return null;
        }
    }
}
