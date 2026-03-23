package com.example.app2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DevelopersFragment extends Fragment {

    private RecyclerView recyclerViewDevelopers;
    private DeveloperAdapter adapter;
    private List<Developer> developersList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_developers, container, false);

        recyclerViewDevelopers = view.findViewById(R.id.recyclerViewDevelopers);

        recyclerViewDevelopers.setLayoutManager(new LinearLayoutManager(requireContext()));

        developersList = new ArrayList<>();
        fillDevelopersList();

        adapter = new DeveloperAdapter(requireContext(), developersList);
        recyclerViewDevelopers.setAdapter(adapter);

        return view;
    }

    private void fillDevelopersList() {
        String eaName = getString(R.string.developer_ea);
        String ubiName = getString(R.string.developer_ubi);
        String blizzardName = getString(R.string.developer_blizzard);

        developersList.add(new Developer(
                eaName,
                getString(R.string.publisher) + ": EA Sports",
                getString(R.string.founded) + ": 1982",
                getString(R.string.headquarters) + ": США",
                R.drawable.ic_launcher_foreground
        ));
        developersList.add(new Developer(
                ubiName,
                getString(R.string.publisher) + ": Ubisoft",
                getString(R.string.founded) + ": 1986",
                getString(R.string.headquarters) + ": Франция",
                R.drawable.ic_launcher_foreground
        ));
        developersList.add(new Developer(
                blizzardName,
                getString(R.string.publisher) + ": Activision Blizzard",
                getString(R.string.founded) + ": 1991",
                getString(R.string.headquarters) + ": США",
                R.drawable.ic_launcher_foreground
        ));
    }
}