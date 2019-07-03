package org.androidtown.mybudgeter.budgetAdd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.category.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryHolder> {
    private List<Category> categories = new ArrayList<>();

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder viewHolder, int i) {
        Category category = categories.get(i);
        viewHolder.category_name.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView category_name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
        }
    }
}
