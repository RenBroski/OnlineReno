package com.example.onlinereno;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CheckoutActivity extends AppCompatActivity {
    private TextView totalPriceValueTextView;
    private TextView orderDetailsLabelTextView;
    private LinearLayout orderDetailsContainerLinearLayout;
    private Button confirmOrderButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout); // Pastikan layout ini sesuai dengan XML yang Anda berikan

        // Inisialisasi views
        ImageView backIcon = findViewById(R.id.backIcon);
        totalPriceValueTextView = findViewById(R.id.totalPriceValue);
        orderDetailsLabelTextView = findViewById(R.id.orderDetailsLabel);
        orderDetailsContainerLinearLayout = findViewById(R.id.orderDetailsContainer);
        confirmOrderButton = findViewById(R.id.confirmOrderBtn);
        databaseHelper = new DatabaseHelper(this); // Inisialisasi Database Helper

        // Mendapatkan data dari Intent
        Intent intent = getIntent();
        String fruitName = intent.getStringExtra("FRUIT_NAME");
        double pricePerKg = intent.getDoubleExtra("PRICE_PER_KG", 0.0);
        double quantity = intent.getDoubleExtra("QUANTITY", 0.0);
        double totalPrice = intent.getDoubleExtra("TOTAL_PRICE", 0.0);

        // Menampilkan total harga
        totalPriceValueTextView.setText(String.format("$%.2f", totalPrice));

        // Menampilkan detail pesanan
        orderDetailsLabelTextView.setText("Order Details:");
        addOrderItem(fruitName, pricePerKg, quantity);

        // Mengatur listener untuk tombol konfirmasi
        confirmOrderButton.setOnClickListener(v -> {
            // Menyimpan data ke database
            boolean isInserted = databaseHelper.addHistory(fruitName, pricePerKg, quantity, totalPrice);
            if (isInserted) {
                Toast.makeText(this, "PEMBELIAN BERHASIL!", Toast.LENGTH_SHORT).show();
                finish(); // Menutup CheckoutActivity
            } else {
                Toast.makeText(this, "Failed to confirm order", Toast.LENGTH_SHORT).show();
            }
        });

        // Mengatur listener untuk ikon kembali
        backIcon.setOnClickListener(v -> finish());
    }

    private void addOrderItem(String fruitName, double pricePerKg, double quantity) {
        // Membuat CardView untuk item pesanan
        CardView orderItemCardView = new CardView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(15, 15, 15, 15); // Set margins
        orderItemCardView.setLayoutParams(params);
        orderItemCardView.setPadding(30, 30, 30, 30);
        orderItemCardView.setCardBackgroundColor(getResources().getColor(R.color.black2));
        orderItemCardView.setCardElevation(10);

        // Membuat LinearLayout di dalam CardView
        LinearLayout orderItemLinearLayout = new LinearLayout(this);
        orderItemLinearLayout.setOrientation(LinearLayout.VERTICAL);
        orderItemCardView.addView(orderItemLinearLayout);

        // Membuat TextView untuk nama buah
        TextView fruitNameTextView = new TextView(this);
        fruitNameTextView.setText(String.format("Nama: %s", fruitName));
        fruitNameTextView.setTextColor(getResources().getColor(R.color.white));
        fruitNameTextView.setTextSize(12);
        orderItemLinearLayout.addView(fruitNameTextView);

        // Membuat TextView untuk harga
        TextView priceTextView = new TextView(this);
        priceTextView.setText(String.format("Harga: $%.2f", pricePerKg));
        priceTextView.setTextColor(getResources().getColor(R.color.white));
        priceTextView.setTextSize(12);
        orderItemLinearLayout.addView(priceTextView);

        // Membuat TextView untuk jumlah
        TextView quantityTextView = new TextView(this);
        quantityTextView.setText(String.format("Jumlah: %.1f kg", quantity));
        quantityTextView.setTextColor(getResources().getColor(R.color.white));
        quantityTextView.setTextSize(12);
        orderItemLinearLayout.addView(quantityTextView);

        // Menambahkan CardView ke dalam LinearLayout utama
        orderDetailsContainerLinearLayout.addView(orderItemCardView);
    }
}