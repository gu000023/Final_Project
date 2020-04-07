package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EnterGeoInfo extends AppCompatActivity {
    public static EditText lon;
    public static EditText lat;
    public static String lonString;
    public static String latString;
    public static Button search;
    public static String dateString="";

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
