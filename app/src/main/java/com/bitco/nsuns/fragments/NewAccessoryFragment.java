package com.bitco.nsuns.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.activities.WorkoutActivity;
import com.bitco.nsuns.adapters.AccessoriesAdapter;
import com.bitco.nsuns.adapters.HomeAdapter;
import com.bitco.nsuns.adapters.NewRepSetAdapter;
import com.bitco.nsuns.adapters.WorkoutAdapter;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewAccessoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public NewAccessoryFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_accessory, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        rAdapter = new NewRepSetAdapter();
        recyclerView.setAdapter(rAdapter);
        EditText editName = view.findViewById(R.id.editName);

        ((Button) view.findViewById(R.id.buttonFinish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<RepSet> repSets = ((NewRepSetAdapter) getrAdapter()).getRepSets();
                String name = editName.getText().toString();

                Exercise e = new Exercise(name, repSets);
                WorkoutActivity activity = (WorkoutActivity) getActivity();
                activity.finishAddAccessory(e);
            }
        });

        return view;
    }

    public RecyclerView.Adapter getrAdapter() {
        return rAdapter;
    }

}
