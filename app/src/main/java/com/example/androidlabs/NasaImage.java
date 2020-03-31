package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NasaImage extends AppCompatActivity {
    private String imageStr;
    private String imageName;
    private String imageFile;
    private Bitmap imageNasa;
    private String dateStr;

    private EditText latInput;
    private EditText lonInput;
    TextView latitude;
    TextView longitude;
    TextView date;
    ImageView imageView;
    ProgressBar progressBar;
    Button favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_image);

        progressBar = (ProgressBar)findViewById(R.id.loadingProgress);
        progressBar.setVisibility(View.VISIBLE);

        favorites = (Button)findViewById(R.id.favoriteImage);
        favorites.setOnClickListener(click-> {
            Intent gotoImageList = new Intent(NasaImage.this, ImageList.class );
            startActivity(gotoImageList);

        });

        NasaimageQuery req = new NasaimageQuery();
        req.execute("https://api.nasa.gov/planetary/earth/imagery/?lon=100&lat=30&date=2014-02-01&api_key=DEMO_KEY#");


    }

    public class NasaimageQuery extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... args) {
            try{

                //create a uv rating URL object of what server to contact:
                URL urlJason = new URL(args[0]);
                HttpsURLConnection jasonConnection = (HttpsURLConnection) urlJason.openConnection();
                InputStream jasonResponse = jasonConnection.getInputStream();

                //JSON reading
                //Build the entire string response
                BufferedReader reader = new BufferedReader(new InputStreamReader(jasonResponse, "UTF-8"),8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while((line = reader.readLine())!=null){
                    sb.append(line + "\n");
                }
                String result = sb.toString();//result is the whole string

                /**
                 * Convert string to Jason
                 */
                JSONObject jObject = new JSONObject(result);

                /**
                 * Get date from .jason
                 */
                dateStr = jObject.getString("date");


                //get the double associate with the value
                imageStr = jObject.getString("url");
                String[] arrOfstr = imageStr.split("=");
                imageName = arrOfstr[arrOfstr.length-1];
                imageFile = imageName + ".png";

                if (fileExistance(imageFile)){
                    FileInputStream fis = null;
                    try {
                        fis = openFileInput(imageFile);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                    imageNasa = BitmapFactory.decodeStream(fis);
                    publishProgress(100);
                    Log.i(imageFile, "Image already exists locally");
                }else{

                    URL urlImage = new URL(imageStr);
                        HttpsURLConnection connection = (HttpsURLConnection) urlImage.openConnection();
                        connection.connect();
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            imageNasa = BitmapFactory.decodeStream(connection.getInputStream());

                            // image fully downloaded
                            publishProgress(100);
                            // save image to local drive
                            FileOutputStream outputStream = openFileOutput(imageFile, Context.MODE_PRIVATE);
                            imageNasa.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        }
                }

            }catch(Exception e){
                Log.e("Error", e.getMessage());

            }
            return "Done";
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();
        }

        @Override
        public void onProgressUpdate(Integer... args){
            progressBar.setProgress(args[0]);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(String fromDoInBackground){
            Log.i("HTTP", fromDoInBackground);

            latitude = (TextView) findViewById(R.id.latitude);
            longitude = (TextView) findViewById(R.id.longitude);
            date = (TextView)findViewById(R.id.imageDate);
            imageView = (ImageView)findViewById(R.id.nasaImage);

            latInput = (EditText)findViewById(R.id.latEdit);
            String lat = latInput.getText().toString();
            lonInput = (EditText)findViewById(R.id.longEdit);
            String lon = lonInput.getText().toString();


            //imageView.setVisibility(bm);
            latitude.setText(getResources().getString(R.string.latitudeText) +" " + lat);
            longitude.setText(getResources().getString(R.string.lonText) + " " + lon);
            date.setText(getResources().getString(R.string.date) + " " + dateStr);

            imageView.setImageBitmap(imageNasa);

           progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
