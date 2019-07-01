package org.androidtown.mybudgeter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.budget.ProcessedBudget;

import java.util.ArrayList;
import java.util.List;

public class BudgetRecyclerAdapter extends RecyclerView.Adapter<BudgetRecyclerAdapter.BudgetHolder> {
    private List<ProcessedBudget> budgets = new ArrayList<>();

    @NonNull
    @Override
    public BudgetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_budget, viewGroup, false);
        return new BudgetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetHolder budgetHolder, int i) {
        ProcessedBudget currentBudget = budgets.get(i);
        budgetHolder.title.setText(currentBudget.getTitle());
        budgetHolder.amount.setText(Integer.toString(currentBudget.getAmount()));
    }

    @Override
    public int getItemCount() {
        return this.budgets.size();
    }

    public void setBudgets(List<ProcessedBudget> budgets) {
        this.budgets = budgets;
        notifyDataSetChanged();
    }

    public static class BudgetHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView amount;
        private TextView day;

        public BudgetHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.budget_name);
            amount = (TextView) itemView.findViewById(R.id.budget_remain_amount);
            day = (TextView) itemView.findViewById(R.id.budget_remain_day);
        }
    }
}
