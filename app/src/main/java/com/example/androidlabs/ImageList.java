package com.example.androidlabs;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ImageList extends AppCompatActivity {
    ArrayList<NasaEarthDB.NasaImage> images = new ArrayList<>();
    int positionClicked = 0;
    private ListView myList;
    ListAdapter myAdapter;
    SQLiteDatabase db = NasaEarthDB.db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);

        myList = (ListView)findViewById(R.id.imageList);

        loadDataFromDatabase();

        myAdapter = new ListAdapter(images);
        myList.setAdapter(myAdapter);

        //listen for items being clicked in the list view
        myList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ImageList.this);
                alertDialogBuilder.setTitle("Do you want to delete this?").setMessage("The selected row is: " + position +
                        "\nThe database id is: " + id).setPositiveButton("OK",(dialog, which) -> {
                    deleteImage(images.get(position));
                    images.remove(position);
                    myAdapter.notifyDataSetChanged();
                 //   if (getSupportFragmentManager().findFragmentById(R.id.fragmentLocation)!= null) {
                 //       getSupportFragmentManager().beginTransaction().remove(dFragment).commit();
                 //   }
                }).setNegativeButton("Cancel", (dialog, which) -> {});
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
                return true;
            }
        });
    }

    private void deleteImage(NasaEarthDB.NasaImage m){
        db.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + " = ?", new String[]{Long.toString(m.id)});
    }

    private void loadDataFromDatabase(){

        for(int i = 0; i< 20; i++ ) {
            NasaEarthDB.NasaImage image = new NasaEarthDB.NasaImage(123456, "104", "10", "2010-12-12", "re43214tewqre");
            images.add(image);
        }
    }

    class ListAdapter extends BaseAdapter {
        ArrayList<NasaEarthDB.NasaImage> elements = new ArrayList<>();

        public ListAdapter(ArrayList<NasaEarthDB.NasaImage> elements) {
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
            return  newView;
        }
    }
}
