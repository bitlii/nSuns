package com.bitco.nsuns.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.TrainingAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Workout;

import java.util.ArrayList;
import java.util.Collections;

public class TrainingFragment extends Fragment {
    private DatabaseHandler db;
    ArrayList<Workout> workouts;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public TrainingFragment(ArrayList<Workout> workouts) {
        super();
        this.workouts = workouts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training, container, false);

        recyclerView = view.findViewById(R.id.workoutList);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new TrainingAdapter(workouts);
        recyclerView.setAdapter(rAdapter);

        return view;
    }

    public ArrayList<Workout> updateData() {
        DatabaseHandler db = new DatabaseHandler(getContext());
        ArrayList<Workout> newWorkouts = db.getAllWorkouts();
        rAdapter = new TrainingAdapter(newWorkouts);
        recyclerView.setAdapter(rAdapter);

        return newWorkouts;
    }


}