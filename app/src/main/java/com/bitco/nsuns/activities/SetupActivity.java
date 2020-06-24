package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        switch(selectedTemplate) {
            case "4-day":
                ArrayList<Workout> workouts = Templates.create4dayWorkouts(dlTm, squatsTm, benchTm, ohpTm);
                Workout w = workouts.get(0);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Squats");
                w = workouts.get(1);
                db.insertSecondaryExercise(w.getPrimaryExercise(), "Bench 1");
                db.insertPrimaryExercise(w.getSecondaryExercise());
                w = workouts.get(2);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Deadlifts");
                w = workouts.get(3);
                db.insertPrimaryExercise(w.getPrimaryExercise());
                db.insertSecondaryExercise(w.getSecondaryExercise(), "Bench 1");

                for (Workout workout : workouts) {
                    db.insertWorkout(workout);
                }

        }
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}