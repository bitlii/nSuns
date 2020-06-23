package com.bitco.nsuns.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.dialogs.UpdateExerciseDialog;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {
    private Exercise exercise;
    private ArrayList<RepSet> setList;
    private View view;

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder {
        private TextView percentage;
        private TextView weight;
        private TextView reps;
        private LinearLayout layout;

        public WorkoutViewHolder(View v) {
            super(v);
            percentage = v.findViewById(R.id.percentage);
            weight = v.findViewById(R.id.weight);

            layout = v.findViewById(R.id.parent);
            reps = v.findViewById(R.id.reps);
        }
    }

    public WorkoutAdapter(Exercise e) {
        setList = e.getSets();
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_set, parent, false);
        view = v;
        WorkoutViewHolder vh = new WorkoutViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        RepSet set = setList.get(position);
        holder.weight.setText(String.valueOf(set.getWeight()));
        holder.percentage.setText((set.getPercent() * 100) + "% of TM");

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
