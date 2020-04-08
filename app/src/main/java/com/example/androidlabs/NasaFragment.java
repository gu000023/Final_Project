package com.example.androidlabs;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

/*
Author: Kaiwen Gu
Version: 1.0
 */
public class NasaFragment extends Fragment {

    private AppCompatActivity parentActivity;
    private Bitmap image;

    public NasaFragment(){}

    /*
    This method will set the textview and imageview to display the corresponding information when the user click on a particular
    listview fragment. The layout will be inflated everytime a new fragment is saved to the listview. The image url will be clickable
    and the user can click and go to the web browser to view the satellite image. The related geographical and date info will also
    be fetched and displayed using textview.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        Bundle dataActivity=getArguments();

        View view=inflater.inflate(R.layout.activity_nasa_fragment,container,false);
        TextView lat=(TextView)view.findViewById(R.id.latitude1);
        lat.setText("Latitude: "+dataActivity.getString("lat"));

        TextView lon=(TextView)view.findViewById(R.id.longitude1);
        lon.setText("Lontitude: "+dataActivity.getString("lon"));

        TextView date=(TextView)view.findViewById(R.id.imageDate1);
        date.setText("Date: "+dataActivity.getString("date"));

        ImageView img=(ImageView)view.findViewById(R.id.nasaImage1);
        img.setImageBitmap(NasaDB.bm1);

        TextView url=(TextView)view.findViewById(R.id.imageURL1);
        url.setText("URL: "+dataActivity.getString("url"));

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        parentActivity=(AppCompatActivity) context;
    }
}
