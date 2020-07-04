package com.bitco.nsuns.adapters;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.dialogs.AddNewSetDialog;
import com.bitco.nsuns.items.Exercise;
import com.bitco.nsuns.items.RepSet;
import com.bitco.nsuns.listeners.DialogFragmentListener;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class NewRepSetAdapter extends RecyclerView.Adapter<NewRepSetAdapter.NewRepSetViewHolder> implements DialogFragmentListener {

    private ArrayList<RepSet> repSets;
    private boolean editMode = false;

    public static class NewRepSetViewHolder extends RecyclerView.ViewHolder {
        private Button button;
        private TextView weight;
        private TextView percentage;
        private TextView reps;
        private ConstraintLayout constraintLayout;
        private MaterialCardView card;
        private ImageView delete;

        public NewRepSetViewHolder(View v) {
            super(v);
            button = v.findViewById(R.id.button_new_set);
            weight = v.findViewById(R.id.weight);
            percentage = v.findViewById(R.id.percentage);
            reps = v.findViewById(R.id.reps);
            constraintLayout = v.findViewById(R.id.constraint_layout);
            card = v.findViewById(R.id.card);
            delete = v.findViewById(R.id.button_delete);
        }
    }

    public NewRepSetAdapter() {
        this.repSets = new ArrayList<>();
    }

    // Edit Accessories
    public NewRepSetAdapter(Exercise accessory) {
        this.repSets = accessory.getSets();
        this.editMode = true;
    }

    @NonNull
    @Override
    public NewRepSetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if (viewType == R.layout.item_new_set) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_set, parent, false);
        }
        else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_btn_new_set, parent, false);
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
            holder.weight.setText(String.valueOf(repSets.get(position).getWeight()));
            holder.reps.setText(String.valueOf(repSets.get(position).getReps()));
            holder.percentage.setVisibility(View.GONE);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.constraintLayout);
            constraintSet.connect(R.id.weight, ConstraintSet.BOTTOM, R.id.constraint_layout, ConstraintSet.BOTTOM);
            constraintSet.centerVertically(R.id.weight, R.id.constraint_layout);
            constraintSet.applyTo(holder.constraintLayout);

            holder.card.setOnClickListener(view -> {
                DialogFragment newFrag = new AddNewSetDialog(position, repSets.get(position).getWeight(), repSets.get(position).getReps());
                Context context = view.getContext();
                newFrag.show(((AppCompatActivity)context).getSupportFragmentManager(), "Edit Set");
            });

            holder.delete.setOnClickListener(view -> {
                repSets.remove(position);
                notifyItemRemoved(position);
            });
        }


    }

    @Override
    public void onReturnValue(String value, int position) { }

    @Override
    public void onReturnBundle(Bundle bundle) {
        float weight = bundle.getFloat("weight");
        int reps = bundle.getInt("reps");
        boolean isEditMode = bundle.getBoolean("editMode");
        int pos = bundle.getInt("pos");
        if (isEditMode) {
            RepSet repSet = repSets.get(pos);
            repSet.setWeight(weight);
            repSet.setReps(reps);
        }
        else {
            RepSet repset = new RepSet(weight, reps);
            repSets.add(repset);
        }
        notifyItemChanged(pos);
    }


    @Override
    public int getItemCount() {
        return repSets.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == repSets.size()) ? R.layout.item_btn_new_set : R.layout.item_new_set;
    }

    public ArrayList<RepSet> getRepSets() {
        return repSets;
    }
}
