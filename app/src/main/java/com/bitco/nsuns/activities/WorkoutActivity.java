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
import com.bitco.nsuns.fragments.NewAccessoryFragment;
import com.bitco.nsuns.fragments.WorkoutFragment;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;

import androidx.appcompat.app.AppCompatActivity;

public class WorkoutActivity extends AppCompatActivity {

    DatabaseHandler db;

    Workout workout;
    Exercise primaryExercise;
    Exercise secondaryExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Intent intent = getIntent();
        workout = intent.getParcelableExtra("workout");

        primaryExercise = workout.getPrimaryExercise();
        secondaryExercise = workout.getSecondaryExercise();

        db = new DatabaseHandler(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new WorkoutFragment(workout)).commit();

    }


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

    /*
        Methods used by buttons.
     */

    /**
     * Launch and replace the current fragment with a new accessory fragment.
     * @param v
     */
    public void addAccessory(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new NewAccessoryFragment()).addToBackStack("Main").commit();
    }

    public void finishAddAccessory(Exercise e) {
        workout.getAccessories().add(e);
        db.updateWorkoutAccessories(workout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new WorkoutFragment(workout, 2)).commit();
    }

    public void finishEditAccessory() {
        db.updateWorkoutAccessories(workout);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new WorkoutFragment(workout, 2)).commit();
    }

}