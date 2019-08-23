package org.androidtown.mybudgeter.view.addbudget;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.view.addbudget.adapter.CalendarRecyclerAdapter;
import org.androidtown.mybudgeter.view.addbudget.viewmodel.SharedViewModel;
import org.androidtown.mybudgeter.data.budget.BudgetEntity;
import org.androidtown.mybudgeter.com.util.OnTransferInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPeriodFragment extends Fragment implements View.OnClickListener, OnTransferInfo {
    private SharedViewModel model;
    private RecyclerView recyclerView;
    private MaterialButton btnConfirm;
    private BottomAppBar bottomAppBar;
    private CalendarRecyclerAdapter calendarRecyclerAdapter;
    private boolean isLoading = false;
    private List<Calendar> calendars;
    private final int SIZE_CALENDAR = 12;
    private HashMap<String, Date> rangeDate;

    public AddPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_period, container, false);
        assignElements(view);
        initSetting(view);
        populateData();
        initAdapter();
        initScrollListener();

        // 백스택으로 돌아왔을 때
        if (rangeDate != null) {
            calendarRecyclerAdapter.setRangeDate(rangeDate);
            bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.first_color));
            btnConfirm.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Date startDate = rangeDate.get("startDate");
        Date endDate = rangeDate.get("endDate");

        if (startDate != null && endDate != null) {
            BudgetEntity budgetEntity = model.getAddedBudget().getValue();
            budgetEntity.setStartDate(startDate);
            budgetEntity.setEndDate(endDate);
            budgetEntity.setPeriod(getPeriod(startDate, endDate));
            model.addBudget(budgetEntity);
            AddAmountFragment amountFragment = new AddAmountFragment();
            String amountTag = amountFragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, amountFragment, amountTag)
                    .addToBackStack(amountTag)
                    .commit();
        }
    }

    @Override
    public void onTransfered(Object object) {
        rangeDate = (HashMap<String, Date>) object;
        if (rangeDate.get("startDate") != null && rangeDate.get("endDate") != null) {
            bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.first_color));
            btnConfirm.setTextColor(getContext().getResources().getColor(R.color.white));
        }
        else {
            bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.grey));
            btnConfirm.setTextColor(getContext().getResources().getColor(R.color.my_app_text_color));
        }

    }

    private void assignElements(View view) {
        recyclerView = view.findViewById(R.id.calendar_recycler);
        btnConfirm = (MaterialButton) view.findViewById(R.id.btn_confirm);
        bottomAppBar = (BottomAppBar) view.findViewById(R.id.bottom_app_bar);
    }

    private void initSetting(View view) {
        // 프래그먼트 간 통신 위한 뷰모델
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        btnConfirm.setOnClickListener(this);
    }

    private void populateData() {
        calendars = new ArrayList<>();
        for(int i = 0; i < SIZE_CALENDAR; i++) {
            Calendar currentDate = Calendar.getInstance();
            currentDate.add(currentDate.MONTH, i);
            calendars.add(currentDate);
            currentDate = null;
        }
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        calendarRecyclerAdapter = new CalendarRecyclerAdapter(calendars, this);
        recyclerView.setAdapter(calendarRecyclerAdapter);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastVisibleItemPosition() == calendars.size()-1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int scrollPosition = calendars.size();
                int nextLimit = scrollPosition + SIZE_CALENDAR;

                for (int i = scrollPosition; i < nextLimit; i++) {
                    Calendar currentDate = Calendar.getInstance();
                    currentDate.add(currentDate.MONTH, i);
                    calendars.add(currentDate);
                    currentDate = null;
                }

                calendarRecyclerAdapter.setCalendars(calendars);
                isLoading = false;
            }
        }, 500);
    }

    public long getPeriod(Date startDate, Date endDate) {
        long diff = endDate.getTime() - startDate.getTime();
        long diffPeriod = diff / (24 * 60 * 60 * 1000);
        return diffPeriod;
    }

}
