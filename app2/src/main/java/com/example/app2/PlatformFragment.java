package com.example.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;

public class PlatformFragment extends Fragment {

    private Spinner spinnerPlatform;
    private Spinner spinnerGenre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_platform, container, false);

        spinnerPlatform = view.findViewById(R.id.spinnerPlatform);
        spinnerGenre = view.findViewById(R.id.spinnerGenre);

        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.platforms_array,
                android.R.layout.simple_spinner_item
        );
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlatform.setAdapter(platformAdapter);

        ArrayAdapter<CharSequence> genreAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.genres_array,
                android.R.layout.simple_spinner_item
        );
        genreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenre.setAdapter(genreAdapter);

        spinnerPlatform.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlatform = parent.getItemAtPosition(position).toString();
                Toast.makeText(getContext(),
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
                Toast.makeText(getContext(),
                        getString(R.string.selected_genre, selectedGenre),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }
}