package com.example.finalproject;
/**
 * Author : Yun Zhu
 */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BbcEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbc_empty);

        Bundle dataToPass = getIntent().getExtras();
        BbcFragment bbcFragment = new BbcFragment();
        bbcFragment.setArguments(dataToPass);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLocation,bbcFragment).commit();
    }
}
