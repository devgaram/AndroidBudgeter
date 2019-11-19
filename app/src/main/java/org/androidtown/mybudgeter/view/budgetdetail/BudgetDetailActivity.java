package org.androidtown.mybudgeter.view.budgetdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import org.androidtown.mybudgeter.data.expenditure.ExpenditureStatics;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureWithBudget;
import org.androidtown.mybudgeter.view.addexpenditure.viewmodel.ExpenditureViewModel;
import org.androidtown.mybudgeter.view.budgetdetail.adapter.ExpenditureRecyclerAdapter;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private HashMap<String, Long> expenditureDateMap;
    private ArrayList<String> dateList;
    private TextView listEmptyView;
    private CoordinatorLayout layout;

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
        listEmptyView = findViewById(R.id.list_empty_text);
        layout = (CoordinatorLayout) findViewById(R.id.main_layout);

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
        chart.setNoDataText("아직 지출이 없습니다.");


        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setLabelCount(3, false);
        ValueFormatter formatter = new StaticsValueFormatter("일");
        xAxis.setValueFormatter(formatter);


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setEnabled(false);
        leftAxis.setAxisMinimum(0f);
        YAxis rightAxis = chart.getAxisRight();
        formatter = new StaticsValueFormatter("원");
        rightAxis.setLabelCount(5, false);
        rightAxis.setValueFormatter(formatter);
        rightAxis.setAxisMinimum(0f);

        // 리사이클러뷰 셋팅
        expenditureRecyclerAdapter = new ExpenditureRecyclerAdapter(this);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(this, BudgetModifyActivity.class);
                intent.putExtra("budgetId", BUDGET_ID);
                ActivityOptionsCompat options =ActivityOptionsCompat.makeSceneTransitionAnimation(this, layout, layout.getTransitionName() );
                startActivity(intent, options.toBundle());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setChartData() {
        if (dateList == null || expenditureDateMap == null) return;
        if (dateList.size() == 0 ) return;
        ArrayList<BarEntry> values = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        for (String strDate : dateList) {
            if (expenditureDateMap.containsKey(strDate)) {
                values.add(new BarEntry(Integer.parseInt(strDate), expenditureDateMap.get(strDate)));
            }
            else {
                values.add(new BarEntry(Integer.parseInt(strDate), null));
            }
        }
        if (values.isEmpty()) return;
        BarDataSet dataSet;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            dataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            dataSet.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            dataSet = new BarDataSet(values, "지출");
            dataSet.setColor(getResources().getColor(R.color.light_budget_state_5));
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(dataSet);
            BarData barData = new BarData(dataSets);
            barData.setBarWidth(0.9f);
            barData.setValueTextSize(10f);
            chart.setData(barData);
        }
        chart.setVisibleXRangeMaximum(7);
        if (expenditureDateMap.size() == 0) chart.getAxisRight().setLabelCount(0, true);
    }
    private void setData() {

        budgetViewModel.getBudgetWithCategoryForId(BUDGET_ID).observe(this, new Observer<BudgetWithCategory>() {
            @Override
            public void onChanged(@Nullable BudgetWithCategory budgetWithCategory) {
                textViewBudgetName.setText(budgetWithCategory.getCategory().getName());
                SimpleDateFormat sdformat = new SimpleDateFormat("MMdd");
                SimpleDateFormat sdformat2 = new SimpleDateFormat("yyyy-MM-dd");
                if (dateList == null) dateList = new ArrayList<>();
                try {
                    Date startDate = budgetWithCategory.getBudget().getStartDate();
                    String date = sdformat2.format(startDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(sdformat2.parse(date));
                    long diff = Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis();
                    diff = (diff / (24 * 60 * 60 * 1000)) + 1;
                    while(dateList.size() < diff) {
                        dateList.add(sdformat.format(calendar.getTime()));
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    setChartData();
                } catch(ParseException ex) {
                    ex.printStackTrace();
                }



            }
        });


        expenditureViewModel.getExpenditureStaticsForBudget(BUDGET_ID).observe(this, new Observer<List<ExpenditureStatics>>() {
            @Override
            public void onChanged(@Nullable List<ExpenditureStatics> expenditureEntities) {
                if (expenditureEntities != null) {
                    expenditureDateMap = new HashMap<>();
                    for (ExpenditureStatics statics : expenditureEntities) {
                        expenditureDateMap.put(statics.getEdate(), statics.getDaysum());
                    }
                    setChartData();
                }
            }
        });

        expenditureViewModel.getExpenditureForBudget(BUDGET_ID).observe(this, new Observer<List<ExpenditureWithBudget>>() {
            @Override
            public void onChanged(@Nullable List<ExpenditureWithBudget> expenditureEntities) {
                if (expenditureEntities != null) {
                    if (expenditureEntities.size() > 0) {
                        expenditureListView.setVisibility(View.VISIBLE);
                        listEmptyView.setVisibility(View.GONE);
                        long budgetAmount = expenditureEntities.get(0).getBudgetAmount();
                        int index = expenditureEntities.size() - 1;
                        while (index >= 0) {
                            budgetAmount = budgetAmount - expenditureEntities.get(index).getExpenditureEntity().getAmount();
                            expenditureEntities.get(index).setRemainAmount(budgetAmount);
                            index--;
                        }

                        expenditureRecyclerAdapter.setExpenditures(expenditureEntities);
                    } else {
                        expenditureListView.setVisibility(View.GONE);
                        listEmptyView.setVisibility(View.VISIBLE);
                    }
                } else {
                    Log.i("chk", "지출없음");
                }
            }
        });

    }
}
