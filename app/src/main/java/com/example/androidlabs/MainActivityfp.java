package com.example.androidlabs;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivityfp extends AppCompatActivity{

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
