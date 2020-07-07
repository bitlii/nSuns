package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Templates;
import com.bitco.nsuns.items.Workout;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private String selectedTemplate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Prevents keyboard popping up when activity is started.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Spinner templateSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.templates, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        templateSpinner.setAdapter(adapter);
        templateSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        selectedTemplate = adapterView.getItemAtPosition(i).toString();
        Log.i("Selected Template", selectedTemplate);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        selectedTemplate = "";
    }

    public void finishSetup(View v) {
        float squatsTm = Float.parseFloat(((EditText) findViewById(R.id.editMax)).getText().toString());
        float dlTm = Float.parseFloat(((EditText) findViewById(R.id.editMax1)).getText().toString());
        float benchTm = Float.parseFloat(((EditText) findViewById(R.id.editMax2)).getText().toString());
        float ohpTm = Float.parseFloat(((EditText) findViewById(R.id.editMax3)).getText().toString());

        DatabaseHandler db = new DatabaseHandler(this);

        ArrayList<Workout> workouts = new ArrayList<>();
        Workout w;

        switch(selectedTemplate) {
            case "4-day":
                workouts = Templates.create4dayWorkouts(dlTm, squatsTm, benchTm, ohpTm);

                w = workouts.get(0);
                db.insertPrimaryExercise(w.getSecondaryExercise());
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Bench M");

                w = workouts.get(1);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlift M");

                w = workouts.get(2);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(3);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squat M");

                break;

            case "5-day":
                workouts = Templates.create5day(dlTm, squatsTm, benchTm, ohpTm);

                w = workouts.get(0);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Bench M");
                db.insertSecondaryExercise(w.getSecondaryExercise(), "OHP M");

                w = workouts.get(1);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlift M");

                w = workouts.get(2);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(3);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squat M");

                w = workouts.get(4);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                break;

            case "6-day deadlift":

                workouts = Templates.create6dayDeadlift(dlTm, squatsTm, benchTm, ohpTm);

                w = workouts.get(0);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Bench M");
                db.insertSecondaryExercise(w.getSecondaryExercise(), "OHP M");

                w = workouts.get(1);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlift M");

                w = workouts.get(2);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(3);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squat M");

                w = workouts.get(4);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(5);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Deadlift M");
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squat M");

                break;

            case "6-day squat":

                workouts = Templates.create6daySquat(dlTm, squatsTm, benchTm, ohpTm);

                w = workouts.get(0);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Bench M");
                db.insertSecondaryExercise(w.getSecondaryExercise(), "OHP M");

                w = workouts.get(1);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlift M");

                w = workouts.get(2);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(3);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squat M");

                w = workouts.get(4);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench M");

                w = workouts.get(5);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Squat M");
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlift M");

                break;
        }

        for (Workout wo : workouts) {
            db.insertWorkout(wo);
        }


        db.close();

        // Stores the completion of setup.
        SharedPreferences prefs = getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(getString(R.string.firstTime), false);
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}