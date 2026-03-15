package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_fragments);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        DynamicFragment dynamicFragment = new DynamicFragment();
        fragmentTransaction.add(R.id.dynamicFragmentContainer, dynamicFragment);
        fragmentTransaction.commit();

        FragmentTransaction transaction2 = fragmentManager.beginTransaction();
        ContainerViewFragment containerViewFragment = new ContainerViewFragment();
        transaction2.add(R.id.fragmentContainerView, containerViewFragment);
        transaction2.commit();
    }
}
