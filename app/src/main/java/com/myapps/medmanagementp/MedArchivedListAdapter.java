package com.myapps.medmanagementp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class MedArchivedListAdapter extends ArrayAdapter<Meds> {
    private Context mContext3;
    int mResource3;
    ArrayList<Meds> archived_mEd;
    WDbHelper WDbHelper;

    public MedArchivedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Meds> objects) {
        super(context, resource, objects);
        mContext3 = context;
        mResource3 = resource;
        archived_mEd = objects;
        WDbHelper = new WDbHelper(mContext3);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the person info
        final String naMe3 = getItem(position).getNaMe();
        final String doSe3 = getItem(position).getDoSe();
        final String instructiOn3 = getItem(position).getInstructions();
        final String provider3 = getItem(position).getProvidEr();

        //create the person object with the injo
        final Meds meds = new Meds(naMe3, doSe3, instructiOn3, provider3);

        LayoutInflater inflater = LayoutInflater.from(mContext3);
        convertView = inflater.inflate(mResource3, parent, false);

        final TextView medName3 = (TextView) convertView.findViewById(R.id.med_name5);
        TextView btnControl = (TextView) convertView.findViewById(R.id.status_control5);
        TextView btnDelete = (TextView) convertView.findViewById(R.id.delete5);

        SpannableString spannable = new SpannableString(naMe3);
        spannable.setSpan(new StrikethroughSpan(), 0, naMe3.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        medName3.setText(spannable);

        btnControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WDbHelper.updateStatus(naMe3, "active");
                Intent intent = new Intent (getContext(), MainActivity.class);
                mContext3.startActivity(intent);
            }});

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3b = new AlertDialog.Builder(mContext3);
                builder3b.setTitle("Delete confirmation")
                        .setMessage("Are you sure that you want to delete " + naMe3 + " ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WDbHelper.deleteMed(naMe3);
                                archived_mEd.remove(position);
                                notifyDataSetChanged();
                                Toast.makeText(mContext3, naMe3 + " was deleted.", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder3b.create();
                dialog.show();
            }
        });

        return convertView;
    }
}
