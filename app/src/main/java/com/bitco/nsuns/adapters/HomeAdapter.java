package com.bitco.nsuns.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    ArrayList<Pair<String, Float>> lifts;

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView weightText;
        public TextView unitText;
        public LinearLayout layout;

        public HomeViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title);
            weightText = v.findViewById(R.id.accessory_name);
            unitText = v.findViewById(R.id.unit_text);
            layout = v.findViewById(R.id.layout);
        }
    }
    public HomeAdapter(ArrayList<Pair<String, Float>> lifts) {
        this.lifts = lifts;
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
        h.title.setText(lifts.get(pos).first);
        h.weightText.setText(String.valueOf(lifts.get(pos).second));
        h.unitText.setText(R.string.unit_metric);
    }

    @Override
    public int getItemCount() {
        return lifts.size();
    }

}
