package com.myapps.medmanagementp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class  SelectDateDialog extends AppCompatDialogFragment {
    private SelectDateDialogListener SDlistener;
    CalendarView calendarView;
    static String selectedDate;
    TextView editDialog;
    int mon, dofM, yeAr;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.select_date_dialog, null);

        calendarView = view.findViewById(R.id.calendarView);
        editDialog = view.findViewById(R.id.text_dialog);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                if (month < 10) {
                    if (dayOfMonth < 10) {
                        selectedDate = "0" + Integer.toString(month + 1) + "/" + "0" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year);}
                    if (dayOfMonth >= 10) {
                        selectedDate = "0" + Integer.toString(month + 1) + "/" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year); }
                }
                else {
                    if (dayOfMonth < 10) {
                        selectedDate = Integer.toString(month + 1) + "/" + "0" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year);}
                    if (dayOfMonth >= 10) {
                        selectedDate = Integer.toString(month + 1) + "/" + Integer.toString(dayOfMonth) + "/" + Integer.toString(year);}
                }
                mon = month + 1;
                dofM = dayOfMonth;
                yeAr = year;
                editDialog.setText(selectedDate);
            }
        });

        editDialog.setVisibility(View.INVISIBLE);

        builder.setView(view)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (editDialog.length() != 0) {
                            editDialog.setText("");
                            SDlistener.applyTexts(selectedDate, mon, dofM, yeAr);}
                        else {
                            Toast.makeText(getContext(),  "Please select a date", Toast.LENGTH_LONG).show();}
                    }
                });

        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            SDlistener = (SelectDateDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface SelectDateDialogListener {
        void applyTexts(String selectedDate, int mon, int dofM, int yeAr);
    }
}

