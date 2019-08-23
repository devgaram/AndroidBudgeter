package org.androidtown.mybudgeter.view.main;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import org.androidtown.mybudgeter.view.main.adapter.BudgetRecyclerAdapter;

public class ItemTouchCallback extends ItemTouchHelper.SimpleCallback {
    private BudgetRecyclerAdapter adapter;

    public ItemTouchCallback(BudgetRecyclerAdapter adapter) {
        // item up or down 안할거니깐 0
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adapter = adapter;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int position = viewHolder.getAdapterPosition();
        adapter.deleteItem(position);
    }

}
