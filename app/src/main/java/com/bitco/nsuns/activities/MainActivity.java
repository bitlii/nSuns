package com.bitco.nsuns.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.fragments.HomeFragment;
import com.bitco.nsuns.fragments.StatsFragment;
import com.bitco.nsuns.fragments.TrainingFragment;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    ArrayList<Workout> workouts;
    ArrayList<Exercise> primaryExercises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Checking if the user has completed the first time setup when launching app.
        SharedPreferences prefs = getSharedPreferences("app", Context.MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean(getString(R.string.firstTime), true);
        if (isFirstTime) {
            Intent intent = new Intent(this, SetupActivity.class);
            startActivity(intent);
            finish();
        }

        // Home/Training Fragment data reading from db.
        db = new DatabaseHandler(this);
        primaryExercises = db.getPrimaryExerciseList();
        workouts = db.getAllWorkouts();

        // Bottom Nav View
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.layout, new HomeFragment(primaryExercises)).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        Fragment selectedFragment = null;

        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new HomeFragment(primaryExercises);
                break;
            case R.id.nav_train:
                selectedFragment = new TrainingFragment(workouts);
                break;
                /**
            case R.id.nav_stats:
                selectedFragment = new StatsFragment();
                break; */
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.layout, selectedFragment).commit();
        return true;
    };


    public void completeTraining(View view) {
        Intent intent = new Intent(view.getContext(), FinishTrainingActivity.class);
        startActivityForResult(intent, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.layout);
        if (frag instanceof TrainingFragment) {
            ((TrainingFragment) frag).updateData();
        }

    }

    public void testSetupScreen(View v) {
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }

}