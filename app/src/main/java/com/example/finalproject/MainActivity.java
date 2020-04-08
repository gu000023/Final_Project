package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button goToTheGuardianButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToTheGuardianButton = (Button)findViewById(R.id.goToTheGuardianSearch);
        goToTheGuardianButton.setOnClickListener( click -> {
            Intent goToChat = new Intent(MainActivity.this, TheGuardianActivity.class);
            startActivity(goToChat);
        });

        Button bt_NasaEarth = (Button)findViewById(R.id.nasaEarthBtn);
        bt_NasaEarth.setOnClickListener(click-> {
            Intent gotoNasaEarth = new Intent(MainActivity.this, EnterGeoInfo.class);
            startActivity(gotoNasaEarth);

        });


    }
}
