package com.bitco.nsuns.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bitco.nsuns.R;
import com.bitco.nsuns.adapters.HomeAdapter;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;
import java.util.Map;


public class HomeFragment extends Fragment {

    ArrayList<Pair<String, Float>> lifts;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public HomeFragment(ArrayList<Pair<String, Float>> mainLifts) {
        super();
        this.lifts = mainLifts;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeAdapter(lifts);
        recyclerView.setAdapter(adapter);

        return view;
    }

}