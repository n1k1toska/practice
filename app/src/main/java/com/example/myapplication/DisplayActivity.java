package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import android.content.Intent;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> scheduleResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        scheduleResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String day = data.getStringExtra("day");
                            String time = data.getStringExtra("time");
                            String comments = data.getStringExtra("comments");

                            Toast.makeText(DisplayActivity.this,
                                    getString(R.string.schedule_success, day, time),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        Student student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            TextView fullNameTV = findViewById(R.id.textViewFullName);
            TextView groupTV = findViewById(R.id.textViewGroup);
            TextView ageTV = findViewById(R.id.textViewAge);
            TextView gradeTV = findViewById(R.id.textViewGrade);

            fullNameTV.setText(student.getFullName());
            groupTV.setText(student.getGroup());
            ageTV.setText(String.valueOf(student.getAge()));
            gradeTV.setText(String.valueOf(student.getGrade()));
        }

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onAllowAccess(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "Доступ разрешён");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void onDenyAccess(View view) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("message", "Доступ запрещён");
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    public void onCancel(View view) {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    public void onAddSchedule (View view) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        scheduleResultLauncher.launch(intent);
    }
}