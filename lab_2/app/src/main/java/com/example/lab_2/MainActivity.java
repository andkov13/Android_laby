package com.example.lab_2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFragment = new InputFragment();
        resultFragment = new ResultFragment();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.input_fragment_container, inputFragment)
                    .add(R.id.result_fragment_container, resultFragment)
                    .commit();
        }
    }

    public void updateResult(String flowerName, String color, String price) {
        if (resultFragment != null) {
            resultFragment.updateResult(flowerName, color, price);
        }
    }

    public void clearInputFields() {
        if (inputFragment != null) {
            inputFragment.clearForm();
        }
    }
}