package org.androidtown.mybudgeter.view.addexpenditure.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureRepository;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureStatics;

import java.util.List;

public class ExpenditureViewModel extends AndroidViewModel {
    private ExpenditureRepository expenditureRepository;
    private LiveData<List<ExpenditureEntity>> allExpenditures ;

    public ExpenditureViewModel(@NonNull Application application) {
        super(application);
        expenditureRepository = new ExpenditureRepository(application);
    }

    public LiveData<List<ExpenditureEntity>> getExpenditureForBudget(int budgetId){
        allExpenditures = expenditureRepository.getExpenditureForBudget(budgetId);
        return allExpenditures;
    }

    public void insert(ExpenditureEntity expenditureEntity) {
        expenditureRepository.insert(expenditureEntity);
    }

    public long getExpenditureSumForBudget(int budgetId) {
        return expenditureRepository.getExpenditureSumForBudget(budgetId);
    }

    public LiveData<List<ExpenditureStatics>> getExpenditureStaticsForBudget(int budgetId) {
        return expenditureRepository.getExpenditureStaticsForBudget(budgetId);
    }
}
