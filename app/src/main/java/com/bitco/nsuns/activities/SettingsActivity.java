package com.bitco.nsuns.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import com.bitco.nsuns.R;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Templates;
import com.bitco.nsuns.items.Workout;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            Preference templatePref = findPreference("template");
            templatePref.setOnPreferenceChangeListener(preferenceChangeListener);
        }

        Preference.OnPreferenceChangeListener preferenceChangeListener = (preference, newValue) -> {
            switch(preference.getKey()) {
                case "template":
                    if (!newValue.toString().equals(getPreferenceManager().getSharedPreferences().getString(preference.getKey(), ""))) {
                        changeTemplate(newValue.toString());
                        Toast.makeText(getContext(), "Successfully changed templates.", Toast.LENGTH_SHORT).show();
                        getActivity().setResult(2);
                    }
                    break;
            }

            return true;
        };

        public void changeTemplate(String newTemplate) {
            ArrayList<Workout> workouts;
            String[] mainArray;
            DatabaseHandler db = new DatabaseHandler(getContext());
            db.changeTemplates();
            ArrayList<Pair<String, Float>> mainLifts = db.getAllMainLifts();
            float squatsTm = mainLifts.get(0).second;
            float dlTm = mainLifts.get(1).second;
            float benchTm = mainLifts.get(2).second;
            float ohpTm = mainLifts.get(3).second;

            switch(newTemplate) {
                case "4-day":
                    workouts = Templates.create4day(dlTm, squatsTm, benchTm, ohpTm);
                    String[] array = { "Bench", "OHP", "Squat", "Deadlift", "Bench", "Bench", "Deadlift", "Squat" };
                    Templates.insertTemplate(db, workouts, array);
                    break;

                case "5-day":
                    workouts = Templates.create5day(dlTm, squatsTm, benchTm, ohpTm);
                    String[] array2 = { "Bench", "OHP", "Squat", "Deadlift", "OHP", "Bench", "Deadlift", "Squat", "Bench", "Bench" };
                    Templates.insertTemplate(db, workouts, array2);
                    break;

                case "6-day deadlift":
                    workouts = Templates.create6dayDeadlift(dlTm, squatsTm, benchTm, ohpTm);
                    String[] array3 = { "Bench", "OHP", "Deadlift", "Squat", "OHP", "Bench", "Squat", "Deadlift", "Bench", "Bench", "Deadlift", "Squat" };
                    Templates.insertTemplate(db, workouts, array3);
                    break;

                case "6-day squat":
                    workouts = Templates.create6daySquat(dlTm, squatsTm, benchTm, ohpTm);
                    String[] array4 = { "Bench", "OHP", "Squat", "Deadlift", "OHP", "Bench", "Deadlift", "Squat", "Bench", "Bench", "Squat", "Deadlift" };
                    Templates.insertTemplate(db, workouts, array4);
                    break;
                default:
                    Toast.makeText(getContext(), "Oops, something went wrong in changing templates.", Toast.LENGTH_SHORT).show();
            }
        }

    }



}