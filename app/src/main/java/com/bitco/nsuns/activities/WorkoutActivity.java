package com.bitco.nsuns.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.MainWorkoutFragment;
import com.bitco.nsuns.items.Exercise;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class WorkoutActivity extends AppCompatActivity {

    Exercise primaryExercise;
    Exercise secondaryExercise;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        primaryExercise = intent.getParcelableExtra("primaryExercise");
        secondaryExercise = intent.getParcelableExtra("secondaryExercise");

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainWorkoutFragment(primaryExercise)).commit();
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Fragment selectedFragment = null;
            switch (tab.getPosition()) {
                case 0:
                    selectedFragment = new MainWorkoutFragment(primaryExercise);
                    Log.d("TAB SELECTED", "0");
                    break;
                case 1:
                    selectedFragment = new MainWorkoutFragment(secondaryExercise);
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_workout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_done:
                finish();
        }
        return true;
    }

}