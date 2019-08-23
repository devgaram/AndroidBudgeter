package org.androidtown.mybudgeter.view.budgetdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.com.util.StaticsValueFormatter;
import org.androidtown.mybudgeter.data.budget.BudgetWithCategory;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureStatics;
import org.androidtown.mybudgeter.view.addexpenditure.viewmodel.ExpenditureViewModel;
import org.androidtown.mybudgeter.view.budgetdetail.adapter.ExpenditureRecyclerAdapter;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.util.ArrayList;
import java.util.List;

public class BudgetDetailActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private LinearLayout chartLayout;
    private BarChart chart;
    private RecyclerView expenditureListView;
    private int BUDGET_ID;
    private BudgetViewModel budgetViewModel;
    private ExpenditureViewModel expenditureViewModel;
    private ExpenditureRecyclerAdapter expenditureRecyclerAdapter;
    private TextView textViewBudgetName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_detail);

        Intent intent = getIntent();
        BUDGET_ID = intent.getIntExtra("budgetId", 0);

        assignUiElements();
        initSettting();
        setEventListener();
        setData();
    }

    private void assignUiElements() {
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        chartLayout = (LinearLayout) findViewById(R.id.chart_layout);
        chart = findViewById(R.id.expenditure_chart);
        expenditureListView = findViewById(R.id.expenditure_list);
        textViewBudgetName = findViewById(R.id.budget_name);

    }

    private void initSettting() {
        // 화면 사이즈 구하기
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // 툴바셋팅
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        collapsingToolbarLayout.setTitleEnabled(false);

        // 차트셋팅
        chartLayout.setMinimumHeight(size.y*3/7);
        chart.setMinimumHeight(size.y*3/7);
        chart.getDescription().setEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(false);
        chart.animateXY(2000, 2000);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(7, true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);

        YAxis rightAxis = chart.getAxisRight();
        ValueFormatter formatter = new StaticsValueFormatter("원");
        rightAxis.setLabelCount(5, false);
        rightAxis.setValueFormatter(formatter);

        // 리사이클러뷰 셋팅
        expenditureRecyclerAdapter = new ExpenditureRecyclerAdapter();
        expenditureListView.setLayoutManager(new LinearLayoutManager(this));
        expenditureListView.setAdapter(expenditureRecyclerAdapter);

        // 뷰모델 셋팅
        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);
        expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

    }

    private void setEventListener() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                float ratio = (float) Math.abs(i) / (float) appBarLayout.getTotalScrollRange();
                chartLayout.setAlpha(1 - ratio);
                if (ratio == 0) {
                    chart.animateXY(2000, 2000);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.budget_detail_menu, menu);
        return true;
    }

    private void setData() {

        budgetViewModel.getBudgetWithCategoryForId(BUDGET_ID).observe(this, new Observer<BudgetWithCategory>() {
            @Override
            public void onChanged(@Nullable BudgetWithCategory budgetWithCategory) {
                textViewBudgetName.setText(budgetWithCategory.getCategory().getName());
            }
        });

        expenditureViewModel.getExpenditureStaticsForBudget(BUDGET_ID).observe(this, new Observer<List<ExpenditureStatics>>() {
            @Override
            public void onChanged(@Nullable List<ExpenditureStatics> expenditureEntities) {
                if (expenditureEntities != null) {
                    ArrayList<BarEntry> values = new ArrayList<>();
                    BarDataSet barDataSet;
                    int index = 1;

                    for (ExpenditureStatics statics : expenditureEntities) {
                        values.add(new BarEntry(index, statics.getDaysum()));
                        index++;
                    }


                    if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {

                    } else {
                        barDataSet = new BarDataSet(values, "지출");
                        barDataSet.setColor(getResources().getColor(R.color.light_budget_state_5));
                        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                        dataSets.add(barDataSet);
                        BarData data = new BarData(dataSets);
                        chart.setData(data);

                    }
                }
            }
        });

        expenditureViewModel.getExpenditureForBudget(BUDGET_ID).observe(this, new Observer<List<ExpenditureEntity>>() {
            @Override
            public void onChanged(@Nullable List<ExpenditureEntity> expenditureEntities) {
                if (expenditureEntities != null) {
                    Log.i("chk", expenditureEntities.size()+"");
                    expenditureRecyclerAdapter.setExpenditures(expenditureEntities);
                } else {
                    Log.i("chk", "지출없음");
                }
            }
        });

    }
}
