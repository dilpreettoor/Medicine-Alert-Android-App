package com.example.malert.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "reminderDatabase";
    static final String TABLE_NAME = "reminder";
    private static final int DATABASE_Version = 1;
    static final String UID="_id";
    static final String NAME = "Name";
    static final String DESCRIPTION= "Description";
    static final String DATE_AND_TIME= "DateAndTime";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(255) ,"
            + DESCRIPTION + " VARCHAR(225) ," + DATE_AND_TIME + " VARCHAR(225) );";
    private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String TAG = MyDatabaseHelper.class.getSimpleName();
    private Context context;

    public MyDatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
        } catch (Exception e) {
            Log.e(TAG,"error: " + e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.e(TAG,"OnUpgrade");
            db.execSQL(DROP_TABLE);
            onCreate(db);
        }catch (Exception e) {
            Log.e(TAG,"error: " + e);
        }
    }
}
