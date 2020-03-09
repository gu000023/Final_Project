package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyActivity extends AppCompatActivity implements DetailsFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        //if(findViewById(R.id.fragmentLocation)==null) {
            FragmentManager fm = getSupportFragmentManager();
            DetailsFragment parent = new DetailsFragment();
            fm.beginTransaction().replace(R.id.fl, parent).commit();
        //}
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
