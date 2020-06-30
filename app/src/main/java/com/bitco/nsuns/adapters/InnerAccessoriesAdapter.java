package com.bitco.nsuns.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InnerAccessoriesAdapter extends RecyclerView.Adapter<InnerAccessoriesAdapter.InnerAccessoriesViewHolder> {

    private Exercise exercise;
    private ArrayList<RepSet> repSets;

    public static class InnerAccessoriesViewHolder extends RecyclerView.ViewHolder {
        private TextView percentage;
        private TextView weight;
        private TextView reps;
        private LinearLayout layout;

        public InnerAccessoriesViewHolder(View v) {
            super(v);
            percentage = v.findViewById(R.id.percentage);
            weight = v.findViewById(R.id.weight);
            layout = v.findViewById(R.id.layout);
            reps = v.findViewById(R.id.reps);
        }
    }

    public InnerAccessoriesAdapter(Exercise exercise) {
        this.exercise = exercise;
        this.repSets = exercise.getSets();
    }

    @NonNull
    @Override
    public InnerAccessoriesAdapter.InnerAccessoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_set, parent, false);
        InnerAccessoriesViewHolder vh = new InnerAccessoriesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull InnerAccessoriesAdapter.InnerAccessoriesViewHolder holder, int position) {
        RepSet set = repSets.get(position);
        holder.weight.setText(String.valueOf(set.getWeight()));

        holder.percentage.setText("");

        if (set.isAmrap()) {
            holder.reps.setText(set.getReps() + "+");
        }
        else {
            holder.reps.setText(Integer.toString(set.getReps()));
        }
    }

    @Override
    public int getItemCount() {
        return repSets.size();
    }
}
