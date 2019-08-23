package org.androidtown.mybudgeter.view.addbudget.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.data.category.CategoryEntity;
import org.androidtown.mybudgeter.com.util.OnClickPosition;
import org.androidtown.mybudgeter.com.util.OnItemClickedListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CategoryHolder> implements OnItemClickedListener {
    private List<CategoryEntity> categories = new ArrayList<>();
    private OnClickPosition mListener;

    public CategoryRecyclerAdapter(OnClickPosition mListener) {
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new CategoryHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder viewHolder, int i) {
        CategoryEntity category = categories.get(i);
        viewHolder.category_name.setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    @Override
    public void onItemClicked(View view, int pos, int viewType) {
        if (mListener != null)
            mListener.getEntity(categories.get(pos));
    }

    @Override
    public void onItemLongClicked(View view, int pos, int viewType) {

    }

    public void setCategories(List<CategoryEntity> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public static class CategoryHolder extends RecyclerView.ViewHolder {
        private TextView category_name;
        public CategoryHolder(@NonNull View itemView, final OnItemClickedListener mListener) {
            super(itemView);
            category_name = (TextView) itemView.findViewById(R.id.category_name);
            category_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemClicked(v, getAdapterPosition(),0);
                }
            });
        }
    }

}
