package com.bitco.nsuns.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.HomeAdapter;
import com.bitco.nsuns.database.DatabaseHandler;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    public DatabaseHandler db;
    private ArrayList<Exercise> exercises;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        db = new DatabaseHandler(getContext());
        exercises = db.getPrimaryExerciseList();

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new HomeAdapter(exercises);
        recyclerView.setAdapter(rAdapter);

        return view;
    }

}