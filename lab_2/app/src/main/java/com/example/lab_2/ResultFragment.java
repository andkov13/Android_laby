package com.example.lab_2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private TextView resultText;
    private Button cancelButton;

    public ResultFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        resultText = view.findViewById(R.id.resultText);
        cancelButton = view.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> clearResult());

        cancelButton.setVisibility(View.GONE);
        resultText.setVisibility(View.GONE);

        return view;
    }

    public void updateResult(String flowerName, String color, String price) {
        String result = "Деталі замовлення:" + "\nКвітка: " + flowerName +
                "\nКолір: " + color +
                "\nЦіна: " + price;
        resultText.setText(result);

        cancelButton.setVisibility(View.VISIBLE);
        resultText.setVisibility(View.VISIBLE);
    }

    private void clearResult() {
        cancelButton.setVisibility(View.GONE);
        resultText.setVisibility(View.GONE);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).clearInputFields();
        }
    }
}