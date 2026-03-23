package com.example.myapplication.practice12;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

import com.example.myapplication.R;

public class ScheduleActivity extends AppCompatActivity {
    private EditText editTextDay;
    private EditText editTextTime;
    private EditText editTextComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);

        editTextDay = findViewById(R.id.editTextDay);
        editTextTime = findViewById(R.id.editTextTime);
        editTextComments = findViewById(R.id.editTextComments);

        Button buttonOK = findViewById(R.id.buttonOK);
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = editTextDay.getText().toString().trim();
                String time = editTextTime.getText().toString().trim();
                String comments = editTextComments.getText().toString().trim();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("day", day);
                resultIntent.putExtra("time", time);
                resultIntent.putExtra("comments", comments);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}