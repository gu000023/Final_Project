package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
Author: Kaiwen Gu
Version:1.0
 */
public class MyOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "NasaImageDB";
    protected final static int VERSION_NUMBER = 2;
    protected final static String TABLE_NAME = "IMAGEINFO";

    protected final static String COL_ID = "_id";
    protected final static String COL_LONGITUDE = "longitude";
    protected final static String COL_LATITUDE = "latitude";
    protected final static String COL_DATE = "date";
    protected final static String COL_IMGNAME = "imageName";


    /*
    Initial constructor to initialize the context using super class constructor.
     */
    public MyOpener(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }

    /*
    This method is used to create the database table to store fetched data from the server for later use in listview.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_LONGITUDE + " text, "
                + COL_LATITUDE + " text, "
                + COL_DATE + " text, "
                + COL_IMGNAME  +  " text);");  // add or remove columns

    }

    /*
    Drop the old table if new version of the table is created and replace the old one with the new one.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //create the new table
        onCreate(db);
    }

    /*
    Drop the old table if new version of the table is created with lower version number and replace the old one with the new one.
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

