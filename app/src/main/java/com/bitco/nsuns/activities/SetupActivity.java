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
        DatabaseHandler db = new DatabaseHandler(this);

        float squatsTm = Float.parseFloat(((EditText) findViewById(R.id.editMax)).getText().toString());
        float dlTm = Float.parseFloat(((EditText) findViewById(R.id.editMax1)).getText().toString());
        float benchTm = Float.parseFloat(((EditText) findViewById(R.id.editMax2)).getText().toString());
        float ohpTm = Float.parseFloat(((EditText) findViewById(R.id.editMax3)).getText().toString());

        db.insertMainLift("Squat", squatsTm);
        db.insertMainLift("Deadlift", dlTm);
        db.insertMainLift("Bench", benchTm);
        db.insertMainLift("OHP", ohpTm);

        ArrayList<Workout> workouts = new ArrayList<>();

        switch(selectedTemplate) {
            case "4-day":
                workouts = Templates.create4day(dlTm, squatsTm, benchTm, ohpTm);
                String[] mainArray = { "Bench", "OHP", "Squat", "Deadlift", "Bench", "Bench", "Deadlift", "Squat" };
                for (int i=0; i<workouts.size(); i++) {
                    Workout w = workouts.get(i);
                    db.insertExercise(w.getPrimaryExercise(), mainArray[i * 2]);
                    w.getPrimaryExercise().setId(db.getExerciseId(w.getPrimaryExercise()));
                    db.insertExercise(w.getSecondaryExercise(), mainArray[i * 2 + 1]);
                    w.getSecondaryExercise().setId(db.getExerciseId(w.getSecondaryExercise()));
                    db.insertWorkout(w);
                }
                break;

            case "5-day":
                workouts = Templates.create5day(dlTm, squatsTm, benchTm, ohpTm);

                break;

            case "6-day deadlift":

                workouts = Templates.create6dayDeadlift(dlTm, squatsTm, benchTm, ohpTm);

                break;

            case "6-day squat":

                workouts = Templates.create6daySquat(dlTm, squatsTm, benchTm, ohpTm);

                break;
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