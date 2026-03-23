package com.example.app2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;

public class PlatformActivity extends AppCompatActivity {

    private Spinner spinnerPlatform;
    private Spinner spinnerGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_platform);

        spinnerPlatform = findViewById(R.id.spinnerPlatform);
        spinnerGenre = findViewById(R.id.spinnerGenre);

        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.platforms_array,
                android.R.layout.simple_spinner_item
        );
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlatform.setAdapter(platformAdapter);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.genres_array,
                android.R.layout.simple_spinner_item
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        spinnerPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlatform = parent.getItemAtPosition(position).toString();
                Toast.makeText(PlatformActivity.this,
                        getString(R.string.selected_platform, selectedPlatform),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGenre = parent.getItemAtPosition(position).toString();
                Toast.makeText(PlatformActivity.this,
                        getString(R.string.selected_genre, selectedGenre),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}