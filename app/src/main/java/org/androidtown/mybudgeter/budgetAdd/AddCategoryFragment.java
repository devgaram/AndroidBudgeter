package org.androidtown.mybudgeter.budgetAdd;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.category.Category;
import org.androidtown.mybudgeter.category.CategoryViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment {
    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private CategoryViewModel categoryViewModel;


    public AddCategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        RecyclerView categoryListView = view.findViewById(R.id.category_list);
        categoryListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        categoryRecyclerAdapter = new CategoryRecyclerAdapter();
        categoryListView.setAdapter(categoryRecyclerAdapter);


        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(@Nullable List<Category> categories) {
                if (!categories.isEmpty()) {
                    Log.i("chk",categories.get(0).getName());
                    categoryRecyclerAdapter.setCategories(categories);
                }
                else Log.i("chk","empty");
            }
        });

        return view;
    }


}
