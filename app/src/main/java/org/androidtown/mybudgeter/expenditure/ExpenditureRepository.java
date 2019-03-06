package org.androidtown.mybudgeter.expenditure;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import org.androidtown.mybudgeter.com.BudgeterDatabase;

import java.util.List;

public class ExpenditureRepository {
    private ExpenditureDao expenditureDao;
    private LiveData<List<Expenditure>> allExpenditures;

    public ExpenditureRepository(Application application) {
        BudgeterDatabase database = BudgeterDatabase.getInstance(application);
        expenditureDao = database.expenditureDao();
    }

    public LiveData<List<Expenditure>> getExpendituresForBudget(int budgetId) {
        allExpenditures = expenditureDao.getExpendituresForBudget(1);
        return allExpenditures;
    }

}
