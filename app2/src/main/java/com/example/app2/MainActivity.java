package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btnCategories = findViewById(R.id.btnCategories);
        Button btnDevelopers = findViewById(R.id.btnDevelopers);
        Button btnRules = findViewById(R.id.btnRules);
        Button btnPlatform = findViewById(R.id.btnPlatform);

        btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoriesActivity.class)));
        btnDevelopers.setOnClickListener(v -> startActivity(new Intent(this, DevelopersActivity.class)));
        btnRules.setOnClickListener(v -> startActivity(new Intent(this, RulesActivity.class)));
        btnPlatform.setOnClickListener(v -> startActivity(new Intent(this, PlatformActivity.class)));
    }
}