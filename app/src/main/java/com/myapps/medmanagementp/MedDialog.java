package com.myapps.medmanagementp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class MedDialog extends AppCompatDialogFragment {
    EditText medDialog;
    WDbHelper WDbHelper;
    String med_dialOg;
    private MedDialogListener medDialogListener;
    TextView dupCheck;
    CheckBox scanLabel;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.meddialog_layout, null);

        WDbHelper = new WDbHelper(getActivity());
        medDialog = view.findViewById(R.id.med_dialog);
        dupCheck = view.findViewById(R.id.testing);
        dupCheck.setVisibility(View.INVISIBLE);
        scanLabel = view.findViewById(R.id.scan_med);

        builder.setView(view)
                .setTitle("Enter Medication Name or Scan Medication Label")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (scanLabel.isChecked()) {
                                    Intent intenta = new Intent (getActivity(), CameraPage.class);
                                    startActivity(intenta);
                                }
                                else if (medDialog.length() != 0) {
                                    med_dialOg = medDialog.getText().toString();
                                    String name1 = med_dialOg.replaceAll(",", "");
                                    String name = name1.replaceAll("\n", " ");

                                    Cursor dataDupSea = WDbHelper.searchForDupMed(name);
                                    while (dataDupSea.moveToNext()) {
                                        dupCheck.setText(dataDupSea.getString(1));}
                                    if (dupCheck.length() != 0) {
                                        Toast.makeText(getContext(),  "This medication already exists. Please enter a " +
                                                "different name (Exp: Ibuprofen #2)!", Toast.LENGTH_LONG).show();
                                    }
                                    else { medDialogListener.applyTextsMed(name);
                                        WDbHelper.createMed(name, "active");
                                        medDialog.setText("");
                                        Intent intent = new Intent(getActivity(), AAddMedication.class);
                                        startActivity(intent);
                                        Toast.makeText(getContext(),  "Med was created", Toast.LENGTH_LONG).show();}}
                                else {
                                    Toast.makeText(getContext(),  "Please enter a medication name or select to scan a medication label", Toast.LENGTH_LONG).show();}
                                ;}

                        }
                );

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            medDialogListener = (MedDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface MedDialogListener {
        void applyTextsMed(String med_dialOg);
    }
}


