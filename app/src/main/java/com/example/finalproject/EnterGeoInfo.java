package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
/*
Author: Kaiwen Gu
Version:1.1
 */
public class EnterGeoInfo extends AppCompatActivity {
    public static EditText lon;
    public static EditText lat;
    public static String lonString;
    public static String latString;
    public static Button search;
    public static String dateString="";
/*
Obtain the edittext which will accept user input and the button to click to save. A onclicklistener will listen to user click and gather
String info and pass them to new activity
 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_geo_info);

        lon=(EditText) findViewById(R.id.longEdit);
        lat=(EditText) findViewById(R.id.latEdit);
        search=(Button) findViewById(R.id.earthImage);



        search.setOnClickListener(click->{
            lonString=lon.getText().toString();
            latString=lat.getText().toString();
            dateString=dateString;
            Intent goTodb = new Intent(EnterGeoInfo.this, NasaDB.class);
            startActivity(goTodb);
        });
    }
}
