package com.myapps.medmanagementp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class MedListAdapter extends ArrayAdapter<Meds> {
    private Context mContext3;
    int mResource3;
    ArrayList<Meds> med_liSt;
    WDbHelper WDbHelper;
    static String edit_mEd;

    public MedListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Meds> objects) {
        super(context, resource, objects);
        mContext3 = context;
        mResource3 = resource;
        med_liSt = objects;
        WDbHelper = new WDbHelper(mContext3);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //get the person info
        final String naMe3 = getItem(position).getNaMe();
        final String doSe3 = getItem(position).getDoSe();
        final String instructiOn3 = getItem(position).getInstructions();
        final String providEr3 = getItem(position).getProvidEr();

        //create the person object with the injo
        final Meds meds = new Meds(naMe3, doSe3, instructiOn3, providEr3);

        LayoutInflater inflater = LayoutInflater.from(mContext3);
        convertView = inflater.inflate(mResource3, parent, false);

        final TextView medName3 = (TextView) convertView.findViewById(R.id.med_name3);
        TextView instruction3 = (TextView) convertView.findViewById(R.id.instruction3);
        TextView btnDelete3 = (TextView) convertView.findViewById(R.id.btn_delete3);
        TextView btnArchive3 = (TextView) convertView.findViewById(R.id.archive3);
        TextView provider3 = (TextView) convertView.findViewById(R.id.provider_name3);

        StringBuilder nnd = new StringBuilder();
        nnd.append(naMe3);
        nnd.append(" ");
        nnd.append(doSe3);
        medName3.setText(nnd);
        instruction3.setText(instructiOn3);
        provider3.setText(providEr3);

        btnArchive3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3a = new AlertDialog.Builder(mContext3);
                builder3a.setTitle("Archive Confirmation")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                WDbHelper.updateStatus(naMe3, "archived");
                                Toast.makeText(mContext3, naMe3 + " was archived.", Toast.LENGTH_SHORT).show();

                                med_liSt.remove(position);
                                notifyDataSetChanged();

                                Intent intentb = new Intent (mContext3, MainActivity.class);
                                mContext3.startActivity(intentb);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                //Creating dialog box
                AlertDialog dialog  = builder3a.create();
                dialog.show();
        }});

        btnDelete3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder3b = new AlertDialog.Builder(mContext3);
                builder3b.setTitle("Delete confirmation")
                        .setMessage("Are you sure that you want to delete " + naMe3 + " ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WDbHelper.deleteMed(naMe3);
                                med_liSt.remove(position);
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

        medName3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_mEd = naMe3;
                Intent intent = new Intent (mContext3, EditMed.class);
                mContext3.startActivity(intent);
            }
        });

        return convertView;
    }
}


