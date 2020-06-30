package com.bitco.nsuns.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.fragments.AccessoriesFragment;
import com.bitco.nsuns.fragments.MainWorkoutFragment;
import com.bitco.nsuns.fragments.NewAccessoryFragment;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class WorkoutActivity extends AppCompatActivity {

    DatabaseHandler db;

    Workout workout;
    Exercise primaryExercise;
    Exercise secondaryExercise;

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        workout = intent.getParcelableExtra("workout");
        primaryExercise = workout.getPrimaryExercise();
        secondaryExercise = workout.getSecondaryExercise();

        db = new DatabaseHandler(this);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(0).setText(workout.getPrimaryExercise().getName());
        tabLayout.getTabAt(1).setText(workout.getSecondaryExercise().getName());
        tabLayout.addOnTabSelectedListener(tabSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new MainWorkoutFragment(primaryExercise)).commit();
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            Fragment selectedFragment = null;
            switch (tab.getPosition()) {
                case 0:
                    selectedFragment = new MainWorkoutFragment(primaryExercise);
                    break;
                case 1:
                    selectedFragment = new MainWorkoutFragment(secondaryExercise);
                    break;
                case 2:
                    selectedFragment = new AccessoriesFragment(workout);
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.layout, selectedFragment).commit();

        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) { }

        @Override
        public void onTabReselected(TabLayout.Tab tab) { }
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
                setResult(Activity.RESULT_OK);
                finish();
        }
        return true;
    }

    public void addAccessory(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new NewAccessoryFragment()).commit();
    }

    public void finishAddAccessory(Exercise e) {
        workout.getAccessories().add(e);
        db.updateWorkoutAccessories(workout);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new AccessoriesFragment(workout)).commit();
    }

}