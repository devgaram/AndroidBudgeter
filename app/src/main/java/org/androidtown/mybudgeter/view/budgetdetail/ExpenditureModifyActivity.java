package org.androidtown.mybudgeter.view.budgetdetail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.com.util.MoneyEditView;
import org.androidtown.mybudgeter.com.util.OnDateTimePickerListener;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.view.addexpenditure.viewmodel.ExpenditureViewModel;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Locale;

public class ExpenditureModifyActivity extends AppCompatActivity implements OnDateTimePickerListener, AlertDialogFragment.OnPositiveClickListener {
    private int EXPENDITURE_ID;
    private int BUDGET_ID;
    private ConstraintLayout layout;
    private TextView dateView;
    private TextView timeView;
    private EditText memoEdit;
    private EditText amountEdit;
    private TextView remainAmountView;

    private ExpenditureViewModel expenditureViewModel;
    private DatePickerFragment dateFragment;
    private TimePickerFragment timeFragment;

    private InputMethodManager inputMethodManager;
    private MaterialButton saveBtn;
    private MaterialButton deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_modify);

        Intent intent = getIntent();
        EXPENDITURE_ID = intent.getIntExtra("eId", 0);
        BUDGET_ID = intent.getIntExtra("budgetId", 0);

        assignUiElements();
        initSetting(intent.getLongExtra("remainAmount", 0));
        setData();
        setEventListener();

    }

    private void assignUiElements() {
        layout = (ConstraintLayout) findViewById(R.id.e_layout);
        dateView = (TextView) findViewById(R.id.e_date);
        timeView = (TextView) findViewById(R.id.e_time);
        memoEdit = (EditText) findViewById(R.id.e_memo);
        amountEdit = (EditText) findViewById(R.id.e_amount);
        remainAmountView = (TextView) findViewById(R.id.e_remain_amount);
        saveBtn = (MaterialButton) findViewById(R.id.btn_e_save);
        deleteBtn = (MaterialButton) findViewById(R.id.btn_e_delete);
    }

    private void initSetting(long remainAmount) {
        remainAmountView.setText(Long.toString(remainAmount));
        expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);

        dateFragment = new DatePickerFragment();
        timeFragment = new TimePickerFragment();
        dateFragment.setListener(this);
        timeFragment.setListener(this);

        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


    }

    private void setEventListener() {
        dateView.setOnClickListener(new View.OnClickListener() {
            DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy년 MM월 dd일");
            @Override
            public void onClick(View v) {
                DateTime dateTime = dateFormatter.parseDateTime(dateView.getText().toString());
                dateFragment.setDate(dateTime);
                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            DateTimeFormatter timeFormatter = DateTimeFormat.forPattern("aa hh:mm");
            @Override
            public void onClick(View v) {
                DateTime dateTime = timeFormatter.parseDateTime(timeView.getText().toString());
                timeFragment.setTime(dateTime);
                timeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                 memoEdit.clearFocus();
                 amountEdit.clearFocus();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenditureViewModel.update(makeEntity());
                onBackPressed();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogFragment alertDialogFragment = new AlertDialogFragment();
                alertDialogFragment.show(getSupportFragmentManager(), "alertDialog");


            }
        });
    }

    private ExpenditureEntity makeEntity() {
        ExpenditureEntity expenditureEntity = new ExpenditureEntity();
        expenditureEntity.setId(EXPENDITURE_ID);
        expenditureEntity.setAmount(((MoneyEditView) amountEdit).getOriginalAmount());
        expenditureEntity.setMemo(memoEdit.getText().toString());
        DateTimeFormatter dateFormatter = DateTimeFormat.forPattern("yyyy년 MM월 dd일 aa hh:mm");
        DateTime dateTime = dateFormatter.parseDateTime(dateView.getText().toString() +" " + timeView.getText().toString());
        expenditureEntity.setDate(dateTime.toDate());
        expenditureEntity.setBudetId(BUDGET_ID);
        return expenditureEntity;
    }

    private void setData() {
        expenditureViewModel.getExpenditureForExpenditureId(EXPENDITURE_ID).observe(this, new Observer<ExpenditureEntity>() {
            @Override
            public void onChanged(@Nullable ExpenditureEntity expenditureEntity) {
                if (expenditureEntity != null) {
                    DateTime dateTime = new DateTime(expenditureEntity.getDate());

                    dateView.setText(DateTimeFormat.forPattern("yyyy년 MM월 dd일").withLocale(Locale.KOREAN).print(dateTime));
                    timeView.setText(DateTimeFormat.forPattern("aa hh:mm").withLocale(Locale.KOREAN).print(dateTime));
                    amountEdit.setText(Long.toString(expenditureEntity.getAmount()));
                    memoEdit.setText(expenditureEntity.getMemo());

                    dateFragment.setDate(dateTime);
                    timeFragment.setTime(dateTime);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void onDateTimePicker(int type, DateTime dt) {
        if (type == OnDateTimePickerListener.TYPE_DATE) {
            dateView.setText(DateTimeFormat.forPattern("yyyy년 MM월 dd일").withLocale(Locale.KOREAN).print(dt));
        } else {
            timeView.setText(DateTimeFormat.forPattern("aa hh:mm").withLocale(Locale.KOREAN).print(dt));
        }
    }


    @Override
    public void onPositiveClick() {
        expenditureViewModel.delete(makeEntity());
        onBackPressed();
    }
}
