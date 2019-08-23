package org.androidtown.mybudgeter.view.main.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import org.androidtown.mybudgeter.data.budget.BudgetEntity;
import org.androidtown.mybudgeter.data.budget.BudgetRepository;
import org.androidtown.mybudgeter.data.budget.BudgetWithCategory;
import org.androidtown.mybudgeter.data.budget.TfBudget;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BudgetViewModel extends AndroidViewModel {
    private BudgetRepository budgetRepository;
    private LiveData<List<BudgetWithCategory>> allBudgetWithCategory ;
    private LiveData<List<TfBudget>> tfAllBudgets;

    public BudgetViewModel(@NonNull Application application) {
        super(application);
        budgetRepository = new BudgetRepository(application);
        allBudgetWithCategory = budgetRepository.getAllBudgetWithCategory();
        tfAllBudgets = Transformations.map(allBudgetWithCategory, new Function<List<BudgetWithCategory>, List<TfBudget>>() {
            @Override
            public List<TfBudget> apply(List<BudgetWithCategory> input) {
                if (!input.isEmpty()) {
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    List<TfBudget> tfBudgets = new ArrayList<>();
                    Calendar cal = Calendar.getInstance();
                    Date today = cal.getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM월dd일");
                    long diff;
                    for(BudgetWithCategory budget : input) {
                        TfBudget tfBudget = new TfBudget();
                        tfBudget.setId(budget.getBudget().getId());
                        tfBudget.setCategoryId(budget.getCategory().getId());
                        tfBudget.setCategoryName(budget.getCategory().getName());
                        tfBudget.setAmount(budget.getBudget().getAmount());
                        tfBudget.setFmtAmount(decimalFormat.format(budget.getBudget().getAmount()));
                        tfBudget.setStartDate(budget.getBudget().getStartDate());
                        tfBudget.setEndDate(budget.getBudget().getEndDate());
                        tfBudget.setPeriod(budget.getBudget().getPeriod());
                        tfBudget.setRemainAmount(budget.getBudget().getAmount()-budget.getRemainAmount());
                        tfBudget.setFmtRemainAmount(decimalFormat.format(budget.getBudget().getAmount()-budget.getRemainAmount()));
                        diff = budget.getBudget().getEndDate().getTime() - today.getTime();
                        tfBudget.setRemainDate((diff / (24 * 60 * 60 * 1000)) + 1);
                        tfBudget.setFmtStartDate(simpleDateFormat.format(budget.getBudget().getStartDate()));
                        tfBudget.setFmtEndDate(simpleDateFormat.format(budget.getBudget().getEndDate()));
                        tfBudgets.add(tfBudget);
                    }
                    return tfBudgets;
                }
                return null;
            }
        });


    }

    public LiveData<List<BudgetWithCategory>> getAllBudgetWithCategory() {
        return allBudgetWithCategory;
    }

    public LiveData<BudgetWithCategory> getBudgetWithCategoryForId(int id) {
        return budgetRepository.getBudgetWithCategoryForId(id);
    }

    public LiveData<List<TfBudget>> getAllTfBudgetWithCategory() {
        return tfAllBudgets;
    }

    public void insert(BudgetEntity budgetEntity) {
        budgetRepository.insert(budgetEntity);
    }

    public void delete(TfBudget tfBudget) {budgetRepository.delete(tfBudget);}

}