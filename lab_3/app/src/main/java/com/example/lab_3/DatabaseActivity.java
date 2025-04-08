package com.example.lab_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    private TextView dbContent;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        dbHelper = new DbHelper(this);
        dbContent = findViewById(R.id.dbContent);
        Button backButton = findViewById(R.id.backButton);

        displayDatabaseContent();

        backButton.setOnClickListener(v -> finish());
    }

    private void displayDatabaseContent() {
        List<Order> orders = dbHelper.getAllOrders();
        StringBuilder content = new StringBuilder();

        if (orders.isEmpty()) {
            content.append("Збережених замовлень немає.");
        } else {
            content.append("Кількість замовлень: ").append(orders.size()).append("\n\n");
            for (Order order : orders) {
                content.append(order.toString()).append("\n\n");
            }
        }

        dbContent.setText(content.toString());
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}