package org.androidtown.mybudgeter;

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

import java.text.DecimalFormat;
import java.util.List;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    private TextView mAllBudgetAmountTextView;
    private TextView mAllBudgetRemainDateTextView;
    private DecimalFormat decimalFormat;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.budget_fragment, container, false);
        mAllBudgetAmountTextView = (TextView) view.findViewById(R.id.all_budget_amount);
        mAllBudgetRemainDateTextView = (TextView) view.findViewById(R.id.all_budget_remain_date);
        decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#,#00");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllBudgets().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(@Nullable List<Budget> allBudgets) {
                if (!allBudgets.isEmpty()) {
                    String allAmount = decimalFormat.format(allBudgets.get(0).getAmount());
                    long remianPeriod = mViewModel.getRemainPeriod(allBudgets.get(0).getEndDate());
                    mAllBudgetAmountTextView.setText(allAmount);
                    mAllBudgetRemainDateTextView.setText(String.valueOf(remianPeriod));
                }
            }
        });
    }

}
