package com.example.app4;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentDbActivity extends AppCompatActivity {
    private EditText etFio, etAge, etCourse, etGpa;
    private StudentDbHelper dbHelper;
    private StudentAdapter adapter;
    private List<Student> studentList;
    private int currentSelectedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_db);

        etFio = findViewById(R.id.etFio);
        etAge = findViewById(R.id.etAge);
        etCourse = findViewById(R.id.etCourse);
        etGpa = findViewById(R.id.etGpa);
        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnSearch = findViewById(R.id.btnSearch);
        Button btnDelete = findViewById(R.id.btnDelete);
        RecyclerView rv = findViewById(R.id.rvStudents);

        dbHelper = new StudentDbHelper(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        loadList();

        btnAdd.setOnClickListener(v -> {
            Student s = new Student(0, etFio.getText().toString(), Integer.parseInt(etAge.getText().toString()), Integer.parseInt(etCourse.getText().toString()), Double.parseDouble(etGpa.getText().toString()));
            if (dbHelper.addStudent(s)) {
                Toast.makeText(this, "Добавлен", Toast.LENGTH_SHORT).show();
                clearFields();
                loadList();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            if (currentSelectedId == -1) {
                Toast.makeText(this, "Сначала найдите студента для обновления!", Toast.LENGTH_SHORT).show();
                return;
            }
            Student s = new Student(currentSelectedId, etFio.getText().toString(), Integer.parseInt(etAge.getText().toString()), Integer.parseInt(etCourse.getText().toString()), Double.parseDouble(etGpa.getText().toString()));
            if (dbHelper.updateStudent(s)) {
                Toast.makeText(this, "Обновлен", Toast.LENGTH_SHORT).show();
                clearFields();
                loadList();
            }
        });

        btnSearch.setOnClickListener(v -> {
            String fio = etFio.getText().toString();
            if (fio.isEmpty()) { Toast.makeText(this, "Введите ФИО для поиска", Toast.LENGTH_SHORT).show(); return; }
            Student s = dbHelper.searchStudent(fio);
            if (s != null) {
                currentSelectedId = s.id;
                etFio.setText(s.fio);
                etAge.setText(String.valueOf(s.age));
                etCourse.setText(String.valueOf(s.course));
                etGpa.setText(String.valueOf(s.gpa));
                Toast.makeText(this, "Найден!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Не найден", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (currentSelectedId == -1) {
                Toast.makeText(this, "Сначала найдите студента для удаления!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (dbHelper.deleteStudent(currentSelectedId)) {
                Toast.makeText(this, "🗑 Удален", Toast.LENGTH_SHORT).show();
                clearFields();
                loadList();
            }
        });
    }

    private void loadList() {
        studentList = dbHelper.getAllStudents();
        adapter = new StudentAdapter(studentList);
        ((RecyclerView) findViewById(R.id.rvStudents)).setAdapter(adapter);
    }

    private void clearFields() {
        currentSelectedId = -1;
        etFio.setText(""); etAge.setText(""); etCourse.setText(""); etGpa.setText("");
    }
}