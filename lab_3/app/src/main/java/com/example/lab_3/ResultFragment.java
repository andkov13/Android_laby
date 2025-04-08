package com.example.lab_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {

    private TextView resultText;
    private Button cancelButton;
    private Button newOrderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        resultText = view.findViewById(R.id.resultText);
        cancelButton = view.findViewById(R.id.cancelButton);
        newOrderButton= view.findViewById(R.id.newOrderButton);

        cancelButton.setOnClickListener(v -> clearResult());
        newOrderButton.setOnClickListener(v -> newOrder());

        controlView(false);

        return view;
    }

    public void updateResult(String flowerName, String color, String price) {
        String result = "Деталі поточного замовлення:" + "\nКвітка: " + flowerName +
                "\nКолір: " + color +
                "\nЦіна: " + price;
        resultText.setText(result);

        controlView(true);
    }

    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void clearResult() {
        controlView(false);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).deleteCurrentOrder();
            ((MainActivity) getActivity()).clearInputFields();
        }
    }

    private void newOrder(){
        controlView(false);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).clearInputFields();
        }
    }

    private void controlView(boolean desiredState){
        if (desiredState){
            cancelButton.setVisibility(View.VISIBLE);
            resultText.setVisibility(View.VISIBLE);
            newOrderButton.setVisibility(View.VISIBLE);
        }
        else{
            cancelButton.setVisibility(View.GONE);
            resultText.setVisibility(View.GONE);
            newOrderButton.setVisibility(View.GONE);
        }
    }
}