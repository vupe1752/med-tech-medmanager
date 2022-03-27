package com.myapps.medmanagementp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MedDialog.MedDialogListener {

    TextView btnAdd, btnExport;
    ListView medList, archivedMedList;
    WDbHelper WDbHelper;
    ArrayList<Meds> med_liSt, archived_mEd;
    static String med_naMe = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnAdd = findViewById(R.id.btn_add);
        medList = findViewById(R.id.active_med_list);
        archivedMedList = findViewById(R.id.archived_med_list);
        btnExport = findViewById(R.id.btn_export);

        WDbHelper = new WDbHelper(this);

        //List active meds
        med_liSt = new ArrayList<>();
        MedListAdapter adaptera = new MedListAdapter(this,
                R.layout.medlist_adapter_layout, med_liSt);
        medList.setAdapter(adaptera);
        Cursor data = WDbHelper.getActiveMedicationList();
        while (data.moveToNext()) {
            String test = data.getString(1);
            med_liSt.add(new Meds (data.getString(1), data.getString(2), data.getString(3),
                    data.getString(7)));
            adaptera.notifyDataSetChanged(); }
        setListViewHeightBasedOnItems(medList);

        // List of archived meds
        archived_mEd = new ArrayList<>();
        MedArchivedListAdapter adapterb = new MedArchivedListAdapter(this,
                R.layout.archivedmed_adapter, archived_mEd);
        archivedMedList.setAdapter(adapterb);
        Cursor datab = WDbHelper.getArchivedMedicationList();
        while (datab.moveToNext()) {
            archived_mEd.add(new Meds (datab.getString(1), "", "",
                    ""));
            adapterb.notifyDataSetChanged(); }
        setListViewHeightBasedOnItems(archivedMedList);

        //Add a new med
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        //Export medication details into an excel file
        btnExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent (MainActivity.this, ExportMeds.class);
                startActivity(intent);
            }
        });

    }

    public void openDialog() {
        MedDialog medDialog = new MedDialog();
        medDialog.show(getSupportFragmentManager(), "Enter a name dialog");
    }

    @Override
    public void applyTextsMed(String med_dialOg) {
        med_naMe = med_dialOg;
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }

    }

}
