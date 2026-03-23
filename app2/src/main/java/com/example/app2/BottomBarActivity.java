package com.example.app2;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomBarActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_bar);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                String title = "";

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                    title = getString(R.string.nav_home);
                } else if (item.getItemId() == R.id.nav_categories) {
                    selectedFragment = new CategoriesFragment();
                    title = getString(R.string.nav_categories);
                } else if (item.getItemId() == R.id.nav_developers) {
                    selectedFragment = new DevelopersFragment();
                    title = getString(R.string.nav_developers);
                } else if (item.getItemId() == R.id.nav_rules) {
                    selectedFragment = new RulesFragment();
                    title = getString(R.string.nav_rules);
                } else if (item.getItemId() == R.id.nav_platform) {
                    selectedFragment = new PlatformFragment();
                    title = getString(R.string.nav_platform);
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    getSupportActionBar().setTitle(title);
                }

                return true;
            }
        });

        // Устанавливаем начальный фрагмент
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
            getSupportActionBar().setTitle(R.string.nav_home);
        }
    }
}