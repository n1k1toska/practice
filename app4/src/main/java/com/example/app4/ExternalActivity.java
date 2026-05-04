package com.example.app4;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ExternalActivity extends AppCompatActivity {

    private EditText etFileName, etContent;
    private static final String TAG = "ExtStorageDebug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external);

        etFileName = findViewById(R.id.etExtFileName);
        etContent = findViewById(R.id.etExtContent);
        Button btnCreate = findViewById(R.id.btnExtCreate);
        Button btnAppend = findViewById(R.id.btnExtAppend);
        Button btnRead = findViewById(R.id.btnExtRead);
        Button btnDelete = findViewById(R.id.btnExtDelete);

        checkPermissions();

        btnCreate.setOnClickListener(v -> saveExtFile(false));
        btnAppend.setOnClickListener(v -> saveExtFile(true));
        btnRead.setOnClickListener(v -> readExtFile());
        btnDelete.setOnClickListener(v -> showExtDeleteDialog());
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                Toast.makeText(this, "Включите 'Доступ ко всем файлам'!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File getExtFile(String filename) {
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!dir.exists()) dir.mkdirs();
        return new File(dir, filename);
    }

    private void saveExtFile(boolean append) {
        String filename = etFileName.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла!", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = getExtFile(filename);
        String textToSave = etContent.getText().toString();

        try {
            FileOutputStream fos = new FileOutputStream(file, append);
            fos.write(textToSave.getBytes(StandardCharsets.UTF_8));
            fos.flush();
            fos.close();

            Log.d(TAG, "Файл сохранён. Путь: " + file.getAbsolutePath() + " | Размер: " + file.length() + " байт");
            Toast.makeText(this, append ? "Дописано" : "Создано/Перезаписано", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Ошибка записи: " + e.getMessage());
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // 🔹 ЧТЕНИЕ
    private void readExtFile() {
        String filename = etFileName.getText().toString().trim();
        if (filename.isEmpty()) {
            Toast.makeText(this, "Введите имя файла!", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = getExtFile(filename);
        Log.d(TAG, "Ищем файл: " + file.getAbsolutePath());
        Log.d(TAG, "Существует: " + file.exists() + " | Размер: " + file.length() + " байт");

        if (!file.exists()) {
            Toast.makeText(this, "Файл не найден!", Toast.LENGTH_LONG).show();
            return;
        }

        if (file.length() == 0) {
            Toast.makeText(this, "Файл пустой. Сначала запишите в него текст.", Toast.LENGTH_LONG).show();
            etContent.setText("");
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();
            isr.close();
            fis.close();

            String result = sb.toString();
            Log.d(TAG, "Прочитано символов: " + result.length());
            Log.d(TAG, "Начало текста: " + result.substring(0, Math.min(50, result.length())));

            runOnUiThread(() -> {
                etContent.setText(result);
                etContent.setSelection(result.length());
                Toast.makeText(this, "Текст отображён!", Toast.LENGTH_SHORT).show();
            });

        } catch (Exception e) {
            Log.e(TAG, "Ошибка чтения: " + e.getMessage());
            runOnUiThread(() -> Toast.makeText(this, "Ошибка чтения: " + e.getMessage(), Toast.LENGTH_LONG).show());
        }
    }

    private void showExtDeleteDialog() {
        String filename = etFileName.getText().toString().trim();
        if (filename.isEmpty()) return;

        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Удалить " + filename + " из папки Документы?")
                .setPositiveButton("Да", (d, w) -> {
                    File file = getExtFile(filename);
                    if (file.exists() && file.delete()) {
                        etContent.setText("");
                        Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Не удалось удалить", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}