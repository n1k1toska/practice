package com.example.myapplication;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText editTextFullName;
    private EditText editTextGroup;
    private EditText editTextAge;
    private EditText editTextGrade;
    private Button buttonProgrammable;
    public void onGoToSecondActivity(View view) {
        String fullName = editTextFullName.getText().toString().trim();
        String group = editTextGroup.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String grade = editTextGrade.getText().toString().trim();

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("Полное имя", fullName);
        intent.putExtra("Группа", group);
        intent.putExtra("Возраст",  age);
        intent.putExtra("Оценка", grade);

        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextGroup = findViewById(R.id.editTextGroup);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGrade = findViewById(R.id.editTextGrade);
        buttonProgrammable = findViewById(R.id.buttonProgrammble);

        buttonProgrammable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataToSecondActivity();
            }
        });
        Log.i(TAG, "onCreate() вызван");
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();
    }

    public void onDeclarativeTransition(View view) {
        sendDataToSecondActivity();
    }

    private void sendDataToSecondActivity() {
        String fullName = editTextFullName.getText().toString().trim();
        String group = editTextGroup.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String grade = editTextGrade.getText().toString().trim();

        if (fullName.isEmpty() || group.isEmpty() || age.isEmpty() || grade.isEmpty()) {
            Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("Полное имя", fullName);
        intent.putExtra("Группа", group);
        intent.putExtra("Возраст", age);
        intent.putExtra("Оценка", grade);

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart() вызван");
        Toast.makeText(this, "onStart()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume() вызван");
        Toast.makeText(this, "onResume()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause() вызван");
        Toast.makeText(this, "onPause()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop() вызван");
        Toast.makeText(this, "onStop()", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() вызван");
        Toast.makeText(this, "onDestroy()", Toast.LENGTH_SHORT).show();
    }
}