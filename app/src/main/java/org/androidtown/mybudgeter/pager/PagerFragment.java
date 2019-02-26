package org.androidtown.mybudgeter.pager;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.budget.Budget;
import org.androidtown.mybudgeter.budget.BudgetViewModel;

import java.text.DecimalFormat;


public class PagerFragment extends Fragment {
    private BudgetViewModel budgetViewModel;
    private TextView mAllBudgetAmountTextView;
    private TextView mAllBudgetRemainDateTextView;
    private DecimalFormat decimalFormat;
    private int budgetId;

    public static PagerFragment newInstance() {
        return new PagerFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_fragment, container, false);
        if (getArguments() != null) {
            budgetId = getArguments().getInt("budgetId");
            mAllBudgetAmountTextView = (TextView) view.findViewById(R.id.all_budget_amount);
            mAllBudgetRemainDateTextView = (TextView) view.findViewById(R.id.all_budget_remain_date);
            decimalFormat = new DecimalFormat();
            decimalFormat.applyPattern("#,#00");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        // TODO: Use the ViewModel
        budgetViewModel.getBudgetById(budgetId).observe(this, new Observer<Budget>() {
            @Override
            public void onChanged(@Nullable Budget budget) {
                if (budget != null) {
                    String allAmount = decimalFormat.format(budget.getAmount());
                    long remianPeriod = budgetViewModel.getRemainPeriod(budget.getEndDate());
                    mAllBudgetAmountTextView.setText(allAmount);
                    mAllBudgetRemainDateTextView.setText(String.valueOf(remianPeriod));
                }
            }
        });
    }
}
