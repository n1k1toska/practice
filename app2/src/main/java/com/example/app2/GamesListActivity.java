package com.example.app2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import java.util.ArrayList;
import java.util.Arrays;

public class GamesListActivity extends AppCompatActivity {

    private ArrayList<String> gamesList;
    private ArrayAdapter<String> adapter;
    private ListView gamesListView;
    private EditText editTextGame;
    private ArrayList<String> selectedGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_games_list);

        String category = getIntent().getStringExtra("category");

        android.widget.TextView titleTextView = findViewById(R.id.titleTextView);
        titleTextView.setText(getString(R.string.games_in_category, category));

        gamesList = new ArrayList<>();
        selectedGames = new ArrayList<>();

        fillGamesList(category);

        gamesListView = findViewById(R.id.gamesListView);
        editTextGame = findViewById(R.id.editTextGame);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_multiple_choice,
                gamesList
        );

        gamesListView.setAdapter(adapter);
        gamesListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        gamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String game = adapter.getItem(position);
                if (gamesListView.isItemChecked(position)) {
                    selectedGames.add(game);
                } else {
                    selectedGames.remove(game);
                }
            }
        });

        Button btnAdd = findViewById(R.id.btnAddGame);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addGame();
            }
        });

        Button btnRemove = findViewById(R.id.btnRemoveGames);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeSelectedGames();
            }
        });
    }

    private void fillGamesList(String category) {
        if (category.equals(getString(R.string.category_action))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_doom),
                    getString(R.string.game_cod),
                    getString(R.string.game_fortnite),
                    getString(R.string.game_apex)
            ));
        } else if (category.equals(getString(R.string.category_strategy))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_civilization),
                    getString(R.string.game_stellaris),
                    getString(R.string.game_age_of_empires),
                    getString(R.string.game_company_of_heroes)
            ));
        } else if (category.equals(getString(R.string.category_adventure))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_uncharted),
                    getString(R.string.game_tomb_raider),
                    getString(R.string.game_last_of_us),
                    getString(R.string.game_red_dead)
            ));
        } else if (category.equals(getString(R.string.category_puzzle))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_portal),
                    getString(R.string.game_tetris),
                    getString(R.string.game_lumino),
                    getString(R.string.game_monument_valley)
            ));
        } else if (category.equals(getString(R.string.category_racing))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_forza),
                    getString(R.string.game_f1),
                    getString(R.string.game_nfs),
                    getString(R.string.game_dirt)
            ));
        } else if (category.equals(getString(R.string.category_rpg))) {
            gamesList.addAll(Arrays.asList(
                    getString(R.string.game_skyrim),
                    getString(R.string.game_witcher),
                    getString(R.string.game_dragon_age),
                    getString(R.string.game_divinity)
            ));
        }
    }

    private void addGame() {
        String game = editTextGame.getText().toString().trim();
        if (!game.isEmpty()) {
            adapter.add(game);
            editTextGame.setText("");
            adapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.game_added, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.enter_game_name, Toast.LENGTH_SHORT).show();
        }
    }

    private void removeSelectedGames() {
        if (selectedGames.isEmpty()) {
            Toast.makeText(this, R.string.select_games_to_remove, Toast.LENGTH_SHORT).show();
            return;
        }

        for (String game : selectedGames) {
            adapter.remove(game);
        }

        gamesListView.clearChoices();
        selectedGames.clear();
        adapter.notifyDataSetChanged();
        Toast.makeText(this, R.string.games_removed, Toast.LENGTH_SHORT).show();
    }
}