package com.bitco.nsuns.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;

import com.bitco.nsuns.activities.FinishTrainingActivity;
import com.bitco.nsuns.listeners.DialogFragmentListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class UpdateExerciseDialog extends DialogFragment {

    private NumberPicker picker;
    private String exerciseName;
    // this is super temporary!!! todo: change this hardcoded stuff.
    private String[] increments = {"5", "2.5", "0", "-2.5", "-5"};

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        picker = new NumberPicker(getContext());
        picker.setDisplayedValues(increments);
        picker.setMinValue(0);
        picker.setMaxValue(4);
        picker.setValue(2);
        picker.setWrapSelectorWheel(false);

        builder.setView(picker);
        builder.setTitle("Update Training Max").setMessage("Increase/Decrease TM");

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selectedIncrement = increments[picker.getValue()];
                //DialogFragmentListener activity = (DialogFragmentListener) getActivity();
                FinishTrainingActivity activity = (FinishTrainingActivity) getActivity();
                DialogFragmentListener frag = (DialogFragmentListener) activity.getrAdapter();
                Bundle bundle = getArguments();
                frag.onReturnValue(selectedIncrement, bundle.getInt("position"));
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            //tbd
        });

        return builder.create();
    }


}
