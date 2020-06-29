package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.UpdateExerciseAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;

public class FinishTrainingActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<Exercise> primaryExercises;

    private RecyclerView recyclerView;
    private UpdateExerciseAdapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_training);

        db = new DatabaseHandler(this);
        primaryExercises = db.getPrimaryExerciseList();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new UpdateExerciseAdapter(primaryExercises);
        recyclerView.setAdapter(rAdapter);
    }

    public void finishTraining(View view) {
        for(int i=0; i<primaryExercises.size(); i++) {
            float change = rAdapter.getSelectedChanges().get(i);
            float newTm = primaryExercises.get(i).getTm() + change;
            db.updateExerciseTrainingMax(primaryExercises.get(i).getName(), newTm);

            if (change != 0) {
                primaryExercises.get(i).setTm(newTm);
                db.updateRepSets(primaryExercises.get(i));
            }

        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    public void back(View v) {
        finish();
    }

    public RecyclerView.Adapter getrAdapter() {
        return rAdapter;
    }
}