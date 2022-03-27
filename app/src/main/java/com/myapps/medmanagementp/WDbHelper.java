package com.myapps.medmanagementp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WDbHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "MedicationManager1.db";
    private static final String TABLE_NAME = "MedicationDetails1";
    private static final String TABLE_NAME1 = "Alarms";
    private static final String KEY_ID = "ID";
    private static final String KEY_MEDICATION = "name";
    private static final String KEY_DOSE = "dose";
    private static final String KEY_INSTRUCTION = "instruction";
    private static final String KEY_START = "start_date";
    private static final String KEY_END = "end_date ";
    private static final String KEY_PROVIDER = "provider ";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIME = "time";
    private static final String KEY_INTTIME = "int_time";
    private static final String KEY_NOTE = "note";
    private static final String KEY_BTNSTATE = "btn_state";
    private static final String KEY_DATE = "date";
    private static final String KEY_REDATE = "redate";

    public WDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MEDICATION + " TEXT," + KEY_DOSE + " TEXT," + KEY_INSTRUCTION + " TEXT," + KEY_NOTE + " TEXT,"
                + KEY_START + " TEXT," + KEY_END + " TEXT," + KEY_PROVIDER + " TEXT," + KEY_STATUS +
                " TEXT)";
        String CREATE_TABLE1 = "CREATE TABLE " + TABLE_NAME1 + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_MEDICATION + " TEXT," + KEY_TIME + " TEXT," + KEY_INTTIME + " INTEGER," +
                KEY_BTNSTATE + " INTEGER," + KEY_DATE + " TEXT," + KEY_REDATE + " INTEGER)";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(db);
    }

    //Table 0
    boolean createMed (String naMe, String statUs) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_MEDICATION, naMe);
        cValues.put(KEY_STATUS, statUs);

        long newRowID = db.insert(TABLE_NAME, null, cValues);
        db.close();
        return false;
    }

    boolean scanMed (String naMe, String statUs, String instructiOn, String providEr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_MEDICATION, naMe);
        cValues.put(KEY_STATUS, statUs);
        cValues.put(KEY_INSTRUCTION, instructiOn);
        cValues.put(KEY_PROVIDER, providEr);

        long newRowID = db.insert(TABLE_NAME, null, cValues);
        db.close();
        return false;
    }

    public Cursor searchForDupMed (String name_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + TABLE_NAME + " where name = ?";
        Cursor dataDupStu = db.rawQuery(query, new String[] {name_kEy});
        return dataDupStu;
    }

    public boolean updateMed(String name_kEy, String doSe, String instructiOn, String noTe, String providEr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_DOSE, doSe);
        cValues.put(KEY_INSTRUCTION, instructiOn);
        cValues.put(KEY_NOTE, noTe);
        cValues.put(KEY_PROVIDER, providEr);

        db.update(TABLE_NAME, cValues, KEY_MEDICATION + " LIKE ? ", new String[] {name_kEy});
        return true;}

    public boolean updateStart(String name_kEy, String start_daTe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_START, start_daTe);

        db.update(TABLE_NAME, cValues, KEY_MEDICATION + " LIKE ? ", new String[] {name_kEy});
        return true;}

    public boolean updateEnd(String name_kEy, String end_daTe){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_END, end_daTe);

        db.update(TABLE_NAME, cValues, KEY_MEDICATION + " LIKE ? ", new String[] {name_kEy});
        return true;}

    public boolean updateStatus(String name_kEy, String statUs){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_STATUS, statUs);

        db.update(TABLE_NAME, cValues, KEY_MEDICATION + " LIKE ? ", new String[] {name_kEy});
        return true;}

    public Cursor getActiveMedicationList () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " where status like '%" + "active'";
        Cursor dataMedList = db.rawQuery(query, null);
        return dataMedList;
    }

    public Cursor getArchivedMedicationList () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " where status like '%" + "archived'";
        Cursor dataMedList = db.rawQuery(query, null);
        return dataMedList;
    }

    public Cursor getMedicationDetails (String name_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME + " where name like '%" + name_kEy + "%'";
        Cursor dataMedDetails = db.rawQuery(query, null);
        return dataMedDetails;
    }

    public Integer deleteMed (String name_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, KEY_MEDICATION + " like ? ", new String[] {name_kEy});
    }

    // Table1
    public boolean insertMedReminder(String naMe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_MEDICATION, naMe);

        long newRowID = db.insert(TABLE_NAME1, null, cValues);
        db.close();
        return false;
    }

    public boolean updateDate(String name_kEy, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_DATE, date);

        db.update(TABLE_NAME1, cValues, KEY_MEDICATION + " like ? ",
                new String[] {name_kEy});
        return true;}

    public boolean insertMedReminder(String naMe, String tiMe, String int_tiMe, String btn_state,
                                     String re_daTe, String daTe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_MEDICATION, naMe);
        cValues.put(KEY_TIME, tiMe);
        cValues.put(KEY_INTTIME, int_tiMe);
        cValues.put(KEY_BTNSTATE, btn_state);
        cValues.put(KEY_REDATE, re_daTe);
        cValues.put(KEY_DATE, daTe);

        long newRowID = db.insert(TABLE_NAME1, null, cValues);
        db.close();
        return true;}

    public boolean updateReminder(String name_kEy, String time_kEy, String btn_state){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_BTNSTATE, btn_state);

        db.update(TABLE_NAME1, cValues, KEY_MEDICATION + " like ? and " + KEY_TIME + " like ? ",
                new String[] {name_kEy, time_kEy});
        return true;}

    public Cursor getAllReminders () {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1;
        Cursor dataAllReminders = db.rawQuery(query, null);
        return dataAllReminders;
    }

    public Cursor getReminders (String name_kEy, String time_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where name like '%" + name_kEy +
                "%' and time like '%" + time_kEy + "%'";
        Cursor dataReminders = db.rawQuery(query, null);
        return dataReminders;
    }

    public Cursor getSpecificReminder (int l, int h) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where int_time between " + l + " and " + h;
        Cursor dataSpecificReminders = db.rawQuery(query, null);
        return dataSpecificReminders;
    }

    public Cursor getExactReminder (String name_kEy, String time_kEy, String redate_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where name like '%" + name_kEy +
                "%' and time like '%" + time_kEy + "%' and redate like '%" + redate_kEy + "%'";
        Cursor dataExactReminder = db.rawQuery(query, null);
        return dataExactReminder;
    }

    public Cursor getRemindersOneMed (String name_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * from " + TABLE_NAME1 + " where name like '%" + name_kEy + "%'";
        Cursor dataReminders = db.rawQuery(query, null);
        return dataReminders;
    }

    public Integer deleteMedReminder (String name_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1, KEY_MEDICATION + " like ? ",
                new String[] {name_kEy});
    }

    public Integer deleteReminder (String name_kEy, String time_kEy) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME1, KEY_MEDICATION + " like ? and " + KEY_TIME + " like ? ",
                new String[] {name_kEy, time_kEy});
    }
}

