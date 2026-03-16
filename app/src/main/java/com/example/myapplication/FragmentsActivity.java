package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class FragmentsActivity extends AppCompatActivity {

    private FrameLayout fragmentContainer;
    private Button btnFragment1, btnFragment2, btnFragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragments);

        fragmentContainer = findViewById(R.id.fragmentContainer);
        btnFragment1 = findViewById(R.id.btnFragment1);
        btnFragment2 = findViewById(R.id.btnFragment2);
        btnFragment3 = findViewById(R.id.btnFragment3);

        loadFragment(new StaticFragment());

        btnFragment1.setOnClickListener(v -> loadFragment(new StaticFragment()));
        btnFragment2.setOnClickListener(v -> loadFragment(new DynamicFragment()));
        btnFragment3.setOnClickListener(v -> loadFragment(new ContainerViewFragment()));

        Button btnBackToInput = findViewById(R.id.btnBackToInput);
        btnBackToInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentsActivity.this, InputActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}