package org.androidtown.mybudgeter.budgetAdd;

import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.androidtown.mybudgeter.R;

public class BudgetAddActivity extends AppCompatActivity {
    private AddCategoryFragment categoryFragment;
    private AddPeriodFragment periodFragment;
    private AddAmountFragment  amountFragment;
    private String categoryTag;
    private String periodTag;
    private String amountTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_add);

        FragmentManager fragmentManager = getSupportFragmentManager();
        //categoryFragment = (AddCategoryFragment) fragmentManager.findFragmentById(R.id.add_category_fragment);
        categoryFragment = new AddCategoryFragment();
        periodFragment = new AddPeriodFragment();
        amountFragment = new AddAmountFragment();
        categoryTag = categoryFragment.getClass().getSimpleName();
        periodTag = periodFragment.getClass().getSimpleName();
        amountTag = amountFragment.getClass().getSimpleName();
        getSupportFragmentManager().popBackStack();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, categoryFragment, categoryTag)
                .commit();

        MaterialButton btnConfirm = (MaterialButton) findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new EventBtnConfirm());
     }

     public class EventBtnConfirm implements View.OnClickListener {
         @Override
         public void onClick(View v) {
             for(Fragment fragment : getSupportFragmentManager().getFragments()) {
                 if(fragment.isVisible()) {
                     if(fragment instanceof AddCategoryFragment) {
                         getSupportFragmentManager()
                                 .beginTransaction()
                                 .replace(R.id.container, periodFragment, periodTag)
                                 .addToBackStack(periodTag)
                                 .commit();
                     } else if(fragment instanceof AddPeriodFragment) {
                         getSupportFragmentManager()
                                 .beginTransaction()
                                 .replace(R.id.container, amountFragment, amountTag)
                                 .addToBackStack(amountTag)
                                 .commit();
                     }
                 }
             }

         }
     }
}
