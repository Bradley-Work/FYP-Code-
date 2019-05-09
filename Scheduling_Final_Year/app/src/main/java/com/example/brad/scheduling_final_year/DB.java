package com.example.brad.scheduling_final_year;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    //private static final String TAG ="DB";
    private static final String TABLE_NAME = "SchedulingEventDatabase1";
    private static final String COLUMN0 = "DateID";
    private static final String COLUMN1 = "EventName";
    private static final String COLUMN2 = "EventType";
    private static final String COLUMN3 = "EventDescription";
    private static final String COLUMN4 = "Date";
    private static final String COLUMN5 = "Time";

    private static DB databaseInstance = null;

    public DB(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public static DB getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = new DB(context);
        }
        return databaseInstance;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS TABLE_NAME");
        onCreate(sqLiteDatabase);
    }

    //one column THIS WORKS KEEP THIS FOR WHEN IT DOESN'T
    //String createTable = "CREATE TABLE " + TABLE_NAME + " (DateID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN1 +" TEXT)";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN0 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN1 + " TEXT,"
                + COLUMN2  + " TEXT,"
                + COLUMN3  + " TEXT,"
                + COLUMN4 + " TEXT,"
                + COLUMN5  + " TEXT"
                + ");";
        sqLiteDatabase.execSQL(createTable);
    }

    public boolean InsertNewRecord(String EventName, String Date, String Time, String EventType, String EventDescription) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN1, EventName);
        cv.put(COLUMN2, EventType);
        cv.put(COLUMN3, EventDescription);
        cv.put(COLUMN4, Date);
        cv.put(COLUMN5, Time);
        long output = database.insert(TABLE_NAME, null, cv);

        if (output == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean EditRecord(int ID, String EventName, String Date, String Time, String EventType, String EventDescription) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues updated = new ContentValues();
        updated.put(COLUMN1, EventName);
        updated.put(COLUMN2, EventType);
        updated.put(COLUMN3, EventDescription);
        updated.put(COLUMN4, Date);
        updated.put(COLUMN5, Time);
        long output = database.update(TABLE_NAME, updated, COLUMN0 + "=" + ID, null);

        if (output == -1) {
            return false;
        } else {
            return true;
        }
        }

    //KEEP
    public Cursor getMeetings(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE EventType = 'Meeting' ORDER BY Date DESC", null);
        return e;
    }

    public Cursor getAppointments(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE EventType = 'Appointment' ORDER BY Date DESC", null);
        return e;
    }

    public Cursor getDeadlines(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE EventType = 'Deadline' ORDER BY Date DESC", null);
        return e;

    }

    public Cursor getHobbies(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE EventType = 'Hobby' ORDER BY Date DESC", null);
        return e;

    }

    public Cursor getOthers(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE EventType = 'Other' ORDER BY Date DESC", null);
        return e;
    }

    public Cursor getTodaysEvents(){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("SELECT * FROM SchedulingEventDatabase1 WHERE Date = date('now') ORDER BY Date DESC", null);
        return e;
    }

    public Cursor getTime(){
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * FROM " + TABLE_NAME + " ORDER BY "+ COLUMN1 +" DESC";
        Cursor d = database.rawQuery(q,null);
        return d;
    }

    public Cursor getEvent(){
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT Time FROM " + TABLE_NAME;
        Cursor d = database.rawQuery(q, null);
        return d;
    }

    public boolean addTime (String time, String date) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
//        cv.put(COLUMN2, time);
        long output = database.insert(TABLE_NAME, null, cv);

        if (output == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor updateRecord() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor e = database.rawQuery("UPDATE SchedulingEventDatabase1 SET EventName=? AND EventDate=? AND EventType=? AND EventDescription=? AND EventTime=? WHERE ID=? ", null);
        return e;
    }

    public Cursor getID(String eName, String eDesc, String eType, String eTime, String eDate) {
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT  " + COLUMN0 + " FROM " + TABLE_NAME + " WHERE "
                + COLUMN1 +" = '" + eName + "'" + " AND "
                + COLUMN2 +" = '" + eType + "'" + " AND "
                + COLUMN4 +" = '" + eDate + "'" + " AND "
                + COLUMN5 +" = '" + eTime + "'" + " AND "
                + COLUMN3 +" = '" + eDesc + "'";
        Cursor e = database.rawQuery(q, null);
        return e;
    }


    public Cursor deleteRecord(int id, String eName, String eDesc, String eType, String eTime, String eDate) {
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN0 +" = '" + id + "'" + " AND "
                + COLUMN1 +" = '" + eName + "'" + " AND "
                + COLUMN2 +" = '" + eType + "'" + " AND "
                + COLUMN4 +" = '" + eDate + "'" + " AND "
                + COLUMN5 +" = '" + eTime + "'" + " AND "
                + COLUMN3 +" = '" + eDesc + "'";
        Cursor e = database.rawQuery(q, null);
        return e;
    }

    public boolean deleteRecord2(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "DateID=" + id, null) > 0;
    }

    public Cursor getDates() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor c = database.rawQuery("SELECT Date FROM " + TABLE_NAME, null);

        return c;
    }

    public Cursor getDeadlineDates() {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor c = database.rawQuery("SELECT Date FROM " + TABLE_NAME + " WHERE " + COLUMN2 +" ='Deadline'" , null);

        return c;
    }

    public Cursor getCalendarEvent(String eDate) {
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * " + " FROM " + TABLE_NAME + " WHERE "
                + COLUMN4 +" = '" + eDate + "'";
        Cursor e = database.rawQuery(q, null);
        return e;
    }

    public Cursor getAll() {
        SQLiteDatabase database = this.getWritableDatabase();
        String q = "SELECT * " + " FROM " + TABLE_NAME + " ORDER BY "
                + COLUMN4 + " ASC";
        Cursor e = database.rawQuery(q, null);
        return e;
    }
}
