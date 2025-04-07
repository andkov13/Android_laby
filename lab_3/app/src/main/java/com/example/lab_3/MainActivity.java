package com.example.lab_3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;
    private DbHelper dbHelper;
    private long lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DbHelper(this);

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

            // Додаємо запис до бази даних
            long id = dbHelper.addOrder(flowerName, color, price);
            lastId = id;
            if (id != -1) {
                resultFragment.showMessage("Замовлення збережено (ID: " + id + ")");
            } else {
                resultFragment.showMessage("Помилка збереження замовлення");
            }
        }
    }

    public void clearInputFields() {
        if (inputFragment != null) {
            inputFragment.clearForm();
        }
        int deleted_id = dbHelper.deleteOrder((int)lastId);
        resultFragment.showMessage("Замовлення видалено (ID: " + deleted_id + ")");
    }

    public void openDatabaseView() {
        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}