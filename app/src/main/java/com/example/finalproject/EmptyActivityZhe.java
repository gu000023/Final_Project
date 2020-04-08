package com.example.finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

/**
 * The EmptyActivityZhe class is to instantiate an instance of DetailsFragmentZhe object
 * @author Zhe Lei
 * @version 1.0
 */
public class EmptyActivityZhe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle dataToPass = getIntent().getExtras();
        FragmentManager fm=getSupportFragmentManager();
        DetailsFragmentZhe fragment=new DetailsFragmentZhe();
        fragment.setArguments(dataToPass);
        fm.beginTransaction().replace(R.id.f1,fragment).commit();
    }

}
