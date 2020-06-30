package com.bitco.nsuns.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.dialogs.UpdateExerciseDialog;
import com.bitco.nsuns.listeners.DialogFragmentListener;
import com.bitco.nsuns.items.Exercise;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

public class UpdateExerciseAdapter extends RecyclerView.Adapter<UpdateExerciseAdapter.UpdateExerciseViewHolder> implements DialogFragmentListener {

    private ArrayList<Exercise> dataset;
    private ArrayList<Float> selectedChanges;

    public static class UpdateExerciseViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView weightText;
        private TextView changeText;
        public LinearLayout layout;

        public UpdateExerciseViewHolder(View v) {
            super(v);
            weightText = v.findViewById(R.id.accessoryName);
            title = v.findViewById(R.id.title);
            changeText = v.findViewById(R.id.change);
            layout = v.findViewById(R.id.layout);
        }
    }

    public UpdateExerciseAdapter(ArrayList<Exercise> data) {
        this.dataset = data;
        selectedChanges = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            selectedChanges.add(0f);
        }
    }

    @NonNull
    @Override
    public UpdateExerciseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_update_exercise_tm, parent, false);
        UpdateExerciseViewHolder vh = new UpdateExerciseViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(UpdateExerciseAdapter.UpdateExerciseViewHolder holder, int position) {
        holder.title.setText(dataset.get(position).getName());
        holder.weightText.setText(Float.toString(dataset.get(position).getTm()));
        holder.changeText.setText("Change: " + selectedChanges.get(position));
        holder.layout.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);

            DialogFragment newFrag = new UpdateExerciseDialog();
            Context context = view.getContext();
            newFrag.setArguments(bundle);
            newFrag.show(((AppCompatActivity)context).getSupportFragmentManager(), "Update TM");

        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    @Override
    public void onReturnValue(String value, int pos) {
        Log.i("onReturnValue", value + " FROM ADAPTER");
        selectedChanges.set(pos, Float.valueOf(value));
        notifyItemChanged(pos);
    }

    @Override
    public void onReturnBundle(Bundle bundle) { }

    public ArrayList<Float> getSelectedChanges() {
        return selectedChanges;
    }
}
