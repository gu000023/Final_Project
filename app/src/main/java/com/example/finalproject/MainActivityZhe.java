package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The MainActivityZhe is the main activity page of the project, it contains 4 buttons link to other activities.
 * @author Zhe Lei
 * @version 1.0
 */
public class MainActivityZhe extends AppCompatActivity {
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener((click) ->{
            Intent project = new Intent(MainActivityZhe.this, NASAImageOfDaySearchZhe.class);
            startActivityForResult(project, 500);
        });
    }
}
