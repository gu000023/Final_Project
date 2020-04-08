package com.example.finalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
/*
Author: Kaiwen Gu
Version: 1.1
 */
public class ListViewForImage extends AppCompatActivity {
    ArrayList<NasaDB.NasaImage> images = new ArrayList<>();
    private android.widget.ListView myList;
    ListAdapter myAdapter;
    SQLiteDatabase db = NasaDB.db;

    //
    String longit;
    String latit;
    String dat;
    String img;
    long jsonid;
    //
    /*
    This method is used to handle response in the listview, including deletion and redirection to a new activity to display wanted
    data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        myList = (android.widget.ListView)findViewById(R.id.imageList);

        loadDataFromDatabase();

        myAdapter = new ListAdapter(images);
        myList.setAdapter(myAdapter);

        Snackbar.make(myList,"You are in your favourite list, choose one to view the content",Snackbar.LENGTH_SHORT).show();
        //listen for items being clicked in the list view
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ListViewForImage.this);
                alertDialogBuilder.setTitle("Confirm deletion?").setMessage("Position: " + position +
                        "\nID: " + id).setPositiveButton("YES",(dialog, which) -> {
                    deleteImage(images.get(position));
                    images.remove(position);
                    myAdapter.notifyDataSetChanged();
                }).setNegativeButton("NO", (dialog, which) -> {});
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                return true;
            }
        });

        myList.setOnItemClickListener((p,v,pos,id)->{
        //Intent gotoImageList = new Intent(ListViewForImage.this, NasaDB.class );
        //startActivity(gotoImageList);
        //finish();
        Bundle passData=new Bundle();
        passData.putString("lat",images.get(pos).latitude);
        passData.putString("lon",images.get(pos).longitude);
        passData.putString("date",images.get(pos).date);
        passData.putString("url",images.get(pos).imageUrl);
        //EnterGeoInfo.latString=images.get(pos).latitude;
        //EnterGeoInfo.lonString=images.get(pos).longitude;
        //NasaDB.dateStr=passData.getString("date");

        Intent intent=new Intent(ListViewForImage.this,EmptyActivity.class);
        intent.putExtras(passData);
        startActivity(intent);
        });
    }
    /*
    This method will create delete query to delete listview from database
     */
    private void deleteImage(NasaDB.NasaImage m){
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + " = ?", new String[]{Long.toString(m.id)});
    }

    /*
    This method will load all saved listview data from database everytime the use enter the listview page. It uses cursor's moveToNext()
    to ensure the full loading of database data.
     */
    private void loadDataFromDatabase(){
        String[] columns={MyOpener.COL_ID,MyOpener.COL_LONGITUDE,MyOpener.COL_LATITUDE,MyOpener.COL_DATE,MyOpener.COL_IMGNAME};
        Cursor cursor=db.query(false,MyOpener.TABLE_NAME,columns,null,null,null,null,null,null);

        int lonI=cursor.getColumnIndex(MyOpener.COL_LONGITUDE);
        int latI=cursor.getColumnIndex(MyOpener.COL_LATITUDE);
        int dateI=cursor.getColumnIndex(MyOpener.COL_DATE);
        int idI=cursor.getColumnIndex(MyOpener.COL_ID);
        int nameI=cursor.getColumnIndex(MyOpener.COL_IMGNAME);

        while(cursor.moveToNext()){
            longit=cursor.getString(lonI);
            latit=cursor.getString(latI);
            dat=cursor.getString(dateI);
            img=cursor.getString(nameI);
            jsonid=cursor.getLong(idI);
            images.add(new NasaDB.NasaImage(jsonid,latit,longit,dat,img));
        }
    }

    /*
    This is an inner class. Its purpose is to retrieve an item and its associated info such as database id. In getView(), the
    desired text will be set to display on each listview row.
     */
    class ListAdapter extends BaseAdapter {
        ArrayList<NasaDB.NasaImage> elements = new ArrayList<>();

        public ListAdapter(ArrayList<NasaDB.NasaImage> elements) {
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
            LayoutInflater inflater = getLayoutInflater();
            View newView = inflater.inflate(R.layout.activity_image_list, parent, false);
            TextView myText = newView.findViewById(R.id.testPage);
            myText.setText("Item " + position + " - (long: " + elements.get(position).longitude + ", lat: " + elements.get(position).latitude + ")");

            //newView.setOnClickListener(click->{
            //    Bundle passData=new Bundle();
            //    passData.putString("lat",elements.get(position).latitude);
            //    passData.putString("lon",elements.get(position).longitude);

            //    Intent intent=new Intent(ListViewForImage.this,NasaDB.class);
            //    intent.putExtras(passData);
            //});
            //newView.setOnItemClickListener((p,v,pos,id)->{
                //Intent gotoImageList = new Intent(ListViewForImage.this, NasaDB.class );
                //startActivity(gotoImageList);
                //finish();
                //Bundle passData=new Bundle();
                //passData.putString("lat",images.get(pos).latitude);
                //passData.putString("lon",images.get(pos).longitude);

                //Intent intent=new Intent(ListViewForImage.this,EnterGeoInfo.class);
                //intent.putExtras(passData);
            //});

            return  newView;
        }
    }
}
