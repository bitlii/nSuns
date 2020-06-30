package com.bitco.nsuns.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.WorkoutAdapter;
import com.bitco.nsuns.items.Exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainWorkoutFragment extends Fragment {

    Exercise exercise;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public MainWorkoutFragment(Exercise e) {
        super();
        exercise = e;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_workout, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new WorkoutAdapter(exercise);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
