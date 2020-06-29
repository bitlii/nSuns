package com.bitco.nsuns.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.dialogs.AddNewSetDialog;
import com.bitco.nsuns.items.RepSet;
import com.bitco.nsuns.listeners.DialogFragmentListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class NewRepSetAdapter extends RecyclerView.Adapter<NewRepSetAdapter.NewRepSetViewHolder> implements DialogFragmentListener {

    private ArrayList<RepSet> repSets;

    public static class NewRepSetViewHolder extends RecyclerView.ViewHolder {
        private Button button;
        private TextView textWeight;
        private TextView textReps;

        public NewRepSetViewHolder(View v) {
            super(v);
            button = v.findViewById(R.id.button_new_set);
            textWeight = v.findViewById(R.id.textWeight);
            textReps = v.findViewById(R.id.textReps);

        }
    }

    public NewRepSetAdapter() {
        this.repSets = new ArrayList<>();
    }

    @NonNull
    @Override
    public NewRepSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == R.layout.card_new_set) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_new_set, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_set, parent, false);
        }
        NewRepSetAdapter.NewRepSetViewHolder vh = new NewRepSetViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NewRepSetViewHolder holder, int position) {
        if (position == repSets.size()) {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DialogFragment newFrag = new AddNewSetDialog();
                    Context context = view.getContext();
                    newFrag.show(((AppCompatActivity)context).getSupportFragmentManager(), "Add New Set");

                }
            });
        }
        else {
            holder.textWeight.setText(String.valueOf(repSets.get(position).getWeight()));
            holder.textReps.setText(String.valueOf(repSets.get(position).getReps()));
        }
    }

    @Override
    public void onReturnValue(String value, int position) { }

    @Override
    public void onReturnBundle(Bundle bundle) {
        float weight = bundle.getFloat("weight");
        int reps = bundle.getInt("reps");

        RepSet repset = new RepSet(weight, reps);
        repSets.add(repset);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return repSets.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == repSets.size()) ? R.layout.item_new_set : R.layout.card_new_set;
    }

    public ArrayList<RepSet> getRepSets() {
        return repSets;
    }
}
