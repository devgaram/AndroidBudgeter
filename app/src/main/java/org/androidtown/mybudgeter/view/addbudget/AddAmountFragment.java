package org.androidtown.mybudgeter.view.addbudget;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.androidtown.mybudgeter.R;
import org.androidtown.mybudgeter.view.addbudget.viewmodel.SharedViewModel;
import org.androidtown.mybudgeter.data.budget.BudgetEntity;
import org.androidtown.mybudgeter.view.main.viewmodel.BudgetViewModel;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAmountFragment extends Fragment implements View.OnClickListener {
    private SharedViewModel model;
    private EditText editAmount;
    private InputMethodManager inputMethodManager;
    private DecimalFormat decimalFormat;
    private MaterialButton btnConfirm;
    private BottomAppBar bottomAppBar;
    private long originalAmount;


    public AddAmountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_amount, container, false);
        assignUiElements(view);
        initSetting(view);
        initEditEventSetting();

        return view;
    }

    private void assignUiElements(View view) {
        editAmount = (EditText) view.findViewById(R.id.edit_amount);
        btnConfirm = (MaterialButton) view.findViewById(R.id.btn_confirm);
        bottomAppBar = (BottomAppBar) view.findViewById(R.id.bottom_app_bar);
    }
    private void initSetting(View view) {
        model = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#,#00");

        editAmount.requestFocus();

        // 키보드 올라오게 셋팅
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        btnConfirm.setOnClickListener(this);

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
                    bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.first_color));
                    btnConfirm.setTextColor(getContext().getResources().getColor(R.color.white));
                } else {
                    result = "";
                    editAmount.setText(result);
                    editAmount.setSelection(0);
                    bottomAppBar.setBackgroundTintList(getContext().getResources().getColorStateList(R.color.grey));
                    btnConfirm.setTextColor(getContext().getResources().getColor(R.color.my_app_text_color));
                }
                editAmount.addTextChangedListener(this);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (originalAmount > 0) {
            BudgetEntity budgetEntity = model.getAddedBudget().getValue();
            budgetEntity.setAmount(originalAmount);
            model.addBudget(budgetEntity);

            BudgetViewModel budgetViewModel = ViewModelProviders.of(getActivity()).get(BudgetViewModel.class);
            budgetViewModel.insert(model.getAddedBudget().getValue());

            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

            getActivity().finish();
        }

    }

}
