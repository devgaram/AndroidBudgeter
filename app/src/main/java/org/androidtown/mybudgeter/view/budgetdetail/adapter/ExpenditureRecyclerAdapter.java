package org.androidtown.mybudgeter.view.budgetdetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.com.util.OnItemClickedListener;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureWithBudget;
import org.androidtown.mybudgeter.view.budgetdetail.ExpenditureModifyActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExpenditureRecyclerAdapter extends RecyclerView.Adapter<ExpenditureRecyclerAdapter.ExpenditureHolder> {
    private List<ExpenditureWithBudget> expenditures = new ArrayList<>();
    SimpleDateFormat simpleDateFormat;
    private OnItemClickedListener mListener;
    private Activity activity;

    public ExpenditureRecyclerAdapter(Activity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ExpenditureHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_expenditure, viewGroup, false);
        simpleDateFormat = new SimpleDateFormat("MM.dd");
        return new ExpenditureHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ExpenditureHolder expenditureHolder, int i) {
        expenditureHolder.date.setText(simpleDateFormat.format(expenditures.get(i).getExpenditureEntity().getDate()));
        expenditureHolder.amount.setText(Long.toString(expenditures.get(i).getExpenditureEntity().getAmount()));
        expenditureHolder.memo.setText(expenditures.get(i).getExpenditureEntity().getMemo());
        expenditureHolder.remainAmount.setText(Long.toString(expenditures.get(i).getRemainAmount()));

    }

    public void setExpenditures(List<ExpenditureWithBudget> expenditures) {
        this.expenditures.clear();
        this.expenditures.addAll(expenditures);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return expenditures.size();
    }

    public class ExpenditureHolder extends RecyclerView.ViewHolder{
        private TextView date;
        private TextView amount;
        private TextView memo;
        private TextView remainAmount;

        public ExpenditureHolder(@NonNull View itemView) {
            super(itemView);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.memo = (TextView) itemView.findViewById(R.id.memo);
            this.date = (TextView) itemView.findViewById(R.id.date);
            this.remainAmount = (TextView) itemView.findViewById(R.id.remain_amount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ExpenditureModifyActivity.class);
                    intent.putExtra("eId", expenditures.get(getAdapterPosition()).getExpenditureEntity().getId());
                    intent.putExtra("remainAmount", expenditures.get(getAdapterPosition()).getRemainAmount());
                    intent.putExtra("budgetId", expenditures.get(getAdapterPosition()).getExpenditureEntity().getBudetId());
                    ActivityOptionsCompat options =ActivityOptionsCompat .makeSceneTransitionAnimation(activity,
                            Pair.create(amount, amount.getTransitionName()));
                    context.startActivity(intent, options.toBundle());
                }
            });
        }

    }
}
