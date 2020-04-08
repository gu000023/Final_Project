package com.example.finalproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.finalproject.MyOpenerZhe.COL_DATE;
import static com.example.finalproject.MyOpenerZhe.COL_HDURL;
import static com.example.finalproject.MyOpenerZhe.COL_ID;
import static com.example.finalproject.MyOpenerZhe.COL_URL;

/**
 * This NASAImageInfoZhe class display the image and related information after user click on search button, user can click help button to see how they can use this applicaion.
 * User can save image to the list, user can also go back to previous page, user can also go to saved list directly from this page
 * @author Zhe Lei
 * @version 1.0
 */
public class NASAImageInfoZhe extends AppCompatActivity {
    public ArrayList<Image> images = new ArrayList<>();
    private MyOpenerZhe op;
    private MyListAdapter adapter;
    static SQLiteDatabase db;
    private Image img;
    ImageView imagePreview;
    ListView imageList;
    TextView datetv;
    TextView hdurltv;
    TextView urltv;
    ProgressBar progressBar;
    String date = "";
    String hdurl = "";
    String url = "";
    static String title = "";

    public static Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasaimage_info);

        imageList = findViewById(R.id.imagelist);

        imagePreview = findViewById(R.id.imagePreview);
        datetv = findViewById(R.id.date);
        hdurltv = findViewById(R.id.hdurl);
        hdurltv.setMovementMethod(LinkMovementMethod.getInstance());
        urltv = findViewById(R.id.url);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        NasaQuery req = new NasaQuery();
        req.execute("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date="+
                NASAImageOfDaySearchZhe.year+"-"+ NASAImageOfDaySearchZhe.month+"-"+ NASAImageOfDaySearchZhe.day);

        Button button1 = findViewById(R.id.btnGoBack);
        Button button2 = findViewById(R.id.buttonList);
        Button button3 = findViewById(R.id.saveImage);
        Button button4 = findViewById(R.id.buttonHelp);

        op = new MyOpenerZhe(this);
        db = op.getWritableDatabase();

        button1.setOnClickListener(click->{
            Intent goBack = new Intent(NASAImageInfoZhe.this, NASAImageOfDaySearchZhe.class);
            startActivity(goBack);

        });

        button2.setOnClickListener(click->{
            Intent goToList = new Intent(NASAImageInfoZhe.this, ImageListZhe.class);
            startActivity(goToList);
        });

        button3.setOnClickListener(click->{
            ContentValues content = new ContentValues();
            content.put(MyOpenerZhe.COL_DATE, date);
            content.put(MyOpenerZhe.COL_URL, url);
            content.put(MyOpenerZhe.COL_HDURL, hdurl);
            long id = db.insert(MyOpenerZhe.TABLE_NAME, null, content);
            Snackbar.make(button3, "Click the button to save the image to list for later viewing", Snackbar.LENGTH_LONG).show();
        });

        button4.setOnClickListener(click -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(NASAImageInfoZhe.this);
            builder.setTitle(getResources().getString(R.string.helpTitle))
                    .setMessage(getResources().getString(R.string.description))
                    .setPositiveButton(getResources().getString(R.string.ok), ((dialog, which) -> {}));
            AlertDialog dialog = builder.create();
            dialog.show();
        });

    }

    /**
     * This NasaQuery class is used to achieve information from API
     */
    public class NasaQuery extends AsyncTask< String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL nasaUrl = new URL("https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" +
                        NASAImageOfDaySearchZhe.year + "-" + NASAImageOfDaySearchZhe.month + "-" + NASAImageOfDaySearchZhe.day);
                HttpURLConnection urlConnection = (HttpURLConnection) nasaUrl.openConnection();
                InputStream response = urlConnection.getInputStream();

                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string

                JSONObject jsonDate = new JSONObject(result);
                date = jsonDate.getString("date");
                Log.i("NasaImageDisplay", "The date is: " + date);

                JSONObject jsonTitle = new JSONObject(result);
                title = jsonTitle.getString("title");

                JSONObject jsonUrl = new JSONObject(result);
                url = jsonUrl.getString("url");
                Log.i("NasaImageDisplay", "The url is: " + url);

                JSONObject jsonHdurl = new JSONObject(result);
                hdurl = jsonHdurl.getString("hdurl");
                Log.i("NasaImageDisplay", "The hdurl is: " + hdurl);

                JSONObject jsonImage = new JSONObject(result);
                URL downloadImage = new URL(jsonImage.getString("url"));
                HttpURLConnection connection = (HttpURLConnection)downloadImage.openConnection();
                connection.setDoInput(true);
                connection.connect();

                InputStream input = connection.getInputStream();
                image = BitmapFactory.decodeStream(input);

            } catch (Exception e) {
                Log.e("Exception", e.getMessage());
            }
            return "Done";
        }


        /**
         * This method is to update the progressBar on the process
         * @param value
         */
        protected void onProgressUpdate(Integer... value){
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        /**
         * This method will execute after retrieve information from APT, and display the image and related information
         * @param fromDoInBackground String
         */
        protected void onPostExecute(String fromDoInBackground){
            imagePreview.setImageBitmap(image);
            datetv.setText("Date: "+ date);
            urltv.setText("Url: "+ url);
            hdurltv.setText("Hdurl: "+hdurl);

            MyListAdapter imageViewAdapter = new MyListAdapter(images);
            imageViewAdapter.setAdapter(imageViewAdapter);

            ContentValues rowValues = new ContentValues();
            //rowValues.put(MyOpenerZhe.API, date);
            long id = db.insert(MyOpenerZhe.TABLE_NAME, null, rowValues);

            img = new Image(id, date, url, hdurl);
            images.add(img);

            progressBar.setVisibility(View.INVISIBLE);

            Log.i("NasaImageDisplay", fromDoInBackground);
        }
    }

    /**
     * This method will delete a column from the database
     * @param list
     * @param position
     */
    public void delete(ArrayList<Image> list, int position) {
        db.delete(MyOpenerZhe.TABLE_NAME, MyOpenerZhe.COL_ID + "= ?", new String[] {Long.toString(list.get(position).getId())});
    }

    /**
     * This method will load data from the database
     */
    public void loadDataFromDatabase(){
        MyOpenerZhe dbOpener = new MyOpenerZhe(this);
        db = dbOpener.getWritableDatabase();

        String[] columns = {COL_ID, COL_DATE, COL_HDURL, COL_URL };
        Cursor results = db.query(false, MyOpenerZhe.TABLE_NAME, columns, null, null, null, null, null, null);

        int colIdIndex = results.getColumnIndex(COL_ID);
        int colDateIndex = results.getColumnIndex(COL_DATE);
        int colUrlIndex = results.getColumnIndex(COL_URL);
        int colHdurlIndex = results.getColumnIndex(COL_HDURL);

        while (results.moveToNext()) {
            String dateMess = results.getString(colDateIndex);
            String urlMess = results.getString(colUrlIndex);
            String hdUrlMess = results.getString(colHdurlIndex);
            long id = results.getLong(colIdIndex);
            images.add(new Image(id, dateMess, urlMess, hdUrlMess));
            Log.d("MESSAGE: ", DatabaseUtils.dumpCurrentRowToString(results));
        }
        printCursor(results, db.getVersion());
    }

    /**
     * This method will print cursor information in debug mode
     * @param c
     * @param version
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
     * This inner class MyListAdapter is to make a view for each item in the listview
     */
    private class MyListAdapter extends BaseAdapter {
        ArrayList<Image> images = new ArrayList<>();

        public MyListAdapter(ArrayList<Image> images){
            this.images = images;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return images.get(position);
        }

        @Override
        public long getItemId(int position) {
            return images.get(position).id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = NASAImageInfoZhe.this.getLayoutInflater();
            View newView = inflater.inflate(R.layout.activity_image_list, parent, false);
            TextView tv = newView.findViewById(R.id.textView3);
            tv.setText(images.get(position).getDate());
            tv.setText(images.get(position).getHdurl());
            return newView;
        }

        public void setAdapter(MyListAdapter imageViewAdapter) {
        }
    }
    
}
