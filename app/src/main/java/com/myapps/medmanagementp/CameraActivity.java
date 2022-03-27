package com.myapps.medmanagementp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class CameraActivity extends AppCompatActivity {
    String TAG = "Detector dependencies not loaded yet";
    TextView mTextView;
    FloatingActionButton btnSave, btnCancel;
    TextView btnName, btnProvider, btnInstruction;
    WDbHelper wDbHelper;
    static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity_layout);


        mTextView = findViewById(R.id.text_view);
        ImageView imageView = findViewById(R.id.image_view);
        btnInstruction = findViewById(R.id.btn_instruction);
        btnName = findViewById(R.id.btn_med_name);
        btnProvider = findViewById(R.id.btn_provider);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        FloatingActionButton fab = findViewById(R.id.fab);
        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                alert.setTitle("Scan Instruction");
                alert.setMessage("1. Take a photo of the medication label \n \n " +
                        "2. If the photo rotated 90 degree, please rotate the label 90 degree in an opposite direction and take another photo \n \n" +
                        "3. Select the wanted text in the result box and click 'Copy' \n \n" +
                        "4. Click on 'name', 'provider' or 'instruction' box twice to paste the text \n \n" +
                        "5. Go back to the result box to repeat step 3 & 4 as needed or take another photo" );
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show(); }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (CameraActivity.this, CameraPage.class);
                startActivity(intent);
            }
        });

        SharedPreferences sp = getSharedPreferences("Photo Path", Context.MODE_PRIVATE);
        String picturePath = sp.getString("Photopath", null);
        Log.d("PhotoPath", picturePath);

        Bitmap reportPicture = BitmapFactory.decodeFile(picturePath);
        imageView.setImageBitmap(reportPicture);
        //Create the TextRecognizer
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                Frame imageFrame = new Frame.Builder()
                      .setBitmap(reportPicture) // your image bitmap
                      .build();

        SparseArray <TextBlock> textBlocks = textRecognizer.detect(imageFrame);
        String blocks = "";
        for (int index = 0; index < textBlocks.size(); index++) {
                    //extract scanned text blocks here
                    TextBlock tBlock = textBlocks.valueAt(index);
                    blocks = blocks + tBlock.getValue() + "\n" + "\n";

                    mTextView.setText(blocks);
        }

        btnName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    String pasteData = "";
                    // If it does contain data, decide if you can handle the data.
                    if (!(clipboard.hasPrimaryClip())) {
                    } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                        // since the clipboard has data but it is not plain text
                    } else {
                        //since the clipboard contains plain text.
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        // Gets the clipboard as text.
                        pasteData = item.getText().toString();
                        btnName.setText(pasteData);
                    }
                }
            });

            btnProvider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    String pasteData = "";
                    // If it does contain data, decide if you can handle the data.
                    if (!(clipboard.hasPrimaryClip())) {
                    } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                        // since the clipboard has data but it is not plain text
                    } else {
                        //since the clipboard contains plain text.
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        // Gets the clipboard as text.
                        pasteData = item.getText().toString();
                        btnProvider.setText(pasteData);
                    }
                }
            });

            btnInstruction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    String pasteData = "";
                    // If it does contain data, decide if you can handle the data.
                    if (!(clipboard.hasPrimaryClip())) {
                    } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {
                        // since the clipboard has data but it is not plain text
                    } else {
                        //since the clipboard contains plain text.
                        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                        // Gets the clipboard as text.
                        pasteData = item.getText().toString();
                        btnInstruction.setText(pasteData);
                    }
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wDbHelper = new WDbHelper(CameraActivity.this);

                    String prename = btnName.getText().toString();
                    String name1 = prename.replaceAll(",", "");
                    name = name1.replaceAll("\n", " ");

                    String preinstruction = btnInstruction.getText().toString();
                    String instruction1 = preinstruction.replaceAll(",", "");
                    String instruction = instruction1.replaceAll("\n", " ");

                    String preprovider = btnProvider.getText().toString();
                    String provider1 = preprovider.replaceAll(",", "");
                    String provider = provider1.replaceAll("\n", " ");

                    String dupCheck = "";
                    if (name.length() != 0) {
                        Cursor dataDupSea = wDbHelper.searchForDupMed(name);
                        while (dataDupSea.moveToNext()) {
                            dupCheck = dataDupSea.getString(1);}
                        if (dupCheck.length() != 0) {
                            Toast.makeText(getApplication(),  "This medication already exists. Please enter a " +
                                    "different name (Exp: Ibuprofen #2)!", Toast.LENGTH_LONG).show(); }
                        else {
                            wDbHelper.scanMed(name, "active", instruction, provider);
                            Intent intent = new Intent (CameraActivity.this, AAddMedication.class);
                            startActivity(intent);}}
                    else {
                        Toast.makeText(CameraActivity.this, "Please add a name and a dose", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent (CameraActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

        }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

}