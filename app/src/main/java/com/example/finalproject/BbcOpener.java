package com.example.finalproject;
/**
 * Author: Yun Zhu
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.util.Log;

public class BbcOpener extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "BbcDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "BbcTable";
    public final static String COL_TITLE ="TITLE";
    public final static String COL_INTRO ="INTRO";
    public final static String COL_DATE="DATE";
    public final static String COL_URL="URL";
    public final static String COL_ID = "_ID";

    public BbcOpener(Context ctx){
        super (ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    /**
     * Create table
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ TABLE_NAME +
                "( "+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +COL_TITLE+" TEXT, "
                    +COL_INTRO+" TEXT, "
                    +COL_DATE+" TEXT, "
                    +COL_URL+" TEXT)");

    }

    /**
     * updatet DB version
     * @param db
     * @param oldVersion
     * @param newVersion
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * downgrade DB version
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
