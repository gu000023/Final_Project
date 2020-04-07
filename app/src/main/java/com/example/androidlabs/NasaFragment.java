package com.example.androidlabs;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class NasaFragment extends Fragment {

    private Bundle parentActivity;
    private Bitmap image;

    public NasaFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        parentActivity=getArguments();

        View view=inflater.inflate(R.layout.)
        String date=parentActivity.getString("date");
        System.out.print(2);
        return null;
    }
}
