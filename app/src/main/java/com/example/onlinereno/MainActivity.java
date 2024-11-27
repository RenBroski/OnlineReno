package com.example.onlinereno;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import com.example.onlinereno.detail_activity;
import com.example.onlinereno.detail_activity2;

public class MainActivity extends AppCompatActivity {

    private View intent1, intent2, intent3, intent4;  // Ubah tipe menjadi View, karena ID ini merujuk pada ConstraintLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Pastikan layout ini sesuai dengan yang Anda buat

        // Inisialisasi view yang ada di layout
        intent1 = findViewById(R.id.intent1);
        intent2 = findViewById(R.id.intent2);
        intent3 = findViewById(R.id.intent3);
        intent4 = findViewById(R.id.intent4);
        LinearLayout linearLayoutHistory = findViewById(R.id.linearLayoutHistory);

        // Set listener untuk intent1
        intent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke ActivityDetail1
                Intent intent = new Intent(MainActivity.this, detail_activity.class);
                startActivity(intent);
            }
        });

        linearLayoutHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent untuk berpindah ke HistoryActivity
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });

        // Set listener untuk intent2
        intent2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke ActivityDetail2
                Intent intent = new Intent(MainActivity.this, detail_activity2.class);
                startActivity(intent);
            }
        });

        intent3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke ActivityDetail1
                Intent intent = new Intent(MainActivity.this, detail_activity3.class);
                startActivity(intent);
            }
        });

        intent4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke ActivityDetail1
                Intent intent = new Intent(MainActivity.this, detail_activity4.class);
                startActivity(intent);
            }
        });
    }
}
