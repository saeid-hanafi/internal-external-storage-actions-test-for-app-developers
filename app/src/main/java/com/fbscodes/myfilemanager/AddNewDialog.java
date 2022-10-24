package com.fbscodes.myfilemanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddNewDialog extends DialogFragment {
    private addNewDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (addNewDialogListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_new_folder_dialog, null, false);
        TextInputLayout textInputLayout = view.findViewById(R.id.add_new_title_input);
        TextInputEditText newFileName = view.findViewById(R.id.add_new_title_input_text);
        MaterialButton addNewBtn = view.findViewById(R.id.add_new_folder_btn);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newFileName.length() > 0) {
                    listener.onCreateNewFolderListener(newFileName.getText().toString());
                    dismiss();
                }else
                    textInputLayout.setError("Folder Name Is Empty!!");
            }
        });

        builder.setView(view);
        return builder.create();
    }

    public interface addNewDialogListener {
        void onCreateNewFolderListener(String folderName);
    }
}
