package com.example.androidlabs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDb extends SQLiteOpenHelper {

    protected final static String DATABASE_NAME = "ChatDB";
    protected final static int VERSION_NUM = 1;
    public final static String TABLE_NAME = "CHATS";
    public final static String COL_SEND = "SEND";
    public final static String COL_RECEIVE = "RECEIVE";
    public final static String COL_ID = "_id";

    public CreateDb(Context c){
        super(c, DATABASE_NAME, null, VERSION_NUM);

    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME + "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SEND + " TEXT,"
                + COL_RECEIVE  + " TEXT"+")");
        //System.out.println("hello 123333");//testing
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {   //Drop the old table:
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);

        //Create the new table:
        onCreate(db);
    }

}
