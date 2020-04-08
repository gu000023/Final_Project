package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private Button goToTheGuardianButton;
    Button button2;


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

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener((click) ->{
            Intent project = new Intent(MainActivity.this, NASAImageOfDaySearchZhe.class);
            startActivityForResult(project, 500);
        });
      
        Button bbc_bt_main = (Button) findViewById(R.id.bbc_bt);
        if(bbc_bt_main!= null) {
            bbc_bt_main.setOnClickListener(click -> {

                Intent goToBbc = new Intent(this, bbc_main.class);
                startActivityForResult(goToBbc, 30);
            });
        }

    }
}
