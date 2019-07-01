package org.androidtown.mybudgeter.budgetAdd;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.androidtown.mybudgeter.R;

public class BudgetAddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);

        FragmentManager fragmentManager = getSupportFragmentManager();
        AddCategoryFragment categoryFragment = (AddCategoryFragment) fragmentManager.findFragmentById(R.id.add_category_fragment);
     }
}
