package com.bitco.nsuns.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private ArrayList<Exercise> exercises;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView weightText;
        public TextView unitText;
        public LinearLayout layout;

        public HomeViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            weightText = v.findViewById(R.id.weightText);
            unitText = v.findViewById(R.id.unitText);
            layout = v.findViewById(R.id.layout);
        }
    }
    public HomeAdapter(ArrayList<Exercise> data) {
        exercises = data;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_exercise, parent, false);
        HomeViewHolder vh = new HomeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder h, int pos) {
        h.title.setText(exercises.get(pos).getName());
        h.weightText.setText(String.valueOf(exercises.get(pos).getTm()));
        h.unitText.setText(R.string.unitMetric);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

}
