package org.androidtown.mybudgeter;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.androidtown.mybudgeter.budget.Budget;
import org.androidtown.mybudgeter.budget.BudgetViewModel;
import org.androidtown.mybudgeter.pager.BudgetPagerAdapter;
import org.androidtown.mybudgeter.pager.PagerFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Pair<PagerFragment, String>> fragments;
    private BudgetViewModel budgetViewModel;
    private BudgetPagerAdapter budgetPagerAdapter;
    private PagerFragment pagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        budgetPagerAdapter = new BudgetPagerAdapter(getSupportFragmentManager());

        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        budgetViewModel.getAllBudgets().observe(this, new Observer<List<Budget>>() {
            @Override
            public void onChanged(@Nullable List<Budget> budgets) {
                if (!budgets.isEmpty()) {
                    fragments = new ArrayList<>();
                    for (Budget budget : budgets) {
                        pagerFragment = new PagerFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("budgetId", budget.getId());
                        pagerFragment.setArguments(bundle);
                        fragments.add(new Pair<>(pagerFragment, budget.getTitle()));
                    }
                    budgetPagerAdapter.setPagerData(fragments);
                    viewPager.setAdapter(budgetPagerAdapter);
                }
            }
        });
    }


}
