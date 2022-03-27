package com.myapps.medmanagementp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class EditMed extends AppCompatActivity implements SelectDateDialog.SelectDateDialogListener,
        SelectDateDialogB.SelectDateDialogBListener {

    EditText dose1, instruction1, note1, providerName1, startDateText1, endDateText1;
    TextView testText, startDate1, endDate1, setReminder1, btnSave1, btnCancel1, btnMed1;
    static String emed_naMe;
    WDbHelper WDbHelper;
    ListView listView1;
    String testiNg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medication_layout);


        btnSave1 = findViewById(R.id.save1);
        btnCancel1 = findViewById(R.id.cancel1);
        btnMed1 = findViewById(R.id.med_name1);
        dose1 = findViewById(R.id.dose1);
        note1 = findViewById(R.id.note1);
        instruction1 = findViewById(R.id.instruction1);
        testText = findViewById(R.id.testing);
        testText.setVisibility(View.INVISIBLE);
        startDate1 = findViewById(R.id.btnstart_date1);
        endDate1 = findViewById(R.id.btnend_date1);
        startDateText1 = findViewById(R.id.start_date1);
        endDateText1 = findViewById(R.id.end_date1);

        providerName1 = findViewById(R.id.provider_name1);

        emed_naMe = MedListAdapter.edit_mEd;
        btnMed1.setText(emed_naMe);

        WDbHelper = new WDbHelper(this);

        //Display in progress med details
        Cursor dataMedDetails = WDbHelper.getMedicationDetails(emed_naMe);
        while (dataMedDetails .moveToNext()) {
            btnMed1.setText(dataMedDetails .getString(1));
            dose1.setText (dataMedDetails .getString(2));
            instruction1.setText(dataMedDetails .getString(3));
            note1.setText(dataMedDetails.getString(4));
            startDateText1.setText(dataMedDetails.getString(5));
            endDateText1.setText(dataMedDetails.getString(6));
            providerName1.setText(dataMedDetails.getString(7));
            startDateText1.setText(dataMedDetails.getString(5));
            endDateText1.setText(dataMedDetails.getString(6));}

        //Enter dates
        startDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDate();
            }
        });
        endDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogDateB();
            }
        });

        btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1a = new Intent (EditMed.this, MainActivity.class);
                startActivity(intent1a);
            }
        });

        btnSave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String predose=dose1.getText().toString();
                String dose1=predose.replaceAll(",", "");
                String dose=dose1.replaceAll("\n", " ");

                String preinstruction = instruction1.getText().toString();
                String instruction1 = preinstruction.replaceAll(",", "");
                String instruction = instruction1.replaceAll("\n", " ");

                String preprovider = providerName1.getText().toString();
                String provider1 = preprovider.replaceAll(",", "");
                String provider = provider1.replaceAll("\n", " ");

                String prenote = note1.getText().toString();
                String note1 = prenote.replaceAll(",", "");
                String note = note1.replaceAll("\n", " ");

                WDbHelper.updateMed(emed_naMe,dose,instruction,note,provider);
                WDbHelper.updateStart(emed_naMe, startDateText1.getText().toString());
                WDbHelper.updateEnd(emed_naMe, endDateText1.getText().toString());

                startDateText1.setText("");
                endDateText1.setText("");
                btnMed1.setText("");
                emed_naMe = null;

                Intent intent1b = new Intent (EditMed.this, MainActivity.class);
                startActivity(intent1b);
            }
        });
    }

    public void openDialogDate() {
        SelectDateDialog selectDateDialog = new SelectDateDialog();
        selectDateDialog.show(getSupportFragmentManager(), "Select a date dialog");
    }
    @Override
    public void applyTexts(String selectedDate, int mon, int dofM, int yeAr) {
        startDateText1.setText(selectedDate);
        WDbHelper.updateStart(emed_naMe, selectedDate);
    }

    public void openDialogDateB() {
        SelectDateDialogB selectDateDialogB = new SelectDateDialogB();
        selectDateDialogB.show(getSupportFragmentManager(), "Select a date dialog");
    }
    @Override
    public void applyTextsB(String selectedDateB) {
        endDateText1.setText(selectedDateB);
        WDbHelper.updateEnd(emed_naMe, selectedDateB);
    }
}



