// detail_activity2.java
package com.example.onlinereno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class detail_activity2 extends AppCompatActivity {

    private TextView fruitNameTextView;
    private TextView pricePerKgTextView;
    private TextView quantityTextView;
    private TextView totalTxt;
    private TextView addBtn;
    private double quantity = 1; // Default quantity
    private final String fruitName = "Berry"; // Fixed fruit name
    private final double pricePerKg = 5.0; // Fixed price per kg

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail2);

        // Initialize Views
        fruitNameTextView = findViewById(R.id.titleTxt);
        pricePerKgTextView = findViewById(R.id.priceKgTxt);
        quantityTextView = findViewById(R.id.weightTxt);
        totalTxt = findViewById(R.id.totalTxt);
        addBtn = findViewById(R.id.AddBtn);

        // Set Fruit Name and Price
        fruitNameTextView.setText(fruitName);
        pricePerKgTextView.setText("$" + String.format("%.2f", pricePerKg));
        updateTotalPrice();

        // Add Button Listener
        addBtn.setOnClickListener(v -> {
            // Send data to CheckoutActivity
            double totalPrice = pricePerKg * quantity;
            Intent checkoutIntent = new Intent(detail_activity2.this, CheckoutActivity.class);
            checkoutIntent.putExtra("FRUIT_NAME", fruitName);
            checkoutIntent.putExtra("PRICE_PER_KG", pricePerKg);
            checkoutIntent.putExtra("QUANTITY", quantity);
            checkoutIntent.putExtra("TOTAL_PRICE", totalPrice);
            startActivity(checkoutIntent);
        });

        // Plus and Minus Button Listeners
        findViewById(R.id.plusBtn).setOnClickListener(v -> {
            quantity++;
            updateQuantityText();
        });

        findViewById(R.id.minusBtn).setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityText();
            }
        });
    }

    private void updateQuantityText() {
        quantityTextView.setText(quantity + " kg");
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        double totalPrice = pricePerKg * quantity;
        totalTxt.setText("$" + String.format("%.2f", totalPrice));
    }
}