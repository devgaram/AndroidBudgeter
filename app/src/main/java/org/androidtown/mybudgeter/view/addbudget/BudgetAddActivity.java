package org.androidtown.mybudgeter.view.addbudget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.androidtown.mybudgeter.R;

public class BudgetAddActivity extends AppCompatActivity {
    private AddCategoryFragment categoryFragment;
    private String categoryTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);

        categoryFragment = new AddCategoryFragment();
        categoryTag = categoryFragment.getClass().getSimpleName();

        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, categoryFragment, categoryTag)
                .commit();

     }

}
