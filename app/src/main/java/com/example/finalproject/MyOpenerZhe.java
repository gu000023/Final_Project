package com.example.finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/**
 * The MyOpenerZhe class implements SQLiteOpenHelper to connect to the database and store the image information from the NASA website to the database.
 * The database name is ImageDB. The table name is NASA_IMAGE_OF_DAY and it has five columns: id, date, title, url and hdurl.
 *
 * @author Zhe Lei
 * @version 1.0
 */

public class MyOpenerZhe extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "ImageDB";
    protected final static int VERSION_NUM = 2;
    public final static String TABLE_NAME = "NASA_IMAGE_OF_DAY";
    public final static String COL_DATE ="DATE";
    public final static String COL_TITLE= "TITLE";
    public final static String COL_URL= "URL";
    public final static String COL_HDURL= "HDURL";
    public final static String COL_ID = "_id";

    public MyOpenerZhe(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * create the table: ImageDB that holds id, date, title, url and hdurl
     * @param db SQLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DATE + " TEXT,"
                + COL_TITLE + " TEXT,"
                + COL_URL + " TEXT,"
                + COL_HDURL  + " TEXT);");
    }
    /**
     * upgrade the version of the database
     * @param db SQLiteDatabase
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    /**
     * downgrade the version of the database
     * @param db SQliteDatabase
     * @param oldVersion old version number
     * @param newVersion new version number
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
