package org.androidtown.mybudgeter.budget;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.expenditure.Expenditure;
import org.androidtown.mybudgeter.expenditure.ExpenditureRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

    private BudgetRepository budgetRepository;
    private ExpenditureRepository expenditureRepository;
    private LiveData<List<Budget>> allBudgets;
    private LiveData<Budget> budget;
    private LiveData<List<Expenditure>> expendituresForBudget;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        budgetRepository = new BudgetRepository(application);
        expenditureRepository = new ExpenditureRepository(application);
        allBudgets = budgetRepository.getAllBudgets();
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
    }
    public LiveData<Budget> getBudgetById(int budgetId) {
        budget = budgetRepository.getBudgetById(budgetId);
        return budget;
    }
    public LiveData<List<Expenditure>> getExpendituresForBudget(int budgetId) {
        expendituresForBudget = expenditureRepository.getExpendituresForBudget(budgetId);
        return expendituresForBudget;
    }

    public long getRemainPeriod(String strEndDate) {
        long calDays = 0;
        Date endDate;
        Date todayDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        try {
            endDate = format.parse(strEndDate);
            long calDate = endDate.getTime() - todayDate.getTime();
            calDays = calDate / (24*60*60*1000);
            calDays = Math.abs(calDays);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }

        return calDays;
    }
}