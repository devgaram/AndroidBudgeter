package org.androidtown.mybudgeter.view.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.com.util.CalendarCompare;
import org.androidtown.mybudgeter.com.util.OnItemClickedListener;
import org.androidtown.mybudgeter.data.budget.TfBudget;
import org.androidtown.mybudgeter.view.addexpenditure.ExpenditureAddActivity;
import org.androidtown.mybudgeter.view.budgetdetail.BudgetDetailActivity;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.util.ArrayList;
import java.util.List;

public class BudgetRecyclerAdapter extends RecyclerView.Adapter<BudgetRecyclerAdapter.BudgetHolder> implements OnItemClickedListener {
    private List<TfBudget> budgets = new ArrayList<>();
    private TfBudget mRecentlyDeletedItem;
    private int mRecentlyDeletedItemPosition;
    private View mainView;
    private BudgetViewModel budgetViewModel;
    private CalendarCompare calendarCompare;
    private static final int VIEW_TYPE_ITEMVIEW = 1;
    private static final int VIEW_TYPE_EXPENDITUREBTN = 2;
    private Context mainContext;
    private AppCompatActivity mainActivity;


    public BudgetRecyclerAdapter(BudgetViewModel budgetViewModel, AppCompatActivity mainActivity) {
        this.budgetViewModel = budgetViewModel;
        this.calendarCompare = new CalendarCompare();
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public BudgetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_budget, viewGroup, false);
        mainView = viewGroup.getRootView();
        mainContext = viewGroup.getContext();
        return new BudgetHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetHolder budgetHolder, int i) {
        TfBudget currentBudget = budgets.get(i);
        double remainAmount = currentBudget.getRemainAmount();
        double totalAmount = currentBudget.getAmount();
        double percent = (remainAmount/totalAmount)*100;
        Log.i("chk", Double.toString(percent));
        budgetHolder.title.setText(currentBudget.getCategoryName());
        budgetHolder.amount.setText(currentBudget.getFmtRemainAmount()+"원");
        budgetHolder.percent.setText((int) percent +"%");

        if (calendarCompare.dayDateToCalendar(budgets.get(i).getStartDate(), calendarCompare.getCalToday()) <= 0) {
            if (percent <= 20) {
                budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.budget_state_5));
                budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.budget_state_5));
            } else if (percent <= 40) {
                budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.budget_state_4));
                budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.budget_state_4));
            } else if (percent <= 60) {
                budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.budget_state_3));
                budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.budget_state_3));
            } else if (percent <= 80) {
                budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.budget_state_2));
                budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.budget_state_2));
            } else {
                budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.budget_state_1));
                budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.budget_state_1));
            }
            budgetHolder.day.setText("D-"+Long.toString(currentBudget.getRemainDate()));
            budgetHolder.day.setTextColor(mainContext.getResources().getColor(R.color.my_app_text_color));
            budgetHolder.btnExpenditure.setTextColor(mainContext.getResources().getColor(R.color.my_app_text_color));
            budgetHolder.title.setTextColor(mainContext.getResources().getColor(R.color.my_app_text_color));
            budgetHolder.amount.setTextColor(mainContext.getResources().getColor(R.color.my_app_text_color));

        } else {
            budgetHolder.day.setText(currentBudget.getFmtStartDate() + "부터 시작");
            budgetHolder.day.setTextColor(mainContext.getResources().getColor(R.color.warnning_color));
            budgetHolder.btnExpenditure.setTextColor(mainContext.getResources().getColor(R.color.grey));
            budgetHolder.title.setTextColor(mainContext.getResources().getColor(R.color.grey));
            budgetHolder.amount.setTextColor(mainContext.getResources().getColor(R.color.grey));
            budgetHolder.percent.setTextColor(mainContext.getResources().getColor(R.color.grey));
            budgetHolder.btnExpenditure.setBackgroundTintList(mainContext.getResources().getColorStateList(R.color.grey));
        }
    }

    @Override
    public int getItemCount() {
        return this.budgets.size();
    }

    @Override
    public void onItemClicked(View view, int pos, int viewType) {
        Context context = view.getContext();
        if (viewType == VIEW_TYPE_EXPENDITUREBTN) {
            if (calendarCompare.dayDateToCalendar(budgets.get(pos).getStartDate(), calendarCompare.getCalToday()) <= 0) {
                Intent intent = new Intent(context, ExpenditureAddActivity.class);
                intent.putExtra("budgetId",budgets.get(pos).getId());
                context.startActivity(intent);
            } else {
                Toast.makeText(view.getContext(), "시작일 이후에 지출 입력이 가능합니다.", Toast.LENGTH_LONG).show();
            }
        } else if (viewType == VIEW_TYPE_ITEMVIEW) {
            Intent intent = new Intent(context, BudgetDetailActivity.class);
            intent.putExtra("budgetId", budgets.get(pos).getId());
            context.startActivity(intent);
        }

    }

    @Override
    public void onItemLongClicked(View view, int pos, int viewType) {
        mainActivity.startSupportActionMode(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                view.setBackground(mainContext.getDrawable(R.drawable.background_all_border));
                View actionModeView = mainActivity.getLayoutInflater().inflate(R.layout.action_mode_cutom, null);
                actionModeView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                MaterialButton btnDelete = actionModeView.findViewById(R.id.action_delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteItem(pos);
                        actionMode.finish();
                    }
                });
                actionMode.setCustomView(actionModeView);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                view.setBackgroundColor(mainContext.getResources().getColor(R.color.white));
            }
        });
    }

    public void setBudgets(List<TfBudget> budgets) {
        this.budgets = budgets;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        mRecentlyDeletedItem = budgets.get(position);
        mRecentlyDeletedItemPosition = position;
        budgets.remove(position);
        notifyItemRemoved(position);
        showSnackBar();
    }

    private void showSnackBar() {
        Snackbar snackbar = Snackbar.make(mainView.findViewById(R.id.btn_add_budget), "예산이 삭제되었습니다.", Snackbar.LENGTH_LONG)
                .setAction("실행취소", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        undoDelete();
                    }
                });
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                super.onDismissed(transientBottomBar, event);
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    budgetViewModel.delete(mRecentlyDeletedItem);
                }
            }
        });
        snackbar.show();
    }


    private void undoDelete() {
        budgets.add(mRecentlyDeletedItemPosition, mRecentlyDeletedItem);
        notifyItemInserted(mRecentlyDeletedItemPosition);
    }

    public static class BudgetHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private View itemView;
        private TextView title;
        private TextView amount;
        private TextView day;
        private TextView percent;
        private Button btnExpenditure;
        private OnItemClickedListener mListener;

        public BudgetHolder(@NonNull View itemView, final OnItemClickedListener mListener) {
            super(itemView);
            this.itemView = itemView;
            this.mListener = mListener;
            title = (TextView) itemView.findViewById(R.id.budget_name);
            amount = (TextView) itemView.findViewById(R.id.budget_remain_amount);
            day = (TextView) itemView.findViewById(R.id.budget_remain_day);
            percent = (TextView) itemView.findViewById(R.id.budget_remain_percent);
            btnExpenditure = (Button) itemView.findViewById(R.id.btn_add_expenditure);

            btnExpenditure.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == btnExpenditure) {
                mListener.onItemClicked(v, getAdapterPosition(),VIEW_TYPE_EXPENDITUREBTN);
            } else if (v == itemView) {
                mListener.onItemClicked(v, getAdapterPosition(),VIEW_TYPE_ITEMVIEW);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            mListener.onItemLongClicked(v, getAdapterPosition(), 0);
            return true;
        }
    }
}
