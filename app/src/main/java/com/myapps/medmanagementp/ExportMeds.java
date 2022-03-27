package com.myapps.medmanagementp;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;


import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExportMeds extends AppCompatActivity {
    WDbHelper wDbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export_layout);

        Button mainPage = (Button) findViewById(R.id.main_page);

        String date = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        StringBuilder data5 = new StringBuilder();
        data5.append("Medications, Status, Dose, Instruction, Provider, Start Date, End Date, Note");

        wDbHelper = new WDbHelper(ExportMeds.this);
        Cursor dataExport5 = wDbHelper.getActiveMedicationList();
        while (dataExport5.moveToNext()) {
            data5.append("\n" + dataExport5.getString(1) + "," + dataExport5.getString(8) + "," +
                    dataExport5.getString(2) + "," + dataExport5.getString(3) + "," + dataExport5.getString(7) +
                    ","+ dataExport5.getString(5) + "," + dataExport5.getString(6) + "," + dataExport5.getString(4));}

        Cursor dataExport5a = wDbHelper.getArchivedMedicationList();
        while (dataExport5a.moveToNext()) {
            data5.append("\n"+ dataExport5a.getString(1) + "," + dataExport5a.getString(8) + "," +
                    dataExport5a.getString(2) + "," + dataExport5a.getString(3) + "," + dataExport5a.getString(7) +
                    ","+ dataExport5a.getString(5) + "," + dataExport5a.getString(6) + "," + dataExport5a.getString(4));}
        try {
            //Save file into device
            FileOutputStream out = openFileOutput("medication_list.csv", MODE_PRIVATE);
            out.write((data5.toString()).getBytes());
            out.close();

            //Exporting
            Context context = getApplicationContext();
            File newFile = new File(getFilesDir(), "medication_list.csv");

            Uri path = FileProvider.getUriForFile(context, "com.mydomain.medmanagementp",
                    newFile);
            Log.d("photoURI", path.toString());
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Medication List on " + date);
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send mail"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExportMeds.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

