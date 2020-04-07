package com.example.androidlabs;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NasaDB extends AppCompatActivity {
    /**
     * primitive type variables
     */
    private String imageFile;
    public static String dateStr;
    public static String latVal;
    public static String lonVal;

    /**
     * object layout TextView, EditText, Button, ProgressBar,
     */
    TextView latitude;
    TextView longitude;
    TextView date;
    ImageView imageView;
    ProgressBar progressBar;
    Bitmap bm;
    Button bt_searchImage;

    String reqUrl;
    String reqUrl2;

    static SQLiteDatabase db;

    /**
     *  NasaImage
     */
    public static class NasaImage{
        long id;
        String longitude;
        String latitude;
        String date;
        String imageUrl;

        public NasaImage(long id, String longitude, String latitude, String date, String url){
            this.id = id;
            this.longitude = longitude;
            this.latitude = latitude;
            this.date = date;
            this.imageUrl = url;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_db);

        //
        new AlertDialog.Builder(NasaDB.this)
                .setTitle("System Alert")
                .setMessage("Entering NASA DB IMAGE SEARCH, CLICK YES TO PROCEED, NO TO EXIT")

                .setPositiveButton(android.R.string.yes, null)

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        //

        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        /**
         * manipulate input from EditText latitude and longitude values into TextView
         */
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        date = (TextView) findViewById(R.id.imageDate);
        imageView = (ImageView) findViewById(R.id.nasaImage);

        //bt_searchImage = EnterGeoInfo.search;
        /**
         * listens for Nasa Earth Image button being clicked
         */
        //bt_searchImage.setOnClickListener(click-> {
            Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show();
            //latInput = (EditText) findViewById(R.id.latEdit);
        if(EnterGeoInfo.latString!=null&&EnterGeoInfo.latString!="") {
            latVal = EnterGeoInfo.latString;
        }
            //lonInput = (EditText) findViewById(R.id.longEdit);
        if(EnterGeoInfo.lonString!=null&&EnterGeoInfo.lonString!="") {
            lonVal = EnterGeoInfo.lonString;
        }
            /**
             * initiate and execute the AsyncTask class
             */
            NasaImageQuery req = new NasaImageQuery();
            /**
             * populate the jason url with changed longitude and latitude
             */
            //String reqUrl = "https://api.nasa.gov/planetary/earth/imagery/?lon="+lonVal+"&lat="+latVal+"&date=2014-02-01&api_key=DEMO_KEY#";
            //String reqUrl="http://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/"+latVal+","+lonVal+"/20?dir=180&ms=200,200&key=Ahmc-zu9S2AqY2k7mJUYLXJjvEykB6U-XTb67vfsv1Wjx4dbdg0ERGAYcWLNnWAh";
            reqUrl="https://dev.virtualearth.net/REST/V1/Imagery/Metadata/Aerial/";
            reqUrl2="?zl=15&o=&key=AnaMEev6ennz3vJWE5kEtBd4v0gd9Dhd3XaI4bB86NCYO3WDTTO4SCoi4vEbcj06";
            req.execute(latVal,lonVal);
        //});


        progressBar = (ProgressBar)findViewById(R.id.loadingProgress);
        progressBar.setVisibility(View.VISIBLE);

        /**
         * listen for Favorites button being clicked
         */
        Button favorites = (Button)findViewById(R.id.favoriteImage);
        favorites.setOnClickListener(click-> {
            Intent gotoImageList = new Intent(NasaDB.this, ListViewForImage.class );
            startActivity(gotoImageList);
        });

        //save to listview
        Button save=(Button) findViewById(R.id.save);
        save.setOnClickListener(click->{
            ContentValues content=new ContentValues();

            content.put(MyOpener.COL_LONGITUDE,lonVal);
            content.put(MyOpener.COL_LATITUDE,latVal);
            content.put(MyOpener.COL_DATE,dateStr);
            content.put(MyOpener.COL_IMGNAME,"null");
            long id=db.insert(MyOpener.TABLE_NAME,null,content);
        });

        //help
        Button help=(Button) findViewById(R.id.help);
        help.setOnClickListener(click->{
            new AlertDialog.Builder(NasaDB.this)
                    .setTitle("Help")
                    .setMessage("To use: type latitude and longitude in the blank input lines given. Hit 'Earth Image' to search, 'Save' to save the data to" +
                            "the listview or 'Favourite' to view saved data")

                    .setPositiveButton(android.R.string.yes, null)

                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    .show();
        });


    }

    protected void onResume(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_db);

        //
        new AlertDialog.Builder(NasaDB.this)
                .setTitle("System Alert")
                .setMessage("Entering NASA DB IMAGE SEARCH, CLICK YES TO PROCEED, NO TO EXIT")

                .setPositiveButton(android.R.string.yes, null)

                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        //

        MyOpener dbOpener = new MyOpener(this);
        db = dbOpener.getWritableDatabase();

        /**
         * manipulate input from EditText latitude and longitude values into TextView
         */
        latitude = (TextView) findViewById(R.id.latitude);
        longitude = (TextView) findViewById(R.id.longitude);
        date = (TextView) findViewById(R.id.imageDate);
        imageView = (ImageView) findViewById(R.id.nasaImage);

        //bt_searchImage = EnterGeoInfo.search;
        /**
         * listens for Nasa Earth Image button being clicked
         */
        //bt_searchImage.setOnClickListener(click-> {
        Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show();
        //latInput = (EditText) findViewById(R.id.latEdit);
        if(EnterGeoInfo.latString!=null&&EnterGeoInfo.latString!="") {
            latVal = EnterGeoInfo.latString;
        }
        //lonInput = (EditText) findViewById(R.id.longEdit);
        if(EnterGeoInfo.lonString!=null&&EnterGeoInfo.lonString!="") {
            lonVal = EnterGeoInfo.lonString;
        }

        //if(EnterGeoInfo.da!=""){
            dateStr=EnterGeoInfo.dateString;
        //}
        /**
         * initiate and execute the AsyncTask class
         */
        NasaImageQuery req = new NasaImageQuery();
        /**
         * populate the jason url with changed longitude and latitude
         */
        //String reqUrl = "https://api.nasa.gov/planetary/earth/imagery/?lon="+lonVal+"&lat="+latVal+"&date=2014-02-01&api_key=DEMO_KEY#";
        //String reqUrl="http://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/"+latVal+","+lonVal+"/20?dir=180&ms=200,200&key=Ahmc-zu9S2AqY2k7mJUYLXJjvEykB6U-XTb67vfsv1Wjx4dbdg0ERGAYcWLNnWAh";
        reqUrl="https://dev.virtualearth.net/REST/V1/Imagery/Metadata/Aerial/";
        reqUrl2="?zl=15&o=&key=AnaMEev6ennz3vJWE5kEtBd4v0gd9Dhd3XaI4bB86NCYO3WDTTO4SCoi4vEbcj06";
        req.execute(latVal,lonVal);
        //});


        progressBar = (ProgressBar)findViewById(R.id.loadingProgress);
        progressBar.setVisibility(View.VISIBLE);

        /**
         * listen for Favorites button being clicked
         */
        Button favorites = (Button)findViewById(R.id.favoriteImage);
        favorites.setOnClickListener(click-> {
            Intent gotoImageList = new Intent(NasaDB.this, ListViewForImage.class );
            startActivity(gotoImageList);
        });

        //save to listview
        Button save=(Button) findViewById(R.id.save);
        save.setOnClickListener(click->{
            ContentValues content=new ContentValues();

            content.put(MyOpener.COL_LONGITUDE,lonVal);
            content.put(MyOpener.COL_LATITUDE,latVal);
            content.put(MyOpener.COL_DATE,dateStr);
            content.put(MyOpener.COL_IMGNAME,"null");
            long id=db.insert(MyOpener.TABLE_NAME,null,content);
        });

        //help
        Button help=(Button) findViewById(R.id.help);
        help.setOnClickListener(click->{
            new AlertDialog.Builder(NasaDB.this)
                    .setTitle("Help")
                    .setMessage("To use: type latitude and longitude in the blank input lines given. Hit 'Earth Image' to search, 'Save' to save the data to" +
                            "the listview or 'Favourite' to view saved data")

                    .setPositiveButton(android.R.string.yes, null)

                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })

                    .show();
        });


    }


    public class NasaImageQuery extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... args) {
            try {
                /**
                 * create a nasa Image Jason URL object of what server to contact:
                 */
                URL urlJason = new URL(reqUrl+latVal+","+lonVal+reqUrl2);
                HttpURLConnection jasonConnection = (HttpURLConnection) urlJason.openConnection();
                InputStream jasonResponse = jasonConnection.getInputStream();
                publishProgress(0);

                /**
                 * Build the entire string response
                 */
                BufferedReader reader = new BufferedReader(new InputStreamReader(jasonResponse, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();
                publishProgress(25);

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();//result is the whole string
                publishProgress(45);

                JSONObject jObject = new JSONObject(result);
                JSONArray resourceSets = jObject.getJSONArray("resourceSets");
                JSONObject firstResourceSet = resourceSets.getJSONObject(0);
                JSONArray resources = firstResourceSet.getJSONArray("resources");
                JSONObject firstResource = resources.getJSONObject(0);
                publishProgress(60);

                dateStr = firstResource.getString("vintageEnd");
                URL imageUrl=new URL(firstResource.getString("imageUrl"));
                publishProgress(80);
                    HttpURLConnection connection = (HttpURLConnection) imageUrl.openConnection();
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        bm = BitmapFactory.decodeStream(connection.getInputStream());
                    }
                publishProgress(100);
                    return bm;

            } catch (Exception e) {
                Log.e("Error", e.getMessage());

                // TODO: snackbar....

                Snackbar.make(bt_searchImage, "Image not recorded",
                        Snackbar.LENGTH_LONG).show();
                return null;
            }

        }

        public boolean fileExistance(String fname) {
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        public void onProgressUpdate(Integer... args) {
            progressBar.setProgress(args[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        public void onPostExecute(Bitmap bm) {
            //Log.i("HTTP", fromDoInBackground);

            //imageView.setVisibility(bm);
            latitude.setText(getResources().getString(R.string.latitudeText) + " " + latVal);
            longitude.setText(getResources().getString(R.string.lonText) + " " + lonVal);
            date.setText(getResources().getString(R.string.date) + " " + dateStr);
            if(bm!=null) {
                imageView.setImageBitmap(bm);
            }

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
