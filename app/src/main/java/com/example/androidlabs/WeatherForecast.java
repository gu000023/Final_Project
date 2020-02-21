package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    TextView tv7;
    TextView tv13;
    TextView tv14;
    TextView tv15;
    ImageView im;
    Bitmap bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);


        tv7=(TextView) findViewById(R.id.textView7);
        tv13=(TextView) findViewById(R.id.textView13);
        tv14=(TextView) findViewById(R.id.textView14);
        tv15=(TextView) findViewById(R.id.textView15);
        im=(ImageView) findViewById(R.id.imageView3);

        ProgressBar pb=(ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.VISIBLE);
        new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
        Log.d("dd","djej");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String UV="";
        String min="";
        String max="";
        String tempNow="";
        Bitmap image=null;
        String iconName="";


        @Override
        protected String doInBackground(String... strings)
        {
            try {

                //create a URL object of what server to contact:
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");
                URL urlUV=new URL("http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                HttpURLConnection urlConnectionUV = (HttpURLConnection) urlUV.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();
                InputStream responseUV = urlConnectionUV.getInputStream();
                //InputStream responseIm = urlConnectionIm.getInputStream();

                //From part 3: slide 19
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                String parameter=null;

                int eventType=xpp.getEventType();

                //uv processing
                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(responseUV, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString(); //result is the whole string


                // convert string to JSON:
                JSONObject uvReport = new JSONObject(result);

                //get the double associated with "value"
                double uvRating = uvReport.getDouble("value");
                UV=String.valueOf(uvRating);
                Log.i("MainActivity", "The uv is now: " + uvRating) ;

                while(eventType!=XmlPullParser.END_DOCUMENT){
                    if(eventType==XmlPullParser.START_TAG){
                        if(xpp.getName().equals("temperature")){
                            tempNow=xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            min=xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            max=xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }

                        if(xpp.getName().equals("weather")){
                           iconName=xpp.getAttributeValue(null,"icon");

                            //test if image already exists
                            if(!fileExistance(iconName + ".png")) {
                                URL urlIm=new URL("http://openweathermap.org/img/w/"+iconName+".png");
                                HttpURLConnection urlConnectionIm = (HttpURLConnection) urlIm.openConnection();
                                //InputStream responseIm = urlConnectionIm.getInputStream();
                                urlConnectionIm.connect();

                                int responseCode = urlConnectionIm.getResponseCode();
                                if (responseCode == 200) {
                                    image = BitmapFactory.decodeStream(urlConnectionIm.getInputStream());
                                    publishProgress(100);
                                    //save to local app storage
                                    FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                            }else{
                                FileInputStream fis = null;
                                try {    fis = openFileInput(iconName + ".png");
                                    Log.i(iconName + ".png", "found locally");}
                                catch (FileNotFoundException e) {    Log.i(iconName + ".png", "need to download"); }
                                bm = BitmapFactory.decodeStream(fis);

                            }

                        }
                    }
                    eventType = xpp.next();
                }

            }
            catch (Exception e)
            {
Log.e("Exception", e.getMessage());
            }



            return "Done";
        }


        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            if(file.exists()){
                return true;
            }
            return false;   }

        protected void onProgressUpdate(Integer... value){
            ProgressBar pb=(ProgressBar) findViewById(R.id.progressBar);
            pb.setVisibility(View.VISIBLE);
            pb.setProgress(0);
        }

        public void onPostExecute(String fromDoInBackground)
        {
            Log.d("dd9","djej");
            Log.i("HTTP", fromDoInBackground);
            tv7.setText(tempNow);
            tv13.setText(min);
            tv14.setText(max);
            tv15.setText(UV);
            im.setImageBitmap(bm);

            ProgressBar pb=(ProgressBar) findViewById(R.id.progressBar);
            pb.setVisibility(View.INVISIBLE);

        }

    }


}
