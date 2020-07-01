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
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TrainingAdapter extends RecyclerView.Adapter<TrainingAdapter.TrainingViewHolder> {

    private ArrayList<Workout> dataset;

    public static class TrainingViewHolder extends RecyclerView.ViewHolder {
        public TextView header;
        public TextView exerciseOne;
        public TextView exerciseTwo;
        public MaterialCardView card;
        public LinearLayout layout;
        public TextView accessoryCount;

        public TrainingViewHolder(View v) {
            super(v);
            header = v.findViewById(R.id.header);
            exerciseOne = v.findViewById(R.id.exercise_one);
            exerciseTwo = v.findViewById(R.id.exercise_two);
            layout = v.findViewById(R.id.layout);
            card = v.findViewById(R.id.card);
            accessoryCount = v.findViewById(R.id.accessory_count);
        }
    }

    public TrainingAdapter(ArrayList<Workout> data) {
        dataset = data;
    }

    @NonNull
    @Override
    public TrainingAdapter.TrainingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false);
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

        int workoutAccSize = dataset.get(position).getAccessories().size();
        if (workoutAccSize == 0) {
            holder.accessoryCount.setText("");
        }
        else if (workoutAccSize == 1) {
            holder.accessoryCount.setText(workoutAccSize + " accessory");
        }
        else {
            holder.accessoryCount.setText(workoutAccSize + " accessories");
        }


        holder.card.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(context, WorkoutActivity.class);
            intent.putExtra("workout", dataset.get(position));
            ((Activity) context).startActivityForResult(intent, 0);
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
