package com.bitco.nsuns.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;

import com.bitco.nsuns.R;
import com.bitco.nsuns.fragments.NewAccessoryFragment;
import com.bitco.nsuns.listeners.DialogFragmentListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddNewSetDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.dialog_new_repset, null));
        builder.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NewAccessoryFragment fragment = (NewAccessoryFragment) getFragmentManager().findFragmentById(R.id.layout);
                DialogFragmentListener frag = (DialogFragmentListener) fragment.getrAdapter();
                Bundle bundle = new Bundle();
                bundle.putFloat("weight", Float.parseFloat(((EditText)getDialog().findViewById(R.id.editWeight)).getText().toString()));
                bundle.putInt("reps", Integer.parseInt(((EditText)getDialog().findViewById(R.id.editReps)).getText().toString()));
                frag.onReturnBundle(bundle);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddNewSetDialog.this.getDialog().cancel();
            }
        });


        return builder.create();
    }
}
