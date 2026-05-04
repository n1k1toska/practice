package com.example.app4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SharedPrefsActivity extends AppCompatActivity {

    private EditText etUsername;
    private TextView tvSavedName;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs);

        etUsername = findViewById(R.id.etUsername);
        tvSavedName = findViewById(R.id.tvSavedName);
        Button btnSave = findViewById(R.id.btnSavePrefs);
        Button btnDelete = findViewById(R.id.btnDeletePrefs);

        prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);

        loadUsername();

        btnSave.setOnClickListener(v -> saveUsername());
        btnDelete.setOnClickListener(v -> deleteUsername());
    }

    private void loadUsername() {
        String name = prefs.getString("username", "Пользователь");
        tvSavedName.setText("Привет, " + name);
        etUsername.setText(name.equals("Пользователь") ? "" : name);
    }

    private void saveUsername() {
        String name = etUsername.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите имя!", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("username", name);
        editor.apply();

        loadUsername();
        Toast.makeText(this, "Имя сохранено", Toast.LENGTH_SHORT).show();
    }

    private void deleteUsername() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("username");
        editor.apply();

        loadUsername();
        Toast.makeText(this, "Имя удалено", Toast.LENGTH_SHORT).show();
    }
}