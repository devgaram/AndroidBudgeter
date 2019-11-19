package org.androidtown.mybudgeter.view.addexpenditure.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureRepository;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureStatics;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureWithBudget;

import java.util.List;

public class ExpenditureViewModel extends AndroidViewModel {
    private ExpenditureRepository expenditureRepository;
    private LiveData<List<ExpenditureWithBudget>> allExpenditures ;

    public ExpenditureViewModel(@NonNull Application application) {
        super(application);
        expenditureRepository = new ExpenditureRepository(application);
    }

    public LiveData<List<ExpenditureWithBudget>> getExpenditureForBudget(int budgetId){
        allExpenditures = expenditureRepository.getExpenditureForBudget(budgetId);
        return allExpenditures;
    }

    public void insert(ExpenditureEntity expenditureEntity) {
        expenditureRepository.insert(expenditureEntity);
    }

    public void update(ExpenditureEntity expenditureEntity) {
        expenditureRepository.update(expenditureEntity);
    }

    public void delete(ExpenditureEntity expenditureEntity) {
        expenditureRepository.delete(expenditureEntity);
    }

    public long getExpenditureSumForBudget(int budgetId) {
        return expenditureRepository.getExpenditureSumForBudget(budgetId);
    }

    public LiveData<ExpenditureEntity> getExpenditureForExpenditureId(int expenditureId) {
        return expenditureRepository.getExpenditureForExpenditureId(expenditureId);
    }

    public LiveData<List<ExpenditureStatics>> getExpenditureStaticsForBudget(int budgetId) {
        return expenditureRepository.getExpenditureStaticsForBudget(budgetId);
    }
}
