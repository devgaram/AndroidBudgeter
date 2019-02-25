package org.androidtown.mybudgeter;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {

    private BudgetRepository repository;
    private LiveData<List<Budget>> allBudgets;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        repository = new BudgetRepository(application);
        allBudgets = repository.getAllBudgets();
    }

    public LiveData<List<Budget>> getAllBudgets() {
        return allBudgets;
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
