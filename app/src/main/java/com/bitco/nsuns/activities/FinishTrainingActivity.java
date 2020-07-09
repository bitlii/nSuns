package com.bitco.nsuns.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.UpdateExerciseAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;

public class FinishTrainingActivity extends AppCompatActivity {

    public DatabaseHandler db;
    ArrayList<Pair<String, Float>> mainLifts;

    private RecyclerView recyclerView;
    private UpdateExerciseAdapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_training);

        db = new DatabaseHandler(this);
        mainLifts = db.getAllMainLifts();

        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new UpdateExerciseAdapter(mainLifts);
        recyclerView.setAdapter(rAdapter);
    }

    public void finishTraining(View view) {
        for(int i=0; i<mainLifts.size(); i++) {
            float change = rAdapter.getSelectedChanges().get(i);
            float newTm = mainLifts.get(i).second + change;

            if (change != 0) {
                db.updateExerciseTrainingMax(mainLifts.get(i).first, newTm);
            }

            /*
            if (change != 0) {
                primaryExercises.get(i).setTm(newTm);
                db.updateRepSets(primaryExercises.get(i));
            }*/
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