package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TGNewsEmptyFrameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tg_empty_frame);

        Bundle dataToPass = getIntent().getExtras();

        TGNewsDetailsFragment dFragment = new TGNewsDetailsFragment();
        dFragment.setArguments( dataToPass ); //pass it a bundle for information
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.DetailFrameLayout2, dFragment) //Add the fragment in FrameLayout
                .commit(); //actually load the fragment.

    }
}
