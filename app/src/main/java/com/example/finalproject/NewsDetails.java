package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class NewsDetails extends AppCompatActivity {

    private TextView sectionTextView;
    private TextView dateTextView;
    private TextView titleTextView;
    private TextView urlTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        sectionTextView = (TextView)findViewById(R.id.newsDetails_section);
        dateTextView = (TextView)findViewById(R.id.newsDetails_date);
        titleTextView = (TextView)findViewById(R.id.newsDetails_title);
        urlTextView = (TextView)findViewById(R.id.newsDetails_url);

        Intent fromTheGuardianActivity = getIntent();
        String section = fromTheGuardianActivity.getStringExtra("SECTION");
        section = "Section: " + section;
        String date = fromTheGuardianActivity.getStringExtra("DATE");
        String title = fromTheGuardianActivity.getStringExtra("TITLE");
        String url = fromTheGuardianActivity.getStringExtra("URL");

        sectionTextView.setText(section);
        dateTextView.setText(date);
        titleTextView.setText(title);
        titleTextView.setTextColor(Color.rgb(255, 51, 51));
        urlTextView.setText(url);
        urlTextView.setLinkTextColor(Color.rgb(255, 102, 102));

    }
}
