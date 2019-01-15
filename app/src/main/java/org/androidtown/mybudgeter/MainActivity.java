package org.androidtown.mybudgeter;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        BudgetFragment budgetFragment = (BudgetFragment) fragmentManager.findFragmentById(R.id.budget_fragment);
        //SpendListFragment spendListFragment = (SpendListFragment) fragmentManager.findFragmentById(R.id.spend_list_fragment);
    }
}
