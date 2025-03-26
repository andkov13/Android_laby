package com.example.lab_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText flowerNameInput;
    private RadioGroup colorGroup, priceGroup;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowerNameInput = findViewById(R.id.flowerNameInput);
        colorGroup = findViewById(R.id.colorGroup);
        priceGroup = findViewById(R.id.priceGroup);
        Button orderButton = findViewById(R.id.okButton);
        resultText = findViewById(R.id.resultText);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processOrder();
            }
        });
    }

    private void processOrder() {
        String flowerName = flowerNameInput.getText().toString().trim();
        int selectedColorId = colorGroup.getCheckedRadioButtonId();
        int selectedPriceId = priceGroup.getCheckedRadioButtonId();

        if(flowerName.isEmpty() || selectedColorId == -1 || selectedPriceId == -1){
            Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedColour = findViewById(selectedColorId);
        RadioButton selectedPrice = findViewById(selectedPriceId);

        String result = "Деталі замовлення:" + "\nКвітка : " + flowerName +
                "\nКолір: " + selectedColour.getText() +
                "\nЦіна: " + selectedPrice.getText();
        resultText.setText(result);

    }
}