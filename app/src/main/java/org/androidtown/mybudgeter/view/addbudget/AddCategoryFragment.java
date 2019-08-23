package org.androidtown.mybudgeter.view.addbudget;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.view.addbudget.adapter.CategoryRecyclerAdapter;
import org.androidtown.mybudgeter.view.addbudget.viewmodel.SharedViewModel;
import org.androidtown.mybudgeter.data.budget.BudgetEntity;
import org.androidtown.mybudgeter.category.CategoryViewModel;
import org.androidtown.mybudgeter.data.category.CategoryEntity;
import org.androidtown.mybudgeter.com.util.OnClickPosition;

import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCategoryFragment extends Fragment implements OnClickPosition, View.OnClickListener {
    private SharedViewModel model;
    private CategoryRecyclerAdapter categoryRecyclerAdapter;
    private CategoryViewModel categoryViewModel;
    private TextView selected_category;
    private RecyclerView categoryListView;
    private MaterialButton btnConfirm;
    private CategoryEntity categoryEntity;
    private BottomAppBar bottomAppBar;
    private boolean selectedFlag = true;

    public AddCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_category, container, false);
        assignUiElements(view);
        initSetting(view);
        connectViewModel();

        // 백스택으로 돌아왔을 때
        if (categoryEntity != null) {
            selected_category.setText(categoryEntity.getName());
            bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.first_color));
            btnConfirm.setTextColor(getContext().getResources().getColor(R.color.white));
        }

        return view;
    }

    private void assignUiElements(View view) {
        selected_category = (TextView) view.findViewById(R.id.selected_category);
        categoryListView = (RecyclerView) view.findViewById(R.id.category_list);
        btnConfirm = (MaterialButton) view.findViewById(R.id.btn_confirm);
        bottomAppBar = (BottomAppBar) view.findViewById(R.id.bottom_app_bar);
    }

    private void initSetting(View view) {
        // 프래그먼트 간 통신 위한 뷰모델
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        // 카테고리 리사이클러뷰 셋팅
        categoryListView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(this);
        categoryListView.setAdapter(categoryRecyclerAdapter);

        // 버튼 이벤트 할당
        btnConfirm.setOnClickListener(this);
        selected_category.setFocusable(false);
        selected_category.setClickable(false);
    }

    // 카테고리 데이터 연결
    private void connectViewModel() {
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(@Nullable List<CategoryEntity> categories) {
                if (!categories.isEmpty()) {
                    categoryRecyclerAdapter.setCategories(categories);
                }
                else Log.i("chk","category is empty");
            }
        });
    }

    @Override
    public void getPosition(View v, Date date) {

    }

    // 카테고리 리스트 리사이클러뷰 아이템 클릭 시
    @Override
    public void getEntity(CategoryEntity categoryEntity) {
        if (selectedFlag) {
            bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.first_color));
            btnConfirm.setTextColor(getContext().getResources().getColor(R.color.white));
            selectedFlag = false;
        }
        this.categoryEntity = categoryEntity;
        selected_category.setText(categoryEntity.getName());
    }

    // btnConfirm 클릭 시 - 기간 프래그먼트로 이동
    @Override
    public void onClick(View v) {
        if (categoryEntity != null) {
            BudgetEntity budgetEntity = new BudgetEntity();
            budgetEntity.setCategoryId(categoryEntity.getId());
            model.addBudget(budgetEntity);
            AddPeriodFragment periodFragment = new AddPeriodFragment();
            String periodTag = periodFragment.getClass().getSimpleName();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, periodFragment, periodTag)
                    .addToBackStack(periodTag)
                    .commit();
        } else {
            Toast.makeText(getContext(), "예산의 카테고리를 선택해주세요.", Toast.LENGTH_LONG);
        }
    }
}
