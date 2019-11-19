package org.androidtown.mybudgeter.view.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.data.budget.TfBudget;
import org.androidtown.mybudgeter.view.addbudget.BudgetAddActivity;
import org.androidtown.mybudgeter.view.addexpenditure.viewmodel.ExpenditureViewModel;
import org.androidtown.mybudgeter.view.main.adapter.BudgetRecyclerAdapter;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private BudgetViewModel budgetViewModel;
    private ExpenditureViewModel expenditureViewModel;
    private RecyclerView recyclerView;
    private BudgetRecyclerAdapter budgetRecyclerAdapter;
    private FloatingActionButton fab_budget;
    private ItemTouchCallback itemTouchCallback;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignUiElements();
        initSettting();
        connectViewModel();

    }

    private void assignUiElements() {
        recyclerView = findViewById(R.id.budget_list);
        fab_budget = (FloatingActionButton)  findViewById(R.id.btn_add_budget);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layout);
    }

    private void initSettting() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fab_budget.setOnClickListener(this);
        //리사이클러뷰 셋팅
        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);
        budgetRecyclerAdapter = new BudgetRecyclerAdapter(budgetViewModel, this);
        //itemTouchCallback = new ItemTouchCallback(budgetRecyclerAdapter);
       // ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        //itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(budgetRecyclerAdapter);
    }

    private void connectViewModel() {
        budgetViewModel.getAllTfBudgetWithCategory().observe(this, new Observer<List<TfBudget>>() {
            @Override
            public void onChanged(@Nullable List<TfBudget> tfBudgets) {
                if (tfBudgets != null) {

                    budgetRecyclerAdapter.setBudgets(tfBudgets);

                    //recyclerView.scheduleLayoutAnimation();
                }
                else Log.i("chk", "tfbudgets is null");
            }
        });
    }

    // 예산 추가 버튼
    @Override
    public void onClick(View v) {
        // new Intent(this~) 라고 하면 에러뜸.
        Intent intent = new Intent(v.getContext(), BudgetAddActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers();
        else
            super.onBackPressed();
    }
}
