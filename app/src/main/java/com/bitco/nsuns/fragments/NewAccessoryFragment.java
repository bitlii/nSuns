package com.bitco.nsuns.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.bitco.nsuns.R;
import com.bitco.nsuns.activities.WorkoutActivity;
import com.bitco.nsuns.adapters.NewRepSetAdapter;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;

import java.util.ArrayList;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewAccessoryFragment extends Fragment {

    private boolean isEditMode = false;
    private Exercise accessory;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager layoutManager;

    public NewAccessoryFragment() {
        super();
    }

    public NewAccessoryFragment(Exercise e) {
        super();
        this.accessory = e;
        this.isEditMode = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_accessory, container, false);

        recyclerView = view.findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        EditText editName = view.findViewById(R.id.editName);
        if (isEditMode) {
            editName.setText(accessory.getName());
            rAdapter = new NewRepSetAdapter(accessory);
            recyclerView.setAdapter(rAdapter);
        }
        else {
            rAdapter = new NewRepSetAdapter();
            recyclerView.setAdapter(rAdapter);
        }

        ((Button) view.findViewById(R.id.buttonFinish)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                if (!isEditMode) {
                    ArrayList<RepSet> repSets = ((NewRepSetAdapter) getrAdapter()).getRepSets();
                    Exercise e = new Exercise(name, repSets);
                    WorkoutActivity activity = (WorkoutActivity) getActivity();
                    activity.finishAddAccessory(e);
                }
                else {
                    accessory.setName(name);
                    WorkoutActivity activity = (WorkoutActivity) getActivity();
                    activity.finishEditAccessory();
                }

            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(simpleCallback);
        helper.attachToRecyclerView(recyclerView);

        return view;
    }

    public RecyclerView.Adapter getrAdapter() {
        return rAdapter;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            int fromPos = viewHolder.getAdapterPosition();
            int toPos = target.getAdapterPosition();

            Collections.swap(accessory.getSets(), fromPos, toPos);
            rAdapter.notifyItemMoved(fromPos, toPos);
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

        }
    };

}
