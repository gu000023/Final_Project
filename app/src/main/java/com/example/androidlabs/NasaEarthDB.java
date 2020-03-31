package com.example.androidlabs;

import android.content.Context;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NasaEarthDB extends AppCompatActivity {
    /**
     * declare global variables
     */
    private String imageStr;
    private String imageName;
    private String imageFile;
    private Bitmap imageNasa;
    private String dateStr;
    private String errMsgStr;
    private String latVal;
    private String lonVal;

    /**
     * declare variables for layout: TextView, EditText, Button, ProgressBar,
     */
    private EditText latInput;
    private EditText lonInput;
    TextView latitude;
    TextView longitude;
    TextView date;
    ImageView imageView;
    ProgressBar progressBar;
    Bitmap bm;
    Button bt_searchImage;

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
        setContentView(R.layout.activity_nasa_earth_db1);

        //try this
        new AlertDialog.Builder(NasaEarthDB.this)
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

        bt_searchImage = (Button)findViewById(R.id.earthImage);
        /**
         * listens for Nasa Earth Image button being clicked
         */
        bt_searchImage.setOnClickListener(click-> {
            Toast.makeText(this, "Fetching data...", Toast.LENGTH_SHORT).show();
            latInput = (EditText) findViewById(R.id.latEdit);
            latVal = latInput.getText().toString();
            lonInput = (EditText) findViewById(R.id.longEdit);
            lonVal = lonInput.getText().toString();

            /**
             * initiate and execute the AsyncTask class
             */
            NasaImageQuery req = new NasaImageQuery();
            /**
             * populate the jason url with changed longitude and latitude
             */
            //String reqUrl = "https://api.nasa.gov/planetary/earth/imagery/?lon="+lonVal+"&lat="+latVal+"&date=2014-02-01&api_key=DEMO_KEY#";
            String reqUrl="http://dev.virtualearth.net/REST/V1/Imagery/Map/Birdseye/"+latVal+","+lonVal+"/20?dir=180&ms=200,200&key=Ahmc-zu9S2AqY2k7mJUYLXJjvEykB6U-XTb67vfsv1Wjx4dbdg0ERGAYcWLNnWAh";
            req.execute(reqUrl);
        });


        progressBar = (ProgressBar)findViewById(R.id.loadingProgress);
        progressBar.setVisibility(View.VISIBLE);

        /**
         * listen for Favorites button being clicked
         */
        Button favorites = (Button)findViewById(R.id.favoriteImage);
        favorites.setOnClickListener(click-> {
            Intent gotoImageList = new Intent(NasaEarthDB.this, ImageList.class );
            startActivity(gotoImageList);
        });

        //help
        Button help=(Button) findViewById(R.id.help);
        help.setOnClickListener(click->{
            new AlertDialog.Builder(NasaEarthDB.this)
                    .setTitle("Help")
                    .setMessage("To use: type latitude and longitude in the blank input lines given. Hit 'Earth Image' to search, 'Save' to save the data to" +
                            "the listview or 'Favourite' to view saved data")

                    .setPositiveButton(android.R.string.yes, null)

                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    //.setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });


    }

    //9.	Each activity must use an AsyncTask to retrieve data from an http server.
    public class NasaImageQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... args) {
            try {
                /**
                 * create a nasa Image Jason URL object of what server to contact:
                 */
                URL urlJason = new URL(args[0]);
                HttpURLConnection jasonConnection = (HttpURLConnection) urlJason.openConnection();
                InputStream jasonResponse = jasonConnection.getInputStream();
                publishProgress(0);
                //JSON reading
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
                /**
                 * Convert string to Jason
                 */
                JSONObject jObject = new JSONObject(result);
                publishProgress(60);
                /**
                 * Get date from .jason
                 */
                dateStr = jObject.getString("date");

                /**
                 * get the string associate with the value
                 */
                imageStr = jObject.getString("url");
                String[] arrOfstr = imageStr.split("=");
                imageName = arrOfstr[1].split("&")[0];
                imageFile = imageName + ".png";

                publishProgress(80);

                if (fileExistance(imageFile)) {
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(imageFile);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    bm = BitmapFactory.decodeStream(fis);

                    publishProgress(100);
                    Log.i(imageFile, "Image already exists locally");
                } else {
                    URL urlImage = new URL(imageStr);
                    HttpURLConnection connection = (HttpURLConnection) urlImage.openConnection();
                    connection.connect();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        imageNasa = BitmapFactory.decodeStream(connection.getInputStream());
                        bm=imageNasa;
                        // image fully downloaded
                        publishProgress(100);
                        // save image to local drive
                        FileOutputStream outputStream = openFileOutput(imageFile, Context.MODE_PRIVATE);
                        imageNasa.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                        outputStream.flush();
                        outputStream.close();
                    }

                }
                publishProgress(90);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());

                // TODO: snackbar....

                Snackbar.make(bt_searchImage, "Image not recorded",
                        Snackbar.LENGTH_LONG).show();
            }
            publishProgress(100);
            return "Done";
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

        @Override
        public void onPostExecute(String fromDoInBackground) {
            Log.i("HTTP", fromDoInBackground);

            //imageView.setVisibility(bm);
            latitude.setText(getResources().getString(R.string.latitudeText) + " " + latVal);
            longitude.setText(getResources().getString(R.string.lonText) + " " + lonVal);
            date.setText(getResources().getString(R.string.date) + " " + dateStr);

            imageView.setImageBitmap(bm);

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
