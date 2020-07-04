package com.bitco.nsuns.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.NewAccessoryFragment;
import com.bitco.nsuns.listeners.DialogFragmentListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddNewSetDialog extends DialogFragment {

    private boolean editMode = false;
    private float currentWeight;
    private int currentReps;
    private int position;

    public AddNewSetDialog() {
        super();
    }

    public AddNewSetDialog(int position, float currentWeight, int currentReps) {
        super();
        this.editMode = true;
        this.position = position;
        this.currentWeight = currentWeight;
        this.currentReps = currentReps;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_new_repset, null);
        builder.setView(view);

        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NewAccessoryFragment fragment = (NewAccessoryFragment) getFragmentManager().findFragmentById(R.id.layout);
                DialogFragmentListener frag = (DialogFragmentListener) fragment.getrAdapter();
                Bundle bundle = new Bundle();
                bundle.putBoolean("editMode", editMode);
                bundle.putFloat("weight", Float.parseFloat(((TextInputEditText)getDialog().findViewById(R.id.editWeight)).getText().toString()));
                bundle.putInt("reps", Integer.parseInt(((TextInputEditText)getDialog().findViewById(R.id.editReps)).getText().toString()));
                bundle.putInt("pos", position);
                frag.onReturnBundle(bundle);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddNewSetDialog.this.getDialog().cancel();
            }
        });

        if (editMode) {
            ((TextInputEditText) view.findViewById(R.id.editWeight)).setText(String.valueOf(currentWeight));
            ((TextInputEditText) view.findViewById(R.id.editReps)).setText(String.valueOf(currentReps));
        }

        return builder.create();
    }
}
