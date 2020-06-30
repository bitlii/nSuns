package com.bitco.nsuns.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.AccessoriesAdapter;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AccessoriesFragment extends Fragment {

    Workout workout;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public AccessoriesFragment(Workout workout) {
        super();
        this.workout = workout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accessories, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new AccessoriesAdapter(this.workout);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
