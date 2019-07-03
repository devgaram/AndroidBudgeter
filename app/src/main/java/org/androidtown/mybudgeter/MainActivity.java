package org.androidtown.mybudgeter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import org.androidtown.mybudgeter.budget.Budget;
import org.androidtown.mybudgeter.budget.BudgetViewModel;
import org.androidtown.mybudgeter.budget.ProcessedBudget;
import org.androidtown.mybudgeter.budgetAdd.BudgetAddActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BudgetViewModel budgetViewModel;
    private BudgetRecyclerAdapter budgetRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.budget_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        budgetRecyclerAdapter = new BudgetRecyclerAdapter();
        recyclerView.setAdapter(budgetRecyclerAdapter);

        FloatingActionButton fab_budget = (FloatingActionButton)  findViewById(R.id.btn_add_budget);
        fab_budget.setOnClickListener(new EventAddBudget());

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgets().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(@Nullable List<Budget> budgets) {
                if (!budgets.isEmpty()) {
                    List<ProcessedBudget> processedBudgets = new ArrayList<>();
                    for (Budget budget : budgets) {
                        ProcessedBudget processedBudget = new ProcessedBudget(budget.getTitle(), budget.getAmount());
                        processedBudgets.add(processedBudget);
                    }
                    budgetRecyclerAdapter.setBudgets(processedBudgets);
                } else Log.i("chk","empty");
            }
        });
    }

    class EventAddBudget implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // new Intent(this~) 라고 하면 에러뜸.
            Intent intent = new Intent(v.getContext(), BudgetAddActivity.class);
            startActivity(intent);
        }
    }


}
