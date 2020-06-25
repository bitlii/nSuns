package com.bitco.nsuns.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.activities.WorkoutActivity;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.Workout;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private List<Workout> dataset;
    private View view;

    public static class TrainingViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView exerciseOne;
        public TextView exerciseTwo;
        public LinearLayout layout;

        public TrainingViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.textHeader);
            exerciseOne = v.findViewById(R.id.exerciseOne);
            exerciseTwo = v.findViewById(R.id.exerciseTwo);
            layout = v.findViewById(R.id.parent);
        }
    }

    public TrainingAdapter(List<Workout> data) {
        dataset = data;
    }

    @NonNull
    @Override
    public TrainingAdapter.TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_workout, parent, false);
        view = v;
        TrainingViewHolder vh = new TrainingViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TrainingViewHolder holder, int position) {
        String headerText = "Day " + (position + 1);
        holder.header.setText(headerText);
        Exercise exercise = dataset.get(position).getPrimaryExercise();
        holder.exerciseOne.setText(exercise.getName());
        exercise = dataset.get(position).getSecondaryExercise();
        holder.exerciseTwo.setText(exercise.getName());

        holder.layout.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, WorkoutActivity.class);
            intent.putExtra("primaryExercise", dataset.get(position).getPrimaryExercise());
            intent.putExtra("secondaryExercise", dataset.get(position).getSecondaryExercise());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
