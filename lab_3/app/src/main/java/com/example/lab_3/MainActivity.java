package com.example.lab_3;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private InputFragment inputFragment;
    private ResultFragment resultFragment;
    private DbHelper dbHelper;
    private Order currentOrder;

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

            if (currentOrder == null){
                currentOrder = new Order(flowerName, color, price);
                long id = dbHelper.addOrder(flowerName, color, price);
                if (id != -1) {
                    currentOrder.setId((int)id);
                    resultFragment.showMessage("Замовлення збережено (ID: " + id + ")");
                } else {
                    resultFragment.showMessage("Помилка збереження замовлення");
                }
            }

            else{
                Order changedOrder = new Order(flowerName, color, price);
                int currentOrderId = currentOrder.getId();
                long id = dbHelper.updateOrder(changedOrder, currentOrderId);
                if (id != -1) {
                    resultFragment.showMessage("Замовлення змінено (ID: " + id + ")");
                } else {
                    resultFragment.showMessage("Помилка зміни замовлення");
                }
            }

        }
    }

    public void deleteCurrentOrder() {
        if (currentOrder != null && currentOrder.getId() > 0) {
            int deleted_id = dbHelper.deleteOrder(currentOrder.getId());
            currentOrder = null;
            resultFragment.showMessage("Замовлення видалено (ID: " + deleted_id + ")");
        }
    }

    public void clearInputFields() {
        if (inputFragment != null) {
            inputFragment.clearForm();
            currentOrder = null;
        }
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