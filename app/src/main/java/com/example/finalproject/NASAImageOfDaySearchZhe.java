package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The NASAImageOfDaySearchZhe allows the user to pick a date and display the selected date's NASA_IMAGE_OF_DAY on the application.
 * @author Zhe Lei
 * @version 1.0
 */
public class NASAImageOfDaySearchZhe extends AppCompatActivity {
    DatePicker picker;
    Button btnSearch;
    TextView tvw;
    public static int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nasaimage_of_day_search);
        btnSearch = findViewById(R.id.buttonSearch);
        tvw=(TextView)findViewById(R.id.textView2);
        picker=(DatePicker)findViewById(R.id.datePicker1);

        btnSearch.setOnClickListener(click->{
            Toast t = Toast.makeText(this,"You clicked on search button", Toast.LENGTH_SHORT);
            t.show();
            year = picker.getYear();
            month = picker.getMonth() + 1;
            day = picker.getDayOfMonth();
            tvw.setText("Selected Date: "+day+"/"+ month+"/"+year);

            Intent goToInfo = new Intent(NASAImageOfDaySearchZhe.this, NASAImageInfoZhe.class);
            startActivity(goToInfo);
        });
    }
}
