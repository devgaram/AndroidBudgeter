package org.androidtown.mybudgeter.view.budgetdetail.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;

import java.util.ArrayList;
import java.util.List;

public class ExpenditureRecyclerAdapter extends RecyclerView.Adapter<ExpenditureRecyclerAdapter.ExpenditureHolder> {
    private List<ExpenditureEntity> expenditures = new ArrayList<ExpenditureEntity>();

    @NonNull
    @Override
    public ExpenditureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expenditure, viewGroup, false);
        return new ExpenditureHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureHolder expenditureHolder, int i) {
        expenditureHolder.amount.setText(Long.toString(expenditures.get(i).getAmount()));
    }

    public void setExpenditures(List<ExpenditureEntity> expenditures) {
        this.expenditures.clear();
        this.expenditures.addAll(expenditures);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return expenditures.size();
    }

    public static class ExpenditureHolder extends RecyclerView.ViewHolder {
        private TextView amount;
        public ExpenditureHolder(@NonNull View itemView) {
            super(itemView);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}
