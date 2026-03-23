package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;


public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);

        ListView categoriesListView = findViewById(R.id.categoriesListView);

        String[] categories = getResources().getStringArray(R.array.game_categories);

        android.widget.ArrayAdapter<String> adapter = new android.widget.ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                categories
        );

        categoriesListView.setAdapter(adapter);

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = categories[position];
                Intent intent = new Intent(CategoriesActivity.this, GamesListActivity.class);
                intent.putExtra("category", selectedCategory);
                startActivity(intent);
            }
        });
    }
}