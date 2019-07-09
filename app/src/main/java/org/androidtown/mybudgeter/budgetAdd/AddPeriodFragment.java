package org.androidtown.mybudgeter.budgetAdd;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidtown.mybudgeter.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPeriodFragment extends Fragment {
    private RecyclerView recyclerView;
    private CalendarRecyclerAdapter calendarRecyclerAdapter;
    private boolean isLoading = false;
    private List<Calendar> calendars;
    private final int SIZE_CALENDAR = 12;

    public AddPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_period, container, false);
        recyclerView = view.findViewById(R.id.calendar_recycler);
        populateData();
        initAdapter();
        initScrollListener();

        MaterialButton btnConfirm = (MaterialButton) view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new EventBtnConfirm());

        return view;
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
        calendarRecyclerAdapter = new CalendarRecyclerAdapter(calendars);
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

    public class EventBtnConfirm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            AddAmountFragment amountFragment = new AddAmountFragment();
            String amountTag = amountFragment.getClass().getSimpleName();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, amountFragment, amountTag)
                    .addToBackStack(amountTag)
                    .commit();
        }
    }
}
