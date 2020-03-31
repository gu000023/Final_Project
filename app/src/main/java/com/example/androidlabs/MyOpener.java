package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpener extends SQLiteOpenHelper {
    protected final static String DATABASE_NAME = "NasaImageDB";
    protected final static int VERSION_NUMBER = 2;
    protected final static String TABLE_NAME = "IMAGEINFO";

    protected final static String COL_ID = "_id";
    protected final static String COL_LONGITUDE = "longitude";
    protected final static String COL_LATITUDE = "latitude";
    protected final static String COL_DATE = "date";
    protected final static String COL_IMGNAME = "imageName";



    public MyOpener(Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_LONGITUDE + " text, "
                + COL_LATITUDE + " text, "
                + COL_DATE + " text, "
                + COL_IMGNAME  +  " text);");  // add or remove columns

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop the old table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        //create the new table
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}

