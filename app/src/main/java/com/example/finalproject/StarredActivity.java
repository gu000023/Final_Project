package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.text.ParseException;

public class StarredActivity extends AppCompatActivity {

    private ListView listView;
    private MyOpener dbHelper;
    private SQLiteDatabase db;
    private Cursor results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starred);

        listView = (ListView)findViewById(R.id.list_starred);
        StarredListAdapter myStarredListAdapter = new StarredListAdapter(this);
        dbHelper = new MyOpener(this);
        db = dbHelper.getWritableDatabase();
        String [] columns = {MyOpener.COL_ID,
                MyOpener.COL_DATE,
                MyOpener.COL_TITLE,
                MyOpener.COL_URL,
                MyOpener.COL_SECTION,
                MyOpener.COL_WEB_ID};
        results = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);
        int dateColIndex = results.getColumnIndex(MyOpener.COL_DATE);
        int titleColIndex = results.getColumnIndex(MyOpener.COL_TITLE);
        int urlColIndex = results.getColumnIndex(MyOpener.COL_URL);
        int sectionColIndex = results.getColumnIndex(MyOpener.COL_SECTION);
        int webIdColIndex = results.getColumnIndex(MyOpener.COL_WEB_ID);

        if (results.getCount() > 0) {
            results.moveToFirst();
            do {
                String date = results.getString(dateColIndex);
                String title = results.getString(titleColIndex);
                String url = results.getString(urlColIndex);
                String section = results.getString(sectionColIndex);
                String webID = results.getString(webIdColIndex);

                myStarredListAdapter.getElements().add(new TheGuardianArticle(date, title, url, section, webID));

            } while(results.moveToNext());
        }

        listView.setAdapter(myStarredListAdapter);

    }
}
