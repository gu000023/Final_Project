package com.example.androidlabs;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NasaEarthDB1 extends AppCompatActivity {

    ListView lv;
    TextView date;
    TextView lat;
    TextView lon;
    ImageView nasaimg;
    Bitmap bm;

    String latinput = "";
    String loninput = "";
    static String url = "";

    static String imgurl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasa_earth_db1);

        date = (TextView) findViewById(R.id.nasadate);
        lat = (TextView) findViewById(R.id.nasalat);
        lon = (TextView) findViewById(R.id.nasalon);
        nasaimg = (ImageView) findViewById(R.id.nasaimg);

        String latinput = enterlatlon.latinput;
        String loninput = enterlatlon.loninput;

        ProgressBar nasapb = (ProgressBar) findViewById(R.id.nasapb);
        nasapb.setVisibility(View.VISIBLE);
        url = "https://api.nasa.gov/planetary/earth/imagery/?lon=" + loninput + "&lat=" + latinput + "&date=2014-02-01&api_key=8ybAXba9iMQch1BzKRzfamdYNZkIvkxgKc8bICVw";
        new NasaDbQuery().execute(url);
        Log.d("last line of oncreate", "ok");

    }

    private class NasaDbQuery extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            //if(!latinput.isEmpty()&&!loninput.isEmpty()) {
            try {

                URL url = new URL(NasaEarthDB1.url);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                publishProgress(0);
                InputStream response = urlConnection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(response));
                StringBuffer buffer = new StringBuffer();

                String line = "";


                while ((line = reader.readLine()) != null) {
                    publishProgress(25);
                    buffer.append(line + "\n");
                    publishProgress(50);
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                publishProgress(100);
                String ss=buffer.toString();
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //}
            return null;

        }

        protected void onProgressUpdate(Integer... value) {
            ProgressBar pb = (ProgressBar) findViewById(R.id.nasapb);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(0);
        }

        @Override
        protected void onPostExecute(String result) {

            Log.d("postexec", "postexec");
            Snackbar.make(findViewById(R.id.nasadisplay), "Displaying result",
                    Snackbar.LENGTH_SHORT)
                    .setAction("CLOSE WINDOW", new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .show();
            JSONObject obj = null;
            String dat = "";

            try {
                obj = new JSONObject(result);
                dat = obj.getString("date");
                imgurl = obj.getString("url");
            } catch (JSONException e) {
                Log.d("jsonobj", "json obj");
            }

            lv=(ListView) findViewById(R.id.nasalv);

            nasaimg.setImageBitmap(bm);
            date.setText("Date and Time: " + dat);
            lat.setText("Latitude: " + enterlatlon.latinput);
            lon.setText("Longtitude: " + enterlatlon.loninput);

            ProgressBar nasapb = (ProgressBar) findViewById(R.id.nasapb);
            nasapb.setVisibility(View.INVISIBLE);
            //date.setText(result);

        }
    }
}

