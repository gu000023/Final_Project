package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //second push

        Button bbc_bt_main = (Button) findViewById(R.id.bbc_bt);
        if(bbc_bt_main!= null) {
            bbc_bt_main.setOnClickListener(click -> {

                Intent goToBbc = new Intent(this, bbc_main.class);
                startActivityForResult(goToBbc, 30);
            });
        }
    }
}
