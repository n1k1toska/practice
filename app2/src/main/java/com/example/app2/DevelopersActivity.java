package com.example.app2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DevelopersActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDevelopers;
    private DeveloperAdapter adapter;
    private List<Developer> developersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_developers);

        developersList = new ArrayList<>();
        fillDevelopersList();

        recyclerViewDevelopers = findViewById(R.id.recyclerViewDevelopers);

        recyclerViewDevelopers.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DeveloperAdapter(developersList);

        recyclerViewDevelopers.setAdapter(adapter);
    }

    private void fillDevelopersList() {
        developersList.add(new Developer(
                getString(R.string.developer_ea),
                getString(R.string.publisher) + ": EA Sports",
                getString(R.string.founded) + ": 1982",
                getString(R.string.headquarters) + ": США",
                R.drawable.ea_logo
        ));
        developersList.add(new Developer(
                getString(R.string.developer_ubi),
                getString(R.string.publisher) + ": Ubisoft",
                getString(R.string.founded) + ": 1986",
                getString(R.string.headquarters) + ": Франция",
                R.drawable.ubisoft_logo
        ));
        developersList.add(new Developer(
                getString(R.string.developer_blizzard),
                getString(R.string.publisher) + ": Activision Blizzard",
                getString(R.string.founded) + ": 1991",
                getString(R.string.headquarters) + ": США",
                R.drawable.blizzard_logo
        ));
        developersList.add(new Developer(
                getString(R.string.developer_valve),
                getString(R.string.publisher) + ": Valve Corporation",
                getString(R.string.founded) + ": 1996",
                getString(R.string.headquarters) + ": США",
                R.drawable.valve_logo
        ));
        developersList.add(new Developer(
                getString(R.string.developer_rockstar),
                getString(R.string.publisher) + ": Rockstar Games",
                getString(R.string.founded) + ": 1998",
                getString(R.string.headquarters) + ": США",
                R.drawable.rockstar_logo
        ));
    }
}