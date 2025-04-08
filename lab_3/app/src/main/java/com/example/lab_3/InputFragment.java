package com.example.lab_3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    private EditText flowerNameInput;
    private RadioGroup colorGroup;
    private RadioGroup priceGroup;
    private Button orderButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        flowerNameInput = view.findViewById(R.id.flowerNameInput);
        colorGroup = view.findViewById(R.id.colorGroup);
        priceGroup = view.findViewById(R.id.priceGroup);
        orderButton = view.findViewById(R.id.okButton);
        Button openButton = view.findViewById(R.id.openButton);

        orderButton.setOnClickListener(v -> processOrder());
        openButton.setOnClickListener(v -> openDatabase());

        return view;
    }

    private void processOrder() {
        String flowerName = flowerNameInput.getText().toString().trim();
        int selectedColorId = colorGroup.getCheckedRadioButtonId();
        int selectedPriceId = priceGroup.getCheckedRadioButtonId();

        if (flowerName.isEmpty() || selectedColorId == -1 || selectedPriceId == -1) {
            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedColour = getView().findViewById(selectedColorId);
        RadioButton selectedPrice = getView().findViewById(selectedPriceId);

        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateResult(
                    flowerName,
                    selectedColour.getText().toString(),
                    selectedPrice.getText().toString()
            );
        }

        orderButton.setText(R.string.ok_button_update);
    }

    private void openDatabase() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).openDatabaseView();
        }
    }

    public void clearForm() {
        if (getView() != null) {
            flowerNameInput.setText("");
            colorGroup.clearCheck();
            priceGroup.clearCheck();
            orderButton.setText(R.string.ok_button);
        }
    }
}