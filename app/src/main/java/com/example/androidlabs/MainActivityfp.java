package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/*
Author: Kaiwen Gu
Version:1.0
 */
public class MainActivityfp extends AppCompatActivity{

    /*
    The main point of entry for the final project. Four buttons will be used to enter four respective programs. In this case,
    the button corresponding to the Nasa database image search program is obtained and used.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bt_NasaEarth = (Button)findViewById(R.id.nasaEarthBtn);
        bt_NasaEarth.setOnClickListener(click-> {
            Intent gotoNasaEarth = new Intent(MainActivityfp.this, EnterGeoInfo.class );
            startActivity(gotoNasaEarth);

        });

    }
}
