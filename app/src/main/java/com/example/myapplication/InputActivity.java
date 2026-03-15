package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import java.util.Locale;

public class InputActivity extends AppCompatActivity {

    private EditText editTextFullName;
    private EditText editTextGroup;
    private EditText editTextAge;
    private EditText editTextGrade;
    private Button buttonSubmit;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            String msg = data.getStringExtra("message");
                            Toast.makeText(InputActivity.this, "Получено: " + msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        Locale locale = new Locale("ru");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_input);

        editTextFullName = findViewById(R.id.editTextFullName);
        editTextGroup = findViewById(R.id.editTextGroup);
        editTextAge = findViewById(R.id.editTextAge);
        editTextGrade = findViewById(R.id.editTextGrade);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        String fullName = editTextFullName.getText().toString().trim();
        String group = editTextGroup.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String gradeStr = editTextGrade.getText().toString().trim();

        if (fullName.isEmpty() || group.isEmpty() || ageStr.isEmpty() || gradeStr.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            float grade = Float.parseFloat(gradeStr);

            Student student = new Student(fullName, group, age, grade);

            Intent intent = new Intent(this, DisplayActivity.class);
            intent.putExtra("student", student);
            activityResultLauncher.launch(intent);
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_numbers, Toast.LENGTH_SHORT).show();
        }
    }

    public void onFragments(View view) {
        startActivity(new Intent(this, FragmentsActivity.class));
    }
}
