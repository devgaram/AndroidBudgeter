package org.androidtown.mybudgeter.budgetAdd;


import android.os.Bundle;
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
    private CalendarRecyclerAdapter calendarRecyclerAdapter;

    public AddPeriodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_period, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.calendar_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Calendar> calendars = new ArrayList<>();

        for(int i=0; i<24; i++) {
            Calendar currentdate = Calendar.getInstance();
            currentdate.add(currentdate.MONTH, i);
            calendars.add(currentdate);
            currentdate = null;
        }

        calendarRecyclerAdapter = new CalendarRecyclerAdapter(calendars);
        recyclerView.setAdapter(calendarRecyclerAdapter);





        return view;
    }

}
