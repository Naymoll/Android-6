package com.example.application6;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

public class IntSum extends Fragment {
    private final String INT_KEY = "Int_Result";

    private TextView resultText;


    public IntSum() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean theme = getActivity()
                .getSharedPreferences(SettingsActivity.APP_SETTINGS, Context.MODE_PRIVATE)
                .getBoolean(SettingsActivity.THEME, false);

        int themeId;
        if (theme) {
            themeId = R.style.MyTheme;
        } else {
            themeId = R.style.AppTheme;
        }

        Context context = new ContextThemeWrapper(getContext(), themeId);
        return inflater.cloneInContext(context).inflate(R.layout.fragment_int_sum, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        resultText = view.findViewById(R.id.result_int_text);

        if (savedInstanceState != null) {
            resultText.setText(savedInstanceState.getString(INT_KEY));
        }

        Button sumButton = view.findViewById(R.id.sum_int_button);
        TextView firstNumber = view.findViewById(R.id.first_int_number);
        TextView secondNumber = view.findViewById(R.id.second_int_number);

        sumButton.setOnClickListener(v -> {
            if (firstNumber.getText().length() == 0 || secondNumber.getText().length() == 0) {
                showSnackbar(v, getString(R.string.empty_field_err));
                return;
            }

            String firstValStr = firstNumber.getText().toString();
            String secondValStr = secondNumber.getText().toString();

            int secondVal;
            int firstVal;
            try {
                secondVal = Integer.parseInt(secondValStr);
                firstVal = Integer.parseInt(firstValStr);
            } catch (NumberFormatException e) {
                showSnackbar(v, getString(R.string.wrong_number_err));
                return;
            }

            String resultStr = String.valueOf(firstVal + secondVal);
            String result = getString(R.string.result) + resultStr;
            resultText.setText(result);

            addHistoryItem(firstValStr, secondValStr, resultStr);
            showSnackbar(v, result);
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(INT_KEY, resultText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    private void addHistoryItem(String first, String second, String result) {
        MainActivity parent = (MainActivity) getActivity();
        parent.addToHistory(new HistoryItem(first, second, result, "StringSum"));
    }
}
