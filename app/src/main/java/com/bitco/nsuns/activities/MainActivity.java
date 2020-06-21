package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.fragments.HomeFragment;
import com.bitco.nsuns.fragments.StatsFragment;
import com.bitco.nsuns.fragments.TrainingFragment;
import com.bitco.nsuns.items.Workout;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        // Temporary to fill database with pre-made data for testing.
        if (DatabaseUtils.queryNumEntries(db.getReadableDatabase(), "exercises") == 0) {
            createDBEntries();
        }

        // Bottom Nav View
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment();
                break;
            case R.id.nav_train:
                selectedFragment = new TrainingFragment();
                break;
            case R.id.nav_stats:
                selectedFragment = new StatsFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        return true;
    };

    // Temp function to fill table with preexisting info until i can get around to making an initialisation page.
    public void createDBEntries() {
        ArrayList<RepSet> newSets = new ArrayList<>();
        newSets.add(new RepSet(0.75f, 5, false));
        newSets.add(new RepSet(0.85f, 3, false));
        newSets.add(new RepSet(0.95f, 1, true));
        newSets.add(new RepSet(0.90f, 3, false));
        newSets.add(new RepSet(0.85f, 3, false));
        newSets.add(new RepSet(0.80f, 3, false));
        newSets.add(new RepSet(0.75f, 3, false));
        newSets.add(new RepSet(0.70f, 3, false));
        newSets.add(new RepSet(0.65f, 3, false));

        Exercise e1 = new Exercise("Deadlifts", newSets,  117.5f);
        e1.updateRepSets();

        Exercise e3 = new Exercise("Bench", newSets, 70f);
        e3.updateRepSets();
        Exercise e4 = new Exercise("OHP", newSets, 42.5f);
        e4.updateRepSets();
        Exercise e5 = new Exercise("Squats", newSets, 105f);
        e5.updateRepSets();
        Exercise e6 = new Exercise("Sumo Deadlifts", newSets, 117.5f);
        e6.updateRepSets();
        Exercise e7 = new Exercise("CG Bench", newSets, 70f);
        e7.updateRepSets();
        Exercise e2 = new Exercise("Front Squats", newSets, 105f);
        e2.updateRepSets();

        ArrayList<Exercise> primaryExercises = new ArrayList<>();
        primaryExercises.add(e1);
        primaryExercises.add(e3);
        primaryExercises.add(e5);
        primaryExercises.add(e4);

        for(int i=0; i<primaryExercises.size(); i++) {
            db.insertPrimaryExercise(primaryExercises.get(i));
        }

        db.insertSecondaryExercise(e2, "Squats");
        db.insertSecondaryExercise(e6, "Deadlifts");
        db.insertSecondaryExercise(e7, "Bench");

        Workout d1 = new Workout(e3, e4);
        Workout d2 = new Workout(e5, e6);
        Workout d3 = new Workout(e3, e7);
        Workout d4 = new Workout(e1, e2);

        db.insertWorkout(d1);
        db.insertWorkout(d2);
        db.insertWorkout(d3);
        db.insertWorkout(d4);

        Log.i("Example Data", "Successfully Inserted Data");

    }

}