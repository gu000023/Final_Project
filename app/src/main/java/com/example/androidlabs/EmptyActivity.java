package com.example.androidlabs;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
/*
 *Author: Kaiwen Gu
 *Version:1.0
 */
public class EmptyActivity extends AppCompatActivity{
    /*
    get the bundle created in listview. The bundle will contain all the wanted json data. The bundle will be passed
    to a new fragment and replace the empty layout
     */
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass=getIntent().getExtras();

        NasaFragment frag=new NasaFragment();

        frag.setArguments(dataToPass);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl,frag).commit();
    }
}
