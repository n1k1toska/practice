package com.example.app4;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText etFileName, etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFileName = findViewById(R.id.etFileName);
        etContent = findViewById(R.id.etContent);
        Button btnCreateWrite = findViewById(R.id.btnCreateWrite);
        Button btnAppend = findViewById(R.id.btnAppend);
        Button btnRead = findViewById(R.id.btnRead);
        Button btnDelete = findViewById(R.id.btnDelete);
        Button btnGoExternal = findViewById(R.id.btnGoExternal);
        findViewById(R.id.btnPrefs).setOnClickListener(v -> startActivity(new Intent(this,
                SharedPrefsActivity.class)));
        findViewById(R.id.btnDb).setOnClickListener(v -> startActivity(new Intent(this,
                StudentDbActivity.class)));

        if (savedInstanceState != null) {
            etFileName.setText(savedInstanceState.getString("filename", ""));
            etContent.setText(savedInstanceState.getString("content", ""));
        }

        btnCreateWrite.setOnClickListener(v -> saveFile(Context.MODE_PRIVATE, false));
        btnAppend.setOnClickListener(v -> saveFile(Context.MODE_APPEND, true));
        btnRead.setOnClickListener(v -> readFile());
        btnDelete.setOnClickListener(v -> showDeleteDialog());
        btnGoExternal.setOnClickListener(v -> {
            startActivity(new Intent(this,
                    ExternalActivity.class));
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("filename", etFileName.getText().toString());
        outState.putString("content", etContent.getText().toString());
    }

    private void saveFile(int mode, boolean isAppend) {
        String filename = etFileName.getText().toString().trim();
        String content = etContent.getText().toString();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла!", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileOutputStream fos = openFileOutput(filename, mode)) {
            fos.write(content.getBytes());
            Toast.makeText(this, isAppend ? "Данные дописаны" : "Файл создан/перезаписан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка записи: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void readFile() {
        String filename = etFileName.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла!", Toast.LENGTH_SHORT).show();
            return;
        }
        try (FileInputStream fis = openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(isr)) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            etContent.setText(sb.toString());
            Toast.makeText(this, "Файл прочитан", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Файл не найден или ошибка чтения", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDeleteDialog() {
        String filename = etFileName.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла для удаления!", Toast.LENGTH_SHORT).show();
            return;
        }
        new AlertDialog.Builder(this)
                .setTitle(" Удаление файла")
                .setMessage("Вы действительно хотите удалить файл \"" + filename + "\"?")
                .setPositiveButton("Да", (dialog, which) -> {
                    if (deleteFile(filename)) {
                        etContent.setText("");
                        Toast.makeText(this, "Файл удален", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Ошибка удаления", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}