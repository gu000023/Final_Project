package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import static com.example.finalproject.MyOpenerZhe.COL_DATE;
import static com.example.finalproject.MyOpenerZhe.COL_HDURL;
import static com.example.finalproject.MyOpenerZhe.COL_ID;
import static com.example.finalproject.MyOpenerZhe.COL_URL;

/**
 * The ImageListZhe class extends AppCompatActivity, this class is to create a Image list, and save information to the listview. Users can click on each list items and delete items.
 * @author Zhe Lei
 * @version 1.0
 */

public class ImageListZhe extends AppCompatActivity {
    ArrayList<Image> images = new ArrayList<>();
    SQLiteDatabase db= NASAImageInfoZhe.db;
    ListView imageList;
    MyListAdapter adapter;
    public static Bundle dataToPass;
    String dateMess, urlMess, hdUrlMess, titleMess;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        imageList = findViewById(R.id.imagelist);
        loadDataFromDatabase();

        adapter = new MyListAdapter(images);
        imageList.setAdapter(adapter);

        Snackbar.make(imageList, getResources().getString(R.string.snackbar1), Snackbar.LENGTH_LONG).show();

        imageList.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.titleMessage))
                    .setMessage(getResources().getString(R.string.deleteMessage))
                    .setPositiveButton(getResources().getString(R.string.yes), ((dialog, which) -> {
                        deleteImage(images.get(position));
                        images.remove(position);
                        adapter.notifyDataSetChanged();
                    })).setNegativeButton(getResources().getString(R.string.no), ((dialog, which) -> {}));
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        });

        imageList.setOnItemClickListener((list, view, position, id) -> {
            dataToPass = new Bundle();

            dataToPass.putString("date", images.get(position).getDate() );
            dataToPass.putString("url", images.get(position).getUrl());
            dataToPass.putString("hdurl", images.get(position).getHdurl());
            Intent nextActivity = new Intent(ImageListZhe.this, EmptyActivityZhe.class);
            nextActivity.putExtras(dataToPass);
            startActivity(nextActivity);
        });
    }

    /**
     * This method will load data from the database to get date, url, hdurl, id of an Image object
     */
    public void loadDataFromDatabase(){
        String[] columns = {MyOpenerZhe.COL_ID, MyOpenerZhe.COL_DATE , MyOpenerZhe.COL_TITLE, MyOpenerZhe.COL_URL, MyOpenerZhe.COL_HDURL};
        Cursor results = db.query(false, MyOpenerZhe.TABLE_NAME, columns, null, null, null, null, null, null);

        int colIdIndex = results.getColumnIndex(COL_ID);
        int colDateIndex = results.getColumnIndex(COL_DATE);
        int colUrlIndex = results.getColumnIndex(COL_URL);
        int colHdurlIndex = results.getColumnIndex(COL_HDURL);

        while (results.moveToNext()) {
            dateMess = results.getString(colDateIndex);
            urlMess = results.getString(colUrlIndex);
            hdUrlMess = results.getString(colHdurlIndex);
            id = results.getLong(colIdIndex);
            images.add(new Image(id, dateMess, urlMess, hdUrlMess));
            Log.d("Image: ", DatabaseUtils.dumpCurrentRowToString(results));
        }
        printCursor(results, db.getVersion());
    }

    /**
     * This method print out the cursor information in debug mode
     * @param c Cursor
     * @param version int
     */
    public void printCursor(Cursor c, int version) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Log.d("Databse version number", String.valueOf(db.getVersion()));
        Log.d("ColumnNumber ", String.valueOf(c.getColumnCount()));
        Log.d("NumberOfResult ", String.valueOf(c.getCount()));
        for (String name : c.getColumnNames()) {
            Log.d("ColumnName ", name);
        }
    }

    /**
     * This method delete image object from the database
     * @param image Image
     */
    private void deleteImage(Image image){
        db.delete(MyOpenerZhe.TABLE_NAME, COL_ID + " = ?", new String[]{Long.toString(image.id)});
    }

    /**
     * This inner class MyListAdapter extends BaseAdapter, it is to make a view for each item in the listview
     */
    private class MyListAdapter extends BaseAdapter{
        ArrayList<Image> elements = new ArrayList<>();

        public MyListAdapter(ArrayList<Image> elements){
            this.elements = elements;
        }

        @Override
        public int getCount() {
            return elements.size();
        }

        @Override
        public Object getItem(int position) {
            return elements.get(position);
        }

        @Override
        public long getItemId(int position) {
            return elements.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ImageListZhe.this.getLayoutInflater();
            View newView = inflater.inflate(R.layout.activity_image_list, parent, false);
            TextView tv = newView.findViewById(R.id.textView3);
            tv.setText("Image" + position+ " " +elements.get(position).getDate() + " " + elements.get(position).getHdurl());
            return newView;
        }
    }
}
