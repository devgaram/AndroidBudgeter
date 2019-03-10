package org.androidtown.mybudgeter.pager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.expenditure.ProcessedExpenditure;

import java.util.ArrayList;
import java.util.List;

public class ExpenditureRecyclerAdapter extends RecyclerView.Adapter<ExpenditureRecyclerAdapter.ExpenditureHolder> {
    private List<ProcessedExpenditure> expenditures = new ArrayList<>();

    @NonNull
    @Override
    public ExpenditureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expenditure, viewGroup, false);
        return new ExpenditureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureRecyclerAdapter.ExpenditureHolder expenditureHolder, int i) {
        ProcessedExpenditure currentExpenditure =expenditures.get(i);
        expenditureHolder.expenditureMemo.setText(currentExpenditure.getMemo());
        expenditureHolder.expenditureAmount.setText(currentExpenditure.getAmount());
    }

    public void setExpenditures(List<ProcessedExpenditure> expenditures) {
        this.expenditures = expenditures;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return expenditures.size();
    }

    public static class ExpenditureHolder extends RecyclerView.ViewHolder {
        private TextView expenditureMemo;
        private TextView expenditureAmount;

        public ExpenditureHolder(View itemView) {
            super(itemView);
            expenditureMemo = (TextView) itemView.findViewById(R.id.item_expenditure_memo);
            expenditureAmount = (TextView) itemView.findViewById(R.id.item_expenditure_amount);
        }
    }
}
