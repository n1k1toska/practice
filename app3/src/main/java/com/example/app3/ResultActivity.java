package com.example.app3;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvResult = findViewById(R.id.tvResult);

        String source = getIntent().getStringExtra("source");
        String data = getIntent().getStringExtra("data");

        if (source != null && data != null) {
            tvResult.setText("Источник: " + source + "\n\nДанные: " + data);
        } else {
            tvResult.setText("Данные не переданы");
        }
    }
}