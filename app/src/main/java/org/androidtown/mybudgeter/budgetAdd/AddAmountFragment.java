package org.androidtown.mybudgeter.budgetAdd;


import android.content.Context;
import android.os.Bundle;
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

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAmountFragment extends Fragment {
    private EditText editAmount;
    private InputMethodManager inputMethodManager;
    private DecimalFormat decimalFormat;


    public AddAmountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_amount, container, false);
        decimalFormat = new DecimalFormat();
        decimalFormat.applyPattern("#,#00");

        editAmount = (EditText) view.findViewById(R.id.edit_amount);
        editAmount.requestFocus();
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
                if (result.length() > 3) {
                    result = decimalFormat.format(Long.parseLong(result)) + "원";
                    editAmount.setText(result);
                    editAmount.setSelection(result.length()-1);
                } else if (result.length() > 0) {
                    result = result + "원";
                    editAmount.setText(result);
                    editAmount.setSelection(result.length()-1);
                } else {
                    result = "";
                    editAmount.setText(result);
                    editAmount.setSelection(0);
                }


                editAmount.addTextChangedListener(this);


            }
        });

        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        MaterialButton btnConfirm = (MaterialButton) view.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new EventBtnConfirm());
        return view;
    }

    public class EventBtnConfirm implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            getActivity().finish();
        }
    }

}
