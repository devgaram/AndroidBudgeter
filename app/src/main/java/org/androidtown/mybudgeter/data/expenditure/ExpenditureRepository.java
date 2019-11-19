package org.androidtown.mybudgeter.data.expenditure;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.androidtown.mybudgeter.com.Database;

import java.util.List;

public class ExpenditureRepository {
    private ExpenditureDao expenditureDao;
    private LiveData<List<ExpenditureWithBudget>> allExpenditures;

    public ExpenditureRepository(Application application) {
        Database database = Database.getInstance(application);
        expenditureDao = database.expenditureDao();
    }

    public LiveData<List<ExpenditureWithBudget>> getExpenditureForBudget(int BudgetId){
        allExpenditures = expenditureDao.getExpenditureForBudget(BudgetId);
        return allExpenditures;
    }

    public void insert(ExpenditureEntity expenditureEntity) {
        new InsertExpenditureAsyncTask(expenditureDao).execute(expenditureEntity);
    }

    public void update(ExpenditureEntity expenditureEntity) {
        new UpdateExpenditureAsyncTask(expenditureDao).execute(expenditureEntity);
    }

    public void delete(ExpenditureEntity expenditureEntity) {
        new DeleteExpenditureAsyncTask(expenditureDao).execute(expenditureEntity);
    }

    public long getExpenditureSumForBudget(int budgetId) {
        return expenditureDao.getExpenditureSumForBudget(budgetId);
    }

    public LiveData<List<ExpenditureStatics>> getExpenditureStaticsForBudget(int budgetId) {
        return expenditureDao.getExpenditureStaticsForBudget(budgetId);
    }

    public LiveData<ExpenditureEntity> getExpenditureForExpenditureId(int expenditureId) {
        return expenditureDao.getExpenditureForExpenditureId(expenditureId);
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

    private static class UpdateExpenditureAsyncTask extends AsyncTask<ExpenditureEntity, Void, Void> {
        private ExpenditureDao expenditureDao;
        public UpdateExpenditureAsyncTask(ExpenditureDao expenditureDao) {
            this.expenditureDao = expenditureDao;
        }

        @Override
        protected Void doInBackground(ExpenditureEntity... expenditureEntities) {
            expenditureDao.update(expenditureEntities[0]);
            return null;
        }
    }

    private static class DeleteExpenditureAsyncTask extends AsyncTask<ExpenditureEntity, Void, Void> {
        private ExpenditureDao expenditureDao;

        public DeleteExpenditureAsyncTask(ExpenditureDao expenditureDao) {
            this.expenditureDao = expenditureDao;
        }
        @Override
        protected Void doInBackground(ExpenditureEntity... expenditureEntities) {
            expenditureDao.delete(expenditureEntities[0]);
            return null;
        }
    }
}
