package com.jleung.breadcrumb.breadcrumbs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.jleung.breadcrumb.R;

public class NewCrumbDialog extends DialogFragment {

    private static final String TAG = NewCrumbDialog.class.getName();

    // Implement listener to pass input back
    public interface NewCrumbDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String description);
    }

    private NewCrumbDialogListener listener;

    // Attach listener to the activity which calls this
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NewCrumbDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    requireActivity().toString() + " must implement NewCrumbDialogListener."
            );
        }
    }

    @SuppressLint("InflateParams")
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View userInputView = inflater.inflate(R.layout.dialog_new_crumb, null);

        builder.setTitle("Drop a Breadcrumb")
                .setView(userInputView)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText descriptionEdit = userInputView.findViewById(R.id.enter_desc);
                        String description = descriptionEdit.getText().toString();
                        Log.d(TAG, "Received description: " + description);

                        listener.onDialogPositiveClick(NewCrumbDialog.this, description);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        return builder.create();
    }


}
