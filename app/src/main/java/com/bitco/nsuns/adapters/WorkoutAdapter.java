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

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private ArrayList<RepSet> setList;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private TextView percentage;
        private TextView weight;
        private TextView reps;

        public WorkoutViewHolder(View v) {
            super(v);
            percentage = v.findViewById(R.id.percentage);
            weight = v.findViewById(R.id.weight);
            reps = v.findViewById(R.id.reps);
        }
    }

    public WorkoutAdapter(Exercise e) {
        setList = e.getSets();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set, parent, false);
        WorkoutViewHolder vh = new WorkoutViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        RepSet set = setList.get(position);
        holder.weight.setText(String.valueOf(set.getWeight()));

        holder.percentage.setText(Math.round(set.getPercent() * 100) + "% of TM");

        if (set.isAmrap()) {
            holder.reps.setText(set.getReps() + "+");
        }
        else {
            holder.reps.setText(Integer.toString(set.getReps()));
        }
    }

    @Override
    public int getItemCount() {
        return setList.size();
    }
}
