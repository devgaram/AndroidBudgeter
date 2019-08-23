package org.androidtown.mybudgeter.view.addexpenditure;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.view.addexpenditure.viewmodel.ExpenditureViewModel;
import org.androidtown.mybudgeter.data.expenditure.ExpenditureEntity;
import org.androidtown.mybudgeter.data.budget.BudgetWithCategory;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.text.DecimalFormat;
import java.util.Date;

public class ExpenditureAddActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView toolbarText;
    private EditText editAmount;
    private InputMethodManager inputMethodManager;
    private DecimalFormat decimalFormat;
    private MaterialButton btnConfirm;
    private BottomAppBar bottomAppBar;
    private BudgetViewModel budgetViewModel;
    private long originalAmount;
    private int budgetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenditure_add);

        Intent intent = getIntent(); /*데이터 수신*/
        budgetId = intent.getExtras().getInt("budgetId");

        assignElements();
        initSetting();
        connectViewModel();
        initEditEventSetting();

    }

    private void assignElements() {
        toolbarText = (TextView) findViewById(R.id.text_toolbar);
        editAmount = (EditText) findViewById(R.id.edit_amount);
        btnConfirm = (MaterialButton) findViewById(R.id.btn_confirm);
        bottomAppBar = (BottomAppBar) findViewById(R.id.bottom_app_bar);
    }

    private void initSetting() {
        budgetViewModel = ViewModelProviders.of(this).get(BudgetViewModel.class);

        decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#,#00");

        editAmount.requestFocus();

        // 키보드 올라오게 셋팅
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnConfirm.setOnClickListener(this);
    }

    private void connectViewModel() {

        budgetViewModel.getBudgetWithCategoryForId(budgetId).observe(this, new Observer<BudgetWithCategory>() {
            @Override
            public void onChanged(@Nullable BudgetWithCategory budgetWithCategory) {
                if (budgetWithCategory != null) {
                    toolbarText.setText(budgetWithCategory.getCategory().getName()+" 지출입력");
                }
            }
        });
    }

    private void initEditEventSetting() {
        editAmount.addTextChangedListener(new TextWatcher() {
            String result;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editAmount.removeTextChangedListener(this);
                result = s.toString().replaceAll(",", "").replace("원", "");
                if (result.length() > 0) originalAmount = Long.parseLong(result);
                if (result.length() > 3) {
                    result = decimalFormat.format(Long.parseLong(result)) + "원";
                    editAmount.setText(result);
                    editAmount.setSelection(result.length()-1);
                } else if (result.length() > 0) {
                    result = result + "원";
                    editAmount.setText(result);
                    editAmount.setSelection(result.length()-1);
                    bottomAppBar.setBackgroundTintList(getResources().getColorStateList(R.color.first_color));
                    btnConfirm.setTextColor(getResources().getColor(R.color.white));
                } else {
                    result = "";
                    editAmount.setText(result);
                    editAmount.setSelection(0);
                    bottomAppBar.setBackgroundTintList(getResources().getColorStateList(R.color.grey));
                    btnConfirm.setTextColor(getResources().getColor(R.color.my_app_text_color));
                }
                editAmount.addTextChangedListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (originalAmount > 0) {
            ExpenditureViewModel expenditureViewModel = ViewModelProviders.of(this).get(ExpenditureViewModel.class);
            ExpenditureEntity expenditureEntity = new ExpenditureEntity();
            expenditureEntity.setAmount(originalAmount);
            expenditureEntity.setDate(new Date());
            expenditureEntity.setBudetId(budgetId);
            expenditureEntity.setDate(new Date());
            expenditureViewModel.insert(expenditureEntity);
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            finish();
        }

    }

}
