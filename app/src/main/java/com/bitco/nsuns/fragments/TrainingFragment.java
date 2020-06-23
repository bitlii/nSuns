package com.bitco.nsuns.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.activities.FinishTrainingActivity;
import com.bitco.nsuns.adapters.TrainingAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;

import java.util.ArrayList;
import java.util.List;

public class TrainingFragment extends Fragment {
    private DatabaseHandler db;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        db = new DatabaseHandler(getContext());
        ArrayList<Workout> data = db.getAllWorkouts();

        recyclerView = view.findViewById(R.id.dayList);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new TrainingAdapter(data);
        recyclerView.setAdapter(rAdapter);

        return view;
    }

    public void updateData() {
        ArrayList<Workout> newWorkouts = db.getAllWorkouts() ;
        rAdapter = new TrainingAdapter(newWorkouts);
        recyclerView.setAdapter(rAdapter);
    }

}