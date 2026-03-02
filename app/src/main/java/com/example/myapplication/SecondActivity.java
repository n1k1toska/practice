package com.example.myapplication;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.widget.TextView;


public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String fullName = intent.getStringExtra("Полное имя");
        String group = intent.getStringExtra("Группа");
        String age = intent.getStringExtra("Возраст");
        String grade = intent.getStringExtra("Оценка");

        TextView textViewFullName = findViewById(R.id.textViewFullName);
        TextView textViewGroup = findViewById(R.id.textViewGroup);
        TextView textViewAge = findViewById(R.id.textViewAge);
        TextView textViewGrade = findViewById(R.id.textViewGrade);

        textViewFullName.setText(fullName != null && !fullName.isEmpty() ? fullName : "Не указано");
        textViewGroup.setText(group);
        textViewAge.setText(age);
        textViewGrade.setText(grade);
    }
}