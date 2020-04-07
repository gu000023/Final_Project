package com.example.androidlabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class EmptyActivity extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass=getIntent().getExtras();

        NasaFragment frag=new NasaFragment();

        frag.setArguments(dataToPass);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,frag).commit();
    }
}
